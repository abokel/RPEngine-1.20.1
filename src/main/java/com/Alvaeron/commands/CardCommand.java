package com.Alvaeron.commands;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.listeners.CardEditEvent;
import com.Alvaeron.listeners.CardEditEvent.CardField;
import com.Alvaeron.player.RoleplayPlayer.Gender;
import com.Alvaeron.utils.Cooldown;
import com.Alvaeron.utils.Lang;

public class CardCommand extends AbstractCommand {

	public CardCommand(Engine plugin) {
		super(plugin, Senders.PLAYER);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
		// Name Field
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("name")) {
				if (args.length >= 2) {
					if (cardTime(player, "name")) {
						final StringBuilder sb2 = new StringBuilder();
						for (int j = 1; j < args.length; ++j) {
							sb2.append(args[j]).append(" ");
						}
						final String name = sb2.toString().trim().replace("\"", "");
						CardEditEvent event = new CardEditEvent(CardField.NAME, name, player, rpp);
						Bukkit.getServer().getPluginManager().callEvent(event);
						if (!event.isCancelled()) {
							rpp.setName(event.getStringValue());
							rpp.setTag();
							player.sendMessage(Lang.CARD_UPDATED_TO.toString()
									.replace("%f", Lang.CARD_FIELD_NAME.toString()).replace("%v", name));
							Engine.card.clickToSendCard(player);
						}
					}
				} else {
					player.sendMessage(Lang.CARD_NAME_USAGE.toString());
				}
			}
			// Age Field
			else if (args[0].equalsIgnoreCase("age")) {
				if (args.length == 2) {
					try {
						int age = Integer.parseInt(args[1]);
						String race = rpp.getRace();
						if (plugin.getConfig().contains("Races." + race + ".MaxAge")) {
							if (age <= plugin.getConfig().getInt("Races." + race + ".MaxAge")) {
								if (cardTime(player, "age")) {
									CardEditEvent event = new CardEditEvent(CardField.AGE, age, player, rpp);
									Bukkit.getServer().getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										rpp.setAge(event.getIntValue());
										player.sendMessage(Lang.CARD_UPDATED_TO.toString()
												.replace("%f", Lang.CARD_FIELD_AGE.toString()).replace("%v", age + ""));
										Engine.card.clickToSendCard(player);
									}
								}
							} else {
								if (plugin.getConfig().contains("Races." + race + ".Plural")) {
									player.sendMessage(Lang.CARD_AGE_RACE_MAX.toString()
											.replace("%r", plugin.getConfig().getString("Races." + race + ".Plural"))
											.replace("%a", Integer
													.toString(plugin.getConfig().getInt("Races." + race + ".MaxAge"))));
								} else {
									player.sendMessage(Lang.CARD_AGE_MAX.toString().replace("%a",
											Integer.toString(plugin.getConfig().getInt("Races." + race + ".MaxAge"))));
								}
							}
						} else {
							if (plugin.getConfig().contains("MaxAge")) {
								if (age <= plugin.getConfig().getInt("MaxAge")) {
									if (cardTime(player, "age")) {
										CardEditEvent event = new CardEditEvent(CardField.AGE, age, player, rpp);
										Bukkit.getServer().getPluginManager().callEvent(event);
										if (!event.isCancelled()) {
											rpp.setAge(event.getIntValue());
											player.sendMessage(Lang.CARD_UPDATED_TO.toString()
													.replace("%f", Lang.CARD_FIELD_AGE.toString())
													.replace("%v", age + ""));
											Engine.card.clickToSendCard(player);
										}
									}
								} else {
									player.sendMessage(Lang.CARD_AGE_MAX.toString().replace("%a",
											Integer.toString(plugin.getConfig().getInt("MaxAge"))));
								}
							} else {
								if (cardTime(player, "age")) {
									CardEditEvent event = new CardEditEvent(CardField.AGE, age, player, rpp);
									Bukkit.getServer().getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										rpp.setAge(event.getIntValue());
										player.sendMessage(Lang.CARD_UPDATED_TO.toString()
												.replace("%f", Lang.CARD_FIELD_AGE.toString()).replace("%v", age + ""));
										Engine.card.clickToSendCard(player);
									}
								}
							}
						}
					} catch (NumberFormatException e) {
						player.sendMessage(Lang.MUST_BE_NUMBER.toString());
					}
				} else {
					player.sendMessage(Lang.CARD_AGE_USAGE.toString());
				}
			}
			// Gender Field
			else if (args[0].equalsIgnoreCase("gender")) {
				if (args.length == 2) {
					if (args[1].equalsIgnoreCase("male")) {
						if (cardTime(player, "gender")) {
							CardEditEvent event = new CardEditEvent(CardField.GENDER, Gender.MALE, player, rpp);
							Bukkit.getServer().getPluginManager().callEvent(event);
							if (!event.isCancelled()) {
								rpp.setGender(event.getGender());
								player.sendMessage(
										Lang.CARD_UPDATED_TO.toString().replace("%f", Lang.CARD_FIELD_GENDER.toString())
												.replace("%v", Lang.CARD_FIELD_GENDER_MALE.toString()));
								Engine.card.clickToSendCard(player);
							}
						}
					} else if (args[1].equalsIgnoreCase("female")) {
						if (cardTime(player, "gender")) {
							CardEditEvent event = new CardEditEvent(CardField.GENDER, Gender.FEMALE, player, rpp);
							Bukkit.getServer().getPluginManager().callEvent(event);
							if (!event.isCancelled()) {
								rpp.setGender(event.getGender());
								player.sendMessage(
										Lang.CARD_UPDATED_TO.toString().replace("%f", Lang.CARD_FIELD_GENDER.toString())
												.replace("%v", Lang.CARD_FIELD_GENDER_FEMALE.toString()));
								Engine.card.clickToSendCard(player);
							}
						}
					} else {
						player.sendMessage(Lang.CARD_GENDER_USAGE.toString());
					}
				} else {
					Engine.card.sendGenderSelect(rpp);
				}
			}
			// Race Field
			else if (args[0].equalsIgnoreCase("race")) {
				final Set<String> races = plugin.getConfig().getConfigurationSection("Races").getKeys(false);
				if (args.length == 2) {
					if (Engine.mu.containsCaseInsensitive(args[1], races)) {
						if (cardTime(player, "race")) {
							CardEditEvent event = new CardEditEvent(CardField.RACE,
									Engine.mu.getValueFromSet(args[1], races), player, rpp);
							Bukkit.getServer().getPluginManager().callEvent(event);
							if (!event.isCancelled()) {
								rpp.setRace(event.getStringValue());
								player.sendMessage(Lang.CARD_UPDATED_TO.toString()
										.replace("%f", Lang.CARD_FIELD_RACE.toString()).replace("%v", args[1]));
								Engine.card.clickToSendCard(player);
								rpp.setTag();
							}
						}
					} else {
						final StringBuilder sb = new StringBuilder();
						for (final String s : races) {
							sb.append(s);
							sb.append("/");
						}
						final String raceString = sb.toString().trim();
						player.sendMessage(Lang.CARD_RACE_USAGE.toString().replace("%r",
								raceString.substring(0, raceString.length() - 1)));
					}
				} else {
					Engine.card.sendRaces(rpp);
				}
			}
			// Nation Field
			else if (args[0].equalsIgnoreCase("nation")) {
				Set<String> nations = plugin.getConfig().getConfigurationSection("Nations").getKeys(false);
				if (args.length == 2) {
					if (Engine.mu.containsCaseInsensitive(args[1], nations)) {
						if (cardTime(player, "nation")) {
							CardEditEvent event = new CardEditEvent(CardField.NATION,
									Engine.mu.getValueFromSet(args[1], nations), player, rpp);
							Bukkit.getServer().getPluginManager().callEvent(event);
							if (!event.isCancelled()) {
								rpp.setNation(event.getStringValue());
								player.sendMessage(Lang.CARD_UPDATED_TO.toString()
										.replace("%f", Lang.CARD_FIELD_NATION.toString()).replace("%v", args[1]));
								Engine.card.clickToSendCard(player);
							}
						}
					} else {
						StringBuilder sb = new StringBuilder();
						for (String s : nations) {
							sb.append(s);
							sb.append("/");
						}
						String nationString = sb.toString().trim();
						player.sendMessage(Lang.CARD_NATION_USAGE.toString().replace("%n",
								nationString.substring(0, nationString.length() - 1)));
					}
				} else {
					Engine.card.sendNations(rpp);
				}
			}
			// Desc Field
			else if (args[0].equalsIgnoreCase("desc") || args[0].equalsIgnoreCase("description")) {
				if (args.length >= 2) {
					if (cardTime(player, "description")) {
						final StringBuilder sb2 = new StringBuilder();
						for (int j = 1; j < args.length; ++j) {
							sb2.append(args[j]).append(" ");
						}
						final String desc = sb2.toString().trim().replace("\"", "");
						CardEditEvent event = new CardEditEvent(CardField.DESC, desc, player, rpp);
						Bukkit.getServer().getPluginManager().callEvent(event);
						if (!event.isCancelled()) {
							rpp.setDesc(event.getStringValue());
							player.sendMessage(Lang.CARD_UPDATED_TO.toString()
									.replace("%f", Lang.CARD_FIELD_DESC.toString()).replace("%v", desc));
							Engine.card.clickToSendCard(player);
						}
					}
				} else {
					player.sendMessage(Lang.CARD_DESC_USAGE.toString());
				}
			}
			// Send players card
			else {
				Player csender = player.getServer().getPlayer(args[0]);
				if (csender != null) {
					Engine.card.sendCardOther(Engine.manager.getPlayer(csender.getUniqueId()), player);
				} else {
					player.sendMessage(Lang.CARD_OFFLINE.toString().replace("%p", args[0]));
				}
			}
		} else {
			Engine.card.sendCard(rpp);
		}

		return true;
	}

	/**
	 * Checks the cooldown on using /card set
	 * 
	 * @param player
	 *            The player object the check is being performed on
	 * @param type
	 *            the type of cooldown this is
	 */
	private boolean cardTime(final Player player, String type) {
		if (!Cooldown.tryCooldown(player, type, (plugin.getConfig().getInt("cardCooldown") * 1000))) {
			long timeLeft = TimeUnit.MILLISECONDS.toMinutes(Cooldown.getCooldown(player, type));
			player.sendMessage(
					ChatColor.RED + "You must wait " + timeLeft + " minutes before changing your " + type + " again.");
			return false;
		}
		return true;

	}

}
