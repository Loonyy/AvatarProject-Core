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
package com.avatarproject.core.ability.fire;

import org.bukkit.entity.Player;

import com.avatarproject.core.ability.FireAbilityProvider;
import com.avatarproject.core.exception.AbilityRegisteredException;
import com.avatarproject.core.player.APCPlayer;
import com.github.abilityapi.Ability;
import com.github.abilityapi.trigger.Actions;
import com.github.abilityapi.trigger.Trigger;
import com.github.abilityapi.trigger.sequence.Sequence;

public class FireBlastProvider extends FireAbilityProvider {

	public FireBlastProvider() throws AbilityRegisteredException {
		super("fireblast", "FireBlast", "Throws a fire. Duh", false, false);
	}

	@Override
	public Ability createInstance(Player player) {
		return new FireBlast(player, false);
	}

	@Override
	public Trigger getTrigger() {
		return () -> Sequence.builder()
				.action(Actions.LEFT_CLICK)
				.condition((player, event) -> APCPlayer.get(player).canBend(this))
				.build();
	}

}
