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

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.github.abilityapi.Ability;

public abstract class BaseAbility extends Ability implements IBaseAbility {
	
	private static Material[] transparent = {
			Material.AIR,
			Material.SAPLING,
			Material.LONG_GRASS,
			Material.YELLOW_FLOWER,
			Material.RED_ROSE,
			Material.BROWN_MUSHROOM,
			Material.RED_MUSHROOM,
			Material.TORCH,
			Material.LADDER,
			Material.SNOW,
			Material.VINE,
			Material.WATER_LILY,
			Material.CARPET,
			Material.DOUBLE_PLANT,
			Material.SIGN_POST,
			Material.WALL_SIGN,
			Material.STANDING_BANNER,
			Material.WALL_BANNER,
			Material.LEVER,
			Material.STONE_PLATE,
			Material.WOOD_PLATE,
			Material.IRON_PLATE,
			Material.GOLD_PLATE,
			Material.REDSTONE_TORCH_OFF,
			Material.REDSTONE_TORCH_ON,
			Material.STONE_BUTTON,
			Material.WOOD_BUTTON,
			Material.RAILS,
			Material.ACTIVATOR_RAIL,
			Material.DETECTOR_RAIL,
			Material.POWERED_RAIL
	};

	/**
	 * Apply damage to an entity
	 * Cancels if the victim is the attacker
	 * Fires {@link EntityDamageByEntityEvent}
	 * @param attacker LivingEntity causing the damage
	 * @param victim LivingEntity being damaged
	 * @param damage Double amount of damage being dealt to the victim
	 */
	@SuppressWarnings("deprecation")
	public void damage(LivingEntity attacker, LivingEntity victim, double damage) {
		if (attacker.getEntityId() == victim.getEntityId()) {
			return;
		}
		EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(attacker, victim, DamageCause.CUSTOM, damage);
    	if (!event.isCancelled()) {
    		damage = event.getDamage();
    		victim.damage(damage, attacker);
    		victim.setLastDamageCause(event);
    	}
	}
	
	/**
	 * Checks if a block can be moved through
	 * Excludes:
	 * <li>Water</li>
	 * <li>Lava</li>
	 * @param block Block to be checked
	 * @return Boolean true if the block can be passed through
	 */
	public boolean isBlockPassable(Block block) {
		return Arrays.asList(transparent).contains(block.getType());
	}
}
