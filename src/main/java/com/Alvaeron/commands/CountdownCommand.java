package com.Alvaeron.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.utils.Countdown;
import com.Alvaeron.utils.Lang;

public class CountdownCommand extends AbstractCommand {

	public CountdownCommand(Engine plugin) {
		super(plugin, Senders.PLAYER);
	}

	@Override
	public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel,
			String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Lang.COUNTDOWN_USAGE.toString());
		}
		if (args.length == 1) {
			try {
				final int n = Integer.parseInt(args[0]);
				Player player = (Player) sender;
				if (n < 0) {
					player.sendMessage(Lang.COUNTDOWN_NEGATIVE.toString());
				} else if (n <= plugin.getConfig().getInt("maxCountdown")) {
					Engine.mu.sendRangedMessage(player
							, Lang.COUNTDOWN_START.toString()
							.replace("%r", rpp.getName())
							.replace("%p", rpp.getPlayer().getName())
							.replace("%n", Integer.toString(n))
							, "rpRange");
					final Countdown c = new Countdown(plugin);
					c.startCountdown(player, true, Integer.parseInt(args[0]));
				} else {
					player.sendMessage(Lang.COUNTDOWN_MAX.toString().replace("%m", Integer.toString(plugin.getConfig().getInt("maxCountdown"))));
				}
			} catch (NumberFormatException e) {
				player.sendMessage(Lang.COUNTDOWN_USAGE.toString());
			}
		}
		return true;

	}
}
