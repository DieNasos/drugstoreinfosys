package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.OrderDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Order;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.TableItem;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class OrderFrame extends ItemFrame {
    private final JLabel customerIDLabel = new JLabel("CUSTOMER ID:");
    private final JTextField customerIDTextField = new JTextField();
    private final JLabel drugIDLabel = new JLabel("DRUG ID:");
    private final JTextField drugIDTextField = new JTextField();
    private final JLabel amountLabel = new JLabel("AMOUNT:");
    private final JTextField amountTextField = new JTextField();
    private final JLabel givenLabel = new JLabel("GIVEN:");
    private final JTextField givenTextField = new JTextField();

    public OrderFrame(ItemFrameType type, String tableName, TableItem ti, JFrame parentFrame, Connection connection) {
        super(type, tableName, ti, parentFrame, connection);
        initComponents();
        setBounds(10, 10, 300, 370);
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            customerIDTextField.setText(String.valueOf(((Order) ti).getCustomerID()));
            drugIDTextField.setText(String.valueOf(((Order) ti).getDrugID()));
            amountTextField.setText(String.valueOf(((Order) ti).getAmount()));
            givenTextField.setText(String.valueOf(((Order) ti).isGiven()));
        } else if (type == ItemFrameType.FIND) {
            customerIDTextField.setText("= 1");
            drugIDTextField.setText("= 1");
            amountTextField.setText("= 1");
            givenTextField.setText("= false");
        }
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        customerIDLabel.setBounds(10, 170, 260, 30);
        customerIDTextField.setBounds(100, 170, 170, 30);
        drugIDLabel.setBounds(10, 210, 260, 30);
        drugIDTextField.setBounds(70, 210, 200, 30);
        amountLabel.setBounds(10, 250, 260, 30);
        amountTextField.setBounds(70, 250, 200, 30);
        givenLabel.setBounds(10, 290, 260, 30);
        givenTextField.setBounds(70, 290, 200, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(customerIDLabel);
        container.add(customerIDTextField);
        container.add(drugIDLabel);
        container.add(drugIDTextField);
        container.add(amountLabel);
        container.add(amountTextField);
        container.add(givenLabel);
        container.add(givenTextField);
    }

    @Override
    protected void create() {
        try {
            OrderDAO dao = (OrderDAO) DAOFactory.createDAO(tableName, connection);
            int customerID = Integer.parseInt(customerIDTextField.getText());
            int drugID = Integer.parseInt(drugIDTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());
            boolean given = Boolean.getBoolean(givenTextField.getText());
            Order o = new Order(customerID, drugID, amount, given);
            dao.add(o);
            parentFrame.setVisible(true);
            dispose();
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not create item!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    protected void edit() {
        try {
            OrderDAO dao = (OrderDAO) DAOFactory.createDAO(tableName, connection);
            int customerID = Integer.parseInt(customerIDTextField.getText());
            int drugID = Integer.parseInt(drugIDTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());
            boolean given = Boolean.getBoolean(givenTextField.getText());
            Order o = new Order(((Order) ti).getID(), customerID, drugID, amount, given);
            dao.update(o);
            parentFrame.setVisible(true);
            dispose();
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not edit item!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    protected void find() {
        try {
            OrderDAO dao = (OrderDAO) DAOFactory.createDAO(tableName, connection);
            StringBuilder condition = new StringBuilder();
            String s1 = null, s2 = null, s3 = null, s4 = null;
            if (!customerIDTextField.getText().equals("")) {
                s1 = "customer_id " + customerIDTextField.getText();
            }
            if (!drugIDTextField.getText().equals("")) {
                s2 = "drug_id " + drugIDTextField.getText();
            }
            if (!amountTextField.getText().equals("")) {
                s3 = "amount " + amountTextField.getText();
            }
            if (!givenTextField.getText().equals("")) {
                s4 = "given " + givenTextField.getText();
            }
            appendConditionPart(condition, s1);
            appendConditionPart(condition, s2);
            appendConditionPart(condition, s3);
            appendConditionPart(condition, s4);
            List<TableItem> foundItems;
            if (condition.length() > 0) {
                foundItems = dao.getByParameters(condition.toString());
            } else {
                foundItems = dao.getAll();
            }
            parentFrame.setVisible(true);
            if (parentFrame instanceof TableFrame) {
                ((TableFrame) parentFrame).updateItems(foundItems);
            }
            dispose();
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not find items!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void setCustomerID(int customerID) {
        customerIDTextField.setText(String.valueOf(customerID));
    }

    public void setGiven(boolean given) {
        givenTextField.setText(String.valueOf(given));
    }
}