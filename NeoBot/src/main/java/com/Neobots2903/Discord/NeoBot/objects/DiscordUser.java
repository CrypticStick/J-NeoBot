package com.Neobots2903.Discord.NeoBot.objects;

import java.time.LocalTime;

import javax.xml.bind.annotation.XmlAttribute;

public class DiscordUser {

    private String id;
    private String name;
    private int useTime;
    private boolean blocked = false;
    private PendingMessageList pendingMessages;

    public DiscordUser() {
    	pendingMessages = new PendingMessageList();
    }

    public DiscordUser(String id, String name) {
        this.id = id;
        this.name = name;
        useTime = LocalTime.now().toSecondOfDay();
    	pendingMessages = new PendingMessageList();
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public DiscordUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DiscordUser setName(String name) {
        this.name = name;
        return this;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public DiscordUser setBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public int getUseTime() {
        return useTime;
    }

    public DiscordUser setUseTime(int useTime) {
        this.useTime = useTime;
        return this;
    }
    
    public PendingMessageList getPendingMessages() {
        return pendingMessages;
    }

    public DiscordUser setPendingMessages(PendingMessageList pendingMessages) {
        this.pendingMessages = pendingMessages;
        return this;
    }
    
    public String getMention() {
    	return "<@" + getId() + ">";
    }
    
}