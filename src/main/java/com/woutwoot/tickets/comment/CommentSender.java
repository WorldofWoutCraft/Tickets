package com.woutwoot.tickets.comment;

/**
 * @author woutwoot
 *         Created by on 25/04/2015 - 14:22.
 */
public interface CommentSender {

    String getSenderName();

    boolean isPlayer();

    boolean isSystem();

}
