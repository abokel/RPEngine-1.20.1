package com.Alvaeron.utils;

import java.lang.reflect.Method;

import org.bukkit.entity.Player;

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
}
