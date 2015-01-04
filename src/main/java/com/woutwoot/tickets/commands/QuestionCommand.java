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
 *         Created by on 3/01/2015 - 16:07.
 */
public class QuestionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("question")){
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
            Main.getManager().createTicket(p, desc, TicketType.QUESTION);
            sender.sendMessage(Vars.tag + "Question ticket created! Now just wait for us to contact you :)");
            return true;
        }
        return false;
    }

}
