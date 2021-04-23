package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.OrderDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Order;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class OrderFrame extends ItemFrame {
    private final JLabel customerNameLabel = new JLabel("CUSTOMER:");
    private final JTextField customerNameTextField = new JTextField();
    private final JLabel phoneNumberLabel = new JLabel("PHONE:");
    private final JTextField phoneNumberTextField = new JTextField();
    private final JLabel addressLabel = new JLabel("ADDRESS:");
    private final JTextField addressTextField = new JTextField();
    private final JLabel drugIDLabel = new JLabel("DRUG ID:");
    private final JTextField drugIDTextField = new JTextField();
    private final JLabel amountLabel = new JLabel("AMOUNT:");
    private final JTextField amountTextField = new JTextField();

    public OrderFrame(ItemFrameType type, TableItem ti, TableFrame tf, Connection connection) {
        super(type, ti, tf, connection);
        initComponents();
        setBounds(10, 10, 300, 410);
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            customerNameTextField.setText(((Order) ti).getCustomerName());
            phoneNumberTextField.setText(((Order) ti).getPhoneNumber());
            addressTextField.setText(((Order) ti).getAddress());
            drugIDTextField.setText(String.valueOf(((Order) ti).getDrugID()));
            amountTextField.setText(String.valueOf(((Order) ti).getAmount()));
        } else if (type == ItemFrameType.FIND) {
            customerNameTextField.setText("= 'Mazhura Denis Denisovich'");
            phoneNumberTextField.setText("= '+7-987-654-32-10'");
            addressTextField.setText("= 'Troika, 34b'");
            drugIDTextField.setText("= 1");
            amountTextField.setText("= 1");
        }
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        customerNameLabel.setBounds(10, 170, 260, 30);
        customerNameTextField.setBounds(90, 170, 180, 30);
        phoneNumberLabel.setBounds(10, 210, 260, 30);
        phoneNumberTextField.setBounds(60, 210, 210, 30);
        addressLabel.setBounds(10, 250, 260, 30);
        addressTextField.setBounds(80, 250, 190, 30);
        drugIDLabel.setBounds(10, 290, 260, 30);
        drugIDTextField.setBounds(70, 290, 200, 30);
        amountLabel.setBounds(10, 330, 260, 30);
        amountTextField.setBounds(70, 330, 200, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(customerNameLabel);
        container.add(customerNameTextField);
        container.add(phoneNumberLabel);
        container.add(phoneNumberTextField);
        container.add(addressLabel);
        container.add(addressTextField);
        container.add(drugIDLabel);
        container.add(drugIDTextField);
        container.add(amountLabel);
        container.add(amountTextField);
    }

    @Override
    protected void create() {
        try {
            OrderDAO dao = (OrderDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String customerName = customerNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String address = addressTextField.getText();
            int drugID = Integer.parseInt(drugIDTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());
            Order o = new Order(customerName, phoneNumber, address, drugID, amount);
            dao.add(o);
            tf.setVisible(true);
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
            OrderDAO dao = (OrderDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String customerName = customerNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String address = addressTextField.getText();
            int drugID = Integer.parseInt(drugIDTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());
            Order o = new Order(((Order)ti).getID(), customerName, phoneNumber, address, drugID, amount);
            dao.update(o);
            tf.setVisible(true);
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
            OrderDAO dao = (OrderDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            StringBuilder condition = new StringBuilder();
            String s1 = null, s2 = null, s3 = null, s4 = null, s5 = null;
            if (!customerNameTextField.getText().equals("")) {
                s1 = "customer_name " + customerNameTextField.getText();
            }
            if (!phoneNumberTextField.getText().equals("")) {
                s2 = "phone_number " + phoneNumberTextField.getText();
            }
            if (!addressTextField.getText().equals("")) {
                s3 = "address " + addressTextField.getText();
            }
            if (!drugIDTextField.getText().equals("")) {
                s4 = "drug_id " + drugIDTextField.getText();
            }
            if (!amountTextField.getText().equals("")) {
                s5 = "amount " + amountTextField.getText();
            }
            appendConditionPart(condition, s1);
            appendConditionPart(condition, s2);
            appendConditionPart(condition, s3);
            appendConditionPart(condition, s4);
            appendConditionPart(condition, s5);
            List<TableItem> foundItems = dao.getByParameters(condition.toString());
            tf.setVisible(true);
            tf.updateItems(foundItems);
            dispose();
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not find items!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}