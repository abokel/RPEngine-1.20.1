package com.Alvaeron;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.Alvaeron.commands.BirdCommand;
import com.Alvaeron.commands.CardCommand;
import com.Alvaeron.commands.ChatCommands;
import com.Alvaeron.commands.CountdownCommand;
import com.Alvaeron.commands.DisplayCommand;
import com.Alvaeron.commands.RPEngineCommand;
import com.Alvaeron.commands.RollCommand;
import com.Alvaeron.commands.SpawnPointCommand;
import com.Alvaeron.listeners.EventListener;
import com.Alvaeron.nametags.Utils;
import com.Alvaeron.player.PlayerManager;
import com.Alvaeron.utils.Card;
import com.Alvaeron.utils.Lang;
import com.Alvaeron.utils.MessageUtil;
import com.Alvaeron.utils.OverheadNameManager;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Engine extends JavaPlugin {

	public static MessageUtil mu = null;
	public static MySQLManager mm = null;
	public static EventListener listener = null;
	public static PlayerManager manager = null;
	public static Card card = null;
	public static OverheadNameManager overheadNames = null;
	public static Engine rpEngine = null;
	public static Utils utils = null;
	public static YamlConfiguration LANG;
	public static File LANG_FILE;

	// Soft Dependencies
	public static Economy econ = null;
	public static Permission perms = null;
	public static Chat chat = null;
	public Boolean vaultEnabled = false;

	@Override
	public void onDisable() {
		if (overheadNames != null) {
			overheadNames.clearAll();
		}
		mm.onDisable();
	}

	@Override
	public void onEnable() {
		mu = new MessageUtil(this);
		mm = new MySQLManager(this);
		listener = new EventListener(this);
		manager = new PlayerManager(this);
		card = new Card(this);
		rpEngine = this;
		utils = new Utils(this);
		overheadNames = new OverheadNameManager(this);

		this.getServer().getPluginManager().registerEvents(manager, this);
		this.getServer().getPluginManager().registerEvents(Engine.listener, this);
		loadCommands();
		this.saveDefaultConfig();
		Engine.mm.OnEnable();
		Engine.mm.initDatabase();
		// On plugin reload, make sure that all online players are cached
		for (Player pl : Bukkit.getOnlinePlayers()) {
			mm.createRoleplayPlayer(pl);
		}
		checkSoftDependencies();

		loadLang();

		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				overheadNames.tick();
			}
		}, 1L, 1L);
	}

	private void loadCommands() {
		getCommand("card").setExecutor(new CardCommand(this));
		getCommand("roll").setExecutor(new RollCommand(this));
		getCommand("bird").setExecutor(new BirdCommand(this));
		getCommand("countdown").setExecutor(new CountdownCommand(this));
		getCommand("rpengine").setExecutor(new RPEngineCommand(this));
		getCommand("spawnpoint").setExecutor(new SpawnPointCommand(this));
		DisplayCommand displayCommand = new DisplayCommand(this);
		getCommand("nametag").setExecutor(displayCommand);
		getCommand("tablist").setExecutor(displayCommand);
		ChatCommands ch = new ChatCommands(this);
		getCommand("whisper").setExecutor(ch);
		getCommand("shout").setExecutor(ch);
		getCommand("RP").setExecutor(ch);
		getCommand("OOC").setExecutor(ch);
		getCommand("toggleooc").setExecutor(ch);
	}

	private boolean setupChat() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		if (rsp == null) {
			return false;
		}
		chat = rsp.getProvider();
		return chat != null;
	}

	public static String getVaultPrefix(Player player) {
		if (chat == null || player == null) {
			return "";
		}
		String prefix = chat.getPlayerPrefix(player);
		return prefix == null ? "" : prefix;
	}

	public static String getVaultSuffix(Player player) {
		if (chat == null || player == null) {
			return "";
		}
		String suffix = chat.getPlayerSuffix(player);
		return suffix == null ? "" : suffix;
	}

	/**
	 * Load the lang.yml file.
	 */
	public void loadLang() {
		File lang = new File(getDataFolder(), this.getConfig().getString("language") + ".yml");
		OutputStream out = null;
		InputStream defLangStream = this.getResource(this.getConfig().getString("language"));
		if (!lang.exists()) {
			lang = new File(getDataFolder(), "en_us.yml");
			defLangStream = this.getResource("en_us.yml");
			if (!lang.exists()) {
				try {
					getDataFolder().mkdir();
					lang.createNewFile();
					if (defLangStream != null) {
						out = new FileOutputStream(lang);
						int read;
						byte[] bytes = new byte[1024];

						while ((read = defLangStream.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}
						YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(lang);
						Lang.setFile(defConfig);
					}
				} catch (IOException e) {
					if(Engine.utils.sendDebug()){
						e.printStackTrace();
					}
					getLogger().severe("[RPEngine] Couldn't create language file.");
					getLogger().severe("[RPEngine] This is a fatal error. Now disabling");
					this.setEnabled(false); // Without it loaded, we can't send them messages
				} finally {
					if (defLangStream != null) {
						try {
							defLangStream.close();
						} catch (IOException e) {
							if(Engine.utils.sendDebug()){
								e.printStackTrace();
							}
						}
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						if(Engine.utils.sendDebug()){
							e.printStackTrace();
						}
					}

				}
			}
		}

		YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
		for (Lang item : Lang.values()) {
			if (conf.getString(item.getPath()) == null) {
				conf.set(item.getPath(), item.getDefault());
			}
		}
		migrateChatFormats(conf);

		Lang.setFile(conf);
		try {
			conf.save(lang);
		} catch (IOException e) {
			getLogger().log(Level.WARNING, "RPEngine: Failed to save lang.yml.");
			getLogger().log(Level.WARNING, "RPEngine: Report this stack trace to a tech.");
			if(Engine.utils.sendDebug()){
				e.printStackTrace();
			}
		}
	}

	private void migrateChatFormats(YamlConfiguration conf) {
		String currentShout = conf.getString(Lang.CHAT_SHOUT_FORMAT.getPath(), "");
		String currentWhisper = conf.getString(Lang.CHAT_WHISPER_FORMAT.getPath(), "");

		if ("&c[Shout] &7%n &f%m".equals(currentShout)) {
			conf.set(Lang.CHAT_SHOUT_FORMAT.getPath(), Lang.CHAT_SHOUT_FORMAT.getDefault());
		}
		if ("&9[Whisper] &7%n &f%m".equals(currentWhisper)) {
			conf.set(Lang.CHAT_WHISPER_FORMAT.getPath(), Lang.CHAT_WHISPER_FORMAT.getDefault());
		}
	}
	public void debug(String message) {
        if (this.getConfig().getBoolean("debug")) {
            getLogger().info("[DEBUG] " + message);
        }
	}
	private void checkSoftDependencies() {
		if (!setupChat()) {
			getLogger().log(Level.WARNING, "Vault chat provider not found.");
			vaultEnabled = false;
		} else {
			vaultEnabled = true;
		}
	}
	public void disablePlugin(){
		this.setEnabled(false);
	}
}
