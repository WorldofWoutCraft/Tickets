package com.woutwoot.tickets.tools;

import org.bukkit.ChatColor;

/**
 * @author woutwoot
 *         Created by on 1/01/2015 - 22:01.
 */
public class Vars {

    public static final String tag = ChatColor.DARK_RED + "[Tickets] " + ChatColor.GOLD;

    public static String getTitle(String tickets) {
        int maxLength = 54;
        int textLength = tickets.length() + 2;
        int sideChars = (maxLength - textLength) / 2;

        String side = "";
        for (int i = 0; i < sideChars; i++){
            side += "-";
        }

        String title = side + " " + tickets + " " + side;
        return title;
    }

    public static String trimToMax(String s) {
        if(s.length() > 54){
            return s.substring(0, 51) + "...";
        }else{
            return s;
        }
    }
}
