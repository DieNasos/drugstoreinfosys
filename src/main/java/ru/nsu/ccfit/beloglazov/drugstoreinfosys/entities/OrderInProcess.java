package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import java.sql.*;
import java.util.*;

public class OrderInProcess extends TableItem {
    private final int id;
    private final int orderID;
    private final Timestamp readyTime;

    public OrderInProcess(int orderID, Timestamp readyTime) {
        id = -1;
        this.orderID = orderID;
        this.readyTime = readyTime;
    }

    public OrderInProcess(int id, int orderID, Timestamp readyTime) {
        this.id = id;
        this.orderID = orderID;
        this.readyTime = readyTime;
    }

    public int getID() { return id; }
    public int getOrderID() { return orderID; }
    public Timestamp getReadyTime() { return readyTime; }

    @Override
    public Map<String, Object> getValues() {
        if (values == null) {
            values = new HashMap<>();
            values.put("id", id);
            values.put("order_id", orderID);
            values.put("ready_time", readyTime);
        }
        return values;
    }

    @Override
    public String toString() {
        return "OrderInProcess{id = " + id
                + ", orderID = " + orderID
                + ", readyTime = " + readyTime + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderInProcess that = (OrderInProcess) o;
        return id == that.id
                && orderID == that.orderID
                && Objects.equals(readyTime, that.readyTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderID, readyTime);
    }
}