package com.rbbn.ems.model;

/**
 * Created by nsoni on 3/15/2018.
 */

public class Alarms {
    private String eventName;
    private String type;
    private String device;
    private String eventSeverity;
    private String date;
    private String summary;

    public Alarms(String eventName, String type, String device, String eventSeverity, String date, String summary) {
        this.eventName = eventName;
        this.type = type;
        this.device = device;
        this.eventSeverity = eventSeverity;
        this.date = date;
        this.summary = summary;
    }

    public Alarms(){

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getEventSeverity() {
        return eventSeverity;
    }

    public void setEventSeverity(String eventSeverity) {
        this.eventSeverity = eventSeverity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
