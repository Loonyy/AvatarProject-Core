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
import org.bukkit.entity.Player;

import com.avatarproject.core.exception.SlotOutOfBoundsException;
import com.avatarproject.core.lang.Strings;
import com.avatarproject.core.player.APCPlayer;

public class CommandClear extends APCommand {

	public CommandClear() {
		super("clear", "/avatar clear [slot]", "Clears all your bound abilities, or a single slot if provided.", new String[] { "clear", "cl" },
				new String[][] {new String[] {"%custom"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isPlayer(sender) || !hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) {
			return;
		}
		Player player = (Player) sender;
		APCPlayer apcp = APCPlayer.get(player);
		if (args.size() == 1) {
			if (!isNumeric(args.get(0))) {
				player.sendMessage(Strings.COMMAND_ERROR_SLOT_BOUNDS.toString(true));
				return;
			}
			int slot = Integer.valueOf(args.get(0)) - 1;
			if (slot < 0 || slot > 8) {
				player.sendMessage(Strings.COMMAND_ERROR_SLOT_BOUNDS.toString(true));
				return;
			}
			try {
				apcp.setAbility(slot, null);
				player.sendMessage(Strings.COMMAND_CLEAR_SINGLE.toString(true, String.valueOf(slot + 1)));
			} catch (SlotOutOfBoundsException e) {
				e.printStackTrace();
			}
		} else {
			apcp.clearAbilities();
			player.sendMessage(Strings.COMMAND_CLEAR_ALL.toString(true));
		}
		apcp.serialize();
	}
	
	@Override
	public String[] tabComplete(CommandSender sender, String[] args) {
		if (args.length == 2) {
			return new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		}
		return null;
	}
}
