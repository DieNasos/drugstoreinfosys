package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.util.*;

public class Drug implements TableItem {
    private final int id;
    private final int typeID;
    private final int technologyID;
    private final float price;
    private final int amount;
    private final int critNorma;

    public Drug(int typeID, int technologyID, float price, int amount, int critNorma) {
        id = -1;
        this.typeID = typeID;
        this.technologyID = technologyID;
        this.price = price;
        this.amount = amount;
        this.critNorma = critNorma;
    }

    public Drug(int id, int typeID, int technologyID, float price, int amount, int critNorma) {
        this.id = id;
        this.typeID = typeID;
        this.technologyID = technologyID;
        this.price = price;
        this.amount = amount;
        this.critNorma = critNorma;
    }

    public int getID() { return id; }
    public int getTypeID() { return typeID; }
    public int getTechnologyID() { return technologyID; }
    public float getPrice() { return price; }
    public int getAmount() { return amount; }
    public int getCritNorma() { return critNorma; }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        values.put("type_id", typeID);
        values.put("technology_id", technologyID);
        values.put("price", price);
        values.put("amount", amount);
        values.put("crit_norma", critNorma);
        return values;
    }

    @Override
    public String toString() {
        return "Drug{id = " + id + ", type_id = " + typeID
                + ", technology_id = " + technologyID
                + ", price = " + price
                + ", amount = " + amount
                + ", crit_norma = " + critNorma + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        return id == drug.id && typeID == drug.typeID
                && technologyID == drug.technologyID
                && Float.compare(drug.price, price) == 0
                && amount == drug.amount
                && critNorma == drug.critNorma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeID, technologyID, price, amount, critNorma);
    }
}