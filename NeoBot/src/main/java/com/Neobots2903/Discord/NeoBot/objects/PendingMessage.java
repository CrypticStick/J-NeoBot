package com.Neobots2903.Discord.NeoBot.objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.Neobots2903.Discord.NeoBot.NeoBot;

public class PendingMessage {

    private String message;
    @XmlTransient private DiscordUser author;
    private int id;
    private String useTime;

    public PendingMessage() {
    }

    public PendingMessage(String message, DiscordUser author) {
        this.message = message;
        this.author = author;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();
    	useTime = dateFormat.format(date);
    	
    	ArrayList<DiscordUser> tempUsers = NeoBot.database.getUserList().getUserList();
    	int existingMsgs = 1;
    	for(DiscordUser user : tempUsers)
    		existingMsgs += user.getPendingMessages().getMessageList().size();
    	id = existingMsgs;
    }

    @XmlAttribute
    public String getMessage() {
        return message;
    }

    public PendingMessage setMessage(String message) {
        this.message = message;
        return this;
    }
    
    @XmlTransient
    public DiscordUser getAuthor() {
        return author;
    }

    public PendingMessage setAuthor(DiscordUser author) {
        this.author = author;
        return this;
    }
    
    public int getId() {
        return id;
    }

    public PendingMessage setId(int id) {
        this.id = id;
        return this;
    }

    public String getUseTime() {
        return useTime;
    }

    public PendingMessage setUseTime(String useTime) {
        this.useTime = useTime;
        return this;
    }
    
}