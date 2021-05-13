package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.RoleDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.TableItem;
import java.sql.*;

public class FrameFactory {
    public static ItemFrame getItemFrame(String tableName, ItemFrameType type, TableItem ti, TableFrame tf, Connection connection) {
        switch (tableName) {
            case "CMPNNTS":
                return new ComponentFrame(type, tableName,ti, tf, connection);
            case "DRGSCMPS":
                return new DrugComponentFrame(type, tableName,ti, tf, connection);
            case "DRGTYPES":
                return new DrugTypeFrame(type, tableName,ti, tf, connection);
            case "DRUGS":
                return new DrugFrame(type, tableName,ti, tf, connection);
            case "INPRCSS":
                return new OrderInProcessFrame(type, tableName,ti, tf, connection);
            case "ORDERS":
                return new OrderFrame(type, tableName,ti, tf, connection);
            case "TCHNLGS":
                return new TechnologyFrame(type, tableName,ti, tf, connection);
            default:
                return null;
        }
    }

    public static MainFrame getMainFrame(LoginFrame lf, Connection connection, User user) throws SQLException {
        RoleDAO rDAO = new RoleDAO(connection);
        Role role = rDAO.getByID(user.getRoleID());
        switch (role.getName()) {
            case "admin":
                return new AdminFrame(lf, connection, user);
            case "store_worker":
                return new StoreWorkerFrame(lf, connection, user);
            case "customer":
                return new CustomerFrame(lf, connection, user);
            default:
                return null;
        }
    }
}