package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Order;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Technology;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.TableItem;
import javax.swing.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderFrame extends ItemFrame {
    private final JLabel customerIDLabel = new JLabel("CUSTOMER ID:");
    private final JTextField customerIDTextField = new JTextField();
    private final JLabel drugLabel = new JLabel("DRUG:");
    private final JComboBox<String> drugsComboBox = new JComboBox<>();
    private final JLabel amountLabel = new JLabel("AMOUNT:");
    private final JTextField amountTextField = new JTextField();
    private final JLabel givenLabel = new JLabel("GIVEN:");
    private final JTextField givenTextField = new JTextField();
    private final List<String> drugNames = new LinkedList<>();

    public OrderFrame(ItemFrameType type, TableItem ti, JFrame parentFrame, DAOFactory daoFactory) {
        super(type, ti, parentFrame, daoFactory, daoFactory.oDAO);
        initComponents();
        setDrugsComboBox();
        setBounds(10, 10, 300, 340);
    }

    private void setDrugsComboBox() {
        try {
            List<TableItem> allDrugs = daoFactory.dDAO.getAll();
            for (TableItem drug : allDrugs) {
                int tID = (Integer) drug.getValues().get("technology_id");
                Technology technology = daoFactory.tDAO.getByID(tID);
                String drugName = technology.getDrugName();
                drugNames.add(drugName);
                drugsComboBox.addItem(drugName);
            }
            drugsComboBox.setSelectedIndex(0);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not get all names of drugs!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            customerIDTextField.setText(String.valueOf(((Order) ti).getCustomerID()));
            amountTextField.setText(String.valueOf(((Order) ti).getAmount()));
            givenTextField.setText(String.valueOf(((Order) ti).isGiven()));
        } else if (type == ItemFrameType.FIND) {
            customerIDTextField.setText("= 1");
            amountTextField.setText("= 1");
            givenTextField.setText("= false");
        }
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        customerIDLabel.setBounds(10, 130, 260, 30);
        customerIDTextField.setBounds(100, 130, 170, 30);
        drugLabel.setBounds(10, 170, 260, 30);
        drugsComboBox.setBounds(70, 170, 200, 30);
        amountLabel.setBounds(10, 210, 260, 30);
        amountTextField.setBounds(70, 210, 200, 30);
        givenLabel.setBounds(10, 250, 260, 30);
        givenTextField.setBounds(70, 250, 200, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(customerIDLabel);
        container.add(customerIDTextField);
        container.add(drugLabel);
        container.add(drugsComboBox);
        container.add(amountLabel);
        container.add(amountTextField);
        container.add(givenLabel);
        container.add(givenTextField);
    }

    private int getSelectedDrugID() {
        int indexOfSelectedDrugName = drugsComboBox.getSelectedIndex();
        String selectedDrugName = drugNames.get(indexOfSelectedDrugName);
        try {
            Technology technology = daoFactory.tDAO.getByDrugName(selectedDrugName);
            return technology.getID();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Some problems with selecting drug name happened!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
            return -1;
        }
    }

    @Override
    protected void create() {
        try {
            int customerID = Integer.parseInt(customerIDTextField.getText());
            int drugID = getSelectedDrugID();
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
            int customerID = Integer.parseInt(customerIDTextField.getText());
            int drugID = getSelectedDrugID();
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
            StringBuilder condition = new StringBuilder();
            String s1 = null, s2 = null, s3 = null, s4 = null;
            if (!customerIDTextField.getText().equals("")) {
                s1 = "customer_id " + customerIDTextField.getText();
            }
            s2 = "drug_id " + getSelectedDrugID();
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