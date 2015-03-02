package com.woutwoot.tickets.db;

import com.woutwoot.tickets.Main;
import com.woutwoot.tickets.ticket.Ticket;
import com.woutwoot.tickets.tools.SQLDatabase;
import com.woutwoot.tickets.tools.sqlite.SQLite;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
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
        db = new SQLite(Main.getInstance(), "tickets.db");
        createTables();
    }

    public void saveTickets(Map<Integer, Ticket> tickets){
        for(Ticket t : tickets.values()){
            updateTicket(t);
        }
    }

    public Map<Integer, Ticket> loadTickets(){
        return new HashMap<>();
    }

    public void updateTicket(Ticket ticket){
        //UPDATE Table1 SET (...) WHERE Column1='SomeValue'
        //IF @@ROWCOUNT=0
        //INSERT INTO Table1 VALUES (...)
    }

    private void createTables() {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream is = cl.getResourceAsStream("sql/createTables.sql");
        String sql = slurp(is, 2048);
        try {
            db.updateSQL(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String slurp(final InputStream is, final int bufferSize)
    {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(is, "UTF-8")) {
            for (;;) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

}
