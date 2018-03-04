package mab.moneymanagement.view.model;

import java.io.Serializable;

/**
 * Created by Gihan on 3/3/2018.
 */

public class Category implements Serializable {


    String id;
    String name;
    String kind;
    double target;
    double expense;
    String date;
    String userId;
    int icon;

    public Category(String name, double target, int icon) {
        this.name = name;
        this.target= target;
        this.icon = icon;
    }

    public Category() {
    }

    public Category(String date,double target, double expense) {
        this.target = target;
        this.expense = expense;
        this.date=date;
    }

    public Category(String name, String kind, Double target, Double expense) {
        this.name = name;
        this.kind = kind;
        this.target = target;
        this.expense = expense;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
