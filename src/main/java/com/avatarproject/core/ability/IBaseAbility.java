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
import org.bukkit.entity.Player;

import com.darkblade12.particleeffect.ParticleEffect;

public interface IBaseAbility {

	/**
	 * Gets the player using the ability
	 * @return Player using the ability
	 */
	public Player getPlayer();
	
	/**
	 * Gets the location of the ability
	 * @return Location of the ability
	 */
	public Location getLocation();
	
	/**
	 * Gets the {@link ParticleEffect} associated with this ability
	 * @return ParticleEffect associated with the ability
	 */
	public ParticleEffect getParticleEffect();
	
	/**
	 * Plays the ability defined particle effect at a given location
	 * @param location Location to spawn the particle effect at
	 */
	public void playParticleEffect(Location location);
	
	/**
	 * Gets the sound associated with this ability
	 * @return Sound associated with the ability
	 */
	public Sound getSound();
	
	/**
	 * Plays the ability defined sound at a given location
	 * @param location Location to play the sound at
	 */
	public void playSound(Location location);
}
