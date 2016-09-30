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
package com.avatarproject.core.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.abilityapi.Service;

public class CooldownService implements Service {

	private final JavaPlugin plugin;
	private final HashMap<UUID, HashMap<String, Long>> cooldowns = new HashMap<>();
	
	public CooldownService(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void start() {
		new BukkitRunnable() {
			@Override
			public void run() {
				Iterator<UUID> it = cooldowns.keySet().iterator();
				while (it.hasNext()) {
					UUID uuid = it.next();
					HashMap<String, Long> playerCooldowns = cooldowns.get(uuid);
					Iterator<String> it2 = playerCooldowns.keySet().iterator();
					while (it2.hasNext()) {
						String ability = it2.next();
						long end = playerCooldowns.get(ability);
						if (System.currentTimeMillis() > end) {
							it2.remove();
						}
					}
					if (playerCooldowns.isEmpty()) {
						it.remove();
					}
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}
	
	@Override
	public void stop() {
		
	}
	
	public HashMap<UUID, HashMap<String, Long>> getCooldowns() {
		return cooldowns;
	}
}
