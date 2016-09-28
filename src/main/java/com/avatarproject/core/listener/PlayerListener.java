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
package com.avatarproject.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.avatarproject.core.exception.SlotOutOfBoundsException;
import com.avatarproject.core.player.APCPlayer;
import com.avatarproject.core.storage.UserCache;

public class PlayerListener implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		UserCache.addUserAsync(player);
		new APCPlayer(player);
		
		try {
			APCPlayer.get(player).setAbility(0, "fireblast");
		} catch (SlotOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onQuit(PlayerQuitEvent event) {
		APCPlayer.get(event.getPlayer()).unload();
	}
}
