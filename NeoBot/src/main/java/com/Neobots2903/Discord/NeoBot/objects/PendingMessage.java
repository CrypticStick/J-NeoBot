package com.Neobots2903.Discord.NeoBot.objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;

public class PendingMessage {

    private String message;
    private String id;
    private String useTime;

    public PendingMessage() {
    }

    public PendingMessage(String message) {
        this.message = message;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();
    	useTime = dateFormat.format(date);
    }

    @XmlAttribute
    public String getMessage() {
        return message;
    }

    public PendingMessage setMessage(String message) {
        this.message = message;
        return this;
    }
    
    public String getId() {
        return id;
    }

    public PendingMessage setId(String id) {
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