package com.Alvaeron.utils;

import java.util.Set;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.player.RoleplayPlayer;

public class Card {
	private Engine plugin;

	public Card(Engine plugin) {
		this.plugin = plugin;
	}

	public void sendCard(RoleplayPlayer rpp) {
		String race = safeValue(rpp.getRace());
		String nation = safeValue(rpp.getNation());
		rpp.getPlayer().sendMessage(Lang.CARD_OWN.toString());
		rpp.getPlayer().sendMessage(Lang.CARD_CLICK_TO_EDIT_FIELDS.toString());
		sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_NAME.toString() + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card name " + rpp.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + rpp.getName() + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card name " + rpp.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
		sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_AGE.toString() + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card age " + rpp.getAge() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + rpp.getAge() + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card age " + rpp.getAge() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
		sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_GENDER.toString() + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card gender\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + Lang.valueOf("CARD_FIELD_GENDER_" + rpp.getGender().name().toUpperCase()) + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card gender\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
		String color = resolveRaceColor(race);
		sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_RACE.toString() + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card race\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + race + "\",\"color\":\"" + color + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card race\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
		sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_NATION.toString() + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card nation\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + nation + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card nation\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
		sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_DESC.toString() + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card desc " + rpp.getDesc() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + rpp.getDesc() + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card desc " + rpp.getDesc() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
	}

	public void sendCardOther(RoleplayPlayer rpp, Player reciever) {
		reciever.sendMessage(Lang.CARD_OTHERS.toString().replace("%p", rpp.getPlayer().getName()));
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_NAME.toString() + ": " + ChatColor.WHITE + rpp.getName());
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_AGE.toString() + ": " + ChatColor.WHITE + rpp.getAge());
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_GENDER.toString() + ": " + ChatColor.WHITE + Lang.valueOf("CARD_FIELD_GENDER_" + rpp.getGender().getName().toUpperCase()).toString());
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_RACE.toString() + ": " + ChatColor.WHITE + safeValue(rpp.getRace()));
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_NATION.toString() + ": " + ChatColor.WHITE + safeValue(rpp.getNation()));
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_DESC.toString() + ": " + ChatColor.WHITE + rpp.getDesc());
	}

	public void sendGenderSelect(RoleplayPlayer rpp) {
		rpp.getPlayer().sendMessage(Lang.CARD_SELECT_GENDER.toString());
		sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_GENDER_MALE.toString() + "\",\"color\":\"blue\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card gender male\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_GENDER.toString().replace("%g", Lang.CARD_FIELD_GENDER_MALE.toString()) + "\",\"color\":\"aqua\"}]}}}]");
		sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_GENDER_FEMALE.toString() + "\",\"color\":\"light_purple\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card gender female\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_GENDER.toString().replace("%g", Lang.CARD_FIELD_GENDER_FEMALE.toString()) + "\",\"color\":\"aqua\"}]}}}]");
	}

	public void sendRaces(RoleplayPlayer rpp) {
		Set<String> races = Collections.emptySet();
		if (plugin.getConfig().getConfigurationSection("Races") != null) {
			races = plugin.getConfig().getConfigurationSection("Races").getKeys(false);
		} else if (plugin.getConfig().getConfigurationSection("races") != null) {
			races = plugin.getConfig().getConfigurationSection("races").getKeys(false);
		}
		rpp.getPlayer().sendMessage(Lang.CARD_SELECT_RACE.toString());
		if (races.isEmpty()) {
			rpp.getPlayer().sendMessage(ChatColor.RED + "No races configured.");
			return;
		}
		for (String race : races) {
			String color = plugin.getConfig().getString("Races." + race + ".Color");
			if (color == null) {
				color = plugin.getConfig().getString("races." + race + ".Color", "white");
			}
			sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + race + "\",\"color\":\"" + resolveTellrawColor(color, "white") + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card race " + race + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_RACE.toString().replace("%r", race) + "\",\"color\":\"aqua\"}]}}}]");
		}
	}

	public void sendNations(RoleplayPlayer rpp) {
		Set<String> nations = Collections.emptySet();
		if (plugin.getConfig().getConfigurationSection("Nations") != null) {
			nations = plugin.getConfig().getConfigurationSection("Nations").getKeys(false);
		} else if (plugin.getConfig().getConfigurationSection("nations") != null) {
			nations = plugin.getConfig().getConfigurationSection("nations").getKeys(false);
		}
		rpp.getPlayer().sendMessage(Lang.CARD_SELECT_NATION.toString());
		if (nations.isEmpty()) {
			rpp.getPlayer().sendMessage(ChatColor.RED + "No nations configured.");
			return;
		}
		for (String nation : nations) {
			sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + nation + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card nation " + nation + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_NATION.toString().replace("%n", nation) + "\",\"color\":\"aqua\"}]}}}]");
		}
	}
	public void clickToSendCard(Player p){
		sendJson(p, "[\"\",{\"text\":\"" + Lang.CARD_CLICK_TO_SHOW.toString() + "\",\"color\":\"aqua\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_SHOW.toString() + "\",\"color\":\"aqua\"}]}}}]");
	}
	public void sendJson(Player p, String json){
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + json);
	}

	private String safeValue(String value) {
		if (value == null || value.trim().isEmpty()) {
			return "NONE";
		}
		return value;
	}

	private String resolveRaceColor(String race) {
		String configured = plugin.getConfig().getString("Races." + race + ".Color");
		if (configured == null) {
			configured = plugin.getConfig().getString("races." + race + ".Color");
		}
		return resolveTellrawColor(configured, "white");
	}

	private String resolveTellrawColor(String color, String fallback) {
		if (color == null) {
			return fallback;
		}
		switch (color.toUpperCase()) {
			case "BLACK":
			case "DARK_BLUE":
			case "DARK_GREEN":
			case "DARK_AQUA":
			case "DARK_RED":
			case "DARK_PURPLE":
			case "GOLD":
			case "GRAY":
			case "DARK_GRAY":
			case "BLUE":
			case "GREEN":
			case "AQUA":
			case "RED":
			case "LIGHT_PURPLE":
			case "YELLOW":
			case "WHITE":
				return color.toLowerCase();
			case "PURPLE":
				return "dark_purple";
			case "BROWN":
				return "gold";
			default:
				return fallback;
		}
	}
}
