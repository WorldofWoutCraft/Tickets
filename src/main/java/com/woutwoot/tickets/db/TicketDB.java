package com.woutwoot.tickets.db;

import com.woutwoot.tickets.Main;
import com.woutwoot.tickets.ticket.Ticket;
import com.woutwoot.tickets.tools.SQLDatabase;
import com.woutwoot.tickets.tools.sqlite.SQLite;

import java.io.File;
import java.util.Map;

/**
 * @author woutwoot
 *         Created by on 1/01/2015 - 22:05.
 */
public class TicketDB {

    private SQLDatabase db;

    public TicketDB(){
        File folder = Main.getInstance().getDataFolder();
        folder.mkdir();
        db = new SQLite(Main.getInstance(), folder + File.separator + "tickets.db");
    }

    public void saveTickets(Map<Integer, Ticket> tickets){
        for(Ticket t : tickets.values()){
            updateTicket(t);
        }
    }

    public Map<Integer, Ticket> loadTickets(){
        return null;
    }

    public void updateTicket(Ticket ticket){

    }

}
