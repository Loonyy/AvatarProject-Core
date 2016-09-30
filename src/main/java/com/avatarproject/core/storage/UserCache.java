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
package com.avatarproject.core.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.avatarproject.core.AvatarProjectCore;

public class UserCache {

	private static File usercache;

	public UserCache() {
		usercache = new File(AvatarProjectCore.get().getDataFolder() + File.separator + "usercache.json");
		create(usercache);
	}

	/**
	 * Adds a player into the custom UserCache.
	 * @param player Player to add to the cache.
	 */
	@SuppressWarnings("unchecked")
	public static void addUser(Player player) {
		String name = player.getName();
		UUID uuid = player.getUniqueId();
		JSONArray array = getUserCache();
		try {
			for(int n = 0; n < array.size(); n++) { //Loop through all the objects in the array.
				JSONObject object = (JSONObject) array.get(n);
				if (object.get("id").equals(uuid.toString())) { //Check if the player's UUID exists in the cache.
					if (String.valueOf(object.get("name")).equalsIgnoreCase(name)) {
						return;
					} else {
						object.put("name", name); //Update the user.
						FileWriter fileOut = new FileWriter(usercache);
						fileOut.write(array.toJSONString()); //Write the JSON array to the file.
						fileOut.close();
						return;
					}
				}
			}
			JSONObject newEntry = new JSONObject();
			newEntry.put("id", uuid.toString());
			newEntry.put("name", name);
			array.add(newEntry); //Add a new player into the cache.
			FileWriter fileOut = new FileWriter(usercache);
			fileOut.write(array.toJSONString()); //Write the JSON array to the file.
			fileOut.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a user asynchronously {@link UserCache#addUser(Player)}
	 * @param player Player to be added to the cache.
	 */
	public static void addUserAsync(final Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				addUser(player);
			}
		}.runTaskAsynchronously(AvatarProjectCore.get());
	}
	
	/**
	 * Gets a UUID of a user from the custom UserCache.
	 * @param playername String name of the player to get.
	 * @return UUID of the player.
	 */
	public static UUID getUser(String playername) {
		JSONArray array = getUserCache();
		try {
			for(int n = 0; n < array.size(); n++) { //Loop through all the objects in the array.
				JSONObject object = (JSONObject) array.get(n);
				if (String.valueOf(object.get("name")).equalsIgnoreCase(playername)) {
					return UUID.fromString(String.valueOf(object.get("id")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File getUserCacheFile() {
		return usercache;
	}

	private static JSONArray getUserCache() {
		BufferedReader reader = null;
		JSONParser parser = new JSONParser();
		JSONArray array = new JSONArray(); //Create new JSON array.
		try {
			reader = new BufferedReader(new FileReader(usercache));
			if (reader.readLine() != null) { //Check if file is empty.
				Object obj = parser.parse(new FileReader(usercache));
				array = (JSONArray) obj;
			}
			reader.close(); //Close the reader.
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	private static void create(File file) {
		if (!file.getParentFile().exists()) {
			try {
				file.getParentFile().mkdirs();
			} catch (Exception e) {
				AvatarProjectCore.get().getLogger().info("Failed to generate directory!");
				e.printStackTrace();
			}
		}

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				AvatarProjectCore.get().getLogger().info("Failed to generate " + file.getName() + "!");
				e.printStackTrace();
			}
		}
	}
}