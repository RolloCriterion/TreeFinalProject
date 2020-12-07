package com.finalproject.entities;

import javax.persistence.*;

@Entity
public class CookieEntity {

    @Id
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
