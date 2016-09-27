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
package com.avatarproject.core;

import org.bukkit.plugin.java.JavaPlugin;

import com.avatarproject.core.ability.BaseAbilityProvider;
import com.avatarproject.core.block.RegenBlockState;
import com.avatarproject.core.block.TempFallingBlock;
import com.avatarproject.core.element.Element;
import com.avatarproject.core.listener.FallingBlockListener;
import com.avatarproject.core.listener.PlayerListener;
import com.avatarproject.core.storage.Serializer;
import com.avatarproject.core.storage.UserCache;

public class AvatarProjectCore extends JavaPlugin {
	
	private static AvatarProjectCore instance;
	
	@Override
	public void onEnable() {
		if (!isSpigot()) {
			getLogger().info("AvatarProject-Core requires Spigot or a fork of Spigot to run!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		setInstance(this);
		
		new UserCache();
		Serializer.setLogger(this.getLogger());
		
		Element.init();
		
		RegenBlockState.manage();
		
		new FallingBlockListener(this);
		new PlayerListener(this);
		
		BaseAbilityProvider.registerAbilities();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Running clean-up tasks...");
		RegenBlockState.revertAll();
		TempFallingBlock.removeAllFallingBlocks();
		getLogger().info("Clean-up tasks complete!");
		getLogger().info("Thanks for using AvatarProject-Core!");
	}
	
	private boolean isSpigot() {
		return getServer().getVersion().toLowerCase().contains("spigot");
	}
	
	
	public static AvatarProjectCore getInstance() {
		return instance;
	}
	
	private static void setInstance(AvatarProjectCore instance) {
		AvatarProjectCore.instance = instance;
	}
}
