package com.woutwoot.tickets.ticket;

import com.woutwoot.tickets.Main;
import com.woutwoot.tickets.TicketsException;
import com.woutwoot.tickets.events.TicketCreateEvent;
import com.woutwoot.tickets.tools.Vars;
import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author woutwoot
 *         Created by on 1/01/2015 - 22:05.
 */
public class TicketManager {

    private Map<Integer, Ticket> tickets = new HashMap<>();
    private int currentID = 1;

    public TicketManager(){
        Main.getInstance().getConfig().addDefault("currentTicketID", 1);
        currentID = Main.getInstance().getConfig().getInt("currentTicketID");
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

        //Call custom event
        TicketCreateEvent event = new TicketCreateEvent(owner, ticket);
        Bukkit.getServer().getPluginManager().callEvent(event);
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

    /**
     * Gets a ticket by id.
     * @param id
     * @return
     * @throws TicketsException
     */
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
    public void closeTicket(int id, UUID closer) throws TicketsException {
        Ticket t = getTicket(id);
        t.close(closer);
    }

    /**
     * Completely deletes a ticket from the database. Can be used to remove spam.
     * @param id
     * @throws TicketsException
     */
    public void deleteTicket(int id) throws TicketsException {
        tickets.remove(id);
    }

    /**
     * Adds a comment to a ticket.
     * @param id
     * @param comment
     * @throws TicketsException
     */
    public void addComment(int id, UUID sender, String comment) throws TicketsException {
        Ticket t = getTicket(id);
        t.addComment(sender, comment);
    }

    /**
     * Sets the priority for a ticket.
     * @param id
     * @param priority
     * @throws TicketsException
     */
    public void setTicketPriority(int id, short priority) throws TicketsException {
        Ticket t = getTicket(id);
        t.setPriority(priority);
    }

    public void saveTickets() {
        //TODO
    }

    public void loadTickets() {
        //TODO
    }

    /**
     * Sends information about a ticket to the sender.
     * @param id
     * @param sender
     * @throws TicketsException
     */
    public void sendInfo(int id, CommandSender sender) throws TicketsException {
        Ticket t = getTicket(id);
        t.sendInfo(sender);
    }

    /**
     * Sends chat message with a ticket list (all tickets) to sender.
     * @param sender
     */
    public void sendFullList(CommandSender sender) {
        ChatColor r = ChatColor.DARK_RED;
        sender.sendMessage(r + Vars.getTitle("List of tickets"));
        for(Ticket t : getTicketsListFIFO()){
            FancyMessage msg = new FancyMessage();
            msg.color(ChatColor.GOLD);
            msg.text(Vars.trimToMax("- # " + t.getId() + "" + t.getStatus() + " " + t.getType() + " - " + t.getDescription()));
            msg.tooltip(ChatColor.AQUA + "Created by: ", " " + t.getOwnerName(), ChatColor.AQUA + "Description: ", " " + WordUtils.wrap(t.getDescription(), 40, "\n ", true));
            msg.command("/ticket info " + t.getId());
            msg.send(sender);
        }
        sender.sendMessage(r + Vars.getEndLine());
    }

    /**
     * Gets a list of tickets. Ordered by FIFO.
     * @return
     */
    public List<Ticket> getTicketsListFIFO(){
        List list = new LinkedList();
        list.addAll(tickets.values());
        Collections.sort(list);
        return list;
    }

    /**
     * Assigns this ticket to a staff member. One ticket can be handled by one or more staff members.
     * @param id
     * @param sender
     * @throws TicketsException
     */
    public void takeTicket(int id, Player sender) throws TicketsException {
        Ticket t = getTicket(id);
        t.addSolver(sender);
        if(t.getStatus() == TicketStatus.NEW){
            t.changeStatus(TicketStatus.ASSIGNED_TO_STAFF, sender.getUniqueId());
        }
    }

    /**
     * Sends chat message with a ticket list (only open) to sender.
     * @param sender
     */
    public void sendList(CommandSender sender) {
        ChatColor r = ChatColor.DARK_RED;
        sender.sendMessage(r + Vars.getTitle("List of tickets"));
        for(Ticket t : getTicketsForPlayer((Player) sender)){
                FancyMessage msg = new FancyMessage();
                msg.color(ChatColor.GOLD);
                msg.text(Vars.trimToMax("- # " + t.getId() + "" + t.getStatus() + " " + t.getType() + " - " + t.getDescription()));
                msg.tooltip(ChatColor.AQUA + "Created by: ", " " + t.getOwnerName(), ChatColor.AQUA + "Description: ", " " + WordUtils.wrap(t.getDescription(), 40, "\n ", true));
                msg.command("/ticket info " + t.getId());
                msg.send(sender);
        }
        sender.sendMessage(r + Vars.getEndLine());
    }

    public boolean isTicketAvailable(Player p) {
        for(Ticket t : tickets.values()){
            if(t.getStatus() == TicketStatus.NEW){
                return true;
            }
        }
        return false;
    }

    public List<Ticket> getTicketsForPlayer(Player p) {
        List<Ticket> list = new ArrayList<>();
        for(Ticket t : tickets.values()){
            if(t.getStatus() == TicketStatus.NEW || t.getStatus() == TicketStatus.ASSIGNED_TO_STAFF){
                if(t.getStatus() == TicketStatus.ASSIGNED_TO_STAFF){
                    if(t.getSolvers().contains(p.getUniqueId())){
                        list.add(t);
                    }
                } else {
                    list.add(t);
                }
            }
        }
        return list;
    }

    public void closeTicket(int id) {
        Ticket t = getTicket(id);
        t.close();
    }

    public void addComment(int id, String comment) {
        Ticket t = getTicket(id);
        t.addComment(comment);
    }
}
