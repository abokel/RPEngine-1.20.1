package com.Alvaeron.api;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.player.RoleplayPlayer;
import com.Alvaeron.player.RoleplayPlayer.Channel;
import com.Alvaeron.player.RoleplayPlayer.Gender;

public class RPEngineAPI {
	
	private RPEngineAPI(){
	}
	/**
	 * Returns a RolePlayer data class
	 *
	 * @param p Bukkit Player
	 * @return RolePlayer data class
	 */
	//Returns a RoleplayPlayer data class
	public static RoleplayPlayer getRoleplayPlayer(Player p){
		return Engine.manager.getPlayer(p.getUniqueId());
	}
	
	/**
	 * Returns a RolePlayer data class
	 *
	 * @param uuid UUID of a Bukkit Player
	 * @return RolePlayer data class
	 */
	public static RoleplayPlayer getRoleplayPlayer(UUID uuid){
		return Engine.manager.getPlayer(uuid);
	}
	
	/**
	 * Returns a RolePlayer data class
	 *
	 * @param playerName Name of a Bukkit Player
	 * @return RolePlayer data class
	 */
	@SuppressWarnings("deprecation")
	public static RoleplayPlayer getRoleplayPlayer(String playerName){
		if(Bukkit.getPlayer(playerName) != null){
			return Engine.manager.getPlayer(Bukkit.getPlayer(playerName).getUniqueId());
		}else if(Bukkit.getOfflinePlayer(playerName) != null){
			Player p = (Player) Bukkit.getOfflinePlayer(playerName);
			if(Engine.manager.getPlayer(p.getUniqueId()) != null){
				return Engine.manager.getPlayer(p.getUniqueId());
			}else{
				Engine.mm.createRoleplayPlayer(p);
				return Engine.manager.getPlayer(p.getUniqueId());
			}
		}else{
			return null;
		}
	}
	
	public static String getRpName(String playerName){
		return getRoleplayPlayer(playerName).getName();
	}
	public static int getRpAge(String playerName){
		return getRoleplayPlayer(playerName).getAge();
	}
	public static Gender getRpGender(String playerName){
		return getRoleplayPlayer(playerName).getGender();
	}
	public static String getRpRace(String playerName){
		return getRoleplayPlayer(playerName).getRace();
	}
	public static String getRpNation(String playerName){
		return getRoleplayPlayer(playerName).getNation();
	}
	public static String getRpDesc(String playerName){
		return getRoleplayPlayer(playerName).getDesc();
	}
	public static Channel getChannel(String playerName){
		return getRoleplayPlayer(playerName).getChannel();
	}
	public static boolean getOOC(String playerName){
		return getRoleplayPlayer(playerName).isOOC();
	}
	
	public static void setRpName(String playerName, String name){
		getRoleplayPlayer(playerName).setName(name);
	}
	public static void setRpAge(String playerName, int age){
		getRoleplayPlayer(playerName).setAge(age);
	}
	public static void setRpGender(String playerName, Gender gender){
		getRoleplayPlayer(playerName).setGender(gender);
	}
	public static void setRpRace(String playerName, String race){
		getRoleplayPlayer(playerName).setRace(race);
	}
	public static void setRpNation(String playerName, String nation){
		getRoleplayPlayer(playerName).setNation(nation);
	}
	public static void setRpDesc(String playerName, String desc){
		getRoleplayPlayer(playerName).setDesc(desc);
	}
	public static void setChannel(String playerName, Channel channel){
		getRoleplayPlayer(playerName).setChannel(channel);
	}
	public static void setOOC(String playerName, boolean ooc){
		getRoleplayPlayer(playerName).setOOC(ooc);
	}
	
	public static Set<String> getRaces(){
		return Engine.rpEngine.getConfig().getConfigurationSection("Races").getKeys(false);
	}
	public static Set<String> getNations(){
		return Engine.rpEngine.getConfig().getConfigurationSection("Nations").getKeys(false);
	}
	public static ChatColor getRaceColor(String race){
		return Engine.mu.getRaceColour(race);
	}
}
