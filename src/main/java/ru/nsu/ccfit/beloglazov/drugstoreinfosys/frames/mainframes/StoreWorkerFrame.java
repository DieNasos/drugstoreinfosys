package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.CustomOperation;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.TableAccessType;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import javax.swing.*;
import java.sql.Timestamp;
import java.util.*;

public class StoreWorkerFrame extends MainFrame {
    public StoreWorkerFrame(LoginFrame lf, DAOFactory daoFactory) {
        super(lf, daoFactory);
        roleLabel.setText("You are STORE WORKER");
        setBounds(10,10,300,450);
    }

    @Override
    protected void initOptions() {
        optionsArray = new String[] {
                "Open COMPONENTS",
                "Open DRUGS",
                "Open ALL ORDERS",
                "Open CUSTOMERS",
                "Customers who haven't received drugs",
                "Cost of ready drug / its components",
                "Info about drug",
                "Open unprocessed orders",
                "Drugs under critical norma",
                "How to prepare drug"
        };
    }

    @Override
    protected void executeOption(String option) {
        try {
            switch (option) {
                case "Open COMPONENTS":
                    TableFrame tf1 = new TableFrame(
                            this, "CMPNNTS", TableAccessType.READ_ONLY, daoFactory, daoFactory.coDAO
                    );
                    tf1.setCustomOperation(createAddAmountForComponentOperation());
                    List<TableItem> allComponents = daoFactory.coDAO.getAll();
                    for (TableItem component : allComponents) {
                        component.getValues().remove("id");
                    }
                    tf1.updateItems(allComponents);
                    break;
                case "Open DRUGS":
                    TableFrame tf2 = new TableFrame(
                            this, "DRUGS", TableAccessType.READ_ONLY, daoFactory, daoFactory.dDAO
                    );
                    tf2.setCustomOperation(createAddAmountForDrugOperation());
                    List<TableItem> allDrugs = daoFactory.dDAO.getAll();
                    for (TableItem drug : allDrugs) {
                        Technology t = daoFactory.tDAO.getByID(
                                (Integer)drug.getValues().get("technology_id")
                        );
                        drug.getValues().put("name", t.getDrugName());
                        DrugType dt = daoFactory.dtDAO.getByID(
                                (Integer)drug.getValues().get("type_id")
                        );
                        drug.getValues().put("type", dt.getName());
                        drug.getValues().remove("type_id");
                        drug.getValues().remove("technology_id");
                        drug.getValues().remove("id");
                    }
                    tf2.updateItems(allDrugs);
                    break;
                case "Open ALL ORDERS":
                    TableFrame tf3 = new TableFrame(
                            this, "ORDERS", TableAccessType.READ_ONLY, daoFactory, daoFactory.oDAO
                    );
                    tf3.setCustomOperation(createSetGivenOperation());
                    List<TableItem> allOrdersList = daoFactory.oDAO.getAll();
                    for (TableItem order : allOrdersList) {
                        int drugID = (Integer) order.getValues().get("drug_id");
                        Drug drug = daoFactory.dDAO.getByID(drugID);
                        Technology technology = daoFactory.tDAO.getByID(drug.getTechnologyID());
                        order.getValues().remove("drug_id");
                        order.getValues().put("drug", technology.getDrugName());
                        int customerID = (Integer) order.getValues().get("customer_id");
                        Customer customer = daoFactory.cuDAO.getByID(customerID);
                        order.getValues().remove("customer_id");
                        order.getValues().put("customer", customer.getName());
                        int orderID = (Integer) order.getValues().get("id");
                        order.getValues().put("ready_time", "-");
                        OrderInProcess oip = daoFactory.oipDAO.getByOrderID(orderID);
                        if (oip != null) {
                            Timestamp readyTime = oip.getReadyTime();
                            order.getValues().put("ready_time", readyTime);
                        }
                    }
                    tf3.updateItems(allOrdersList);
                    break;
                case "Open CUSTOMERS":
                    TableFrame tf4 = new TableFrame(
                            this, "CUSTOMERS", TableAccessType.READ_ONLY, daoFactory, daoFactory.cuDAO
                    );
                    break;
                case "Customers who haven't received drugs":
                    TableFrame tf5 = new TableFrame(
                            this, "CSTMRS", TableAccessType.READ_ONLY, daoFactory, daoFactory.cuDAO
                    );
                    List<OrderInProcess> ordersInProcess = daoFactory.oipDAO.getForgottenOrders();
                    List<TableItem> orders = new LinkedList<>();
                    for (OrderInProcess orderInProcess : ordersInProcess) {
                        orders.add(daoFactory.oDAO.getByID(orderInProcess.getOrderID()));
                    }
                    List<TableItem> customers = new LinkedList<>();
                    for (TableItem order : orders) {
                        Customer customer = daoFactory.cuDAO.getByID(((Order)order).getCustomerID());
                        customer.getValues().put("order_id", ((Order)order).getID());
                        customer.getValues().remove("user_id");
                        customer.getValues().remove("id");
                        customers.add(customer);
                    }
                    tf5.updateItems(customers);
                    break;
                case "Open unprocessed orders":
                    TableFrame tf6 = new TableFrame(
                            this, "ORDERS", TableAccessType.READ_ONLY, daoFactory, daoFactory.oDAO
                    );
                    List<TableItem> osip = daoFactory.oipDAO.getAll();
                    List<Integer> idsOfOrdersInProcess = new LinkedList<>();
                    for (TableItem oip : osip) {
                        idsOfOrdersInProcess.add((Integer) oip.getValues().get("order_id"));
                    }
                    List<TableItem> allOrders = daoFactory.oDAO.getAll();
                    List<TableItem> unprocessedOrders = new LinkedList<>();
                    for (TableItem order : allOrders) {
                        if (!idsOfOrdersInProcess.contains(
                                order.getValues().get("id"))) {
                            boolean canBePrepared = true;
                            int amount = (Integer) order.getValues().get("amount");
                            int drugID = (Integer) order.getValues().get("drug_id");
                            Drug drug = daoFactory.dDAO.getByID(drugID);
                            Technology t = daoFactory.tDAO.getByID(drug.getTechnologyID());
                            Customer c = daoFactory.cuDAO.getByID((Integer) order.getValues().get("customer_id"));
                            amount -= drug.getAmount();
                            if (amount > 0) {
                                Map<Integer, Float> components = daoFactory.dcDAO.getComponentsForDrug(drugID);
                                for (int cID : components.keySet()) {
                                    Component component = daoFactory.coDAO.getByID(cID);
                                    if (component.getAmount() < components.get(cID) * amount) {
                                        canBePrepared = false;
                                    }
                                }
                            }
                            order.getValues().put("can_be_prepared", canBePrepared);
                            order.getValues().put("drug_name", t.getDrugName());
                            order.getValues().put("customer", c.getName());
                            order.getValues().remove("given");
                            order.getValues().remove("drug_id");
                            order.getValues().remove("customer_id");
                            unprocessedOrders.add(order);
                        }
                    }
                    tf6.updateItems(unprocessedOrders);
                    tf6.setCustomOperation(createSetReadyTimeOperation());
                    break;
                case "Drugs under critical norma":
                    TableFrame tf7 = new TableFrame(
                        this, "DRUGS", TableAccessType.READ_ONLY, daoFactory, daoFactory.dDAO
                    );
                    List<TableItem> drugs = daoFactory.dDAO.getAll();
                    List<TableItem> drugsUnderCritNorma = new LinkedList<>();
                    for (TableItem drug : drugs) {
                        int amount = ((Drug)drug).getAmount();
                        int critNorma = ((Drug)drug).getCritNorma();
                        if (amount < critNorma) {
                            int tID = ((Drug)drug).getTechnologyID();
                            Technology t = daoFactory.tDAO.getByID(tID);
                            String drugName = t.getDrugName();
                            drug.getValues().put("drug_name", drugName);
                            drug.getValues().remove("id");
                            drug.getValues().remove("technology_id");
                            drug.getValues().remove("type_id");
                            drug.getValues().remove("price");
                            drugsUnderCritNorma.add(drug);
                        }
                    }
                    tf7.updateItems(drugsUnderCritNorma);
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

    private CustomOperation createSetReadyTimeOperation() {
        return new CustomOperation("Set ready time") {
            @Override
            public void execute(List<Object> args, TableFrame tf) {
                TableItem order = (TableItem) args.get(0);
                boolean canBePrepared = (Boolean) order.getValues().get("can_be_prepared");
                if (!canBePrepared) {
                    return;
                }
                String[] parameters = new String[3];
                parameters[0] = String.valueOf(order.getValues().get("id"));
                parameters[1] = null;
                qf.executeQuery("Set ready time", parameters);
                tf.dispose();
            }
        };
    }

    private CustomOperation createSetGivenOperation() {
        return new CustomOperation("Set given") {
            @Override
            public void execute(List<Object> args, TableFrame tf) {
                TableItem order = (TableItem) args.get(0);
                String[] parameters = new String[1];
                parameters[0] = String.valueOf(order.getValues().get("id"));
                qf.executeQuery("Set order given", parameters);
                tf.dispose();
            }
        };
    }

    private CustomOperation createAddAmountForComponentOperation() {
        return new CustomOperation("Add amount") {
            @Override
            public void execute(List<Object> args, TableFrame tf) {
                TableItem component = (TableItem) args.get(0);
                String[] parameters = new String[2];
                parameters[0] = String.valueOf(component.getValues().get("name"));
                parameters[1] = null;
                qf.executeQuery("Add amount for component", parameters);
                tf.dispose();
            }
        };
    }

    private CustomOperation createAddAmountForDrugOperation() {
        return new CustomOperation("Add amount") {
            @Override
            public void execute(List<Object> args, TableFrame tf) {
                TableItem drug = (TableItem) args.get(0);
                String[] parameters = new String[2];
                parameters[0] = String.valueOf(drug.getValues().get("name"));
                parameters[1] = null;
                qf.executeQuery("Add amount for drug", parameters);
                tf.dispose();
            }
        };
    }
}