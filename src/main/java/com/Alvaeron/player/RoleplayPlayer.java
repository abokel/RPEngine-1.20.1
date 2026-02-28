package com.Alvaeron.player;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.nametagedit.plugin.NametagEdit;

@AllArgsConstructor
@Getter
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
	private Engine plugin;

	public RoleplayPlayer(Player pl) {
		this.uuid = pl.getUniqueId();
		this.playerName = pl.getName();
	}

	public Player getPlayer() {
		if(Bukkit.getPlayer(uuid) != null){
			return Bukkit.getPlayer(uuid);
		}else{
			return (Player) Bukkit.getOfflinePlayer(uuid);
		}
	}

	public void setGender(Gender gender) {
		this.gender = gender;
		Engine.mm.setStringField(uuid, "gender", gender.name());
	}

	public void setAge(int age) {
		this.age = age;
		getPlayer().sendMessage(ChatColor.GREEN + "Age updated to" + ChatColor.WHITE + ": " + age);
		Engine.mm.setStringField(uuid, "age", age + "");
	}

	public void setDesc(String description) {
		this.desc = description;
		Engine.mm.setStringField(uuid, "desc", desc);
	}

	public void setName(String name) {
		this.name = name;
		Engine.mm.setStringField(uuid, "name", name);
		setTag();
	}

	public void setRace(String race) {
		this.race = race;
		Engine.mm.setStringField(uuid, "race", race);
		setTag();
	}

	public void setNation(String nation) {
		this.nation = nation;
		Engine.mm.setStringField(uuid, "nation", nation);
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
	public void setTag() {
		NametagEdit.getApi().setPrefix(getPlayer(), Engine.mu.getRaceColour(race) + name.substring(0, Math.min(9, name.length())) + ChatColor.GRAY + " [");
		NametagEdit.getApi().setSuffix(getPlayer(), "]");
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
