package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.TableAccessType;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes.*;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class CustomerFrame extends MainFrame {
    public CustomerFrame(LoginFrame lf, Connection connection, User user) {
        super(lf, connection, user);
        roleLabel.setText("You are CUSTOMER");
        setBounds(10,10,300,450);
    }

    @Override
    protected void initOptions() {
        optionsArray = new String[] {
                "My orders",
                "Create order",
                "Drugs catalog"
        };
    }

    @Override
    protected void executeOption(String option) {
        try {
            CustomerDAO cDAO = new CustomerDAO(connection);
            OrderDAO oDAO = new OrderDAO(connection);
            OrderInProcessDAO oipDAO = new OrderInProcessDAO(connection);
            DrugDAO dDAO = new DrugDAO(connection);
            TechnologyDAO tDAO = new TechnologyDAO(connection);
            DrugTypeDAO dtDAO = new DrugTypeDAO(connection);
            switch (option) {
                case "My orders":
                    TableFrame oTF = new TableFrame(this, "ORDERS", connection, TableAccessType.READ_ONLY);
                    Customer customer = cDAO.getByUserID(user.getID());
                    List<TableItem> orders = oDAO.getByCustomerID(customer.getID());
                    for (TableItem order : orders) {
                        int drugID = (Integer) order.getValues().get("drug_id");
                        Drug drug = dDAO.getByID(drugID);
                        Technology technology = tDAO.getByID(drug.getTechnologyID());
                        if (!((Order) order).isGiven()) {
                            OrderInProcess oip = oipDAO.getByOrderID(((Order) order).getID());
                            Timestamp readyTime = oip.getReadyTime();
                            order.getValues().put("ready_time", readyTime);
                        } else {
                            order.getValues().put("ready_time", "-");
                        }
                        order.getValues().put("drug_name", technology.getDrugName());
                        order.getValues().remove("drug_id");
                        order.getValues().remove("customer_id");
                        order.getValues().remove("id");
                    }
                    oTF.updateItems(orders);
                    break;
                case "Create order":
                    OrderFrame of = new OrderFrame(ItemFrameType.CREATE, "ORDERS", null, this, connection);
                    Customer c = cDAO.getByUserID(user.getID());
                    of.setCustomerID(c.getID());
                    of.setGiven(false);
                    break;
                case "Drugs catalog":
                    TableFrame dTF = new TableFrame(this, "DRUGS", connection, TableAccessType.READ_ONLY);
                    List<TableItem> drugs = dDAO.getAll();
                    for (TableItem drug : drugs) {
                        DrugType type = dtDAO.getByID(((Drug) drug).getTypeID());
                        Technology technology = tDAO.getByID(((Drug) drug).getTechnologyID());
                        drug.getValues().put("type", type.getName());
                        drug.getValues().remove("type_id");
                        drug.getValues().put("name", technology.getDrugName());
                        drug.getValues().remove("technology_id");
                        drug.getValues().remove("crit_norma");
                    }
                    dTF.updateItems(drugs);
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not execute option!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}