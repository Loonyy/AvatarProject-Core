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

import java.util.List;

import org.bukkit.command.CommandSender;

public interface IAPCommand {

	/**
	 * Gets the name of the command
	 * @return String name of the command
	 */
	public String getName();
	
	/**
	 * Gets the command's aliases
	 * @return String array of the command's aliases
	 */
	public String[] getAliases();
	
	/**
	 * Gets the proper use of the command
	 * @return String command usage
	 */
	public String getProperUse();
	
	/**
	 * Gets the description of the command
	 * @return String description of the command
	 */
	public String getDescription();
	
	/**
	 * Gets the tab completers of the command
	 * Used when auto-tabbing
	 * @return 3D String array of the completers
	 */
	public String[][] getCompleters();
	
	/**
	 * Send the command usage to the {@link CommandSender}
	 * @param sender CommandSender recieving the help
	 * @param description Boolean should the description also be provided when sending help?
	 */
	public void help(CommandSender sender, boolean description);
	
	/**
	 * Run when the command is executed
	 * @param sender CommandSender command user
	 * @param args List<String> args provided in the command
	 */
	public void execute(CommandSender sender, List<String> args);
}
