package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.components.ComponentDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.DAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.drugtypes.DrugTypeDAO;
import java.sql.*;

public class DAOFactory {
    public static DAO createDAO(String tableName, Connection connection) throws SQLException {
        DAO dao = null;
        switch (tableName) {
            case "DRGTYPES":
                dao = new DrugTypeDAO(connection);
                break;
            case "CMPNNTS":
                dao = new ComponentDAO(connection);
                break;
            default:
                break;
        }
        return dao;
    }
}