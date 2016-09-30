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
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.avatarproject.core.ability.BaseAbilityProvider;
import com.avatarproject.core.player.APCPlayer;
import com.github.abilityapi.Service;

public class CooldownService implements Service {

	private final JavaPlugin plugin;
	private final HashMap<UUID, List<CooldownEntry>> cooldowns = new HashMap<>();

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
					List<CooldownEntry> entries = cooldowns.get(uuid);
					Iterator<CooldownEntry> it2 = entries.iterator();
					while (it2.hasNext()) {
						CooldownEntry entry = it2.next();
						Player player = Bukkit.getPlayer(uuid);
						if (entry.getRemainder() <= 0) {
							if (entry.getBar() != null) {
								entry.getBar().removePlayer(player);
							}
							it2.remove();
						} else {
							if (player != null) {
								handleBossBar(player, entry);
							}
						}
					}
					if (entries.isEmpty()) {
						it.remove();
					}
				}
			}
		}.runTaskTimerAsynchronously(plugin, 0, 1);
	}

	@Override
	public void stop() {

	}
	
	/**
	 * Manage the player's BossBar showing the cool-down progress
	 * @param player Player to show the BossBar to
	 * @param entry CooldownEntry cool-down information
	 */
	private void handleBossBar(Player player, CooldownEntry entry) {
		String ability = APCPlayer.get(player).getAbility(player.getInventory().getHeldItemSlot());
		if (ability != null && ability.equals(entry.getAbility())) {
			if (entry.getBar() == null) {
				entry.setBar(Bukkit.createBossBar(BaseAbilityProvider.get(ability).getName(), BarColor.WHITE, BarStyle.SOLID));
			}
			if (!entry.getBar().getPlayers().contains(player)) {
				entry.getBar().addPlayer(player);
			}
			float percent = 100 - ((float) ((entry.getRemainder() * 100f) / entry.getDuration()));
			entry.getBar().setProgress(percent/100);
		} else {
			if (entry.getBar() != null) {
				entry.getBar().removePlayer(player);
			}
		}
	}

	/**
	 * Get all the cool-down instances
	 * @return HashMap<> of the cool-down instances
	 */
	public HashMap<UUID, List<CooldownEntry>> getCooldowns() {
		return cooldowns;
	}
}
