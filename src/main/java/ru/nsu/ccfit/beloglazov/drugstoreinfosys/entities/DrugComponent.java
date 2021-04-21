package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.util.*;

public class DrugComponent implements TableItem {
    private final int id;
    private final int drugID;
    private final int componentID;
    private final float gramsOfComponent;

    public DrugComponent(int drugID, int componentID, float gramsOfComponent) {
        id = -1;
        this.drugID = drugID;
        this.componentID = componentID;
        this.gramsOfComponent = gramsOfComponent;
    }

    public DrugComponent(int id, int drugID, int componentID, float gramsOfComponent) {
        this.id = id;
        this.drugID = drugID;
        this.componentID = componentID;
        this.gramsOfComponent = gramsOfComponent;
    }

    public int getID() { return id; }
    public int getDrugID() { return drugID; }
    public int getComponentID() { return componentID; }
    public float getGramsOfComponent() { return gramsOfComponent; }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        values.put("drug_id", drugID);
        values.put("component_id", componentID);
        values.put("grams_of_component", gramsOfComponent);
        return values;
    }

    @Override
    public String toString() {
        return "DrugComponent{id = " + id
                + ", drug_id = " + drugID
                + ", component_id = " + componentID
                + ", grams_of_component = " + gramsOfComponent + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugComponent that = (DrugComponent) o;
        return id == that.id
                && drugID == that.drugID
                && componentID == that.componentID
                && gramsOfComponent == that.gramsOfComponent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drugID, componentID, gramsOfComponent);
    }
}