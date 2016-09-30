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
package com.avatarproject.core.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.avatarproject.core.AvatarProjectCore;
import com.avatarproject.core.ability.BaseAbilityProvider;
import com.avatarproject.core.element.Element;
import com.avatarproject.core.exception.SlotOutOfBoundsException;
import com.avatarproject.core.exception.TooManyBindsException;
import com.avatarproject.core.storage.Serializer;
import com.avatarproject.core.storage.StorageUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

public class APCPlayer extends Serializer {

	private static final HashMap<UUID, APCPlayer> PLAYERS = new HashMap<>();

	/**
	 * Versions:
	 */
	private static int currentDataStorageVersion = 0;

	@SerializedName("version") 
	@Expose @Since(1.0)
	private int dataStorageVersion;

	@SerializedName("uuid") 
	@Expose @Since(1.0)
	private UUID uuid;
	@SerializedName("username") 
	@Expose @Since(1.0)
	private String name;
	@SerializedName("firstlogin") 
	@Expose @Since(1.0)
	private long firstLogin;
	@SerializedName("lastlogin") 
	@Expose @Since(1.0)
	private long lastLogin;
	@SerializedName("element") 
	@Expose @Since(1.0)
	private List<String> element;
	@SerializedName("ability") 
	@Expose @Since(1.0)
	private HashMap<Integer, String> abilities;

	/**
	 * Creates a new APCPlayer for a player
	 * If an APCPlayer already exists, it will update the player data
	 * @param player Player to create/update the data for
	 */
	public APCPlayer(OfflinePlayer player) {
		super(StorageUtil.getJSONFile("players", player.getUniqueId().toString()));
		if (!exists()) {
			setUniqueId(player.getUniqueId());
			setName(player.getName());
			setFirstLogin(System.currentTimeMillis());
			setLastLogin(System.currentTimeMillis());
			setElement(new ArrayList<String>());
			try {
				setAbilities(new HashMap<Integer, String>());
				try {
					for (int i = 0; i < 9; i++) {
						setAbility(i, null);
					}
				} catch (SlotOutOfBoundsException e) {
					e.printStackTrace();
				}
			} catch (TooManyBindsException e) {
				e.printStackTrace();
			}
			setDataStorageVersion(getCurrentDataStorageVersion());
			serialize();
			PLAYERS.put(getUniqueId(), this);
		} else {
			get(player.getUniqueId()).login(player);
		}
	}

	/**
	 * Gets the current version the data is saved as.
	 * @return
	 */
	public int getDataStorageVersion() {
		return dataStorageVersion;
	}

	/**
	 * Sets the version the data should be saved as.
	 * @param dataStorageVersion
	 */
	public void setDataStorageVersion(int dataStorageVersion) {
		this.dataStorageVersion = dataStorageVersion;
	}

	/**
	 * Gets the UUID of the APCPlayer
	 * @return UUID of the Player
	 */
	public UUID getUniqueId() {
		return uuid;
	}

	/**
	 * Sets the UUID of the APCPlayer
	 * Should only be called internally by APCPlayer
	 * @param uuid UUID of the Player
	 */
	private void setUniqueId(UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets the name of the APCPlayer
	 * @return String name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the APCPlayer
	 * Should only be called internally by APCPlayer
	 * @param name String name of the Player
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the first login of the APCPlayer
	 * @return long date of first login
	 */
	public long getFirstLogin() {
		return firstLogin;
	}

	/**
	 * Sets the first login of the APCPlayer
	 * Should only be called internally by APCPlayer
	 * @param firstlogin long date of first login
	 */
	private void setFirstLogin(long firstLogin) {
		this.firstLogin = firstLogin;
	}

	/**
	 * Gets the last login of the APCPlayer
	 * @return long date of last login
	 */
	public long getLastLogin() {
		return lastLogin;
	}

	/**
	 * Sets the first login of the APCPlayer
	 * Should only be called internally by APCPlayer
	 * @param firstlogin long date of first login
	 */
	private void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * Gets a list of all the elements the APCPlayer has
	 * @return List<String> of the player's elements
	 */
	private List<String> getElement() {
		return element;
	}

	/**
	 * Sets the APCPlayer's elements
	 * @param element List<String> of elements you wish the player to have
	 */
	private void setElement(List<String> element) {
		this.element = element;
	}

	/**
	 * Gets the elements the APCPlayer has
	 * @return List<Element> of the elements the APCPlayer has
	 */
	public List<Element> getElements() {
		List<Element> e = new ArrayList<>();
		for (String s : getElement()) {
			e.add(Element.fromString(s));
		}
		return e;
	}

	/**
	 * Sets the elements the APCPlayer has
	 * @param elements List<Element> of the elements you wish the player to have
	 */
	public void setElements(List<Element> elements) {
		List<String> s = new ArrayList<>();
		for (Element e : elements) {
			s.add(e.toString());
		}
		this.element = s;
	}

	/**
	 * Checks if the APCPlayer has the specified element
	 * @param element Element to check if the player has
	 * @return true if the player has the specified element
	 */
	public boolean hasElement(Element element) {
		return getElement().contains(element.toString());
	}
	
	/**
	 * Sets the APCPlayers element to a single element
	 * @param element Element you wish to give t he APCPlayer
	 */
	public void setElement(Element element) {
		getElement().clear();
		addElement(element);
	}

	/**
	 * Adds an element(s) to the APCPlayer
	 * @param elements Element(s) you wish to give the APCPlayer
	 */
	public void addElement(Element...elements) {
		for (Element e : elements) {
			if (hasElement(e)) {
				continue;
			}
			getElement().add(e.toString());
		}
	}

	/**
	 * Removes an element(s) from the APCPlayer
	 * @param elements Element(s) you wish to remove from the APCPlayer
	 */
	public void removeElement(Element...elements) {
		for (Element e : elements) {
			getElement().remove(e.toString());
		}
	}

	/**
	 * Gets the APCPlayer's bound abilities
	 * @return HashMap<Integer, String> of the player's bound abilities
	 */
	public HashMap<Integer, String> getAbilities() {
		return abilities;
	}

	/**
	 * Sets the APCPlayer's bound abilities
	 * A maximum of 9 abilities can be bound at once
	 * @param ability HashMap<Integer, String> of the player's bound abilities
	 * @throws TooManyBindsException Thrown if ability size is greater than 9
	 */
	public void setAbilities(HashMap<Integer, String> abilities) throws TooManyBindsException {
		if (abilities.size() > 9) {
			throw new TooManyBindsException("Error when trying to bind more than 9 abilities to APCPlayer " + getUniqueId() + "!");
		}
		this.abilities = abilities;
	}

	/**
	 * Sets the ability on a specific slot
	 * @param slot Slot to bind the ability to
	 * @param ability Ability to be bound
	 * @throws SlotOutOfBoundsException Thrown if the slot is not  within 0-8
	 */
	public void setAbility(int slot, String ability) throws SlotOutOfBoundsException {
		if (slot < 0 || slot > 8) {
			throw new SlotOutOfBoundsException("Error when trying to bind ability to slot " + slot + "!");
		}
		if (ability != null && !BaseAbilityProvider.isValidAbility(ability)) {
			return;
		}
		getAbilities().put(slot, ability);
	}
	
	/**
	 * Gets the ability bound to a specific slot
	 * @param slot Slot to get the bound ability on
	 * @return String id of the bound ability
	 */
	public String getAbility(int slot) {
		return getAbilities().get(slot);
	}
	
	/**
	 * Clears all the APCPlayer's bound abilities
	 */
	public void clearAbilities() {
		try {
			for (int i = 0; i < 9; i++) {
				setAbility(i, null);
			}
		} catch (SlotOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the APCPlayer can bind a specified ability
	 * @param ability BaseAbilityProvider to check if the player can bind
	 * @return Boolean true if the player can bind that ability
	 */
	public boolean canBind(BaseAbilityProvider ability) {
		if (!getElements().contains(ability.getElement())) {
			return false;
		}
		Player player = getBukkitPlayer();
		if (player == null || !player.hasPermission("avatar.ability." + ability.getElement().getId() + "." + ability.getId())) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the APCPlayer can a specified ability
	 * @param ability
	 * @return
	 */
	public boolean canBend(BaseAbilityProvider ability) {
		Player player = getBukkitPlayer();
		if (player != null && !getAbility(player.getInventory().getHeldItemSlot()).equals(ability.getId())) {
			return false;
		}
		if (getCooldown(ability.getId()) > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Adds a cool-down to a specific ability
	 * @param ability String id of the ability
	 * @param cooldown Long cool-down duration to be added (in m/s)
	 */
	public void addCooldown(String ability, long cooldown) {
		HashMap<String, Long> cooldowns = new HashMap<>();
		if (AvatarProjectCore.get().getCooldownService().getCooldowns().containsKey(getUniqueId())) {
			cooldowns = AvatarProjectCore.get().getCooldownService().getCooldowns().get(getUniqueId());
		}
		cooldowns.put(ability, System.currentTimeMillis() + cooldown);
		AvatarProjectCore.get().getCooldownService().getCooldowns().put(getUniqueId(), cooldowns);
	}
	
	/**
	 * Removes a cool-down from a specific ability
	 * @param ability String id of the ability
	 */
	public void removeCooldown(String ability) {
		if (AvatarProjectCore.get().getCooldownService().getCooldowns().containsKey(getUniqueId())) {
			HashMap<String, Long> cooldowns = AvatarProjectCore.get().getCooldownService().getCooldowns().get(getUniqueId());
			cooldowns.remove(ability);
			AvatarProjectCore.get().getCooldownService().getCooldowns().put(getUniqueId(), cooldowns);
		}
	}
	
	/**
	 * Gets the cool-down of a specific ability
	 * @param ability String id of the ability
	 * @return Long time of when the cool-down should end
	 */
	public long getCooldown(String ability) {
		if (AvatarProjectCore.get().getCooldownService().getCooldowns().containsKey(getUniqueId())) {
			HashMap<String, Long> cooldowns = AvatarProjectCore.get().getCooldownService().getCooldowns().get(getUniqueId());
			if (cooldowns.containsKey(ability)) {
				return cooldowns.get(ability);
			}
		}
		return 0;
	}

	/**
	 * Returns the player from the UUID
	 * @return Player
	 */
	public Player getBukkitPlayer() {
		return Bukkit.getPlayer(getUniqueId());
	}

	/**
	 * Update lastLogin and username of player when they join the server
	 * @param player Player to update
	 */
	public void login(OfflinePlayer player) {
		setName(player.getName());
		setLastLogin(System.currentTimeMillis());
		serialize();
	}

	/**
	 * Unloads an APCPlayer from memory and serializes the data
	 */
	public void unload() {
		PLAYERS.remove(getUniqueId());
		serialize();
	}

	/**
	 * Update the way the file is structured.
	 */
	public void updateDataStorageFormat() {
		if (getDataStorageVersion() == 0) {
			//Do nothing
			//This will serve as a way to update the file data in future
		}
	}

	/**
	 * Gets a APCPlayer instance from OfflinePlayer
	 * @param player OfflinePlayer to get instance of
	 * @return APCPlayer if player exists
	 */
	public static APCPlayer get(OfflinePlayer player) {
		return get(player.getUniqueId());
	}

	/**
	 * Gets a APCPlayer instance from UUID
	 * Will automatically unload the player if the player is not online
	 * after 60 seconds
	 * @param uuid UUID of player to get instance of
	 * @return APCPlayer if player exists
	 */
	public static APCPlayer get(UUID uuid) {
		if (!PLAYERS.containsKey(uuid)) {
			APCPlayer apcp = (APCPlayer) Serializer.get(APCPlayer.class, StorageUtil.getJSONFile("players", uuid.toString()));
			if (apcp == null) {
				return null;
			}
			apcp.updateDataStorageFormat();
			PLAYERS.put(uuid, apcp);
			new BukkitRunnable() {
				@Override
				public void run() {
					if (Bukkit.getPlayer(apcp.getUniqueId()) == null) {
						apcp.unload();
					}
				}
			}.runTaskLaterAsynchronously(AvatarProjectCore.get(), 60*20l);
		}
		return PLAYERS.get(uuid);
	}
	
	/**
	 * Unloads the all the loaded APCPlayers and serializes them
	 */
	public static void unloadAll() {
		for (APCPlayer apcp : PLAYERS.values()) {
			apcp.unload();
		}
		PLAYERS.clear();
	}

	/**
	 * Get the current data storage version
	 * @return Integer of the current data storage version
	 */
	public static int getCurrentDataStorageVersion() {
		return currentDataStorageVersion;
	}
}
