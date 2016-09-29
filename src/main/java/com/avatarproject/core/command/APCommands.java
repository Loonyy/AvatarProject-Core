/*******************************************************************************
 * AvatarProject Core
 * Copyright (C) 2016  (AvatarProject), (jedk1)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.avatarproject.core.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import com.avatarproject.core.AvatarProjectCore;
import com.avatarproject.core.lang.Strings;

public class APCommands {

	public APCommands() {
		init();
	}

	private void init() {
		PluginCommand horse = AvatarProjectCore.getInstance().getCommand("avatar");

		CommandExecutor exe;

		new CommandHelp();
		
		new CommandAdd();
		new CommandBind();
		new CommandChoose();
		new CommandClear();
		new CommandVersion();
		new CommandWho();

		exe = new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
				try {
					for (int i = 0; i < args.length; i++) {
						args[i] = args[i];
					}

					if (args.length == 0) {
						s.sendMessage(Strings.COMMAND_HELP_TIP.toString(true));
						return true;
					}

					List<String> sendingArgs = Arrays.asList(args).subList(1, args.length);
					for (APCommand command : APCommand.instances.values()) {
						if (Arrays.asList(command.getAliases()).contains(args[0].toLowerCase())) {
							command.execute(s, sendingArgs);
							return true;
						}
					}

					s.sendMessage(Strings.COMMAND_UNKNOWN.toString(true));
				} catch (Exception e) {
					s.sendMessage(Strings.COMMAND_ERROR.toString(true));
					StringBuilder builder = new StringBuilder();
					builder.append(AvatarProjectCore.getInstance().getDescription().getName());
					builder.append(" Command Error:");
					builder.append(" Sender: " + s.getName());
					builder.append(" Command: " + c.getLabel());
					builder.append(" " + Arrays.asList(args).toString().replaceAll("[\\[\\,\\]]", ""));
					AvatarProjectCore.getInstance().getLogger().info(builder.toString());
					e.printStackTrace();
				}
				return true;
			}
		};
		horse.setExecutor(exe);
		horse.setTabCompleter(new APTabCompleter());
	}
}