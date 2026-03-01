package com.Alvaeron.utils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;

public class OverheadNameManager {

	@SuppressWarnings("unused")
    private final Engine plugin;
	private final Map<UUID, UUID> labels = new ConcurrentHashMap<UUID, UUID>();

	public OverheadNameManager(Engine plugin) {
		this.plugin = plugin;
	}

	public void show(Player player, String displayName) {
		if (player == null || !player.isOnline()) {
			return;
		}
		ArmorStand stand = getOrCreate(player);
		if (stand == null) {
			return;
		}
		stand.setCustomName(displayName);
		stand.setCustomNameVisible(true);
		stand.setVisible(false);
		stand.setInvulnerable(true);
		stand.setGravity(false);
		stand.setSmall(true);
		stand.setMarker(true);
		stand.teleport(getNameLocation(player));
	}

	public void hide(Player player) {
		remove(player);
	}

	public void tick() {
		for (UUID playerId : labels.keySet()) {
			Player player = Bukkit.getPlayer(playerId);
			if (player == null || !player.isOnline()) {
				remove(playerId);
				continue;
			}
			ArmorStand stand = getStand(playerId);
			if (stand == null || stand.isDead() || !stand.getWorld().equals(player.getWorld())) {
				remove(playerId);
				continue;
			}
			stand.teleport(getNameLocation(player));
		}
	}

	public void remove(Player player) {
		if (player == null) {
			return;
		}
		remove(player.getUniqueId());
	}

	public void clearAll() {
		for (UUID playerId : labels.keySet()) {
			remove(playerId);
		}
		labels.clear();
	}

	private void remove(UUID playerId) {
		UUID standId = labels.remove(playerId);
		if (standId == null) {
			return;
		}
		Entity entity = Bukkit.getEntity(standId);
		if (entity != null) {
			entity.remove();
		}
	}

	private ArmorStand getOrCreate(Player player) {
		ArmorStand stand = getStand(player.getUniqueId());
		if (stand != null && !stand.isDead()) {
			return stand;
		}
		Location at = getNameLocation(player);
		Entity entity = player.getWorld().spawnEntity(at, EntityType.ARMOR_STAND);
		if (!(entity instanceof ArmorStand)) {
			entity.remove();
			return null;
		}
		stand = (ArmorStand) entity;
		labels.put(player.getUniqueId(), stand.getUniqueId());
		return stand;
	}

	private ArmorStand getStand(UUID playerId) {
		UUID standId = labels.get(playerId);
		if (standId == null) {
			return null;
		}
		Entity entity = Bukkit.getEntity(standId);
		if (entity instanceof ArmorStand) {
			return (ArmorStand) entity;
		}
		return null;
	}

	private Location getNameLocation(Player player) {
		Location eye = player.getLocation().clone();
		eye.setY(eye.getY() + 2.35D);
		return eye;
	}
}
