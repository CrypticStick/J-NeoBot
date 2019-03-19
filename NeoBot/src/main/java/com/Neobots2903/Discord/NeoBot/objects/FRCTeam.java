package com.Neobots2903.Discord.NeoBot.objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;

public class FRCTeam {

    private int teamNumber;
    private String name;
    private ArrayList<String> mediaList;

    public FRCTeam() {
    }

    public FRCTeam(int teamNumber, String name, ArrayList<String> mediaList) {
        this.teamNumber = teamNumber;
        this.name = name;
        this.mediaList = mediaList;
    }

    @XmlAttribute
    public int getTeamNumber() {
        return teamNumber;
    }

    public FRCTeam setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public FRCTeam setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<String> getMediaList() {
        return mediaList;
    }

    public FRCTeam setMediaList(ArrayList<String> mediaList) {
        this.mediaList = mediaList;
        return this;
    }
}