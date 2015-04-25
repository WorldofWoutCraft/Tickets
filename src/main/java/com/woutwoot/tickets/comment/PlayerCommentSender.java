package com.woutwoot.tickets.comment;

import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * @author woutwoot
 *         Created by on 25/04/2015 - 14:24.
 */
public class PlayerCommentSender implements CommentSender {

    private UUID senderUUID;

    public PlayerCommentSender(UUID senderUUID) {
        super();
        this.senderUUID = senderUUID;
    }

    public UUID getSenderUUID() {
        return senderUUID;
    }

    public void setSenderUUID(UUID senderUUID) {
        this.senderUUID = senderUUID;
    }

    @Override
    public String getSenderName() {
        return Bukkit.getOfflinePlayer(senderUUID).getName();
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean isSystem() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerCommentSender that = (PlayerCommentSender) o;

        return senderUUID.equals(that.senderUUID);

    }

    @Override
    public int hashCode() {
        return senderUUID.hashCode();
    }
}
