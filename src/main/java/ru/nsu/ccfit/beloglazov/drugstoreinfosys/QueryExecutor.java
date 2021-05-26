package ru.nsu.ccfit.beloglazov.drugstoreinfosys;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class QueryExecutor {
    private final QueryFrame qf;
    private final DAOFactory daoFactory;

    public QueryExecutor(QueryFrame qf, DAOFactory daoFactory) {
        this.qf = qf;
        this.daoFactory = daoFactory;
    }

    public String[] execute(String queryName, String[] parameters) throws SQLException {
        switch (queryName) {
            case "Cost of ready drug / its components":
                return costOfDrugAndComponents((parameters == null) ? null : parameters[0]);
            case "Info about drug":
                return infoAboutDrug((parameters == null) ? null : parameters[0]);
            case "Set ready time":
                return setReadyTime(parameters[0], parameters[1]);
            case "Set order given":
                return setOrderGiven(parameters[0]);
            case "Add amount for component":
                return addAmountForComponent(parameters[0], parameters[1]);
            case "Add amount for drug":
                return addAmountForDrug(parameters[0], parameters[1]);
            case "How to prepare drug":
                return howToPrepareDrug((parameters == null) ? null : parameters[0]);
            default:
                return null;
        }
    }

    private String[] infoAboutDrug(String drugName) throws SQLException {
        if (drugName == null) {
            QueryParameterFrame qpf = new QueryParameterFrame(qf, "Info about drug", "Print drug name", null);
            return new String[0];
        } else {
            Technology technology = daoFactory.tDAO.getByDrugName(drugName);
            Drug drug = daoFactory.dDAO.getByTechnologyID(technology.getID());
            DrugType dt = daoFactory.dtDAO.getByID(drug.getTypeID());
            Set<Integer> componentsIDs = daoFactory.dcDAO.getComponentsForDrug(drug.getID()).keySet();
            List<String> result = new LinkedList<>();
            result.add("NAME: " + drugName);
            result.add("TYPE: " + dt.getName());
            result.add("PRICE: $" + drug.getPrice());
            result.add("AMOUNT: " + drug.getAmount());
            result.add("CRIT. NORMA: " + drug.getCritNorma());
            result.add("COMPONENTS:");
            int count = 1;
            for (int cID : componentsIDs) {
                Component component = daoFactory.coDAO.getByID(cID);
                result.add(count + ". " + component.getName());
                count++;
            }
            return result.toArray(new String[0]);
        }
    }

    private String[] costOfDrugAndComponents(String drugName) throws SQLException {
        if (drugName == null) {
            QueryParameterFrame qpf = new QueryParameterFrame(qf, "Cost of ready drug / its components", "Print drug name", null);
            return new String[0];
        } else {
            Technology technology = daoFactory.tDAO.getByDrugName(drugName);
            Drug drug = daoFactory.dDAO.getByTechnologyID(technology.getID());
            Map<Integer, Float> componentsAndGrams = daoFactory.dcDAO.getComponentsForDrug(drug.getID());
            Set<Integer> componentsIDs = componentsAndGrams.keySet();
            List<String> result = new LinkedList<>();
            result.add("NAME: " + drugName);
            result.add("PRICE: $" + drug.getPrice());
            result.add("MADE OF COMPONENTS:");
            int count = 1;
            float sum = 0;
            for (int cID : componentsIDs) {
                Component component = daoFactory.coDAO.getByID(cID);
                float costPerGram = component.getCostPerGram();
                float grams = componentsAndGrams.get(cID);
                result.add(count + ". " + component.getName()
                        + ": $" + costPerGram + "/gram, "
                        + grams + " grams"
                );
                sum += costPerGram * grams;
                count++;
            }
            result.add("COST OF COMPONENTS SEPARATELY: $" + sum);
            return result.toArray(new String[0]);
        }
    }

    public String[] setReadyTime(String orderID, String readyTime) {
        if (readyTime == null) {
            String[] currentArgs = new String[1];
            currentArgs[0] = orderID;
            QueryParameterFrame qpf = new QueryParameterFrame(qf, "Set ready time", "Print ready time (yyyy-mm-dd hh:mm:ss.ms)", currentArgs);
            return new String[0];
        }
        boolean enoughComponents = true;
        Map<Integer, Float> grams = null;
        List<Component> components = new LinkedList<>();
        int minusDrugs;
        int minusComponents;
        Drug drug;
        try {
            Order order = daoFactory.oDAO.getByID(Integer.parseInt(orderID));
            int amount = order.getAmount();
            int drugID = order.getDrugID();
            drug = daoFactory.dDAO.getByID(drugID);
            minusDrugs = Math.min(drug.getAmount(), amount);
            minusComponents = amount - minusDrugs;
            if (minusComponents > 0) {
                grams = daoFactory.dcDAO.getComponentsForDrug(drugID);
                for (Integer cID : grams.keySet()) {
                    Component component = daoFactory.coDAO.getByID(cID);
                    if (grams.get(cID) * minusComponents > component.getAmount()) {
                        enoughComponents = false;
                    }
                    components.add(component);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    qf, "Could not get details of order!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
            return new String[0];
        }
        List<String> result = new LinkedList<>();
        try {
            if (enoughComponents) {
                drug.decrementAmount(minusDrugs);
                daoFactory.dDAO.update(drug);
                if (minusComponents > 0) {
                    for (Component component : components) {
                        component.decrementAmount(grams.get(component.getID()).intValue() * minusComponents);
                        daoFactory.coDAO.update(component);
                    }
                }
                OrderInProcess oip = new OrderInProcess(
                        Integer.parseInt(orderID), Timestamp.valueOf(readyTime));
                daoFactory.oipDAO.add(oip);
                result.add("Ready time " + readyTime + " for order " + orderID + " was set successfully");
            } else {
                result.add("Not enough components for order");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    qf, "Could not set ready time!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
            return new String[0];
        }
        return result.toArray(new String[0]);
    }

    private String[] setOrderGiven(String orderID) throws SQLException {
        List<String> result = new LinkedList<>();
        Order order = daoFactory.oDAO.getByID(Integer.parseInt(orderID));
        OrderInProcess oip = daoFactory.oipDAO.getByOrderID(Integer.parseInt(orderID));
        if (order.isGiven()) {
            result.add("Order №" + orderID + " is already marked as given");
            return result.toArray(new String[0]);
        }
        if (oip == null) {
            result.add("Order №" + orderID + " is not in process");
            return result.toArray(new String[0]);
        }
        daoFactory.oDAO.setGiven(Integer.parseInt(orderID));
        daoFactory.oipDAO.delete(oip.getID());
        result.add("Order №" + orderID + " was marked as given");
        return result.toArray(new String[0]);
    }

    private String[] addAmountForComponent(String componentName, String grams) throws SQLException {
        if (grams == null) {
            String[] currentArgs = new String[1];
            currentArgs[0] = componentName;
            QueryParameterFrame qpf = new QueryParameterFrame(qf, "Add amount for component", "Print grams to add", currentArgs);
            return new String[0];
        }
        Component component = daoFactory.coDAO.getByName(componentName);
        component.incrementAmount(Integer.parseInt(grams));
        daoFactory.coDAO.update(component);
        List<String> result = new LinkedList<>();
        result.add(grams + " grams were added for component " + component.getName());
        return result.toArray(new String[0]);
    }

    private String[] addAmountForDrug(String drugName, String grams) throws SQLException {
        if (grams == null) {
            String[] currentArgs = new String[1];
            currentArgs[0] = drugName;
            QueryParameterFrame qpf = new QueryParameterFrame(qf, "Add amount for drug", "Print number of packs to add", currentArgs);
            return new String[0];
        }
        Technology technology = daoFactory.tDAO.getByDrugName(drugName);
        Drug drug = daoFactory.dDAO.getByTechnologyID(technology.getID());
        drug.incrementAmount(Integer.parseInt(grams));
        daoFactory.dDAO.update(drug);
        List<String> result = new LinkedList<>();
        result.add(grams + " packs were added for drug " + drugName);
        return result.toArray(new String[0]);
    }

    private String[] howToPrepareDrug(String drugName) throws SQLException {
        if (drugName == null) {
            QueryParameterFrame qpf = new QueryParameterFrame(qf, "How to prepare drug", "Print drug name", null);
            return new String[0];
        } else {
            Technology technology = daoFactory.tDAO.getByDrugName(drugName);
            Drug drug = daoFactory.dDAO.getByTechnologyID(technology.getID());
            Map<Integer, Float> components = daoFactory.dcDAO.getComponentsForDrug(drug.getID());
            List<String> result = new LinkedList<>();
            result.add("DRUG NAME: " + drugName);
            result.add("COMPONENTS:");
            int count = 1;
            for (int cID : components.keySet()) {
                Component component = daoFactory.coDAO.getByID(cID);
                result.add(count + ". " + component.getName() + ": " + components.get(cID) + " grams");
                count++;
            }
            result.add("TECHNOLOGY: " + technology.getDescription());
            return result.toArray(new String[0]);
        }
    }
}