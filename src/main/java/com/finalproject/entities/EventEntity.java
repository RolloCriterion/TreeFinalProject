package com.finalproject.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class EventEntity {

    @Id
    private UUID eventid;

    @ManyToOne
    private UserEntity owner;

    private String name;
    private Timestamp date;
    private String place;
    private Integer capacity;

    public UUID getEventid() {
        return eventid;
    }
    public void setEventid(UUID eventid) {
        this.eventid = eventid;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
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
    
}
