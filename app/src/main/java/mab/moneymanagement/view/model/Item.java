package mab.moneymanagement.view.model;

import java.io.Serializable;

/**
 * Created by Gihan on 3/3/2018.
 */

public class Item implements Serializable {

    int id;
    String name;
    String note;
    int Price;
    int incomeId;
    int expenseId;
    String incomName;
    String expenseName;
    String date;

    public Item(int id, String name, String note, int price, int incomeId, int expenseId, String incomName, String expenseName, String date) {
        this.id = id;
        this.name = name;
        this.note = note;
        Price = price;
        this.incomeId = incomeId;
        this.expenseId = expenseId;
        this.incomName = incomName;
        this.expenseName = expenseName;
        this.date = date;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getIncomName() {
        return incomName;
    }

    public void setIncomName(String incomName) {
        this.incomName = incomName;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
