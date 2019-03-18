package com.Neobots2903.Discord.NeoBot.objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class DiscordChannelList {

    private ArrayList<String> logList;

    public DiscordChannelList() {
    }

    public DiscordChannelList(ArrayList<String> logList) {
        this.logList = logList;
    }

    @XmlElement(name = "channel")
    public ArrayList<String> getLogList() {
        return logList;
    }

    public DiscordChannelList setLogList(ArrayList<String> logList) {
        this.logList = logList;
        return this;
    }
    
    public boolean isChannelLogged(String channelId) {
    	if (logList == null)
    		logList = new ArrayList<String>();
    	
    	for(int i = 0; this.logList.size() > i ; i++) 
    		if (logList.get(i).equals(channelId)) 
    			return true;
    	
        return false;
    }
    
    public DiscordChannelList addChannel(String id) {
    	if (!isChannelLogged(id))
    		getLogList().add(id);
    	return this;
    }
    
    public DiscordChannelList removeChannel(String id) {
    	if (isChannelLogged(id))
    		getLogList().remove(id);
    	return this;
    }

}