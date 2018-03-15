package mab.moneymanagement.view.model;

import java.io.Serializable;

/**
 * Created by Gihan on 3/9/2018.
 */

public class User implements Serializable {
    String Email;
    String Password;
    String FullName;
    int Currency;
    int BegainDayOfWeek;
    Boolean BadgetSelected;
    boolean DailyAlert;
    int BadgetValue;

    public User() {
    }

    public User(String email, String password, String fullName, int currency, int begainDayOfWeek, Boolean badgetSelected, boolean dailyAlert,int badgetValue) {
        Email = email;
        Password = password;
        FullName = fullName;
        Currency = currency;
        BegainDayOfWeek = begainDayOfWeek;
        BadgetSelected = badgetSelected;
        DailyAlert = dailyAlert;
        BadgetValue = badgetValue;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getCurrency() {
        return Currency;
    }

    public void setCurrency(int currency) {
        Currency = currency;
    }

    public int getBegainDayOfWeek() {
        return BegainDayOfWeek;
    }

    public void setBegainDayOfWeek(int begainDayOfWeek) {
        BegainDayOfWeek = begainDayOfWeek;
    }

    public Boolean getBadgetSelected() {
        return BadgetSelected;
    }

    public void setBadgetSelected(Boolean badgetSelected) {
        BadgetSelected = badgetSelected;
    }

    public boolean isDailyAlert() {
        return DailyAlert;
    }

    public void setDailyAlert(boolean dailyAlert) {
        DailyAlert = dailyAlert;
    }


    public int getBadgetValue() {
        return BadgetValue;
    }

    public void setBadgetValue(int badgetValue) {
        BadgetValue = badgetValue;
    }
}
