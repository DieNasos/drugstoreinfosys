package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.RoleDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.tablesdao.*;
import java.sql.Connection;

public class DAOFactory {
    public final ComponentDAO coDAO;
    public final CustomerDAO cuDAO;
    public final DrugComponentDAO dcDAO;
    public final DrugDAO dDAO;
    public final DrugTypeDAO dtDAO;
    public final OrderDAO oDAO;
    public final OrderInProcessDAO oipDAO;
    public final TechnologyDAO tDAO;
    public final RoleDAO rDAO;
    
    public DAOFactory(Connection connection) {
        coDAO = new ComponentDAO(connection);
        cuDAO = new CustomerDAO(connection);
        dcDAO = new DrugComponentDAO(connection);
        dDAO = new DrugDAO(connection);
        dtDAO = new DrugTypeDAO(connection);
        oDAO = new OrderDAO(connection);
        oipDAO = new OrderInProcessDAO(connection);
        tDAO = new TechnologyDAO(connection);
        rDAO = new RoleDAO(connection);
    }
}