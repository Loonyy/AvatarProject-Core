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
	private int ticks;
	private boolean charged;
	
	private long expire;

	public FireBlast(Player player, boolean charged) {
		this.player = player;
		this.location = player.getEyeLocation().clone();
		this.damage = 1;
		this.fireTicks = 20;
		this.charged = charged;
		this.expire = 20;
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
		return expire;
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void update() {
		if (!isBlockPassable(location.getBlock())) {
			if (charged) {
				//TODO create fake explosion
			}
			//TODO cancel ability here
			return;
		}
		if (charged) {
			ticks++;
			if (!player.isSneaking() && ticks < 40) {
				//TODO cancel ability here
				return;
			}
			if (player.isSneaking()) {
				location = player.getEyeLocation().clone();
				expire++;
				if (ticks >= 40) {
					playParticleEffect(location.clone().add(location.getDirection().multiply(3)));
				}
				return;
			}
			location.add(location.getDirection().multiply(1));
			playSound(location);
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					for (int z = -1; z < 2; z++) {
						Location loc = location.clone().add(x, y, z);
						playParticleEffect(loc);
						loc.getWorld().getNearbyEntities(loc, 1, 1, 1).stream().filter(entity -> entity instanceof LivingEntity).forEach(entity -> {
							damage(player, (LivingEntity) entity, damage);
							ignite(player, (LivingEntity) entity, fireTicks);
							//TODO cancel ability here
							//TODO create fake explosion
						});
					}
				}
			}
		} else {
			location.add(location.getDirection().multiply(1));
			playSound(location);
			playParticleEffect(location);
			location.getWorld().getNearbyEntities(location, 1, 1, 1).stream().filter(entity -> entity instanceof LivingEntity).forEach(entity -> {
				damage(player, (LivingEntity) entity, damage);
				ignite(player, (LivingEntity) entity, fireTicks);
				//TODO cancel ability here
			});
		}
	}

}
