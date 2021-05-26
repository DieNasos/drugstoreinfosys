package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.Role;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.TableItem;
import java.sql.*;

public class FrameFactory {
    public static ItemFrame getItemFrame(String tableName, ItemFrameType type, TableItem ti, TableFrame tf, DAOFactory daoFactory) {
        switch (tableName) {
            case "CMPNNTS":
                return new ComponentFrame(type, ti, tf, daoFactory);
            case "DRGSCMPS":
                return new DrugComponentFrame(type, ti, tf, daoFactory);
            case "DRGTYPES":
                return new DrugTypeFrame(type, ti, tf, daoFactory);
            case "DRUGS":
                return new DrugFrame(type, ti, tf, daoFactory);
            case "INPRCSS":
                return new OrderInProcessFrame(type, ti, tf, daoFactory);
            case "ORDERS":
                return new OrderFrame(type, ti, tf, daoFactory);
            case "TCHNLGS":
                return new TechnologyFrame(type, ti, tf, daoFactory);
            default:
                return null;
        }
    }

    public static MainFrame getMainFrame(LoginFrame lf, DAOFactory daoFactory, Role role, String login) throws SQLException {
        switch (role) {
            case ADMIN:
                return new AdminFrame(lf, daoFactory);
            case STORE_WORKER:
                return new StoreWorkerFrame(lf, daoFactory);
            case CUSTOMER:
                return new CustomerFrame(lf, daoFactory, login);
            default:
                return null;
        }
    }
}