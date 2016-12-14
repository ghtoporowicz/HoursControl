package com.example.aluno.hourscontrol.model;

import java.util.Date;

/**
 * Created by ghtop on 03/12/2016.
 */

public class WorkDay {

    private int idWorkDay;
    private Date dateEntry;
    private Date dateBreak;
    private Date datebackBreak;
    private Date dateOut;
    private Date balanceOfTheDay;
    private String stateHours;
    private User userLoggin;

    public String getStateHours() {
        return stateHours;
    }

    public void setStateHours(String stateHours) {
        this.stateHours = stateHours;
    }
    public User getUserLoggin() {
        return userLoggin;
    }

    public void setUserLoggin(User userLoggin) {
        this.userLoggin = userLoggin;
    }

    public int getIdWorkDay() {
        return idWorkDay;
    }

    public void setIdWorkDay(int idWorkDay) {
        this.idWorkDay = idWorkDay;
    }

    public Date getDateBreak() {
        return dateBreak;
    }

    public void setDateBreak(Date dateBreak) {
        this.dateBreak = dateBreak;
    }

    public Date getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(Date dateEntry) {
        this.dateEntry = dateEntry;
    }

    public Date getDatebackBreak() {
        return datebackBreak;
    }

    public void setDatebackBreak(Date datebackBreak) {
        this.datebackBreak = datebackBreak;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public Date getBalanceOfTheDay() {
        return balanceOfTheDay;
    }

    public void setBalanceOfTheDay(Date balanceOfTheDay) {
        this.balanceOfTheDay = balanceOfTheDay;
    }
}
