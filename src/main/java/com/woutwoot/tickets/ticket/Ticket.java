package com.woutwoot.tickets.ticket;

import com.woutwoot.tickets.TicketsException;
import com.woutwoot.tickets.tools.Vars;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author woutwoot
 *         Created by on 1/01/2015 - 22:00.
 */
public class Ticket implements Comparable<Ticket>{

    private TicketStatus status = TicketStatus.NEW;
    private TicketType type = TicketType.GENERAL;

    private int id;
    private String description;

    private UUID ownerUUID;
    private String ownerName;

    private Set<UUID> solvers = new HashSet<>();
    private Map<Date, String> comments = new TreeMap<>();

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

    public Set<UUID> getSolvers() {
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

    /**
     * Changes the status for this ticket and logs a message to the comments section.
     * @param status
     * @param changer
     * @throws TicketsException
     */
    public void changeStatus(TicketStatus status, String changer) throws TicketsException {
        if(status == null || changer == null || changer.isEmpty()){
            throw new TicketsException("Can't change status. (null?)");
        }
        this.setStatus(status);
        if(status == TicketStatus.CLOSED){
            addComment("Ticket closed by " + changer + ".");
        }else if(status == TicketStatus.ASSIGNED_TO_STAFF){
            addComment("Ticket assigned to " + changer + ".");
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
        ChatColor red = ChatColor.DARK_RED;
        ChatColor lightRed = ChatColor.RED;
        ChatColor gold = ChatColor.GOLD;

        sender.sendMessage(red + Vars.getTitle("Ticket #" + getId()));
        sender.sendMessage(gold + "Creator: " + getOwnerName());
        sender.sendMessage(gold + "Create date: " + Vars.dateFormat.format(getDateAsked()));
        sender.sendMessage(gold + "Description: " + getDescription());
        sender.sendMessage(gold + "Status: " + getStatus());
        sender.sendMessage(gold + "Type: " + getType());
        sender.sendMessage(red + Vars.getTitle("Comments"));
        for(Date d : comments.keySet()){
            sender.sendMessage(red + "" + Vars.dateFormat.format(d) + " - " + gold + comments.get(d));
        }
        sender.sendMessage(red + Vars.getTitle("Actions"));
        FancyMessage act = new FancyMessage("TELEPORT ");
        act.color(gold).command("/ticket tp " + getId());
        act.then("TAKE ").color(lightRed).command("/ticket take " + getId());
        act.then("COMMENT ").color(gold).suggest("/tck c " + getId() + " comment");
        act.then("CLOSE ").color(lightRed).command("/ticket close " + getId());
        act.then("DELETE ").color(gold).suggest("/ticket delete " + getId());
        act.then("NEED MORE INFO ").color(lightRed).suggest("/ticket delete " + getId());
        act.send(sender);
        sender.sendMessage(red + Vars.getEndLine());
    }

    @Override
    public int compareTo(Ticket t) {
        if(t != null){
            return dateAsked.compareTo(t.dateAsked);
        }
        return 0;
    }

    public Location getLocation() {
        return location;
    }

    public void addSolver(Player player) {
        solvers.add(player.getUniqueId());
    }
}
