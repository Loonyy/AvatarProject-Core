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
package com.avatarproject.core.block;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

public class TempFallingBlock {

	public static ConcurrentHashMap<FallingBlock, TempFallingBlock> instances = new ConcurrentHashMap<FallingBlock, TempFallingBlock>();
	
	private FallingBlock fallingblock;
	private long creation;
	private boolean expire;
	
	/**
	 * Creates a new temporary falling block
	 * @param location Location to spawn the falling block at
	 * @param material Material of the falling block
	 * @param data Data of the Material of the falling block
	 * @param veloctiy Velocity that should be applied to the falling block
	 */
	public TempFallingBlock(Location location, Material material, byte data, Vector veloctiy) {
		this(location, material, data, veloctiy, false);
	}
	
	/**
	 * Creates a new temporary falling block
	 * @param location Location to spawn the falling block at
	 * @param material Material of the falling block
	 * @param data Data of the Material of the falling block
	 * @param veloctiy Velocity that should be applied to the falling block
	 * @param expire Boolean should the falling block expire (WIP)
	 */
	@SuppressWarnings("deprecation")
	public TempFallingBlock(Location location, Material material, byte data, Vector veloctiy, boolean expire) {
		this.fallingblock = location.getWorld().spawnFallingBlock(location, material, data);
		this.fallingblock.setVelocity(veloctiy);
		this.fallingblock.setDropItem(false);
		this.creation = System.currentTimeMillis();
		this.expire = expire;
		instances.put(fallingblock, this);
	}
	
	/**
	 * Gets a TempFallingBlock instance from a {@link FallingBlock} entity
	 * @param fallingblock FallingBlock entity
	 * @return TempFallingBlock instance if exists
	 */
	public static TempFallingBlock get(FallingBlock fallingblock) {
		if (isTempFallingBlock(fallingblock)) {
			return instances.get(fallingblock);
		}
		return null;
	}
	
	/**
	 * Checks whether a {@link FallingBlock} entity has a TempFallingBlock instance
	 * @param fallingblock FallingBlock entity
	 * @return true if instance exists
	 */
	public static boolean isTempFallingBlock(FallingBlock fallingblock) {
		return instances.containsKey(fallingblock);
	}
	
	/**
	 * Removes a {@link FallingBlock} entity and it's TempFallingBlock instance
	 * @param fallingblock FallingBlock entity
	 */
	public static void removeFallingBlock(FallingBlock fallingblock) {
		if (isTempFallingBlock(fallingblock)) {
			fallingblock.remove();
			instances.remove(fallingblock);
		}
	}
	
	/**
	 * Remove all TempFallingBlock instances and its {@link FallingBlock} entity counterparts
	 */
	public static void removeAllFallingBlocks() {
		for (FallingBlock fallingblock : instances.keySet()) {
			fallingblock.remove();
			instances.remove(fallingblock);
		}
	}
	
	public void remove() {
		fallingblock.remove();
		instances.remove(fallingblock);
	}
	
	public FallingBlock getFallingBlock() {
		return fallingblock;
	}
	
	public Material getMaterial() {
		return fallingblock.getMaterial();
	}

	@SuppressWarnings("deprecation")
	public byte getData() {
		return fallingblock.getBlockData();
	}
	
	public Location getLocation() {
		return fallingblock.getLocation();
	}
	
	public long getCreationTime() {
		return creation;
	}
	
	public boolean canExpire() {
		return expire;
	}
}