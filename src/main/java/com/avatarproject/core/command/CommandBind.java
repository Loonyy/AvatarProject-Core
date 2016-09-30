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

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avatarproject.core.ability.BaseAbilityProvider;
import com.avatarproject.core.element.Element;
import com.avatarproject.core.exception.SlotOutOfBoundsException;
import com.avatarproject.core.lang.Strings;
import com.avatarproject.core.player.APCPlayer;

public class CommandBind extends APCommand {

	public CommandBind() {
		super("bind", "/avatar bind <ability> [slot]", "Allows you to bind an ability. If no slot is provided, the ability will be bound to your current slot.", new String[] { "bind", "b" },
				new String[][] {new String[] {"%custom"}, new String[] {"%custom"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isPlayer(sender) || !hasPermission(sender) || !correctLength(sender, args.size(), 1, 2)) {
			return;
		}
		Player player = (Player) sender;
		APCPlayer apcp = APCPlayer.get(player);
		BaseAbilityProvider bap = BaseAbilityProvider.get(args.get(0).toLowerCase());
		if (bap == null || bap.isHidden()) {
			player.sendMessage(Strings.COMMAND_ERROR_INVALID_ABILITY.toString(true));
			return;
		}
		if (!apcp.canBind(bap)) {
			player.sendMessage(Strings.PLAYER_ERROR_CANT_BIND.toString(true));
			return;
		}
		
		int slot = player.getInventory().getHeldItemSlot();
		if (args.size() == 2) {
			if (isNumeric(args.get(1))) {
				slot = Integer.valueOf(args.get(1)) - 1;
				if (slot < 0 || slot > 8) {
					player.sendMessage(Strings.COMMAND_ERROR_SLOT_BOUNDS.toString(true));
					return;
				}
			}
		}
		try {
			apcp.setAbility(slot, bap.getId());
			player.sendMessage(Strings.COMMAND_BIND_BOUND.toString(true, bap.getFancyName(), String.valueOf(slot + 1)));
		} catch (SlotOutOfBoundsException e) {
			e.printStackTrace();
		}
		apcp.serialize();
	}
	
	@Override
	public String[] tabComplete(CommandSender sender, String[] args) {
		if (args.length == 2) {
			List<String> names = new ArrayList<>();
			List<Element> elements = APCPlayer.get((Player) sender).getElements();
			List<BaseAbilityProvider> abilities = BaseAbilityProvider.getAbilities(elements.toArray(new Element[elements.size()]));
			abilities.stream().filter(ability -> APCPlayer.get((Player) sender).canBind(ability)).forEach(ability -> names.add(ability.getName()));
			return names.toArray(new String[names.size()]);
		}
		if (args.length == 3) {
			return new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		}
		return null;
	}
}
