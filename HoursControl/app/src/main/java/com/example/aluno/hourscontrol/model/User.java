package com.example.aluno.hourscontrol.model;

import java.util.Date;

/**
 * Created by aline on 12/1/2016.
 */

public class User
{
    private int id;
    private String fullname;
    private String username;
    private String dailyhour;
    private String email;
    private String password;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getFullname() { return fullname; }

    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getDailyhour() {
        return dailyhour;
    }

    public void setDailyhour(String dailyhour) {
        this.dailyhour = dailyhour;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
