package com.Neobots2903.Discord.NeoBot.objects;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;

public class ReactionMessage {
    private Message message = null;
    private MessageReaction reaction = null;

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setReaction(MessageReaction reaction) {
        this.reaction = reaction;
    }

    public MessageReaction getReaction() {
        return this.reaction;
    }
}

