package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.sql.Connection;

public class FrameFactory {
    public static ItemFrame getItemFrame(String tableName, TableItem ti, TableFrame tf, Connection connection) {
        switch (tableName) {
            case "CMPNNTS":
                return new ComponentFrame(ti, tf, connection);
            case "DRGSCMPS":
                return new DrugComponentFrame(ti, tf, connection);
            case "DRGTYPES":
                return new DrugTypeFrame(ti, tf, connection);
            case "DRUGS":
                return new DrugFrame(ti, tf, connection);
            case "GIVEN":
                return new GivenOrderFrame(ti, tf, connection);
            case "INPRCSS":
                return new OrderInProcessFrame(ti, tf, connection);
            case "ORDERS":
                return new OrderFrame(ti, tf, connection);
            case "TCHNLGS":
                return new TechnologyFrame(ti, tf, connection);
            default:
                return null;
        }
    }
}