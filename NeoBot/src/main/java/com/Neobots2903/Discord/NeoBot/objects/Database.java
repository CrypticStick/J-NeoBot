package com.Neobots2903.Discord.NeoBot.objects;

import javax.xml.bind.annotation.*;

import com.Neobots2903.Discord.NeoBot.NeoBot;

@XmlRootElement
public class Database {

    private String serverId;
    private String name;
    private String game;
    private String modRoleId;
    private DiscordChannelList logList;
    private DiscordUserList userList;
    private FRCTeamList teamList;

    public Database(){ }

    public Database(String serverId, String name, String game, String modRoleId, DiscordChannelList logList, DiscordUserList userList, FRCTeamList teamList) {
        this.serverId = serverId;
        this.name = name;
        this.game = game;
        this.modRoleId = modRoleId;
        this.logList = logList;
        this.userList = userList;
        this.teamList = teamList;
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
    public String getName() {
        return name;
    }

    public Database setName(String name) {
        this.name = name;
        NeoBot.setDatabase(this);
        return this;
    }
    
    @XmlElement
    public String getGame() {
        return game;
    }

    public Database setGame(String game) {
        this.game = game;
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
    
    @XmlElement
    public FRCTeamList getTeamList() {
        return teamList;
    }

    public Database setTeamList(FRCTeamList teamList) {
        this.teamList = teamList;
        NeoBot.setDatabase(this);
        return this;
    }
}