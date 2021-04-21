package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.util.*;

public class GivenOrder implements TableItem {
    private final int id;
    private final int orderID;

    public GivenOrder(int orderID) {
        id = -1;
        this.orderID = orderID;
    }

    public GivenOrder(int id, int orderID) {
        this.id = id;
        this.orderID = orderID;
    }

    public int getID() { return id; }
    public int getOrderID() { return orderID; }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        values.put("order_id", orderID);
        return values;
    }

    @Override
    public String toString() {
        return "GivenOrder{id = " + id
                + ", orderID = " + orderID + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GivenOrder that = (GivenOrder) o;
        return id == that.id
                && orderID == that.orderID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderID);
    }
}