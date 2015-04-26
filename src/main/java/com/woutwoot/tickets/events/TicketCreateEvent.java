package com.woutwoot.tickets.events;


import com.woutwoot.tickets.ticket.Ticket;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author woutwoot
 *         Created by on 4/01/2015 - 14:14.
 */
public class TicketCreateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Ticket ticket;

    public TicketCreateEvent(Player player, Ticket ticket) {
        super();
        this.player = player;
        this.ticket = ticket;
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

    public Player getPlayer() {
        return player;
    }
}
