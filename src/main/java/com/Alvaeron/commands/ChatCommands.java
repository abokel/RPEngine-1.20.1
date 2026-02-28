package com.Alvaeron.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.Alvaeron.Engine;
import com.Alvaeron.player.RoleplayPlayer.Channel;
import com.Alvaeron.utils.Lang;

public class ChatCommands extends AbstractCommand {

	public ChatCommands(Engine plugin) {
		super(plugin, Senders.PLAYER);
	}

	@Override
	public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel,
			String[] args) {
		
		if(Engine.listener.chatEnabled) {
			if (cmd.getName().equalsIgnoreCase("rp")) {
				rpp.setChannel(Channel.RP);
				rpp.setTag();
				return true;
			}
			if (cmd.getName().equalsIgnoreCase("ooc")) {
				if (!rpp.isOOC()) {
					rpp.switchOOC();
				}
				rpp.setChannel(Channel.OOC);
				rpp.setTag();
	
				return true;
			}
			if (cmd.getName().equalsIgnoreCase("toggleooc")) {
				rpp.setChannel(Channel.RP);
				rpp.setTag();
				rpp.switchOOC();
				if (!rpp.isOOC() && (rpp.getChannel() == Channel.OOC)) {
					rpp.setChannel(Channel.RP);
				}
				return true;
			}
			if (cmd.getName().equalsIgnoreCase("whisper")) {
	
				if (args.length >= 1) {
					final StringBuilder sb2 = new StringBuilder();
					for (int j = 0; j < args.length; ++j) {
						sb2.append(args[j]).append(" ");
					}
					final String message2 = sb2.toString().trim();
					Engine.mu.sendRangedMessage(player, Lang.CHAT_WHISPER_FORMAT.toString()
							.replace("%m", message2)
							.replace("%n", rpp.getName())
							, "whisperRange");
	
				} else {
					player.sendMessage(Lang.CHAT_WHISPER_USAGE.toString()
							.replace("%c", Commandlabel.toLowerCase()));
				}
			}
			if (cmd.getName().equalsIgnoreCase("shout")) {
				if (args.length >= 1) {
					final StringBuilder sb2 = new StringBuilder();
					for (int j = 0; j < args.length; ++j) {
						sb2.append(args[j]).append(" ");
					}
					final String message2 = sb2.toString().trim();
					Engine.mu.sendRangedMessage(player, Lang.CHAT_SHOUT_FORMAT.toString()
							.replace("%m", message2)
							.replace("%n", rpp.getName())
							, "shoutRange");
				}
				else {
					player.sendMessage(Lang.CHAT_SHOUT_USAGE.toString()
							.replace("%c", Commandlabel.toLowerCase()));
				}
				return true;
			}
		}else 
		{
			rpp.getPlayer().sendMessage(Lang.CHAT_DISABLED.toString());
		}
		return false;
	}
	
}
