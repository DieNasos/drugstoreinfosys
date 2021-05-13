package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import java.util.*;

public class Technology extends TableItem {
    private final int id;
    private final String drugName;
    private final String description;

    public Technology(String drugName, String description) {
        id = -1;
        this.drugName = drugName;
        this.description = description;
    }

    public Technology(int id, String drugName, String description) {
        this.id = id;
        this.drugName = drugName;
        this.description = description;
    }

    public int getID() { return id; }
    public String getDrugName() {return drugName;}
    public String getDescription() { return description; }

    @Override
    public Map<String, Object> getValues() {
        if (values == null) {
            values = new HashMap<>();
            values.put("id", id);
            values.put("drug_name", drugName);
            values.put("description", description);
        }
        return values;
    }

    @Override
    public String toString() {
        return "Technology{id = " + id
                + ", drug_name = '" + drugName
                + "', description = '" + description + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Technology that = (Technology) o;
        return id == that.id
                && Objects.equals(drugName, that.drugName)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drugName, description);
    }
}