package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.DAO;
import java.sql.*;

public class DAOFactory {
    public static DAO createDAO(String tableName, Connection connection) {
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
            case "INPRCSS":
                dao = new OrderInProcessDAO(connection);
                break;
            case "ORDERS":
                dao = new OrderDAO(connection);
                break;
            case "TCHNLGS":
                dao = new TechnologyDAO(connection);
                break;
            case "CSTMRS":
                dao = new CustomerDAO(connection);
                break;
            case "USERS":
                dao = new UserDAO(connection);
                break;
            case "ROLES":
                dao = new RoleDAO(connection);
            default:
                break;
        }
        return dao;
    }
}