package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import java.util.Map;

public abstract class TableItem {
    protected Map<String, Object> values;
    public abstract Map<String, Object> getValues();
}