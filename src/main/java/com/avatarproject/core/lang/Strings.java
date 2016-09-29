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

import java.util.Arrays;

import org.bukkit.ChatColor;

import com.avatarproject.core.configuration.LanguageConfig;

public enum Strings {
	
	//General
	PLUGIN_PREFIX ("General.PluginPrefix"),
	NO_PERMISSION ("General.NoPermission"),
	
	//Elements
	ELEMENT_AIR ("Element.Air.Main"),
	ELEMENT_FLIGHT ("Element.Air.Flight"),
	
	ELEMENT_EARTH ("Element.Earth.Main"),
	ELEMENT_LAVA ("Element.Earth.Lava"),
	ELEMENT_METAL ("Element.Earth.Metal"),
	
	ELEMENT_FIRE ("Element.Fire.Main"),
	ELEMENT_COMBUSTION ("Element.Fire.Combustion"),
	ELEMENT_LIGHTNING ("Element.Fire.Lightning"),
	
	ELEMENT_WATER ("Element.Water.Main"),
	ELEMENT_BLOOD ("Element.Water.Blood"),
	ELEMENT_PLANT ("Element.Water.Plant"),
	
	ELEMENT_CHI ("Element.Chi.Main"),
	
	ELEMENT_NOUN_BENDER ("Element.Nouns.Bender"),
	ELEMENT_NOUN_BENDING ("Element.Nouns.Bending"),
	ELEMENT_NOUN_BEND ("Element.Nouns.Bend"),
	ELEMENT_NOUN_BLOCKER ("Element.Nouns.Blocker"),
	ELEMENT_NOUN_BLOCKING ("Element.Nouns.Blocking"),
	ELEMENT_NOUN_BLOCK ("Element.Nouns.Block"),
	
	//Commands
	COMMAND_HELP_TIP ("Command.HelpTip"),
	COMMAND_TITLE ("Command.Title"),
	COMMAND_UNKNOWN ("Command.Unknown"),
	COMMAND_USAGE ("Command.Usage"),
	
	COMMAND_HELP_TITLE ("Command.Help.Title"),
	
	COMMAND_BIND_BOUND ("Command.Bind.Bound", new String[] {"%ability%", "%slot%"}),
	
	COMMAND_ADD_SELF ("Command.Add.Self.Add", new String[] {"%element%", "%bend%"}),
	COMMAND_ADD_SELF_SUB ("Command.Add.Self.Subelement", new String[] {"%parent%", "%bend%", "%sub%", "%bending%"}),
	COMMAND_ADD_OTHER_SENDER ("Command.Add.Other.Sender", new String[] {"%player%", "%element%", "%bending%"}),
	COMMAND_ADD_OTHER_PLAYER ("Command.Add.Other.Player", new String[] {"%sender%", "%element%", "%bending%"}),
	COMMAND_ADD_OTHER_SUB ("Command.Add.Other.Subelement", new String[] {"%player%", "%parent%", "%bend%", "%sub%", "%bending%"}),
	
	COMMAND_CHOOSE_SELF ("Command.Choose.Self.Choose", new String[] {"%element%", "%bender%"}),
	COMMAND_CHOOSE_OTHER_SENDER ("Command.Choose.Other.Sender", new String[] {"%player%", "%element%", "%bender%"}),
	COMMAND_CHOOSE_OTHER_PLAYER ("Command.Choose.Other.Player", new String[] {"%sender%", "%element%", "%bender%"}),
	
	COMMAND_CLEAR_ALL ("Command.Clear.All"),
	COMMAND_CLEAR_SINGLE ("Command.Clear.Single", new String[] {"%slot%"}),
	
	COMMAND_VERSION_VERSION ("Command.Version.Version"),
	COMMAND_VERSION_URL ("Command.Version.URL"),
	
	COMMAND_WHO_ABILITIES ("Command.Who.Abilities"),
	COMMAND_WHO_TEMPLATE ("Command.Who.Template", new String[] {"%slot%", "%ability%"}),
	
	COMMAND_ERROR ("Command.Error.Error"),
	COMMAND_ERROR_SLOT_BOUNDS ("Command.Error.SlotOutOfBounds"),
	COMMAND_ERROR_INVALID_ABILITY ("Command.Error.Invalid.Ability"),
	COMMAND_ERROR_INVALID_PLAYER ("Command.Error.Invalid.Player"),
	COMMAND_ERROR_INVALID_ELEMENT ("Command.Error.Invalid.Element"),
	
	//Player
	PLAYER_ERROR_CANT_BIND ("Player.Error.CantBind");
	
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
			//Replace instances of $a with the appropriate a/an (Only works for the english language)
			String temp = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', s));
			for (String word : temp.split(" ")) {
				if (word.equals("$a")) {
					int index = Arrays.asList(temp.split(" ")).indexOf(word);
					if (index < temp.split(" ").length) {
						String next = temp.split(" ")[index + 1];
						s = s.replaceFirst("\\$a", next.toLowerCase().matches("^(a|e|i|o|u).*$") ? "an" : "a");
					}
				}
			}
		}
		return ChatColor.translateAlternateColorCodes('&', (prefix ? PLUGIN_PREFIX : "") + s);
	}
	
	public String[] getArgs() {
		return args;
	}
}