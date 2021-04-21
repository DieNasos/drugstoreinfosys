package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order implements TableItem {
    private final int id;
    private final String customerName;
    private final String phoneNumber;
    private final String address;
    private final int drugID;
    private final int amount;

    public Order(String customerName, String phoneNumber, String address, int drugID, int amount) {
        id = -1;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.drugID = drugID;
        this.amount = amount;
    }

    public Order(int id, String customerName, String phoneNumber, String address, int drugID, int amount) {
        this.id = id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.drugID = drugID;
        this.amount = amount;
    }

    public int getID() { return id; }
    public String getCustomerName() { return customerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public int getDrugID() { return drugID; }
    public int getAmount() { return amount; }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        values.put("customer_name", customerName);
        values.put("phone_number", phoneNumber);
        values.put("address", address);
        values.put("drug_id", drugID);
        values.put("amount", amount);
        return values;
    }

    @Override
    public String toString() {
        return "Order{id = " + id
                + ", customerName = '" + customerName
                + "', phoneNumber = '" + phoneNumber
                + "', address = '" + address
                + "', drugID = " + drugID
                + ", amount = " + amount + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id
                && drugID == order.drugID
                && amount == order.amount
                && Objects.equals(customerName, order.customerName)
                && Objects.equals(phoneNumber, order.phoneNumber)
                && Objects.equals(address, order.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, phoneNumber, address, drugID, amount);
    }
}