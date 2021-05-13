package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import java.util.*;

public class Customer extends TableItem  {
    private final int id;
    private final int userID;
    private final String name;
    private final String phoneNumber;
    private final String address;

    public Customer(int id, int userID, String name, String phoneNumber, String address) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Customer(int userID, String name, String phoneNumber, String address) {
        this(-1, userID, name, phoneNumber, address);
    }

    public int getID() { return id; }
    public int getUserID() { return userID; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }

    @Override
    public Map<String, Object> getValues() {
        if (values == null) {
            values = new HashMap<>();
            values.put("id", id);
            values.put("user_id", userID);
            values.put("name", name);
            values.put("phone_number", phoneNumber);
            values.put("address", address);
        }
        return values;
    }

    @Override
    public String toString() {
        return "Customer{id = " + id
                + ", userID = " + userID
                + ", name = '" + name
                + "', phoneNumber = '" + phoneNumber
                + "', address = '" + address + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id
                && userID == customer.userID
                && Objects.equals(name, customer.name)
                && Objects.equals(phoneNumber, customer.phoneNumber)
                && Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, name, phoneNumber, address);
    }
}