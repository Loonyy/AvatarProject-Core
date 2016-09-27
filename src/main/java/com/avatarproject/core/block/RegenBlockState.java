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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

import com.avatarproject.core.AvatarProjectCore;

public class RegenBlockState {

	public static ConcurrentHashMap<Block, Long> blocks = new ConcurrentHashMap<Block, Long>();
	public static ConcurrentHashMap<Block, BlockState> states = new ConcurrentHashMap<Block, BlockState>();

	/**
	 * Creates a TempBlock or a State of a block that reverts after a certain time.
	 * @param block Block to be updated/reverted.
	 * @param material Material to be changed.
	 * @param data Data to be changed.
	 * @param delay Delay until block regens.
	 */
	@SuppressWarnings("deprecation")
	public RegenBlockState(Block block, Material material, byte data, long delay) {
		if (blocks.containsKey(block)) {
			blocks.replace(block, System.currentTimeMillis() + delay);
			block.setType(material);
			block.setData(data);
		} else {
			blocks.put(block, System.currentTimeMillis() + delay);
			states.put(block, block.getState());
			if (material != null) {
				block.setType(material);
				block.setData(data);
			}
		}
	}

	/**
	 * Manages blocks to be reverted.
	 */
	public static void manage() {
		new BukkitRunnable() {
			public void run() {
				for (Block b : blocks.keySet()) {
					if (System.currentTimeMillis() >= blocks.get(b)) {
						if (states.containsKey(b)) {
							BlockState bs = states.get(b);
							bs.update(true);
							states.remove(b);
						}
						//handlePlant(b);
						blocks.remove(b);
					}
				}
			}
		}.runTaskTimer(AvatarProjectCore.getInstance(), 0, 1);
	}

	/**
	 * Reverts an individual block.
	 * @param block
	 */
	public static void revert(Block block) {
		if (blocks.containsKey(block)) {
			if (states.containsKey(block)) {
				states.get(block).update(true);
				states.remove(block);
			}
			blocks.remove(block);
		}
	}

	/**
	 * Reverts all blocks.
	 */
	public static void revertAll() {
		for (Block b : blocks.keySet()) {
			if (states.containsKey(b)) {
				states.get(b).update(true);
			}
		}
		states.clear();
		blocks.clear();
	}

	/**
	 * Returns true if the block is a RegenTempBlock.
	 * @param block
	 * @return
	 */
	public static boolean hasBlock(Block block) {
		if (blocks.containsKey(block)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the block is stored as a block state.
	 * @param block
	 * @return
	 */
	public static boolean isBlockState(Block block) {
		if (states.containsKey(block)) {
			return true;
		}
		return false;
	}
}