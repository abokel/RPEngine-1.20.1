package com.Alvaeron.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.Alvaeron.Engine;

public class RPCommand extends AbstractCommand {
	public RPCommand(Engine plugin) {
		super(plugin, Senders.PLAYER);
	}

	@Override
	public boolean handleCommand(CommandSender sender, Command cmd,
			String Commandlabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rp")) {

		}
		return false;
	}
}
