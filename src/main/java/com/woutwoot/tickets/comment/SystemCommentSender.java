package com.woutwoot.tickets.comment;

/**
 * @author woutwoot
 *         Created by on 25/04/2015 - 14:26.
 */
public class SystemCommentSender implements CommentSender {

    @Override
    public String getSenderName() {
        return "System";
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean isSystem() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemCommentSender that = (SystemCommentSender) o;

        return isSystem() == that.isSystem();

    }

    @Override
    public int hashCode() {
        return 158846512;
    }
}
