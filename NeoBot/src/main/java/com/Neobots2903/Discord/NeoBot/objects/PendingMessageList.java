package com.Neobots2903.Discord.NeoBot.objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class PendingMessageList {

    private ArrayList<PendingMessage> messageList;

    public PendingMessageList() {
    }

    public PendingMessageList(ArrayList<PendingMessage> messageList) {
        this.messageList = messageList;
    }

    @XmlElement(name = "message")
    public ArrayList<PendingMessage> getMessageList() {
        return messageList;
    }

    public PendingMessageList setMessageList(ArrayList<PendingMessage> messageList) {
        this.messageList = messageList;
        return this;
    }
}