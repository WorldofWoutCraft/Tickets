package com.woutwoot.tickets;

import com.woutwoot.tickets.ticket.Ticket;
import com.woutwoot.tickets.ticket.TicketStatus;
import com.woutwoot.tickets.ticket.TicketType;
import com.woutwoot.tickets.tools.Vars;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author woutwoot
 *         Created by on 1/01/2015 - 22:05.
 */
public class TicketManager {

    private Map<Integer, Ticket> tickets = new HashMap<>();
    private int currentID = 1;

    public TicketManager(){
        //TODO: Make this load and save from and to config.
        //currentID = Main.getInstance().getConfig().getInt("currentTicketID");
    }

    /**
     * Creates a new ticket.
     * @param owner The owner (asker) for the ticket that is being created.
     * @param desc The description for the ticket. (The question)
     * @param type The type of the ticket. For example grief, ...
     */
    public void createTicket(Player owner, String desc, TicketType type) throws TicketsException {
        if(tickets.containsKey(currentID)){
            throw new TicketsException("Problem while creating ticket.");
        }
        if(owner == null){
            throw new TicketsException("Owner may not be null!");
        }
        if(desc == null || desc.isEmpty()){
            throw new TicketsException("Description may not be null or empty.");
        }
        Ticket ticket = new Ticket(currentID, owner, desc);
        if(type != null){
            ticket.setType(type);
        }
        this.tickets.put(currentID, ticket);
        currentID++;
    }

    /**
     * Creates a new ticket.
     * @param owner The owner (asker) for the ticket that is being created.
     * @param desc The description for the ticket. (The question)
     */
    public void createTicket(Player owner, String desc) throws TicketsException {
        createTicket(owner, desc, null);
    }

    /**
     * Gets all open tickets for a user. This means dateClosed is null.
     * @param owner The player to search tickets for.
     * @return List of tickets for this player.
     */
    public List<Ticket> getOpenTickets(Player owner){
        List<Ticket> ticketsList = new ArrayList<>();
        for(Ticket t : tickets.values()){
            if(t.getDateClosed() == null) {
                if (t.getOwnerUUID().equals(owner.getUniqueId())) {
                    ticketsList.add(t);
                }
            }
        }
        return ticketsList;
    }

    public Ticket getTicket(int id) throws TicketsException {
        Ticket t = this.tickets.get(id);
        if(t == null){
            throw new TicketsException("Ticket not found!");
        }
        return t;
    }

    /**
     * Closes a ticket.
     * @param id
     * @throws TicketsException
     */
    public void closeTicket(int id, String closer) throws TicketsException {
        Ticket t = getTicket(id);
        t.close(closer);
    }

    public void changeTicketState(int id, TicketStatus status, String changer) throws TicketsException {
        Ticket t = getTicket(id);
        t.changeStatus(status, changer);
    }

    public void deleteTicket(int id) throws TicketsException {
        tickets.remove(id);
    }

    public void addComment(int id, String comment) throws TicketsException {
        Ticket t = getTicket(id);
        t.addComment("comment");
    }

    public void setTicketPriority(int id, int priority) throws TicketsException {
        Ticket t = getTicket(id);
        t.setPriority(priority);
    }

    public void saveTickets() {
        //TODO: Save to DB
    }

    public void loadTickets() {
        //TODO: Load from DB
    }

    public void sendInfo(int id, CommandSender sender) {
        Ticket t = getTicket(id);
        t.sendInfo(sender);
    }

    public void sendList(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_RED + Vars.getTitle("List of tickets"));
        for(Ticket t : tickets.values()){
            FancyMessage msg = new FancyMessage();
            msg.text(ChatColor.GOLD + Vars.trimToMax("- #" + t.getId() + "" + t.getStatus() + " -" + t.getDescription()));
            msg.tooltip(ChatColor.AQUA + "Created by: ", t.getOwnerName(), ChatColor.AQUA + "Description: ", t.getDescription());
            msg.send(sender);
        }
    }
}
