package com.woutwoot.tickets;

/**
 * @author woutwoot
 *         Created by on 1/01/2015 - 22:52.
 */
public class TicketsException extends RuntimeException{

    public TicketsException(String message){
        super(message);
    }

    public TicketsException(String message, Exception ex){
        super(message, ex);
    }

}
