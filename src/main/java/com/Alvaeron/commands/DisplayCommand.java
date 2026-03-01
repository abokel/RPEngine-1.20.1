package com.Alvaeron.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.Alvaeron.Engine;

public class DisplayCommand extends AbstractCommand {

	public DisplayCommand(Engine plugin) {
		super(plugin, Senders.PLAYER);
	}

	@Override
	public boolean handleCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (rpp == null) {
			Engine.mm.createRoleplayPlayer(player);
			player.sendMessage(ChatColor.YELLOW + "Your profile is still loading. Please try again in a moment.");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("nametag")) {
			Boolean hide = parseHideShow(args, rpp.isHeadNameHidden());
			if (hide == null) {
				player.sendMessage(ChatColor.RED + "Usage: /nametag [hide|show]");
				return true;
			}
			rpp.setHeadNameHidden(hide);
			player.sendMessage(ChatColor.YELLOW + "Overhead RP name is now " + (hide ? ChatColor.RED + "hidden" : ChatColor.GREEN + "shown") + ChatColor.YELLOW + ".");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("tablist")) {
			Boolean hide = parseHideShow(args, rpp.isTabListHidden());
			if (hide == null) {
				player.sendMessage(ChatColor.RED + "Usage: /tablist [hide|show]");
				return true;
			}
			rpp.setTabListHidden(hide);
			player.sendMessage(ChatColor.YELLOW + "Tab list name is now " + (hide ? ChatColor.RED + "hidden" : ChatColor.GREEN + "shown") + ChatColor.YELLOW + ".");
			return true;
		}

		return false;
	}

	private Boolean parseHideShow(String[] args, boolean currentHiddenValue) {
		if (args.length == 0) {
			return !currentHiddenValue;
		}
		if (args.length > 1) {
			return null;
		}

		if (args[0].equalsIgnoreCase("hide") || args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("false")) {
			return true;
		}
		if (args[0].equalsIgnoreCase("show") || args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("true")) {
			return false;
		}
		return null;
	}
}
