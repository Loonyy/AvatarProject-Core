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
package com.avatarproject.core.lang;

import org.bukkit.ChatColor;

import com.avatarproject.core.configuration.LanguageConfig;

public enum Strings {
	
	//General
	PLUGIN_PREFIX ("General.PluginPrefix"),
	NO_PERMISSION ("General.NoPermission");
	
	private String path;
	private String[] args;
	
	Strings(String path) {
		this(path, null);
	}
	
	Strings(String path, String[] args) {
		this.path = path;
		this.args = args;
	}
	
	@Override
	public String toString() {
		return toString(false);
	}
	
	/**
	 * Returns a string with no replaced args.
	 * @param prefix Append a prefix to beginning of sentence.
	 * @return String without replaced variables.
	 */
	public String toString(boolean prefix) {
		if (!LanguageConfig.getInstance().get().contains(path)) {
			return ChatColor.RED + "Error: " + ChatColor.WHITE + "String missing from [TODO: ADD FILE NAME]! Missing: " + this.name() + " Path: " + path;
		}
		return ChatColor.translateAlternateColorCodes('&', (prefix ? PLUGIN_PREFIX : "") + LanguageConfig.getInstance().get().getString(path));
	}
	
	/**
	 * Returns a string with replaced args with NO appended prefix.
	 * @param args Variables to be replaced in string.
	 * @return String with replaced variables.
	 */
	public String toString(String... args) {
		return toString(false, args);
	}
	
	/**
	 * Returns a string with replaced args.
	 * @param prefix Append prefix to beginning of sentence.
	 * @param args Variables to be replaced in string.
	 * @return String with replaced variables.
	 */
	public String toString(boolean prefix, String... args) {
		String s = toString();
		if (args != null && args.length > 0 && getArgs() != null && getArgs().length > 0) {
			for (int i = 0; i < getArgs().length; i++) {
				if (args.length < i || args[i] == null) {
					continue;
				}
				s = s.replace(getArgs()[i], args[i]);
			}
		}
		return ChatColor.translateAlternateColorCodes('&', (prefix ? PLUGIN_PREFIX : "") + s);
	}
	
	public String[] getArgs() {
		return args;
	}
}