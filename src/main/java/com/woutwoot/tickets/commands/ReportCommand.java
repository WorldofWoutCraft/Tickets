package com.woutwoot.tickets.commands;

import com.woutwoot.tickets.Main;
import com.woutwoot.tickets.ticket.TicketType;
import com.woutwoot.tickets.tools.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author woutwoot
 *         Created by on 3/01/2015 - 15:53.
 */
public class ReportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("report")){
            Player p;
            if(sender instanceof Player){
                p = (Player) sender;
            }else{
                sender.sendMessage(Vars.tag + "You have to be a player to use this command.");
                return true;
            }
            String desc = "";
            for(String arg : args){
                desc += arg + " ";
            }
            Main.getManager().createTicket(p, desc, TicketType.REPORT);
            sender.sendMessage(Vars.tag + "Report ticket created! Now just wait for us to contact you :)");
            return true;
        }
        return false;
    }

}
