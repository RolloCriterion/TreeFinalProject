package com.finalproject.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class UserEntity {

    @Id
    private String username;
    private String name;
    private String surname;
    private Date birthDate;
    private String gender;
    private String password;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "eventid"))
    private List<EventEntity> eventEntityList = new ArrayList<>();

    public void addEvent(EventEntity eventEntity) {
        eventEntityList.add(eventEntity);
        eventEntity.getUserEntityList().add(this);
    }

    public void removeEvent(EventEntity eventEntity) {
        eventEntityList.remove(eventEntity);
        eventEntity.getUserEntityList().remove(this);
    }

    public List<EventEntity> getEventEntityList() {
        return eventEntityList;
    }

    public void setEventEntityList(List<EventEntity> eventEntityList) {
        this.eventEntityList = eventEntityList;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }
    public String setPassword(String password) {
        return this.password=password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}