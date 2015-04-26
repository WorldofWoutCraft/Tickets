package com.woutwoot.tickets.events;

import com.woutwoot.tickets.ticket.Ticket;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author woutwoot
 *         Created by on 4/01/2015 - 14:14.
 */
public class TicketNewCommentEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Ticket ticket;
    private String comment;

    public TicketNewCommentEvent(String comment, Ticket ticket) {
        super();
        this.ticket = ticket;
        this.comment = comment;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getComment() {
        return comment;
    }
}
