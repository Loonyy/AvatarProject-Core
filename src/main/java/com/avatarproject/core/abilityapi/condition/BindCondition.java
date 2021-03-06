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
package com.avatarproject.core.abilityapi.condition;

import org.bukkit.entity.Player;

import com.avatarproject.core.ability.BaseAbilityProvider;
import com.avatarproject.core.player.APCPlayer;

public class BindCondition {

	/**
	 * Checks if a player has the specified ability bound on the slot they have selected
	 * @param player Player to check
	 * @param baseAbilityProvider Ability to check
	 * @return Boolean true if the player has the ability bound
	 */
	public boolean bind(Player player, BaseAbilityProvider baseAbilityProvider) {
		String ability = APCPlayer.get(player).getAbility(player.getInventory().getHeldItemSlot());
		return ability != null && ability.equals(baseAbilityProvider.getId());
	}
}
