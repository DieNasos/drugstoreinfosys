package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import java.util.*;

public class Role extends TableItem {
    private final int id;
    private final String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this(-1, name);
    }

    public int getID() { return id; }
    public String getName() { return name; }

    @Override
    public Map<String, Object> getValues() {
        if (values == null) {
            values = new HashMap<>();
            values.put("id", id);
            values.put("name", name);
        }
        return values;
    }

    @Override
    public String toString() {
        return "Role{id = " + id
                + ", name = '" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id
                && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}