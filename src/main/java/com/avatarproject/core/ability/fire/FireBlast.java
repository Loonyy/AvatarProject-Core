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

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.avatarproject.core.ability.FireAbility;

public class FireBlast extends FireAbility {

	private Player player;
	private Location location;
	private double damage;
	private int fireTicks;
	
	public FireBlast(Player player) {
		this.player = player;
		this.location = player.getEyeLocation().clone();
		this.damage = 1;
		this.fireTicks = 20;
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public Location getLocation() {
		return location;
	}
	
	@Override
	public long getExpireTicks() {
		return 20;
	}

	@Override
    public void start() {
		
    }
	
    @Override
    public void stop() {
    	
    }

	@Override
    public void update() {
        location.add(location.getDirection().multiply(1));
        playSound(location);
        playParticleEffect(location);
        location.getWorld().getNearbyEntities(location, 1, 1, 1).stream().filter(entity -> entity instanceof LivingEntity).forEach(entity -> {
        	damage(player, (LivingEntity) entity, damage);
        	ignite((LivingEntity) entity, fireTicks);
        });
        stop();
    }

}
