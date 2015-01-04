package com.woutwoot.tickets.tasks;

import com.woutwoot.tickets.Main;
import com.woutwoot.tickets.tools.Vars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author woutwoot
 *         Created by on 4/01/2015 - 23:25.
 */
public class YourTicketsTask implements Runnable{

    @Override
    public void run() {
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(p.hasPermission("tickets.staff")){
                if(Main.getManager().isTicketAvailable(p)){
                    p.sendMessage(Vars.tag + "You've got tickets to solve!");
                    Main.getManager().sendList(p);
                }
            }
        }
    }

}
