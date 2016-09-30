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
package com.avatarproject.core.configuration;

import java.io.File;
import java.lang.reflect.Modifier;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.avatarproject.core.AvatarProjectCore;
import com.google.common.reflect.ClassPath;

public abstract class Config implements IConfig {

	private JavaPlugin plugin;

	private File file;
	public FileConfiguration config;

	public Config(JavaPlugin plugin, File file) {
		this.plugin = plugin;
		this.file = new File(plugin.getDataFolder() + File.separator + file);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		reload();
	}

	/**
	 * Create the file and parent directories for the the file
	 */
	public void create() {
		if (!file.getParentFile().exists()) {
			try {
				file.getParentFile().mkdir();
			} catch (Exception e) {
				plugin.getLogger().info("Failed to generate directory!");
				e.printStackTrace();
			}
		}

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().info("Failed to generate " + file.getName() + "!");
				e.printStackTrace();
			}
		}
	}

	@Override
	public FileConfiguration get() {
		return config;
	}

	@Override
	public void reload() {
		create();
		try {
			config.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		try {
			config.options().copyDefaults(true);
			config.options().header("##### AvatarProject-Core Config #####");
			config.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean delete() {
		try {
			if (file.delete()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void loadConfigurations() {
		loadConfigurations(AvatarProjectCore.get(), "com.avatarproject");
	}
	
	public static void loadConfigurations(JavaPlugin plugin, String packageName) {
		ClassLoader loader = plugin.getClass().getClassLoader();
		try {
			ClassPath.from(loader).getAllClasses().stream()
			.filter(info -> {
				if (!info.getPackageName().startsWith(packageName)) {
					return false;
				}
				Class<?> c = info.load();
				if (!Config.class.isAssignableFrom(c) || c.isInterface() || Modifier.isAbstract(c.getModifiers())) {
					return false;
				}
				return true;
			})
			.forEach(info -> {
				Class<?> c = info.load();
				try {
					c.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}