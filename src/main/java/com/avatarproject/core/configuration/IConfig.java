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

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfig {

	/**
	 * Gets the {@link FileConfiguration} instance of the configuration file
	 * @return FileConfiguration instance of the configuration
	 */
	public FileConfiguration get();
	
	/**
	 * Reloads the configuration file
	 */
	public void reload();
	
	/**
	 * Saves the configuration file
	 */
	public void save();
	
	/**
	 * Deletes the configuration file
	 * @return Boolean true if the file is deleted
	 */
	public boolean delete();
	
}
