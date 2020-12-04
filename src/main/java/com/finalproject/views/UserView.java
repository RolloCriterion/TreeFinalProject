package com.finalproject.views;

import com.finalproject.entities.Gender;

import java.util.Date;

public class UserView {
    private String username;
    private String nome;
    private String surname;
    private Date birthDate;
    private Gender gender;
    private String password;

    public UserView(String username, String nome, String surname, Date birthDate, Gender gender, String password) {
        this.username = username;
        this.nome = nome;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
