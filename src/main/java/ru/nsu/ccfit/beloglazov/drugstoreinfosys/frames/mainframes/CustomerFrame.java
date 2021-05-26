package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.TableAccessType;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes.*;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class CustomerFrame extends MainFrame {
    private final String login;

    public CustomerFrame(LoginFrame lf, DAOFactory daoFactory, String login) {
        super(lf, daoFactory);
        this.login = login;
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
            switch (option) {
                case "My orders":
                    TableFrame oTF = new TableFrame(this, "ORDERS", TableAccessType.READ_ONLY, daoFactory, daoFactory.oDAO);
                    Customer customer = daoFactory.cuDAO.getByLogin(login);
                    List<TableItem> orders = daoFactory.oDAO.getByCustomerID(customer.getID());
                    for (TableItem order : orders) {
                        int drugID = (Integer) order.getValues().get("drug_id");
                        Drug drug = daoFactory.dDAO.getByID(drugID);
                        Technology technology = daoFactory.tDAO.getByID(drug.getTechnologyID());
                        order.getValues().put("ready_time", "-");
                        if (!((Order) order).isGiven()) {
                            OrderInProcess oip = daoFactory.oipDAO.getByOrderID(((Order) order).getID());
                            if (oip != null) {
                                Timestamp readyTime = oip.getReadyTime();
                                order.getValues().put("ready_time", readyTime);
                            }
                        }
                        order.getValues().put("drug_name", technology.getDrugName());
                        order.getValues().remove("drug_id");
                        order.getValues().remove("customer_id");
                        order.getValues().remove("id");
                    }
                    oTF.updateItems(orders);
                    break;
                case "Create order":
                    OrderFrame of = new OrderFrame(ItemFrameType.CREATE, null, this, daoFactory);
                    Customer c = daoFactory.cuDAO.getByLogin(login);
                    of.setCustomerID(c.getID());
                    of.setGiven(false);
                    break;
                case "Drugs catalog":
                    TableFrame dTF = new TableFrame(this, "DRUGS", TableAccessType.READ_ONLY, daoFactory, daoFactory.dDAO);
                    List<TableItem> drugs = daoFactory.dDAO.getAll();
                    for (TableItem drug : drugs) {
                        DrugType type = daoFactory.dtDAO.getByID(((Drug) drug).getTypeID());
                        Technology technology = daoFactory.tDAO.getByID(((Drug) drug).getTechnologyID());
                        drug.getValues().put("type", type.getName());
                        drug.getValues().put("name", technology.getDrugName());
                        drug.getValues().remove("type_id");
                        drug.getValues().remove("technology_id");
                        drug.getValues().remove("crit_norma");
                        drug.getValues().remove("amount");
                        drug.getValues().remove("id");
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