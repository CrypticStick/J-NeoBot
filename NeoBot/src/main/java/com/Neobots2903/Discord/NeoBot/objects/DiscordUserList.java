package com.Neobots2903.Discord.NeoBot.objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class DiscordUserList {

    private ArrayList<DiscordUser> userList;

    public DiscordUserList() {
    }

    public DiscordUserList(ArrayList<DiscordUser> userList) {
        this.userList = userList;
    }

    @XmlElement(name = "user")
    public ArrayList<DiscordUser> getUserList() {
        return userList;
    }

    public DiscordUserList setUserList(ArrayList<DiscordUser> userList) {
        this.userList = userList;
        return this;
    }
    
    public DiscordUser getUser(String UserId) {
    	for(int i = 0; this.userList.size() > i ; i++) 
    		if (userList.get(i).getId().equals(UserId)) 
    			return userList.get(i);
    	
        return null;
    }

    public DiscordUserList setUser(DiscordUser user) {
    	String UserId = user.getId();
    	boolean exists = false;
    	for(int i = 0; this.userList.size() > i ; i++)
    		if (userList.get(i).getId().equals(UserId)) {
    			userList.set(i, user);
    			exists = true;
    		}
    	if (!exists) userList.add(user);
        return this;
    }

}