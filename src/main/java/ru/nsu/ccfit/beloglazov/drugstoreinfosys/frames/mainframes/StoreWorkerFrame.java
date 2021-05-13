package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.TableAccessType;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import javax.swing.*;
import java.sql.Connection;
import java.util.*;

public class StoreWorkerFrame extends MainFrame {
    public StoreWorkerFrame(LoginFrame lf, Connection connection, User user) {
        super(lf, connection, user);
        roleLabel.setText("You are STORE WORKER");
        setBounds(10,10,300,450);
    }

    @Override
    protected void initOptions() {
        optionsArray = new String[] {
                "Open Components",
                "Open Drugs components",
                "Open Drugs",
                "Open Drug types",
                "Open All orders",
                "Open Orders in process",
                "Open Technologies",
                "Customers who haven't received drugs",
                "Cost of ready drug / its components",
                "Info about drug"
        };
    }

    @Override
    protected void executeOption(String option) {
        try {
            OrderInProcessDAO oipDAO = new OrderInProcessDAO(connection);
            OrderDAO oDAO = new OrderDAO(connection);
            CustomerDAO cDAO = new CustomerDAO(connection);
            switch (option) {
                case "Open Components":
                    TableFrame tf1 = new TableFrame(
                            this, "CMPNNTS", connection, TableAccessType.READ_ONLY
                    );
                    break;
                case "Open Drugs components":
                    TableFrame tf2 = new TableFrame(
                            this, "DRGSCMPS", connection, TableAccessType.READ_ONLY
                    );
                    break;
                case "Open Drugs":
                    TableFrame tf3 = new TableFrame(
                            this, "DRUGS", connection, TableAccessType.READ_ONLY
                    );
                    break;
                case "Open Drug types":
                    TableFrame tf4 = new TableFrame(
                            this, "DRGTYPES", connection, TableAccessType.READ_ONLY
                    );
                    break;
                case "Open All orders":
                    TableFrame tf5 = new TableFrame(
                            this, "ORDERS", connection, TableAccessType.READ_ONLY
                    );
                    break;
                case "Open Orders in process":
                    TableFrame tf6 = new TableFrame(
                            this, "INPRCSS", connection, TableAccessType.READ_ONLY
                    );
                    break;
                case "Open Technologies":
                    TableFrame tf7 = new TableFrame(
                            this, "TCHNLGS", connection, TableAccessType.READ_ONLY
                    );
                    break;
                case "Customers who haven't received drugs":
                    TableFrame tf8 = new TableFrame(
                            this, "CSTMRS", connection, TableAccessType.READ_ONLY
                    );
                    List<OrderInProcess> ordersInProcess = oipDAO.getForgottenOrders();
                    List<TableItem> orders = new LinkedList<>();
                    for (OrderInProcess orderInProcess : ordersInProcess) {
                        orders.add(oDAO.getByID(orderInProcess.getOrderID()));
                    }
                    List<TableItem> customers = new LinkedList<>();
                    for (TableItem order : orders) {
                        Customer customer = cDAO.getByID(((Order)order).getCustomerID());
                        customer.getValues().put("order_id", ((Order)order).getID());
                        customer.getValues().remove("user_id");
                        customer.getValues().remove("id");
                        customers.add(customer);
                    }
                    tf8.updateItems(customers);
                    break;
                default:
                    qf.executeQuery(option, null);
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