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
		
		//General
		get().addDefault("General.PluginPrefix", "&8[&3AvatarProject&8]&r ");
		get().addDefault("General.NoPermission", "&cSorry but you do not have the permission required to do that!");
		
		//Elements
		get().addDefault("Element.Air.Main", "Air");
		get().addDefault("Element.Air.Flight", "Flight");
		
		get().addDefault("Element.Earth.Main", "Earth");
		get().addDefault("Element.Earth.Lava", "Lava");
		get().addDefault("Element.Earth.Metal", "Metal");
		
		get().addDefault("Element.Fire.Main", "Fire");
		get().addDefault("Element.Fire.Combustion", "Combustion");
		get().addDefault("Element.Fire.Lightning", "Lightning");
		
		get().addDefault("Element.Water.Main", "Water");
		get().addDefault("Element.Water.Blood", "Blood");
		get().addDefault("Element.Water.Plant", "Plant");
		
		get().addDefault("Element.Chi.Main", "Chi");
		
		get().addDefault("Element.Nouns.Bend", "bend");
		get().addDefault("Element.Nouns.Bender", "bender");
		get().addDefault("Element.Nouns.Bending", "bending");
		
		get().addDefault("Element.Nouns.Block", "block");
		get().addDefault("Element.Nouns.Blocker", "blocker");
		get().addDefault("Element.Nouns.Blocking", "blocking");
		
		//Commands
		get().addDefault("Command.HelpTip", "&7Use '&a/avatar help&7' for help!");
		get().addDefault("Command.Title", "&8=====: &3AvatarProject Core &8:=====");
		get().addDefault("Command.Unknown", "&7Unknown command! Use '&a/avatar help&7' to view all commands!");
		get().addDefault("Command.Usage", "&3Usage: &f");
		
		get().addDefault("Command.Help.Title", "&aCommands: <required> [optional]");
		
		get().addDefault("Command.Add.Self.Add", "&7You can now also &f%element%%bend%&7!");
		get().addDefault("Command.Add.Self.Subelement", "&7You need to be able to &f%parent%%bend%&7 before you can add &f%sub%%bending%&7!");
		get().addDefault("Command.Add.Other.Sender", "&7You have added &f%element%%bending%&7 to &f%player%&7!");
		get().addDefault("Command.Add.Other.Player", "&f%sender%&7 has added &f%element%%bending%&7 to you!");
		get().addDefault("Command.Add.Other.Subelement", "&f%player%&7 needs to be able to &f%parent%%bend%&7 before you can add &f%sub%%bending%&7 to them!");
		
		get().addDefault("Command.Bind.Bound", "&7You have bound &f%ability%&7 to slot &f%slot%&7!");
		
		get().addDefault("Command.Choose.Self.Choose", "&7You have set yourself as $a &f%element%%bender%&7!");
		get().addDefault("Command.Choose.Other.Sender", "&7You have set &f%player%&7 as $a &f%element%%bender%&7!");
		get().addDefault("Command.Choose.Other.Player", "&f%sender%&7 has set you as $a &f%element%%bender%&7!");
		
		get().addDefault("Command.Clear.All", "&7You have cleared all your slots!");
		get().addDefault("Command.Clear.Single", "&7Slot &f%slot%&7 has been cleared!");
		
		get().addDefault("Command.Version.Version", "&7Version: &f");
		get().addDefault("Command.Version.URL", "&7GitHub: &f");
		
		get().addDefault("Command.Who.Abilities", "&fAbilities:");
		get().addDefault("Command.Who.Template", "&f%slot%&7 - &f%ability%");
		
		get().addDefault("Command.Error.Error", "&7Uh oh, something went wrong when trying to execute that command!");
		get().addDefault("Command.Error.SlotOutOfBounds", "&7Slot must be within 1 - 9!");
		get().addDefault("Command.Error.Invalid.Ability", "&7That isn't a valid Ability!");
		get().addDefault("Command.Error.Invalid.Player", "&cCouldn't find a Player with that name!");
		get().addDefault("Command.Error.Invalid.Element", "&7That isn't a valid Element!");
		
		//Player
		get().addDefault("Player.Error.CantBind", "&7Sorry but you can't bind that Ability!");
		
		save();
		instance = this;
	}
	
	public static Config getInstance() {
		return instance;
	}
}
