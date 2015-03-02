package com.woutwoot.tickets.events;


import com.woutwoot.tickets.ticket.Ticket;
import com.woutwoot.tickets.ticket.TicketStatus;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author woutwoot
 *         Created by on 4/01/2015 - 14:14.
 */
public class TicketStatusChangedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private TicketStatus oldStatus;
    private Ticket ticket;
    private String changer;

    public TicketStatusChangedEvent(String changer, TicketStatus oldStatus, Ticket ticket) {
        super();
        this.oldStatus = oldStatus;
        this.ticket = ticket;
        this.changer = changer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public TicketStatus getOldStatus() {
        return oldStatus;
    }

    public String getChanger() {
        return changer;
    }
}
