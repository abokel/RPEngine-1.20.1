package com.Alvaeron.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
 
/**
* An enum for requesting strings from the language file.
*/
public enum Lang {
    TITLE("title-name", "&4[&fRPEngine&4]"),
    PLAYER_ONLY("player-only", "Sorry but that can only be run by a player!"),
    NO_PERMS("no-permissions", "&cError: &fYou don't have permission for that!"),
    MUST_BE_NUMBER("must-be-number", "&cError: &fPlease use a number not a word!."),
    CHAT_DISABLED("chat-disabled", "&cError: &fChat functionality disabled by admin"),
    VERSION("version", "&6Currently running version: &f%v"),
    RELOAD("reload", "&6Plugin reloaded!"),
    RP_JOIN("rp-join", "&eYou are now chatting in &f: RP"),
    
    CARD_OWN("card-own", "&a-=-=-=-=- &bYour Card &a-=-=-=-=-"),
    CARD_OTHERS("card-others", "&a-=-=-=-=-&b %p's card &a-=-=-=-=-"),
    CARD_CLICK_TO_EDIT("card-click-to-edit", "Click to edit"),
    CARD_CLICK_TO_EDIT_FIELDS("card-click-to-edit-fields", "&bClick the fields to edit them"),
    CARD_CLICK_TO_GENDER("card-click-to-gender", "Click to set your gender to %g"),
    CARD_CLICK_TO_RACE("card-click-to-race", "Click to set your race to %r"),
    CARD_CLICK_TO_NATION("card-click-to-nation", "Click to set your nation to %n"),
    CARD_SELECT_GENDER("card-select-gender", "&aSelect your Gender"),
    CARD_SELECT_RACE("card-select-race", "&aSelect your Race"),
    CARD_SELECT_NATION("card-select-nation", "&aSelect your Nations"),
    CARD_UPDATED_TO("card-updated-to", "&a%f updated to&f: %v"),
    CARD_CLICK_TO_SHOW("card-click-to-show", "Click to show your card"),
    
    CARD_FIELD_NAME("card-field-name", "Name"),
    CARD_FIELD_AGE("card-field-age", "Age"),
    CARD_FIELD_GENDER("card-field-gender", "Gender"),
    CARD_FIELD_GENDER_MALE("card-field-gender-male", "Male"),
    CARD_FIELD_GENDER_FEMALE("card-field-gender-female", "Female"),
    CARD_FIELD_GENDER_NONE("card-field-gender-none", "None"),
    CARD_FIELD_RACE("card-field-race", "Race"),
    CARD_FIELD_NATION("card-field-nation", "Nation"),
    CARD_FIELD_DESC("card-field-desc", "Description"),
    
    BIRD_USAGE("bird-usage", "&cUsage: &f/bird [player] [message]"),
    BIRD_OFFLINE("bird-offline", "&f%p &cis offline"),
    BIRD_DIFFERENT_WORLD("bird-different-world", "&eYour bird gets lost in an interdimentional rift &c(Player is in another world)"),
    BIRD_PAPER("bird-paper", "&cYou need at least one piece of paper to send a bird!"),
    BIRD_LOST("bird-lost", "&cYour &abird &cgot lost"),
    BIRD_DELIVER("bird-deliver", "&bYour &abird &bhas delivered your letter to %n &f(%p)."),
    BIRD_LAND("bird-land", "&bA &abird &blands in front of you with a letter from %n &f(%p)."),
    BIRD_SENT("bird-sent", "&bYou've sent a &abird to %n &f(%p)."),
    
    ROLL_USAGE("roll-usage", "&cUsage: &f/roll [number]."),
    ROLL_MAX("roll-max", "&cNumber is too high. Please use a number below %n."),
    ROLL_RESULT("roll-result", "&b%r &f(%p) &bhas rolled &6%n &bout of &6%m."),
    ROLL_CONSOLE("roll-console", "&7 The Console &bhas rolled &6%n &bout of &6%m."),
    
    COUNTDOWN_USAGE("countdown-usage", "&cUsage: &f/countdown [time]."),
    COUNTDOWN_NEGATIVE("countdown-negative", "&CYou can not use a negative number!"),
    COUNTDOWN_MAX("countdown-max", "&cYou can not use a number over %m!"),
    COUNTDOWN_START("countdown-start", "&b%r &f(%p) &bhas started a countdown from &6%n."),
    
    SPAWNPOINT_USAGE("spawnpoint-usage", "&cUsage: &f/%c [%l]."),
    SPAWNPOINT_SET_USAGE("spawnpoint-set-usage", "&cUsage: &f/%c set [%l]."),
    SPAWNPOINT_SET("spawnpoint-set", "&aSpawnpoint of &f%n &aset to your location."),
    SPAWNPOINT_NO_NATION("spawnpoint-no-nation", "&cError: &fPlease set your nation with /card nation."),
    SPAWNPOINT_CONFIG_ERROR("spawnpoint-config-error", "&cError: &fSomething went wrong with retrieving data from the config file."),
    SPAWNPOINT_TELEPORT("spawnpoint-teleport", "&aYou have been teleported to &f%n."),
    
    CHAT_SHOUT_USAGE("chat-shout-usage", "&cUsage: &f/%c [message]."),
    CHAT_SHOUT_FORMAT("chat-shout-format", "&c[Shout] &7%n &f%m"),
    CHAT_WHISPER_USAGE("chat-whisper-usage", "&cUsage: &f/%c [message]."),
    CHAT_WHISPER_FORMAT("chat-whisper-format", "&9[Whisper] &7%n &f%m"),
    
    CARD_NAME_USAGE("card-name-usage", "&cUsage: &f/card name [name]."),
    CARD_AGE_RACE_MAX("card-age-race-max", "&cError: &g%r can not be any older than %a"),
    CARD_AGE_MAX("card-age-max", "&cError: &fYou can no be any older than %a"),
    CARD_AGE_USAGE("card-age-usage", "&cUsage: &f/card age [age]."),
    CARD_GENDER_USAGE("card-gender-usage", "&cUsage: &f/card gender [male/female]."),
    CARD_RACE_USAGE("card-race-usage", "&cUsage: &f/card race %r."),
    CARD_NATION_USAGE("card-nation-usage", "&cUsage: &f/card nation %n."),
    CARD_DESC_USAGE("card-desc-usage", "&cUsage: &f/card desc [description]."),
    CARD_OFFLINE("card-offline", "&cError: &f%p is offline.");
 
    private String path;
    private String def;
    private static YamlConfiguration LANG;
 
    /**
    * Lang enum constructor.
    * @param path The string path.
    * @param start The default string.
    */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }
 
    /**
    * Set the [@code YamlConfiguration} to use.
    * @param config The config to set.
    */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }
 
    @Override
    public String toString() {
        if (this == TITLE)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }
 
    /**
    * Get the default value of the path.
    * @return The default value of the path.
    */
    public String getDefault() {
        return this.def;
    }
 
    /**
    * Get the path to the string.
    * @return The path to the string.
    */
    public String getPath() {
        return this.path;
    }
}
 