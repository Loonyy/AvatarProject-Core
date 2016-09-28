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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avatarproject.core.lang.Strings;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class HelpCommand extends APCommand {
	
	public HelpCommand() {
		super("help", "/avatar help [page|command]", "Shows all the available commands, and indepth help on each command.", new String[] { "help", "h" },
				new String[][] {new String[] {"%custom"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isPlayer(sender) || !hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) {
			return;
		}
		
		Player player = (Player) sender;
		List<String> order = new ArrayList<String>();
		HashMap<String, TextComponent> components = new HashMap<String, TextComponent>();
		for (APCommand command : instances.values()) {
			if (hasPermissionHelp(sender, command.getName())) {
				TextComponent tc = new TextComponent(ChatColor.GRAY + command.getProperUse());
				String tooltip = WordUtils.wrap(ChatColor.GRAY + command.getDescription(), 40, "\n" + ChatColor.GRAY, false);
				tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(tooltip).create()));
				tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command.getProperUse()));
				order.add(command.getName());
				components.put(command.getName(), tc);
			}
		}
		order.remove(APCommand.instances.get("help").getName());
		Collections.sort(order);
		Collections.reverse(order);
		order.add(APCommand.instances.get("help").getName());
		Collections.reverse(order);
		
 		List<TextComponent> neworder = new ArrayList<TextComponent>();
		for (String s : order) {
			neworder.add(components.get(s));
		}
		
		if (args.size() == 0 || isNumeric(args.get(0))) {
			int page = 0;
			if (args.size() > 0 && isNumeric(args.get(0))) {
				page = Integer.valueOf(args.get(0)) - 1;
			}
			for (TextComponent tc : getPage(Strings.COMMAND_HELP_TITLE.toString(), page, neworder)) {
				player.spigot().sendMessage(tc);
			}
			return;
		}
		
		String arg = args.get(0);
		if (instances.keySet().contains(arg.toLowerCase())) {
			instances.get(arg).help(sender, true);
		} else {
			sender.sendMessage("Invalid command!");
		}
	}
	
	private boolean hasPermissionHelp(CommandSender sender, String permission) {
		return sender.hasPermission("avatar.command." + permission);
	}
	
	@Override
	public String[] tabComplete(CommandSender sender, String[] args) {
		if (args.length == 2) {
			List<String> cmds = new ArrayList<>();
			instances.values().stream().filter(command -> hasPermission(sender, command.getName())).forEach(command -> cmds.add(command.getName()));
			return cmds.toArray(new String[cmds.size()]);
		}
		return null;
	}
}
