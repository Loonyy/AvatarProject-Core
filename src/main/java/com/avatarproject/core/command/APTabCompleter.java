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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class APTabCompleter implements TabCompleter {

	/**
	 * Breaks down the possible list and returns what is applicable depending on
	 * what the user has currently typed.
	 * 
	 * @author D4rKDeagle
	 * 
	 * @param args Args of the command. Provide all of them.
	 * @param possibilitiesOfCompletion List of things that can be given
	 */
	public static List<String> getPossibleCompletionsForGivenArgs(String[] args, List<String> possibilitiesOfCompletion) {
		String argumentToFindCompletionFor = args[args.length - 1];

		List<String> listOfPossibleCompletions = new ArrayList<String>();

		for (String foundString : possibilitiesOfCompletion) {
			if (foundString.regionMatches(true, 0, argumentToFindCompletionFor, 0, argumentToFindCompletionFor.length())) {
				listOfPossibleCompletions.add(foundString);
			}
		}
		Collections.sort(listOfPossibleCompletions);
		return listOfPossibleCompletions;
	}

	public static List<String> getPossibleCompletionsForGivenArgs(String[] args, String[] possibilitiesOfCompletion) {
		return getPossibleCompletionsForGivenArgs(args, Arrays.asList(possibilitiesOfCompletion));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		List<String> complete = new ArrayList<String>();
		if (cmd.getName().equalsIgnoreCase("avatar")) {
			if (args.length == 1) {
				List<String> possible = new ArrayList<String>();
				for (APCommand command : APCommand.instances.values()) {
					if (hasPermission(sender, command.getName())) {
						possible.add(command.getAliases()[0]);
					}
				}
				return getPossibleCompletionsForGivenArgs(args, possible);
			}
			if (args.length > 1) {
				for (APCommand command : APCommand.instances.values()) {
					if (ArrayUtils.contains(command.getAliases(), args[0].toLowerCase())) {
						if (command.getCompleters() != null) {
							if (command.getCompleters().length >= (args.length - 1)) {
								String[] completers = command.getCompleters()[(args.length - 2)];
								if (Arrays.asList(completers).contains("%custom")) {
									completers = (String[]) ArrayUtils.removeElement(completers, "%custom");
									completers = (String[]) ArrayUtils.addAll(completers, command.tabComplete(args));
								}
								if (Arrays.asList(completers).contains("%player")) {
									completers = (String[]) ArrayUtils.removeElement(completers, "%player");
									List<String> array = new ArrayList<String>();
									for (Player p : Bukkit.getOnlinePlayers()) {
										array.add(p.getName());
									}
									completers = (String[]) ArrayUtils.addAll(completers, array.toArray(new String[array.size()]));
								}
								return getPossibleCompletionsForGivenArgs(args, completers);
							}
						}
					}
				}
			}
		}
		return complete;
	}
	
	private boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission("avatar.command." + permission);
	}
}