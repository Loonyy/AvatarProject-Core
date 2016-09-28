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
package com.avatarproject.core.command;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avatarproject.core.lang.Strings;
import com.avatarproject.core.storage.UserCache;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class APCommand implements IAPCommand {

	//TODO add documentation
	
	private final String name;

	private final String properUse;

	private final String description;

	private final String[] aliases;
	
	private final String[][] completers;

	public static Map<String, APCommand> instances = new HashMap<String, APCommand>();

	public APCommand(String name, String properUse, String description, String[] aliases) {
		this(name, properUse, description, aliases, null);
	}
	
	public APCommand(String name, String properUse, String description, String[] aliases, String[][] completers) {
		this.name = name;
		this.properUse = properUse;
		this.description = description;
		this.aliases = aliases;
		this.completers = completers;
		instances.put(name, this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getProperUse() {
		return properUse;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}
	
	@Override
	public String[][] getCompleters() {
		return completers;
	}

	@Override
	public void help(CommandSender sender, boolean description) {
		sender.sendMessage("Usage: " + this.properUse);
		if (description) {
			sender.sendMessage(ChatColor.GRAY + this.description);
		}
	}

	protected boolean hasPermission(CommandSender sender) {
		return hasPermission(sender, null);
	}
	
	protected boolean hasPermission(CommandSender sender, String extra) {
		return hasPermission(sender, extra, true);
	}

	protected boolean hasPermission(CommandSender sender, String extra, boolean notify) {
		StringBuilder perm = new StringBuilder("avatar.command.").append(name);
		if (extra != null) {
			perm.append(".").append(extra);
		}
		if (sender.hasPermission(perm.toString())) {
			return true;
		} else {
			if (notify) {
				sender.sendMessage(Strings.NO_PERMISSION.toString());
			}
			return false;
		}
	}

	public boolean correctLength(CommandSender sender, int size, int min, int max) {
		if (size < min || size > max) {
			help(sender, false);
			return false;
		} else {
			return true;
		}
	}

	public boolean isPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		} else {
			sender.sendMessage(ChatColor.RED + "You must be a player to use that command.");
			return false;
		}
	}

	public boolean isNumeric(String id) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(id, pos);
		return id.length() == pos.getIndex();
	}

	public OfflinePlayer getPlayer(String name) {
		if (name.length() > 16) {
			return null;
		}
		Player p = Bukkit.getPlayer(name);
		if (p != null) {
			return p;
		}
		OfflinePlayer offp = null;
		UUID uuid = UserCache.getUser(name);
		if (uuid == null) {
			return null;
		} else {
			offp = Bukkit.getOfflinePlayer(uuid);
		}
		if (offp != null) {
			if (offp.hasPlayedBefore()) { 
				return offp;
			} else {
				return null;
			}
		}
		return null;
	}

	public OfflinePlayer getPlayerUUID(UUID uuid) {
		Player p = Bukkit.getPlayer(uuid);
		if (p != null) {
			return p;
		}
		OfflinePlayer offp = Bukkit.getOfflinePlayer(uuid);
		if (offp != null) {
			if (offp.hasPlayedBefore()) { 
				return offp;
			} else {
				return null;
			}
		}
		return null;
	}
	
	protected String[] tabComplete(String[] args) {
		return null;
	}
	
	public List<TextComponent> getPage(String title, int page, List<TextComponent> entries) {
		List<TextComponent> strings = new ArrayList<>();

		if (page < 0) {
			page = 0;
		}
		
		List<List<TextComponent>> pages = new ArrayList<>();
		List<TextComponent> pagedata = new ArrayList<>();
		for (TextComponent tc : entries) {
			List<TextComponent> pageclone = new ArrayList<>();
			if (tc.getText().equals("%newpage%")) {
				pageclone.addAll(pagedata);
				pages.add(pageclone);
				pagedata.clear();
				continue;
			}
			pagedata.add(tc);
			if (entries.indexOf(tc) == (entries.size() - 1) || pagedata.size() == 8) {
				pageclone.addAll(pagedata);
				pages.add(pageclone);
				pagedata.clear();
			}
		}
		
		int maxPage = pages.size() - 1;
		
		if (page > maxPage) {
			page = maxPage;
		}
		
		strings.add(new TextComponent("Some title here" + ChatColor.DARK_GRAY + " [" + ChatColor.GRAY + (page + 1) + "/" + (maxPage + 1) + ChatColor.DARK_GRAY + "]"));
		strings.add(new TextComponent(title));
		
		pages.get(page).stream().forEach(textcomponent -> strings.add(textcomponent));

		return strings;
	}
}
