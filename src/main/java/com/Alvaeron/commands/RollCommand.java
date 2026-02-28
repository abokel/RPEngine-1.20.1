package com.Alvaeron.commands;

import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.utils.Lang;

public class RollCommand extends AbstractCommand {

	public RollCommand(Engine plugin) {
		super(plugin, Senders.values());
	}

	@Override
	public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel,
			String[] args) {
		Random diceRoller = new Random();
		int number = 20;
		if (args.length > 1) {
			sender.sendMessage(Lang.ROLL_USAGE.toString());
			return true;
		}
		if (args.length == 1) {
			try {
				int parsedNumber = Integer.parseInt(args[0]);
				if (parsedNumber <= 0) {
					sender.sendMessage(Lang.ROLL_USAGE.toString());
					return true;
				}
				if (parsedNumber <= plugin.getConfig().getInt("maxRoll")) {
					number = parsedNumber;
				} else {
					sender.sendMessage(Lang.ROLL_MAX.toString().replace("%n", Integer.toString(plugin.getConfig().getInt("maxRoll"))));
					return true;
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage(Lang.ROLL_USAGE.toString());
				return true;
			}
		}

		int roll = diceRoller.nextInt(number) + 1;
		if (sender instanceof Player) {
			Engine.mu.sendRangedMessage(player
					, Lang.ROLL_RESULT.toString()
					.replace("%p", rpp.getPlayer().getName())
					.replace("%r", rpp.getName())
					.replace("%n", Integer.toString(roll))
					.replace("%m", Integer.toString(number))
					, "rpRange");
		} else {
			plugin.getServer().broadcastMessage(Lang.ROLL_CONSOLE.toString()
					.replace("%p", rpp.getPlayer().getName())
					.replace("%r", rpp.getName())
					.replace("%n", Integer.toString(roll))
					.replace("%m", Integer.toString(number)));
		}
		return true;

	}
}
