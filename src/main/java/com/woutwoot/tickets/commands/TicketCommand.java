package com.woutwoot.tickets.commands;

import com.woutwoot.tickets.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author woutwoot
 *         Created by on 1/01/2015 - 22:11.
 */
public class TicketCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("ticket")){
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("info")){
                    int id = Integer.parseInt(args[1]);
                    Main.getManager().sendInfo(id, sender);
                }
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("list")){
                    Main.getManager().sendList(sender);
                }
            }
            return true;
        }
        return false;
    }

}
