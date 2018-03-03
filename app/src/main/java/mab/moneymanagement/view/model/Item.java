package mab.moneymanagement.view.model;

import java.io.Serializable;

/**
 * Created by Gihan on 3/3/2018.
 */

public class Item implements Serializable {


    String name;
    Double Price;
    String note;
    String category;
    String Payment;
    String date;

    public Item() {
    }

    public Item(String name, Double price, String note, String category, String payment, String date) {
        this.name = name;
        Price = price;
        this.note = note;
        this.category = category;
        Payment = payment;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
