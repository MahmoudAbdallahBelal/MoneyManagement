package mab.moneymanagement.view.model;

import java.io.Serializable;

/**
 * Created by Gihan on 3/3/2018.
 */

public class Category implements Serializable {


    int id;
    String name;
    String icon;
    int money;
    int Budget;
    String createDate;

    public Category(int id, String name, String icon, int money, int budget, String createDate) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.money = money;
        Budget = budget;
        this.createDate = createDate;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBudget() {
        return Budget;
    }

    public void setBudget(int budget) {
        Budget = budget;
    }
}
