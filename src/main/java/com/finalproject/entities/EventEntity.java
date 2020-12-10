package com.finalproject.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @ManyToMany(mappedBy = "eventEntityList", cascade = CascadeType.REMOVE)
    private List<UserEntity> userEntityList = new ArrayList<>();

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity that = (EventEntity) o;
        return Objects.equals(eventid, that.eventid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventid);
    }
}
