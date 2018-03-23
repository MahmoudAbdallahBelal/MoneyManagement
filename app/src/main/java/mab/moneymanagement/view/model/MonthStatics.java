package mab.moneymanagement.view.model;

/**
 * Created by Gihan on 3/22/2018.
 */

public class MonthStatics {

    int money;
    int budget;
    int month;
    int year;

    public MonthStatics(int money, int budget, int month, int year) {
        this.money = money;
        this.budget = budget;
        this.month = month;
        this.year = year;
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
