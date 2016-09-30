package com.avatarproject.core.command;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avatarproject.core.lang.Strings;
import com.avatarproject.core.player.APCPlayer;

public class CommandToggle extends APCommand {

	public CommandToggle() {
		super("toggle", "/avatar toggle [player]", "Toggle your bending on or off, or another players bending.", new String[] { "toggle", "t" },
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
			if (!hasPermission(sender, "other")) {
				return;
			}
			OfflinePlayer op = getPlayer(args.get(0));
			if (op == null) {
				sender.sendMessage(Strings.COMMAND_ERROR_INVALID_PLAYER.toString(true));
				return;
			}
			apcp = APCPlayer.get(op);
		}
		apcp.setToggled(!apcp.isToggled());
		apcp.serialize();
		if (args.size() == 1) {
			Strings str = apcp.isToggled() ? Strings.COMMAND_TOGGLE_OFF_OTHER : Strings.COMMAND_TOGGLE_ON_OTHER;
			sender.sendMessage(str.toString(true, apcp.getName()));
		} else {
			Strings str = apcp.isToggled() ? Strings.COMMAND_TOGGLE_OFF_SELF : Strings.COMMAND_TOGGLE_ON_SELF;
			sender.sendMessage(str.toString(true));
		}
	}
}