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
		sender.sendMessage(Strings.COMMAND_USAGE.toString() + this.properUse);
		if (description) {
			sender.sendMessage(ChatColor.GRAY + this.description);
		}
	}

	/**
	 * Checks if the sender has the required permission for this command
	 * Permission format is "avatar.command.####"
	 * @param sender CommandSender to check the permission for
	 * @return Boolean true if the sender has permission
	 */
	protected boolean hasPermission(CommandSender sender) {
		return hasPermission(sender, null);
	}
	
	/**
	 * Checks if the sender has the required permission for this command with additional permission
	 * Permission format is "avatar.command.####.####"
	 * @param sender CommandSender to check the permission for
	 * @param extra String additional string to append on the end of the existing permission
	 * @return Boolean true if the sender has permission
	 */
	protected boolean hasPermission(CommandSender sender, String extra) {
		return hasPermission(sender, extra, true);
	}

	/**
	 * Checks if the sender has the required permission for this command with additional permission
	 * Permission format is "avatar.command.####.####"
	 * @param sender CommandSender to check the permission for
	 * @param extra String additional string to append on the end of the existing permission
	 * @param notify Boolean send message to sender if they do not have required permission
	 * @return Boolean true if the sender has the permission
	 */
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

	/**
	 * Checks if the amount of arguments passed in the command are within a range
	 * @param sender CommandSender sender of the command
	 * @param size Integer amount of arguments
	 * @param min Integer minimum expected arguments
	 * @param max Integer maximum expected arguments
	 * @return Boolean true if the size is within the minimum and maximum expected arguments
	 */
	public boolean correctLength(CommandSender sender, int size, int min, int max) {
		if (size < min || size > max) {
			help(sender, false);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks if the sender is a {@link Player}
	 * @param sender CommandSender to check
	 * @return Boolean true of the sender is a {@link Player}
	 */
	public boolean isPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		} else {
			sender.sendMessage(ChatColor.RED + "You must be a player to use that command.");
			return false;
		}
	}

	/**
	 * Checks if a given string is numerical
	 * @param id String to check
	 * @return Boolean true if the string is numerical
	 */
	public boolean isNumeric(String id) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(id, pos);
		return id.length() == pos.getIndex();
	}

	/**
	 * Gets an {@link OfflinePlayer} instance of a player if they have played on the server
	 * @param name String name of the player
	 * @return {@link OfflinePlayer} instance of a player from a name
	 */
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

	/**
	 * Gets an {@link OfflinePlayer} instance of a player if they have played on the server
	 * @param uuid UUID of the player
	 * @return {@link OfflinePlayer} instance of a player from a UUID
	 */
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
	
	/**
	 * Gets the auto-complete variables when tabbing on a command
	 * @param sender CommandSender sender of the command
	 * @param args String[] args to be provided
	 * @return String[] args to be provided
	 */
	protected String[] tabComplete(CommandSender sender, String[] args) {
		return null;
	}
	
	/**
	 * Gets an array of {@link TextComponent}s for a page
	 * @param title String title that should be displayed
	 * @param page Integer page to be displayed
	 * @param entries List<TextComponent> all possible entries for all pages
	 * @return List<TextComponent> with entries on a specific page
	 */
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
		
		strings.add(new TextComponent(Strings.COMMAND_TITLE.toString() + ChatColor.DARK_GRAY + " [" + ChatColor.GRAY + (page + 1) + "/" + (maxPage + 1) + ChatColor.DARK_GRAY + "]"));
		strings.add(new TextComponent(title));
		
		pages.get(page).stream().forEach(textcomponent -> strings.add(textcomponent));

		return strings;
	}
}
