package com.Alvaeron.utils;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class NametagBridge {

	private NametagBridge() {
	}

	private static Object getApi() {
		try {
			Class<?> nametagClass = Class.forName("com.nametagedit.plugin.NametagEdit");
			Method getApi = nametagClass.getMethod("getApi");
			return getApi.invoke(null);
		} catch (Exception ignored) {
			return null;
		}
	}

	private static boolean invokePlayerMethod(String methodName, Player player, String value) {
		Object api = getApi();
		if (api == null || player == null) {
			return false;
		}
		try {
			Method method = api.getClass().getMethod(methodName, Player.class, String.class);
			method.invoke(api, player, value);
			return true;
		} catch (Exception ignored) {
			return false;
		}
	}

	public static boolean clearNametag(Player player) {
		Object api = getApi();
		if (api == null || player == null) {
			return false;
		}
		try {
			Method method = api.getClass().getMethod("clearNametag", Player.class);
			method.invoke(api, player);
			return true;
		} catch (Exception ignored) {
			return false;
		}
	}

	public static boolean setPrefix(Player player, String prefix) {
		return invokePlayerMethod("setPrefix", player, prefix);
	}

	public static boolean setSuffix(Player player, String suffix) {
		return invokePlayerMethod("setSuffix", player, suffix);
	}

	@SuppressWarnings("deprecation")
	public static boolean setNameTagVisible(Player player, boolean visible) {
		if (player == null || Bukkit.getScoreboardManager() == null) {
			return false;
		}
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		String teamName = ("rpe_" + player.getUniqueId().toString().replace("-", "")).substring(0, 16);
		Team team = board.getTeam(teamName);
		if (team == null) {
			team = board.registerNewTeam(teamName);
		}
		if (!team.hasEntry(player.getName())) {
			team.addEntry(player.getName());
		}
		try {
			team.setOption(Team.Option.NAME_TAG_VISIBILITY, visible ? Team.OptionStatus.ALWAYS : Team.OptionStatus.NEVER);
		} catch (NoSuchMethodError ignored) {
			team.setNameTagVisibility(visible ? NameTagVisibility.ALWAYS : NameTagVisibility.NEVER);
		}
		player.setScoreboard(board);
		return true;
	}
}
