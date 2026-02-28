package com.Alvaeron.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.utils.Lang;

public class RPEngineCommand extends AbstractCommand {
	public RPEngineCommand(Engine plugin) {
		super(plugin, Senders.values());
	}

	@Override
	public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("rpengine")) {
			if (!(sender instanceof Player) || sender.hasPermission("rpengine.admin")) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload")) {
						plugin.reloadConfig();
						plugin.loadLang();
						sender.sendMessage(Lang.TITLE.toString() + Lang.RELOAD);
					} else if (args[0].equalsIgnoreCase("debug")) {
						Engine.manager.players.clear();
						
						for (Player pl : Bukkit.getOnlinePlayers()) {
							Engine.mm.createRoleplayPlayer(pl);
						}
						
						sender.sendMessage("Players recached");
					}
				} else {
					sender.sendMessage(Lang.TITLE.toString() + Lang.VERSION.toString().replace("%v", plugin.getDescription().getVersion()));
				}
			} else {
				sender.sendMessage(Lang.NO_PERMS.toString());
			}
			return true;
		}
		return false;

	}
}
