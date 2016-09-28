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

import com.avatarproject.core.AvatarProjectCore;

public class LanguageConfig extends Config {
	
	private static Config instance;
	
	public LanguageConfig() {
		super(AvatarProjectCore.getInstance(), new File("en_US.yml"));
		get().addDefault("General.Prefix", "&8[&3AvatarProject&8]&r ");
		get().addDefault("General.NoPermission", "&cSorry but you do not have the permission required to do that!");
		save();
		instance = this;
	}
	
	public static Config getInstance() {
		return instance;
	}
}
