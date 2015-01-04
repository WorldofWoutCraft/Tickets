package com.woutwoot.tickets.listeners;

import com.woutwoot.tickets.events.TicketCreateEvent;
import com.woutwoot.tickets.tools.Vars;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author woutwoot
 *         Created by on 4/01/2015 - 14:18.
 */
public class TicketCreateListener implements Listener {

    @EventHandler
    public void onTiketCreate(TicketCreateEvent event){
        for(Player p : getOnlineOps()){
            FancyMessage msg = new FancyMessage(Vars.tag + "A new ticket was created! Click here to view the ticket.");
            msg.tooltip(ChatColor.GOLD + "Click here to view ticket.");
            msg.command("/ticket info " + event.getTicket().getId());
            msg.send(p);
        }
    }

    public List<Player> getOnlineOps(){
        List<Player> pls = new ArrayList<>();
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(p.isOp()){
                pls.add(p);
            }
        }
        return pls;
    }

}
