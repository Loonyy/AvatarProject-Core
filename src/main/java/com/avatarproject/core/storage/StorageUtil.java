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

import java.io.File;

import com.avatarproject.core.AvatarProjectCore;

public class StorageUtil {

	/**
	 * Easy method to get a file for an object
	 * @param path parent directories to the file
	 * @param name String name of the file
	 * @return File file
	 */
	public static File getJSONFile(String path, String name) {
		return new File(AvatarProjectCore.getInstance().getDataFolder() + File.separator + path + File.separator + name + ".json");
	}
}
