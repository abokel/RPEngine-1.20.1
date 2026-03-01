package com.Alvaeron.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.utils.NametagBridge;

public class RoleplayPlayer {

	UUID uuid;
	String playerName;
	String name = "NONE";
	String race = "NONE";
	String nation = "NONE";
	Gender gender = Gender.NONE;
	int age = 0;
	String desc = "NONE";
	Channel channel = Channel.RP;
	boolean OOC = false;
	boolean online = true;
	boolean headNameHidden = false;
	boolean tabListHidden = false;
	private Engine plugin;

	public RoleplayPlayer(UUID uuid, String playerName, String name, String race, String nation, Gender gender,
			int age, String desc, Channel channel, boolean OOC, boolean online, Engine plugin) {
		this.uuid = uuid;
		this.playerName = playerName;
		this.name = name;
		this.race = race;
		this.nation = nation;
		this.gender = gender;
		this.age = age;
		this.desc = desc;
		this.channel = channel;
		this.OOC = OOC;
		this.online = online;
		this.plugin = plugin;
		if (plugin != null) {
			this.headNameHidden = plugin.getConfig().getBoolean("display.hideHeadNamesDefault", false);
			this.tabListHidden = plugin.getConfig().getBoolean("display.hideTabListDefault", false);
		}
	}

	public RoleplayPlayer(Player pl) {
		this.uuid = pl.getUniqueId();
		this.playerName = pl.getName();
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getName() {
		return name;
	}

	public String getRace() {
		return race;
	}

	public String getNation() {
		return nation;
	}

	public Gender getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public String getDesc() {
		return desc;
	}

	public Channel getChannel() {
		return channel;
	}

	public boolean isOOC() {
		return OOC;
	}

	public boolean isOnline() {
		return online;
	}

	public Engine getPlugin() {
		return plugin;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
		Engine.mm.setStringField(uuid, "gender", gender.name());
		Engine.card.saveCard(this);
	}

	public void setAge(int age) {
		this.age = age;
		getPlayer().sendMessage(ChatColor.GREEN + "Age updated to" + ChatColor.WHITE + ": " + age);
		Engine.mm.setStringField(uuid, "age", age + "");
		Engine.card.saveCard(this);
	}

	public void setDesc(String description) {
		this.desc = description;
		Engine.mm.setStringField(uuid, "desc", desc);
		Engine.card.saveCard(this);
	}

	public void setName(String name) {
		this.name = name;
		Engine.mm.setStringField(uuid, "name", name);
		Engine.card.saveCard(this);
		setTag();
	}

	public void setRace(String race) {
		this.race = race;
		Engine.mm.setStringField(uuid, "race", race);
		Engine.card.saveCard(this);
		setTag();
	}

	public void setNation(String nation) {
		this.nation = nation;
		Engine.mm.setStringField(uuid, "nation", nation);
		Engine.card.saveCard(this);
	}

	public void switchOOC() {
		if (OOC) {
			getPlayer().sendMessage(ChatColor.RED + "OOC chat is off now");
			Engine.mm.setStringField(uuid, "ooc", "0");
			OOC = false;
		} else {
			getPlayer().sendMessage(ChatColor.GREEN + "OOC chat is on now");
			Engine.mm.setStringField(uuid, "ooc", "1");
			OOC = true;
		}

	}

	public void setChannel(Channel channel) {
		if (this.channel == channel) {
			getPlayer().sendMessage(ChatColor.YELLOW + "You are already chatting in" + ChatColor.WHITE + ": " + channel.name());
		} else {
			getPlayer().sendMessage(ChatColor.YELLOW + "You are now chatting in" + ChatColor.WHITE + ": " + channel.name());
		}
		this.channel = channel;
	}
	public void setOOC(boolean ooc){
		this.OOC = ooc;
	}

	public boolean isHeadNameHidden() {
		return headNameHidden;
	}

	public void setHeadNameHidden(boolean headNameHidden) {
		this.headNameHidden = headNameHidden;
		setTag();
	}

	public boolean isTabListHidden() {
		return tabListHidden;
	}

	public void setTabListHidden(boolean tabListHidden) {
		this.tabListHidden = tabListHidden;
		applyTabListVisibility();
	}

	public void setTag() {
		Player player = getPlayer();
		if (player == null) {
			return;
		}
		String rpName = name == null ? "NONE" : name;
		rpName = rpName.substring(0, Math.min(32, rpName.length()));

		NametagBridge.clearNametag(player);
		NametagBridge.setNameTagVisible(player, false);
		if (!headNameHidden) {
			Engine.overheadNames.show(player, Engine.mu.getRaceColour(race) + rpName + ChatColor.RESET);
		} else {
			Engine.overheadNames.hide(player);
		}
		applyTabListVisibility();
	}

	private void applyTabListVisibility() {
		Player player = getPlayer();
		if (player == null) {
			return;
		}
		if (tabListHidden) {
			player.setPlayerListName(" ");
		} else {
			player.setPlayerListName(null);
		}
	}

	public static enum Channel {
		OOC, RP;
	}

	public static enum Gender {
		MALE, FEMALE, NONE;
		public String getName() {
			return this == Gender.NONE ? "NONE" : Character.toString(this.name().charAt(0)).toUpperCase() + this.name().toLowerCase().substring(1);
		}
	}
}
