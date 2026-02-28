package com.Alvaeron.nametags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.Alvaeron.Engine;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

	private Engine plugin;
	
	public Utils(Engine plugin) 
	{
		this.plugin = plugin;
	}
	
    public static String format(String[] text, int to, int from) {
        if (text == null || text.length == 0) {
            return "";
        }

        int start = Math.max(0, to);
        int end = Math.min(text.length, from);
        if (start >= end) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int index = start; index < end; index++) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(text[index]);
        }

        return builder.toString().replace("'", "");
    }

    public static String deformat(String input) {
        return input.replace("§", "&");
    }

    public static String format(String input) {
        return format(input, false);
    }

    public static String format(String input, boolean limitChars) {
        String colored = ChatColor.translateAlternateColorCodes('&', input);
        return limitChars && colored.length() > 16 ? colored.substring(0, 16) : colored;
    }

    public static List<Player> getOnline() {
        List<Player> list = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            list.addAll(world.getPlayers());
        }

        return Collections.unmodifiableList(list);
    }

    public static YamlConfiguration getConfig(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
			if(Engine.utils.sendDebug()){
				e.printStackTrace();
			}
        }

        return YamlConfiguration.loadConfiguration(file);
    }
	public boolean sendDebug(){
		if(plugin.getConfig().contains("debug")){
			return plugin.getConfig().getBoolean("debug");
		}
		return false;
	}
    public static YamlConfiguration getConfig(File file, String resource, Plugin plugin) {
        try {
            if (!file.exists()) {
                file.createNewFile();
                InputStream inputStream = plugin.getResource(resource);
                OutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
			if(Engine.utils.sendDebug()){
				e.printStackTrace();
			}
        }

        return YamlConfiguration.loadConfiguration(file);
    }

}