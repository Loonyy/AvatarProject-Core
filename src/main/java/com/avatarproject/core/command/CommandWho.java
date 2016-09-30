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

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avatarproject.core.ability.BaseAbilityProvider;
import com.avatarproject.core.element.Element;
import com.avatarproject.core.element.ElementNoun;
import com.avatarproject.core.lang.Strings;
import com.avatarproject.core.player.APCPlayer;

public class CommandWho extends APCommand {

	public CommandWho() {
		super("who", "/avatar who [player]", "Display your binds or another player's binds.", new String[] { "who", "w" },
				new String[][] {new String[] {"%player"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) {
			return;
		}
		APCPlayer apcp = null;
		if (args.size() == 0) {
			if (!isPlayer(sender)) {
				return;
			}
			apcp = APCPlayer.get((Player) sender);
		}
		if (args.size() == 1) {
			OfflinePlayer op = getPlayer(args.get(0));
			if (op == null) {
				sender.sendMessage(Strings.COMMAND_ERROR_INVALID_PLAYER.toString(true));
				return;
			}
			apcp = APCPlayer.get(op);
		}
		
		sender.sendMessage(Strings.PLUGIN_PREFIX.toString() + apcp.getName() + ChatColor.GRAY + ":");
		for (Element e : Element.getElements().values()) {
			if (apcp.hasElement(e)) {
				StringBuilder sb = new StringBuilder(e.getColor() + "-");
				if (e.isSub()) {
					sb.append("-");
				}
				sb.append(" " + e.getFancyName() + e.getContext(ElementNoun.BENDER));
				sender.sendMessage(sb.toString());
			}
		}
		sender.sendMessage(Strings.COMMAND_WHO_ABILITIES.toString());
		for (int i : apcp.getAbilities().keySet()) {
			BaseAbilityProvider bap = BaseAbilityProvider.get(apcp.getAbility(i));
			if (bap != null) {
				sender.sendMessage(Strings.COMMAND_WHO_TEMPLATE.toString(String.valueOf(i + 1), bap.getFancyName()));
			}
		}
	}
}