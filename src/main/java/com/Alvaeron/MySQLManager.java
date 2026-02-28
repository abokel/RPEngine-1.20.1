package com.Alvaeron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Alvaeron.player.RoleplayPlayer;
import com.Alvaeron.player.RoleplayPlayer.Channel;
import com.Alvaeron.player.RoleplayPlayer.Gender;
import com.Alvaeron.utils.Lang;

public class MySQLManager {
	public Connection connection;
	private Engine plugin;
	private String dbType = "sqlite";
	private String myHost = null;
	private String myPort = null;
	private String myDB = null;
	private String myUser = null;
	private String myPassword = null;
	private String tablePrefix = "rpen_";

	public MySQLManager(final Engine plugin) {
		this.plugin = plugin;
	}
	
	public void OnEnable(){
		if(plugin.getConfig().contains("databasetype")){
			if(plugin.getConfig().getString("databasetype").equalsIgnoreCase("mysql")){
				boolean mysqlLegit = true;
				if(plugin.getConfig().contains("mysql.host")){
					myHost = plugin.getConfig().getString("mysql.host");
				}else{
					mysqlLegit = false;
				}
				if(plugin.getConfig().contains("mysql.port")){
					myPort = plugin.getConfig().getString("mysql.port");
				}else{
					mysqlLegit = false;
				}
				if(plugin.getConfig().contains("mysql.database")){
					myDB = plugin.getConfig().getString("mysql.database");
				}else{
					mysqlLegit = false;
				}
				if(plugin.getConfig().contains("mysql.user")){
					myUser = plugin.getConfig().getString("mysql.user");
				}else{
					mysqlLegit = false;
				}
				if(plugin.getConfig().contains("mysql.password")){
					myPassword = plugin.getConfig().getString("mysql.password");
				}else{
					mysqlLegit = false;
				}
				if(mysqlLegit){
					dbType = "mysql";
				}
			}
		}
		if(plugin.getConfig().contains("table-prefix")){
			tablePrefix = plugin.getConfig().getString("table-prefix");
		}
	}
	
	public void onDisable() {
		try {
			if ((this.connection == null) && !this.connection.isClosed()) {
				this.connection.close();
			}
		} catch (Exception e) {
			if(Engine.utils.sendDebug()){
				e.printStackTrace();
			}
		}
	}

	private void openConnection() {
		try {
			if (dbType == "mysql") {
				this.connection = DriverManager.getConnection(
						"jdbc:mysql://"
								+ this.myHost
								+ ":"
								+ this.myPort
								+ "/"
								+ this.myDB,
						new StringBuilder().append(this.myUser).toString(),
						new StringBuilder().append(this.myPassword).toString());
			} else {
				Class.forName("org.sqlite.JDBC");
				this.connection = DriverManager.getConnection("jdbc:sqlite:"
						+ this.plugin.getDataFolder() + "/data.db");
			}
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, Lang.TITLE.toString() + " Couldn't connect to database");
			plugin.getLogger().log(Level.SEVERE, Lang.TITLE.toString() + " This is a fatal error, disabling plugin");
			if(Engine.utils.sendDebug()){
				if(Engine.utils.sendDebug()){
					e.printStackTrace();
				}
			}
			plugin.disablePlugin();
		}
	}

	public void closeConnection() {
		try {
			this.connection.close();
		} catch (Exception e) {
			if(Engine.utils.sendDebug()){
				e.printStackTrace();
			}
		}
	}

	public void initDatabase() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				openConnection();
				try {
					final PreparedStatement sql = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + tablePrefix + "Cards` (`UUID` varchar(100) NOT NULL UNIQUE, `username` varchar(100), `name` varchar(250), `race` varchar(100), `nation` varchar(100), `gender` varchar(20), `age` INT, `desc` TEXT, `channel` varchar(20), `ooc` tinyint(1), `OOCban` tinyint(1), `BannedTill` DATETIME) ;");
					sql.execute();
					sql.close();
				} catch (Exception e) {
					if(Engine.utils.sendDebug()){
						e.printStackTrace();
					}
					return;
				} finally {
					closeConnection();
				}
				closeConnection();
			}
		});
	}
	public void createRoleplayPlayer(final Player p){
		createRoleplayPlayer(p.getUniqueId(), p.getName());
	}
	
	public void createRoleplayPlayer(final UUID uuid, final String playerName) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				openConnection();
				try {
					boolean online = true;
					if(Bukkit.getPlayer(uuid) == null){online = false;}
					PreparedStatement sql = connection.prepareStatement("SELECT * FROM `" + tablePrefix + "Cards` WHERE `UUID`=?;");
					sql.setString(1, uuid.toString());
					ResultSet rs = sql.executeQuery();
					if (rs.next()) {
						PreparedStatement sql3 = connection.prepareStatement("UPDATE `" + tablePrefix + "Cards` SET `username`=? WHERE `UUID`=?;");
						sql3.setString(1, playerName);
						sql3.setString(2, uuid.toString());
						sql3.executeUpdate();
						sql3.close();
						Engine.manager.addPlayer(new RoleplayPlayer(uuid, playerName, rs.getString("name"), rs.getString("race"), rs.getString("nation"), Gender.valueOf(rs.getString("gender").toUpperCase()), rs.getInt("age"), rs.getString("desc"), Channel.RP, true, online,plugin));
						if (rs.getInt("ooc") == 0) {
							setStringField(uuid, "ooc", "1");
						}
					} else {
						final PreparedStatement sql2 = connection.prepareStatement("INSERT INTO `" + tablePrefix + "Cards` (`UUID`, `username`, `name`, `race`, `nation`, `gender`, `age`, `desc`, `channel`, `ooc`) VALUES(?,?,?,?,?,?,?,?,?,?);");
						sql2.setString(1, uuid.toString());
						sql2.setString(2, playerName);
						sql2.setString(3, "NONE");
						sql2.setString(4, "NONE");
						sql2.setString(5, "NONE");
						sql2.setString(6, "NONE");
						sql2.setString(7, "0");
						sql2.setString(8, "NONE");
						sql2.setString(9, "RP");
						sql2.setString(10, "1");
						sql2.execute();
						sql2.close();
						Engine.manager.addPlayer(new RoleplayPlayer(uuid, playerName, playerName, "NONE", "NONE", Gender.NONE, 0, "NONE", Channel.RP, true, online,plugin));
					}
					sql.close();
					rs.close();
				} catch (SQLException e) {
					if(Engine.utils.sendDebug()){
						e.printStackTrace();
					}
					return;
				} finally {
					closeConnection();
				}
				Bukkit.getScheduler().runTask(plugin, new Runnable() {
					public void run() {
						Engine.manager.getPlayer(uuid).setTag();					
					}
				});
			}
		});

	}

	/**
	 * Set an String-based field in a players card
	 *
	 * @param u
	 *            - The players UUID
	 * @param field
	 *            - the field name
	 * @param data
	 *            - the String to set
	 */
	public void setStringField(final UUID u, final String field, final String data) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				openConnection();
				try {
					final PreparedStatement sql1 = connection.prepareStatement("UPDATE `" + tablePrefix + "Cards` SET `" + field + "`=? WHERE `UUID`=?;");
					sql1.setString(1, data);
					sql1.setString(2, u.toString());
					sql1.executeUpdate();
					sql1.close();
				} catch (Exception e) {
					if(Engine.utils.sendDebug()){
						e.printStackTrace();
					}
					return;
				} finally {
					closeConnection();
				}
				closeConnection();
			}
		});
	}

	/**
	 * Set an Integer-based field in a players card
	 *
	 * @param u
	 *            - The players UUID
	 * @param field
	 *            - the field name
	 * @param data
	 *            - the integer to set
	 */
	public void setIntegerField(final UUID u, final String field, final int data) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				openConnection();
				try {
					final PreparedStatement sql1 = connection.prepareStatement("UPDATE `" + tablePrefix + "Cards` SET `" + field + "`=? WHERE `UUID`=?;");
					sql1.setInt(1, data);
					sql1.setString(2, u.toString());
					sql1.executeUpdate();
					sql1.close();
				} catch (Exception e) {
					if(Engine.utils.sendDebug()){
						e.printStackTrace();
					}
					return;
				} finally {
					closeConnection();
				}
				closeConnection();
			}
		});
	}

	/**
	 * Sends the card of one player to another. Accepts a player object and a string
	 *
	 * @param sendto
	 *            The player object the card is being sent to
	 * @param cardOwner
	 *            The UUID of the player who owns the card
	 */

	/**
	 * Check if a value from a card is a default value
	 *
	 * @param text
	 *            value
	 * @param compare
	 *            default
	 * @return ChatColor.GRAY+value if default, else it just returns the value
	 */
	public String getDefaultValue(String text, String compare) {
		return text.equalsIgnoreCase(compare) ? ChatColor.GRAY + compare : text;
	}

}
