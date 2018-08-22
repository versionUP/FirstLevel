package com.example.akula.messagebox.messages;

import java.io.Serializable;
import java.util.List;

public class MOBInboxMessage implements Serializable{

    private String _id;
    private String title;
    private String body;
    private String icon;
    private String messageType;
    private String messageSubType;
    private String creationDateTimeUtc;
    private String acknowledgmentDateTimeUtc;
    private String deletedDateTimeUtc;
    private String type;
    private List<String> channels;
    private boolean isReadOnServer;
    private boolean isDeletedOnServer;


    //this parameter is not there in ios
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public MOBInboxMessage() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageSubType() {
        return messageSubType;
    }

    public void setMessageSubType(String messageSubType) {
        this.messageSubType = messageSubType;
    }

    public String getCreationDateTimeUtc() {
        return creationDateTimeUtc;
    }

    public void setCreationDateTimeUtc(String creationDateTimeUtc) {
        this.creationDateTimeUtc = creationDateTimeUtc;
    }

    public String getAcknowledgmentDateTimeUtc() {
        return acknowledgmentDateTimeUtc;
    }

    public void setAcknowledgmentDateTimeUtc(String acknowledgmentDateTimeUtc) {
        this.acknowledgmentDateTimeUtc = acknowledgmentDateTimeUtc;
    }

    public String getDeletedDateTimeUtc() {
        return deletedDateTimeUtc;
    }

    public void setDeletedDateTimeUtc(String deletedDateTimeUtc) {
        this.deletedDateTimeUtc = deletedDateTimeUtc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public boolean isReadOnServer() {
        return isReadOnServer;
    }

    public void setReadOnServer(boolean readOnServer) {
        isReadOnServer = readOnServer;
    }

    public boolean isDeletedOnServer() {
        return isDeletedOnServer;
    }

    public void setDeletedOnServer(boolean deletedOnServer) {
        isDeletedOnServer = deletedOnServer;
    }
}
