package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import java.util.*;

public class Order extends TableItem {
    private final int id;
    private final int customerID;
    private final int drugID;
    private final int amount;
    private final boolean given;

    public Order(int id, int customerID, int drugID, int amount, boolean given) {
        this.id = id;
        this.customerID = customerID;
        this.drugID = drugID;
        this.amount = amount;
        this.given = given;
    }

    public Order(int customerID, int drugID, int amount, boolean given) {
        this(-1, customerID, drugID, amount, given);
    }

    public int getID() { return id; }
    public int getCustomerID() { return customerID; }
    public int getDrugID() { return drugID; }
    public int getAmount() { return amount; }
    public boolean isGiven() { return given; }

    @Override
    public Map<String, Object> getValues() {
        if (values == null) {
            values = new HashMap<>();
            values.put("id", id);
            values.put("customer_id", customerID);
            values.put("drug_id", drugID);
            values.put("amount", amount);
            values.put("given", given);
        }
        return values;
    }

    @Override
    public String toString() {
        return "Order{id = " + id
                + ", customerID = " + customerID
                + ", drugID = " + drugID
                + ", amount = " + amount
                + ", given = " + given + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id
                && drugID == order.drugID
                && amount == order.amount
                && given == order.given
                && Objects.equals(customerID, order.customerID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerID, drugID, amount, given);
    }
}