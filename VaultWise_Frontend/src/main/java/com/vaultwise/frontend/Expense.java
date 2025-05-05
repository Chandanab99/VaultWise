package com.vaultwise.frontend;

import javafx.beans.property.*;

public class Expense {
    private final StringProperty category;
    private final DoubleProperty amount;
    private final StringProperty date;

    public Expense(String category, double amount, String date) {
        this.category = new SimpleStringProperty(category);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleStringProperty(date);
    }

    public StringProperty categoryProperty() { return category; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty dateProperty() { return date; }
}
