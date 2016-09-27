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

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import com.darkblade12.particleeffect.ParticleEffect;

public abstract class FireAbility extends BaseAbility {
	
	/**
	 * Gets the {@link ParticleEffect.FLAME} particle
	 * @return ParticleEffect.FLAME
	 */
	@Override
	public ParticleEffect getParticleEffect() {
		return ParticleEffect.FLAME;
	}
	
	/**
	 * Plays the {@link ParticleEffect.FLAME} in a location
	 * @param location Location to play the particle at
	 */
	@Override
	public void playParticleEffect(Location location) {
		//TODO add configuration for amount of particles spawned
		getParticleEffect().display((float) Math.random()/2, (float) Math.random()/2, (float) Math.random()/2, 0f, 5, location, 257d);
	}
	
	/**
	 * Gets the {@link Sound.BLOCK_FIRE_AMBIENT} sound
	 * @return Sound.BLOCK_FIRE_AMBIENT
	 */
	@Override
	public Sound getSound() {
		return Sound.BLOCK_FIRE_AMBIENT;
	}
	
	@Override
	public void playSound(Location location) {
		location.getWorld().playSound(location, getSound(), 1f, 1f);
	}
	
	/**
	 * Ignites a LivingEntity on fire
	 * @param victim LivingEntity to be set on fire
	 * @param ticks Integer ticks duration for entity to be alight
	 */
	public void ignite(LivingEntity victim, int ticks) {
		victim.setFireTicks(ticks);
	}
}
