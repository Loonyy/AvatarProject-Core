package com.avatarproject.core.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avatarproject.core.ability.BaseAbilityProvider;
import com.avatarproject.core.element.Element;
import com.avatarproject.core.element.ElementNoun;
import com.avatarproject.core.lang.Strings;
import com.avatarproject.core.player.APCPlayer;

public class CommandWho extends APCommand {

	public CommandWho() {
		super("who", "/avatar who [player]", "Display your binds or another player's binds.", new String[] { "who", "w" },
				new String[][] {new String[] {"%player"}});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) {
			return;
		}
		APCPlayer apcp = null;
		if (args.size() == 0) {
			if (!isPlayer(sender)) {
				return;
			}
			apcp = APCPlayer.get((Player) sender);
		}
		if (args.size() == 1) {
			OfflinePlayer op = getPlayer(args.get(0));
			if (op == null) {
				sender.sendMessage(Strings.COMMAND_ERROR_INVALID_PLAYER.toString(true));
				return;
			}
			apcp = APCPlayer.get(op);
		}
		
		sender.sendMessage(Strings.PLUGIN_PREFIX.toString() + apcp.getName() + ChatColor.GRAY + ":");
		for (Element e : Element.getElements().values()) {
			if (apcp.hasElement(e)) {
				StringBuilder sb = new StringBuilder(e.getColor() + "-");
				if (e.isSub()) {
					sb.append("-");
				}
				sb.append(" " + e.getFancyName() + e.getContext(ElementNoun.BENDER));
				sender.sendMessage(sb.toString());
			}
		}
		sender.sendMessage(Strings.COMMAND_WHO_ABILITIES.toString());
		for (int i : apcp.getAbilities().keySet()) {
			BaseAbilityProvider bap = BaseAbilityProvider.fromString(apcp.getAbility(i));
			if (bap != null) {
				sender.sendMessage(Strings.COMMAND_WHO_TEMPLATE.toString(String.valueOf(i + 1), bap.getFancyName()));
			}
		}
	}
}