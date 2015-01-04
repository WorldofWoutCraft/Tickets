package com.woutwoot.tickets.ticket;

import com.woutwoot.tickets.TicketsException;
import com.woutwoot.tickets.tools.Vars;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author woutwoot
 *         Created by on 1/01/2015 - 22:00.
 */
public class Ticket {

    private TicketStatus status = TicketStatus.NEW;
    private TicketType type = TicketType.GENERAL;

    private int id;
    private String description;

    private UUID ownerUUID;
    private String ownerName;

    private List<UUID> solvers = new ArrayList<>();
    private Map<Date, String> comments = new HashMap<>();

    private Date dateAsked = new Date();
    private Date dateClosed = null;

    //-5 low, 5 high (Standard = 0)
    private int priority;

    private Location location;

    public Ticket(int id, Player owner, String description) throws TicketsException {
        this.id = id;
        this.setDescription(description);
        this.ownerUUID = owner.getUniqueId();
        this.ownerName = owner.getName();
        this.location = owner.getLocation().clone();
        this.comments.put(new Date(), "Ticket opened by " + ownerName + ".");
    }

    /**
     * Sets the description for this ticket. This will typically be a question.
     * @param description
     * @throws TicketsException
     */
    public void setDescription(String description) throws TicketsException {
        this.description = description;
    }

    /**
     * Sets the priority of this ticket.
     * @param priority Number between -5 and 5 where 5 is very high priority.
     * @throws TicketsException when not between -5 and 5
     */
    public void setPriority(int priority) throws TicketsException {
        if(priority < -5 || priority > 5){
            throw new TicketsException("Priority must be between -5 and 5.");
        }
        this.priority = priority;
    }

    /**
     * Sets the current status for this ticket.
     * @param status status to set.
     * @throws TicketsException
     */
    private void setStatus(TicketStatus status) throws TicketsException {
        if(status == null){
            throw new TicketsException("Status may not be null.");
        }
        this.status = status;
    }

    /**
     * Gets the id for this ticket.
     * @return Integer id
     */
    public int getId() {
        return id;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public List<UUID> getSolvers() {
        return solvers;
    }

    public Map<Date, String> getComments() {
        return comments;
    }

    public Date getDateAsked() {
        return dateAsked;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public int getPriority() {
        return priority;
    }

    public void changeStatus(TicketStatus status, String changer) throws TicketsException {
        if(status == null || changer == null || changer.isEmpty()){
            throw new TicketsException("Can't change status. (null?)");
        }
        this.setStatus(status);
        if(status == TicketStatus.CLOSED){
            addComment("Ticket closed by " + changer + ".");
        }else{
            addComment("Status changed to " + status.toString() + " by " + changer + ".");
        }
    }

    public void close(String closer) {
        this.dateClosed = new Date();
        this.changeStatus(TicketStatus.CLOSED, closer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void addComment(String comment) throws TicketsException {
        if(comment == null){
            throw new TicketsException("Comment can not be null.");
        }
        comments.put(new Date(), comment);
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void sendInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_RED + Vars.getTitle("Ticket #" + getId()));
        sender.sendMessage(ChatColor.GOLD + "Creator: " + getOwnerName());
        sender.sendMessage(ChatColor.GOLD + "Create date: " + getDateAsked());
        sender.sendMessage(ChatColor.GOLD + "Description: " + getDescription());
        sender.sendMessage(ChatColor.GOLD + "Status: " + getStatus());
        sender.sendMessage(ChatColor.GOLD + "Type: " + getType());
        sender.sendMessage(ChatColor.DARK_RED + Vars.getTitle("Comments"));
        for(Date d : comments.keySet()){
            sender.sendMessage(ChatColor.DARK_RED + "" + d + " - " + ChatColor.GOLD + comments.get(d));
        }
    }
}
