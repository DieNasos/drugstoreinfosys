package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import java.util.*;

public class Component extends TableItem {
    private final int id;
    private final String name;
    private int amount;
    private final float costPerGram;

    public Component(String name, int amount, float costPerGram) {
        this.id = -1;
        this.name = name;
        this.amount = amount;
        this.costPerGram = costPerGram;
    }

    public Component(int id, String name, int amount, float costPerGram) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.costPerGram = costPerGram;
    }

    public int getID() { return id; }
    public String getName() { return name; }
    public int getAmount() { return amount; }
    public float getCostPerGram() { return costPerGram; }

    public void decrementAmount(int gramsTaken) {
        amount -= gramsTaken;
    }

    public void incrementAmount(int gramsAdded) {
        amount += gramsAdded;
    }

    @Override
    public Map<String, Object> getValues() {
        if (values == null) {
            values = new HashMap<>();
            values.put("id", id);
            values.put("name", name);
            values.put("amount", amount);
            values.put("cost_per_gram", costPerGram);
        }
        return values;
    }

    @Override
    public String toString() {
        return "Component{id = " + id
                + ", name = '" + name
                + "', amount = " + amount
                + ", cost_per_gram = " + costPerGram + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return id == component.id
                && amount == component.amount
                && Objects.equals(name, component.name)
                && Objects.equals(costPerGram, component.costPerGram);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, costPerGram);
    }
}