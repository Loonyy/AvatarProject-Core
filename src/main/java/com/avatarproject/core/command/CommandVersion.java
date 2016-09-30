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

import java.util.List;

import org.bukkit.command.CommandSender;

import com.avatarproject.core.AvatarProjectCore;
import com.avatarproject.core.lang.Strings;

public class CommandVersion extends APCommand {

	public CommandVersion() {
		super("version", "/avatar version", "Display current plugin version.", new String[] { "version", "v" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 0)) {
			return;
		}
		sender.sendMessage(Strings.COMMAND_VERSION_VERSION.toString(true) + AvatarProjectCore.get().getDescription().getVersion());
		sender.sendMessage(Strings.COMMAND_VERSION_URL.toString(true) + "https://github.com/jedk1/AvatarProject-Core");
	}
}
