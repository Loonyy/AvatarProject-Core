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
package com.avatarproject.core.ability;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.github.abilityapi.Ability;

public abstract class BaseAbility extends Ability implements IBaseAbility {

	/**
	 * Apply damage to an entity
	 * Fires {@link EntityDamageByEntityEvent}
	 * @param attacker LivingEntity causing the damage
	 * @param victim LivingEntity being damaged
	 * @param damage Double amount of damage being dealt to the victim
	 */
	@SuppressWarnings("deprecation")
	public void damage(LivingEntity attacker, LivingEntity victim, double damage) {
		EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(attacker, victim, DamageCause.CUSTOM, damage);
    	if (!event.isCancelled()) {
    		damage = event.getDamage();
    		victim.damage(damage, attacker);
    		victim.setLastDamageCause(event);
    	}
	}
}
