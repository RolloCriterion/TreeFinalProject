package com.finalproject.views;

import java.sql.Timestamp;
import java.util.UUID;

public class EventView {
    private UUID eventid;
    private Boolean owned;
    private String name;
    private Timestamp date;
    private String place;
    private Integer capacity;

    public EventView(UUID eventid, Boolean owned, String name, Timestamp date, String place, Integer capacity) {
        this.eventid = eventid;
        this.owned = owned;
        this.name = name;
        this.date = date;
        this.place = place;
        this.capacity = capacity;
    }

    public UUID getEventid() {
        return eventid;
    }

    public void setEventid(UUID eventid) {
        this.eventid = eventid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getOwned() {
        return owned;
    }

    public void setOwned(Boolean owned) {
        this.owned = owned;
    }
}
