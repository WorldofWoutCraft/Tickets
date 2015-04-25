package com.woutwoot.tickets.comment;

import java.util.UUID;

/**
 * @author woutwoot
 *         Created by on 25/04/2015 - 14:18.
 */
public class Comment {

    private CommentSender sender;
    private String comment;

    public Comment(CommentSender sender, String comment) {
        this.sender = sender;
        this.comment = comment;
    }

    public Comment(UUID uuid, String comment) {
        this.sender = new PlayerCommentSender(uuid);
        this.comment = comment;
    }

    public String getRawComment() {
        return comment;
    }

    public String getComment() {
        return comment.replace("{sender}", this.getSender().getSenderName());
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommentSender getSender() {
        return sender;
    }

    public void setSender(CommentSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment1 = (Comment) o;

        if (!sender.equals(comment1.sender)) return false;
        return comment.equals(comment1.comment);

    }

    @Override
    public int hashCode() {
        int result = sender.hashCode();
        result = 31 * result + comment.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.getComment();
    }
}
