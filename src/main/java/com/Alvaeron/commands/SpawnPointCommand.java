package com.Alvaeron.commands;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.utils.Lang;

public class SpawnPointCommand extends AbstractCommand {

	public SpawnPointCommand(Engine plugin) {
		super(plugin, Senders.PLAYER);
	}

	@Override
	public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawnpoint")){
			Player player = (Player) sender;
			String nation = rpp.getNation();
			Set<String> nations = plugin.getConfig().getConfigurationSection("Nations").getKeys(false);
			StringBuilder sb = new StringBuilder();
			
			for (String s : nations) {
				sb.append(s);
				sb.append("/");
			}
			
			String nationString = sb.toString().trim();
			String nationList = nationString.substring(0, nationString.length() - 1);
			
			if(args.length == 1){
				//Check if the first argument is set
				if(args[0].equalsIgnoreCase("set")){
					//Check if player has the permission to set nation spawnpoint for all nations return usage message with a list of all nations
					if(player.hasPermission("rpengine.spawnpoint.set.all") || player.isOp()){
						player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", nationList)
								.replace("%c", Commandlabel.toLowerCase()));
					}
					//If player has permission to set the spawnpoint of it's own nation return a usage message with just their own nation in the list 
					else if(player.hasPermission("rpengine.spawnpoint.set.own")){
						player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", rpp.getNation())
								.replace("%c", Commandlabel.toLowerCase()));
					}else{
						player.sendMessage(Lang.NO_PERMS.toString());
					}
					return false;
				} else {
					if(Engine.mu.containsCaseInsensitive(args[0], nations)){
						nation = Engine.mu.getValueFromSet(args[0], nations);
						if(nation.equalsIgnoreCase(rpp.getNation())){
							if(!player.hasPermission("rpengine.spawnpoint.others") && !player.isOp() && !player.hasPermission("rpengine.spawnpoint.own")){
								player.sendMessage(Lang.NO_PERMS.toString());
								return false;
							}
						} else {
							if(!player.hasPermission("rpengine.spawnpoint.others") && !player.isOp()){
								player.sendMessage(Lang.NO_PERMS.toString());
								return false;
							}
						}
					} else {
						if(player.hasPermission("rpengine.spawnpoint.others") || player.isOp()){
							player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", nationList)
									.replace("%c", Commandlabel.toLowerCase()));
						} else if(player.hasPermission("rpengine.spawnpoint.own")){
							player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", rpp.getNation())
									.replace("%c", Commandlabel.toLowerCase()));
						} else {
							player.sendMessage(Lang.NO_PERMS.toString());
						}
						return false;
					}
				}
			} else if(args.length == 2){
				if(args[0].equalsIgnoreCase("set")){
					if(Engine.mu.containsCaseInsensitive(args[1], nations)){
						nation = Engine.mu.getValueFromSet(args[1], nations);
						if(nation.equalsIgnoreCase(rpp.getNation())){
							if(!player.hasPermission("rpengine.spawnpoint.set.all")&& !player.hasPermission("rpengine.spawnpoint.set.own") && !player.isOp()){
								player.sendMessage(Lang.NO_PERMS.toString());
								return false;
							}
						}else{
							if(!player.hasPermission("rpengine.spawnpoint.set.all") && !player.isOp()){
								player.sendMessage(Lang.NO_PERMS.toString());
								return false;
							}
						}
						plugin.getConfig().set("Nations." + nation + ".spawnX", player.getLocation().getX());
						plugin.getConfig().set("Nations." + nation + ".spawnY", player.getLocation().getY());
						plugin.getConfig().set("Nations." + nation + ".spawnZ", player.getLocation().getZ());
						plugin.getConfig().set("Nations." + nation + ".spawnYaw", player.getLocation().getYaw());
						plugin.getConfig().set("Nations." + nation + ".spawnPitch", player.getLocation().getPitch());
						plugin.getConfig().set("Nations." + nation + ".spawnWorld", player.getWorld().getName());
						plugin.saveConfig();
						player.sendMessage(Lang.SPAWNPOINT_SET.toString()
								.replace("%n", nation));
						return true;
					} else {
						//Check if player has the permission to set nation spawnpoint for all nations return usage message with a list of all nations
						if(player.hasPermission("rpengine.spawnpoint.set.all") || player.isOp()){
							player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", nationList)
									.replace("%c", Commandlabel.toLowerCase()));
						}
						//If player has permission to set the spawnpoint of it's own nation return a usage message with just their own nation in the list 
						else if(player.hasPermission("rpengine.spawnpoint.set.own")){
							player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", rpp.getNation())
									.replace("%c", Commandlabel.toLowerCase()));
						} else {
							player.sendMessage(Lang.NO_PERMS.toString());
						}
						return false;
					}
				} else {
					if(player.hasPermission("rpengine.spawnpoint.set.own") || player.hasPermission("rpengine.spawnpoint.set.all") || player.isOp()){
						player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", nationList)
								.replace("%c", Commandlabel.toLowerCase()));
					} else {
						player.sendMessage(Lang.SPAWNPOINT_USAGE.toString().replace("%n", nationList)
								.replace("%c", Commandlabel.toLowerCase()));
					}
					return false;
				}
			}
			if(nation.equalsIgnoreCase("NONE")){
				player.sendMessage(Lang.SPAWNPOINT_NO_NATION.toString());
				player.teleport(player.getWorld().getSpawnLocation());
				return false;
			}
			Location location = player.getLocation();
			if(plugin.getConfig().contains("Nations." + nation + ".spawnX")){
				location.setX(plugin.getConfig().getDouble("Nations." + nation + ".spawnX"));
			} else {
				player.sendMessage(ChatColor.RED + "Error: " + ChatColor.WHITE + "Something went wrong with retrieving data from the config file");
				return false;
			}
			if(plugin.getConfig().contains("Nations." + nation + ".spawnY")){
				location.setY(plugin.getConfig().getDouble("Nations." + nation + ".spawnY"));
			} else {
				player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
				return false;
			}
			if(plugin.getConfig().contains("Nations." + nation + ".spawnZ")){
				location.setZ(plugin.getConfig().getDouble("Nations." + nation + ".spawnZ"));
			} else {
				player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
				return false;
			}
			if(plugin.getConfig().contains("Nations." + nation + ".spawnYaw")){
				location.setYaw((float) plugin.getConfig().getDouble("Nations." + nation + ".spawnYaw"));
			} else {
				player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
				return false;
			}
			if(plugin.getConfig().contains("Nations." + nation + ".spawnPitch")){
				location.setPitch((float) plugin.getConfig().getDouble("Nations." + nation + ".spawnPitch"));
			} else {
				player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
				return false;
			}
			if(plugin.getConfig().contains("Nations." + nation + ".spawnWorld")){
				if(Bukkit.getWorld(plugin.getConfig().getString("Nations." + nation + ".spawnWorld")) != null){
					location.setPitch((float) plugin.getConfig().getDouble("Nations." + nation + ".spawnPitch"));
				} else {
					player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
					return false;
				}
			} else {
				player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
				return false;
			}
			player.teleport(location);
			player.sendMessage(Lang.SPAWNPOINT_TELEPORT.toString().replace("%n", nation));
			return true;
		}
		return false;
	}

}
