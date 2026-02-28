package com.Alvaeron.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;

public class Countdown
{
	private final Engine plugin;
	private int countdownTimer;

	public Countdown(final Engine i) {
		this.plugin = i;
	}

	public void startCountdown(final Player p, final boolean all, final int time) {
		this.countdownTimer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			int i = time;

			@Override
			public void run() {
				if (all) {
					Engine.mu.sendRangedMessage(p, ChatColor.GOLD + Integer.toString(this.i), "rpRange");
				}
				this.i--;
				if (this.i <= 0) {
					Countdown.this.cancel();
					Engine.mu.sendRangedMessage(p, "Go!", "rpRange");
				}
			}
		}, 0L, 20L);
	}

	public void cancel() {
		Bukkit.getScheduler().cancelTask(this.countdownTimer);
	}
}
