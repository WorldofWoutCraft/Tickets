package com.woutwoot.tickets.db;

import com.woutwoot.tickets.Main;
import com.woutwoot.tickets.ticket.Ticket;
import com.woutwoot.tickets.tools.SQLDatabase;
import com.woutwoot.tickets.tools.sqlite.SQLite;
import org.bukkit.Location;

import java.io.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        if(ticketExists(ticket)) {
            int ticketId = ticket.getId();
            String ticketStatus = ticket.getStatus().toString();
            String ticketType = ticket.getType().toString();
            String description = ticket.getDescription();
            String owneruuid = ticket.getOwnerUUID().toString();
            Date dateasked = dateToString(ticket.getDateAsked());
            Date dateclosed = dateToString(ticket.getDateClosed());
            int priority = ticket.getPriority();
            int locationid = getLocationId(ticket.getLocation());

            String sql = "UPDATE tickets SET ticketstatus=?, tickettype=?, description=?, owneruuid=?, dateasked=?, dateclosed=?, priority=?, locationid=? WHERE ticketid=?";

            try {
                PreparedStatement ps = db.getConnection().prepareStatement(sql);
                ps.setString(1, ticketStatus);
                ps.setString(2, ticketType);
                ps.setString(3, description);
                ps.setString(4, owneruuid);
                ps.setDate(5, dateasked);
                ps.setDate(6, dateclosed);
                ps.setInt(7, priority);
                ps.setInt(8, locationid);
                ps.setInt(9, ticketId);
                db.updatePrepSQL(ps);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            createTicket(ticket);
        }
    }

    private boolean ticketExists(Ticket ticket) {
        try {
            ResultSet res = db.querySQL("SELECT ticketid FROM tickets WHERE ticketid=" + ticket.getId());
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createTicket(Ticket ticket){
        int ticketId = ticket.getId();
        String ticketStatus = ticket.getStatus().toString();
        String ticketType = ticket.getType().toString();
        String description = ticket.getDescription();
        String owneruuid = ticket.getOwnerUUID().toString();
        Date dateasked = dateToString(ticket.getDateAsked());
        Date dateclosed = dateToString(ticket.getDateClosed());
        int priority = ticket.getPriority();
        int locationid = getLocationId(ticket.getLocation());

        String sql = "INSERT INTO tickets (ticketid, ticketstatus, tickettype, description, owneruuid, dateasked, dateclosed, priority, locationid) VALUES (?, ?, ?, ?, ?, ? ,? ,?, ?);";

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, ticketId);
            ps.setString(2, ticketStatus);
            ps.setString(3, ticketType);
            ps.setString(4, description);
            ps.setString(5, owneruuid);
            ps.setDate(6, dateasked);
            ps.setDate(7, dateclosed);
            ps.setInt(8, priority);
            ps.setInt(9, locationid);
            db.updatePrepSQL(ps);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int getLocationId(Location location) {
        return 0;
    }

    private void createTables() {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream is = cl.getResourceAsStream("sql/createTables.sql");
        String sql = slurp(is, 2048);
        try {
            db.updateSQL(sql);
        } catch (SQLException | ClassNotFoundException e) {
            Main.getInstance().getLogger().warning("*** FAILED CREATING DATABASE ***");
        }
    }

    /**
     * Reads text from an InputStream into a string
     * @param is InputStream
     * @param bufferSize 2048
     * @return String
     */
    public static String slurp(final InputStream is, final int bufferSize){
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

    public static Date dateToString(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
}
