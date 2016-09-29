package com.avatarproject.core.command;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.avatarproject.core.AvatarProjectCore;
import com.avatarproject.core.lang.Strings;

public class CommandVersion extends APCommand {

	public CommandVersion() {
		super("version", "/avatar version", "Display current plugin version.", new String[] { "version", "v" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 0)) {
			return;
		}
		sender.sendMessage(Strings.COMMAND_VERSION_VERSION.toString(true) + AvatarProjectCore.getInstance().getDescription().getVersion());
		sender.sendMessage(Strings.COMMAND_VERSION_URL.toString(true) + "https://github.com/jedk1/AvatarProject-Core");
	}
}
