package ru.nsu.ccfit.beloglazov.drugstoreinfosys.components;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.util.*;

public class Component implements TableItem {
    private final int id;
    private final String name;
    private final int amount;

    public Component(String name, int amount) {
        this.id = -1;
        this.name = name;
        this.amount = amount;
    }

    public Component(int id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public int getID() { return id; }

    public String getName() { return name; }

    public int getAmount() { return amount; }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        values.put("name", name);
        values.put("amount", amount);
        return values;
    }

    @Override
    public String toString() {
        return "Component{id = " + id + ", name = '" + name + "', amount = " + amount + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return id == component.id && amount == component.amount && Objects.equals(name, component.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount);
    }
}