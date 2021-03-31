package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.components.Component;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.drugtypes.DrugType;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.sql.Connection;

public class FrameFactory {
    public static CreateFrame getCreateFrame(String tableName, TableFrame tf, Connection connection) {
        CreateFrame cf = null;
        switch (tableName) {
            case "DRGTYPES":
                cf = new DrugTypeCreateFrame(tf, connection);
                break;
            case "CMPNNTS":
                cf = new ComponentCreateFrame(tf, connection);
                break;
            default:
                break;
        }
        return cf;
    }

    public static EditFrame getEditFrame(String tableName, TableItem ti, TableFrame tf, Connection connection) {
        EditFrame ef = null;
        switch (tableName) {
            case "DRGTYPES":
                ef = new DrugTypeEditFrame((DrugType) ti, tf, connection);
                break;
            case "CMPNNTS":
                ef = new ComponentEditFrame((Component) ti, tf, connection);
                break;
            default:
                break;
        }
        return ef;
    }
}