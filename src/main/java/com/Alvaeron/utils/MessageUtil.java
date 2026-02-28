package com.Alvaeron.utils;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;

public class MessageUtil {
	private Engine plugin;

	public MessageUtil(Engine plugin) {
		this.plugin = plugin;
	}

	/**
	 * Send a message to players within the range of the RP chat
	 * 
	 * @param player
	 *            - The player who spoke
	 * @param text
	 *            - What they said
	 * @param key
	 *            - The type of message this is
	 */
	public void sendRangedMessage(Player player, String text, String key) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (player.getWorld() == p.getWorld()) {
				if (p.getLocation().distance(player.getLocation()) <= plugin.getConfig().getInt(key)) {
					p.sendMessage(text);
				}
			}
		}
	}

	public void sendLocalizedMessage(Player player, String key) {

	}

	/**
	 * Search an Set of strings for a string
	 * 
	 * @param s
	 *            - the string to search for
	 * @param l
	 *            - the set we are searching in
	 * @return boolean - true if the set contained the string, false if it didn't
	 */
	public boolean containsCaseInsensitive(final String s, final Set<String> l) {
		for (final String string : l) {
			if (string.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	public String getValueFromSet(final String s, final Set<String> l){
		for (final String string : l) {
			if (string.equalsIgnoreCase(s)) {
				return string;
			}
		}
		return null;
	}
	/**
	 * @param race
	 *            - The race you want the color of
	 * @return The ChatColor representing the race, or ChatColor.GRAY if one isn't found
	 */
	public ChatColor getRaceColour(final String race) {
		if (!plugin.getConfig().contains("Races." + race + ".Color")) {
			return ChatColor.GRAY;
		}
		final String color = plugin.getConfig().getString("Races." + race + ".Color");
		try {
			return ChatColor.valueOf(color);
		} catch (IllegalArgumentException ex) {
			return ChatColor.WHITE;
		}
	}
	
	public String replaceIfOdd(String stringToChange,
	        String searchingWord, String replacingWord) {
	    final String separator = "#######";
	    String splittingString = stringToChange.replaceAll(searchingWord,
	            separator + searchingWord);
	    String[] splitArray = splittingString.split(separator);
	    String result = "";
	    for (int i = 0; i < splitArray.length; i++) {
	        if (i % 2 == 1)
	            splitArray[i] = splitArray[i].replace(searchingWord,
	                    replacingWord);
	        result += splitArray[i];
	    }
	    return result;
	}
	public String replaceIfEven(String stringToChange,
	        String searchingWord, String replacingWord) {
	    final String separator = "#######";
	    String splittingString = stringToChange.replaceAll(searchingWord,
	            separator + searchingWord);
	    String[] splitArray = splittingString.split(separator);
	    String result = "";
	    for (int i = 0; i < splitArray.length; i++) {
	        if (i % 2 == 0)
	            splitArray[i] = splitArray[i].replace(searchingWord,
	                    replacingWord);
	        result += splitArray[i];
	    }
	    return result;
	}

}
