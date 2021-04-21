package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.util.*;

public class DrugType implements TableItem {
    private final int id;
    private final String name;

    public DrugType(String name) {
        this.id = -1;
        this.name = name;
    }

    public DrugType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() { return id; }
    public String getName() { return name; }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        values.put("name", name);
        return values;
    }

    @Override
    public String toString() {
        return "DrugType{id = " + id
                + ", name = '" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugType drugType = (DrugType) o;
        return id == drugType.id
                && Objects.equals(name, drugType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}