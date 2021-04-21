package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.DAO;
import java.sql.*;

public class DAOFactory {
    public static DAO createDAO(String tableName, Connection connection) throws SQLException {
        DAO dao = null;
        switch (tableName) {
            case "CMPNNTS":
                dao = new ComponentDAO(connection);
                break;
            case "DRGSCMPS":
                dao = new DrugComponentDAO(connection);
                break;
            case "DRGTYPES":
                dao = new DrugTypeDAO(connection);
                break;
            case "DRUGS":
                dao = new DrugDAO(connection);
                break;
            case "GIVEN":
                dao = new GivenOrderDAO(connection);
                break;
            case "INPRCSS":
                dao = new OrderInProcessDAO(connection);
                break;
            case "ORDERS":
                dao = new OrderDAO(connection);
                break;
            case "TCHNLGS":
                dao = new TechnologyDAO(connection);
                break;
            default:
                break;
        }
        return dao;
    }
}