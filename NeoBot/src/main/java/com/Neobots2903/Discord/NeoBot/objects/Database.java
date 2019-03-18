package com.Neobots2903.Discord.NeoBot.objects;

import javax.xml.bind.annotation.*;

import com.Neobots2903.Discord.NeoBot.NeoBot;

@XmlRootElement
public class Database {

    private String serverId;
    private String description;
    private String modRoleId;
    private DiscordChannelList logList;
    private DiscordUserList userList;

    public Database(){ }

    public Database(String serverId, String description, String modRoleId, DiscordChannelList logList, DiscordUserList userList) {
        this.serverId = serverId;
        this.description = description;
        this.modRoleId = modRoleId;
        this.logList = logList;
        this.userList = userList;
    }

    @XmlAttribute
    public String getServerId() {
        return serverId;
    }

    public Database setServerId(String serverId) {
        this.serverId = serverId;
        NeoBot.setDatabase(this);
        return this;
    }
    
    @XmlElement
    public String getDescription() {
        return description;
    }

    public Database setDescription(String description) {
        this.description = description;
        NeoBot.setDatabase(this);
        return this;
    }
    
    @XmlElement
    public String getModRoleId() {
        return modRoleId;
    }

    public Database setModRoleId(String modRoleId) {
        this.modRoleId = modRoleId;
        NeoBot.setDatabase(this);
        return this;
    }
    
    @XmlElement
    public DiscordChannelList getLogList() {
        return logList;
    }

    public Database setLogList(DiscordChannelList logList) {
        this.logList = logList;
        NeoBot.setDatabase(this);
        return this;
    }
    
    @XmlElement
    public DiscordUserList getUserList() {
        return userList;
    }

    public Database setUserList(DiscordUserList userList) {
        this.userList = userList;
        NeoBot.setDatabase(this);
        return this;
    }
}