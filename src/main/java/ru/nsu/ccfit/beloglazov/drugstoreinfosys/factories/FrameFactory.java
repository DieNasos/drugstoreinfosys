package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.sql.Connection;

public class FrameFactory {
    public static ItemFrame getItemFrame(String tableName, ItemFrameType type, TableItem ti, TableFrame tf, Connection connection) {
        switch (tableName) {
            case "CMPNNTS":
                return new ComponentFrame(type, ti, tf, connection);
            case "DRGSCMPS":
                return new DrugComponentFrame(type, ti, tf, connection);
            case "DRGTYPES":
                return new DrugTypeFrame(type, ti, tf, connection);
            case "DRUGS":
                return new DrugFrame(type, ti, tf, connection);
            case "GIVEN":
                return new GivenOrderFrame(type, ti, tf, connection);
            case "INPRCSS":
                return new OrderInProcessFrame(type, ti, tf, connection);
            case "ORDERS":
                return new OrderFrame(type, ti, tf, connection);
            case "TCHNLGS":
                return new TechnologyFrame(type, ti, tf, connection);
            default:
                return null;
        }
    }
}