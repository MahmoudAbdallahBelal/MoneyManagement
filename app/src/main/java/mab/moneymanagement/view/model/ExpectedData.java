package mab.moneymanagement.view.model;

/**
 * Created by Gihan on 3/22/2018.
 */

public class ExpectedData {

    int id;
    String name;
    String icon;
    int money;
    int budget;
    int month;
    int year;

    public ExpectedData(int id, String name, String icon, int money, int budget, int month, int year) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.money = money;
        this.budget = budget;
        this.month = month;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
