package com.woutwoot.tickets;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author woutwoot
 *         Created by on 26/04/2015 - 11:09.
 */
public class Messages {

    private static FileConfiguration conf;

    public static String get(String key) {
        if (conf.getString(key) == null) {
            return "Message key \"" + key + "\" not found in messages.yml";
        }
        return ChatColor.translateAlternateColorCodes('&', conf.getString(key));
    }

    public static void load() {
        try {
            loadMessages();
        } catch (Exception e) {
            Main.getLog().warning("Failed to load messages.yml!");
        }
    }

    private static void loadMessages() throws Exception {
        File folder = Main.getInstance().getDataFolder();
        conf = new YamlConfiguration();
        File confFile = new File(folder, "messages.yml");
        addDefaults();
        if (confFile.exists()) {
            conf.load(confFile);
        } else {
            conf.options().copyDefaults(true);
            conf.save(confFile);
        }
    }

    private static void addDefaults() {
        d("", "");
    }

    private static void d(String s, String t) {
        conf.addDefault(s, t);
    }
}
