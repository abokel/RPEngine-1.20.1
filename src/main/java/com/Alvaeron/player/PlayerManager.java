package com.Alvaeron.player;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Alvaeron.Engine;
import com.nametagedit.plugin.NametagEdit;

public class PlayerManager implements Listener {
	public Engine plugin;
	public ArrayList<RoleplayPlayer> players = new ArrayList<RoleplayPlayer>();

	public PlayerManager(Engine plugin) {
		this.plugin = plugin;
	}

	public RoleplayPlayer getPlayer(UUID uuid) {
		for (RoleplayPlayer p : players) {
			if (p.uuid.equals(uuid)) {
				return p;
			}
		}
		return null;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		clearPlayer(event.getPlayer().getUniqueId());
		NametagEdit.getApi().clearNametag(event.getPlayer());
	}

	public void clearPlayer(UUID u) {
		if (getPlayer(u) != null) {
			players.remove(getPlayer(u));
		}
	}

	public void addPlayer(RoleplayPlayer rpp) {
		clearPlayer(rpp.uuid);
		players.add(rpp);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		NametagEdit.getApi().clearNametag(player);
		Engine.mm.createRoleplayPlayer(player);
	}
}
