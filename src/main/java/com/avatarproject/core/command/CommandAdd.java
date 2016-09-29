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
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avatarproject.core.element.Element;
import com.avatarproject.core.element.ElementNoun;
import com.avatarproject.core.lang.Strings;
import com.avatarproject.core.player.APCPlayer;

public class CommandAdd extends APCommand {

	public CommandAdd() {
		super("add", "/avatar add <element> [player]", "Allows you to add an element to yourself or another player.", new String[] { "add" },
				new String[][] {new String[] {"%custom"}, new String[] {"%player"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!correctLength(sender, args.size(), 1, 2)) {
			return;
		}
		Element element = Element.fromString(args.get(0));
		if (element == null) {
			sender.sendMessage(Strings.COMMAND_ERROR_INVALID_ELEMENT.toString(true));
			return;
		}
		if (args.size() == 2) {
			if (sender instanceof Player && !hasPermission(sender, "other")) {
				return;
			}
			OfflinePlayer op = getPlayer(args.get(1));
			if (op == null) {
				sender.sendMessage(Strings.COMMAND_ERROR_INVALID_PLAYER.toString(true));
				return;
			}
			APCPlayer apcp = APCPlayer.get(op);
			if ((apcp.getElements().isEmpty() && element.isSub()) || (element.isSub() && !apcp.hasElement(element.getParent()))) {
				sender.sendMessage(Strings.COMMAND_ADD_OTHER_SUB.toString(true, apcp.getName(), 
						element.getParent().getFancyName(), element.getParent().getContext(ElementNoun.BEND), element.getFancyName(), element.getContext(ElementNoun.BENDING)));
				return;
			}
			apcp.addElement(element);
			apcp.serialize();
			sender.sendMessage(Strings.COMMAND_ADD_OTHER_SENDER.toString(true, op.getName(), element.getFancyName(), element.getContext(ElementNoun.BENDING)));
			if (op.isOnline()) {
				Player p = Bukkit.getPlayer(op.getName());
				p.sendMessage(Strings.COMMAND_ADD_OTHER_PLAYER.toString(true, sender.getName(), element.getFancyName(), element.getContext(ElementNoun.BENDING)));
			}
		} else {
			if (!isPlayer(sender)) {
				return;
			}
			Player player = (Player) sender;
			APCPlayer apcp = APCPlayer.get(player);
			if ((apcp.getElements().isEmpty() && element.isSub()) || (element.isSub() && !apcp.hasElement(element.getParent()))) {
				sender.sendMessage(Strings.COMMAND_ADD_SELF_SUB.toString(true, element.getParent().getFancyName(), 
						element.getParent().getContext(ElementNoun.BEND), element.getFancyName(), element.getContext(ElementNoun.BENDING)));
				return;
			}
			apcp.addElement(element);
			apcp.serialize();
			sender.sendMessage(Strings.COMMAND_ADD_SELF.toString(true, element.getFancyName(), element.getContext(ElementNoun.BEND)));
		}
	}
	
	@Override
	public String[] tabComplete(CommandSender sender, String[] args) {
		if (args.length == 2) {
			List<String> elements = new ArrayList<>();
			Element.getElements().values().stream().forEach(element -> elements.add(element.getId()));
			return elements.toArray(new String[elements.size()]);
		}
		return null;
	}
}
