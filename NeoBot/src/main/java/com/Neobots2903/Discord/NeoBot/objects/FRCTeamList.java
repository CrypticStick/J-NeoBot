package com.Neobots2903.Discord.NeoBot.objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class FRCTeamList {

    private ArrayList<FRCTeam> teamList;

    public FRCTeamList() {
    }

    public FRCTeamList(ArrayList<FRCTeam> teamList) {
        this.teamList = teamList;
    }

    @XmlElement(name = "team")
    public ArrayList<FRCTeam> getTeamList() {
        return teamList;
    }

    public FRCTeamList setUserList(ArrayList<FRCTeam> teamList) {
        this.teamList = teamList;
        return this;
    }
    
    public FRCTeam getTeam(int teamNumber) {
    	for(int i = 0; this.teamList.size() > i ; i++) 
    		if (teamList.get(i).getTeamNumber() == teamNumber) 
    			return teamList.get(i);
    	
        return null;
    }

    public FRCTeamList setTeam(FRCTeam team) {
    	int teamNumber = team.getTeamNumber();
    	boolean exists = getTeam(teamNumber) != null;
    	if (!exists) teamList.add(team);
    	else teamList.set(teamList.indexOf(getTeam(teamNumber)), team);
        return this;
    }

}