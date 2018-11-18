package com.Neobots2903.Discord.NeoBot.objects;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Database {

    private String serverId;
    private String description;
    private DiscordUserList userList;

    public Database(){ }

    public Database(String serverId, String description, DiscordUserList userList) {
        this.serverId = serverId;
        this.description = description;
        this.userList = userList;
    }

    @XmlAttribute
    public String getServerId() {
        return serverId;
    }

    public Database setServerId(String serverId) {
        this.serverId = serverId;
        return this;
    }
    
    @XmlElement
    public String getDescription() {
        return description;
    }

    public Database setDescription(String description) {
        this.description = description;
        return this;
    }
    
    @XmlElement
    public DiscordUserList getUserList() {
        return userList;
    }

    public Database setUserList(DiscordUserList userList) {
        this.userList = userList;
        return this;
    }
}