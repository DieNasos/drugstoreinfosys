package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.ComponentDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class ComponentFrame extends ItemFrame {
    private final JLabel nameLabel = new JLabel("NAME:");
    private final JTextField nameTextField = new JTextField();
    private final JLabel amountLabel = new JLabel("AMOUNT:");
    private final JTextField amountTextField = new JTextField();
    private final JLabel costLabel = new JLabel("COST/GRAM:");
    private final JTextField costTextField = new JTextField();

    public ComponentFrame(ItemFrameType type, String tableName, TableItem ti, JFrame parentFrame, Connection connection) {
        super(type, tableName, ti, parentFrame, connection);
        initComponents();
        setBounds(10, 10, 300, 330);
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            nameTextField.setText(((Component) ti).getName());
            amountTextField.setText(String.valueOf(((Component) ti).getAmount()));
            costTextField.setText(String.valueOf(((Component) ti).getCostPerGram()));
        } else if (type == ItemFrameType.FIND) {
            nameTextField.setText("= 'component_1'");
            amountTextField.setText("> 50");
            costTextField.setText("<= 10.0");
        }
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        nameLabel.setBounds(10, 170, 260, 30);
        nameTextField.setBounds(70, 170, 200, 30);
        amountLabel.setBounds(10, 210, 260, 30);
        amountTextField.setBounds(70, 210, 200, 30);
        costLabel.setBounds(10, 250, 260, 30);
        costTextField.setBounds(90, 250, 180, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(nameLabel);
        container.add(nameTextField);
        container.add(amountLabel);
        container.add(amountTextField);
        container.add(costLabel);
        container.add(costTextField);
    }

    @Override
    protected void create() {
        try {
            ComponentDAO dao = (ComponentDAO) DAOFactory.createDAO(tableName, connection);
            String name = nameTextField.getText();
            int amount = Integer.parseInt(amountTextField.getText());
            float costPerGram = Float.parseFloat(costTextField.getText());
            Component c = new Component(name, amount, costPerGram);
            dao.add(c);
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
            ComponentDAO dao = (ComponentDAO) DAOFactory.createDAO(tableName, connection);
            String name = nameTextField.getText();
            int amount = Integer.parseInt(amountTextField.getText());
            float costPerGram = Float.parseFloat(costTextField.getText());
            Component newC = new Component(((Component)ti).getID(), name, amount, costPerGram);
            dao.update(newC);
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
            ComponentDAO dao = (ComponentDAO) DAOFactory.createDAO(tableName, connection);
            StringBuilder condition = new StringBuilder();
            String s1 = null, s2 = null, s3 = null;
            if (!nameTextField.getText().equals("")) {
                s1 = "name " + nameTextField.getText();
            }
            if (!amountTextField.getText().equals("")) {
                s2 = "amount " + amountTextField.getText();
            }
            if (!costTextField.getText().equals("")) {
                s3 = "cost_per_gram " + costTextField.getText();
            }
            appendConditionPart(condition, s1);
            appendConditionPart(condition, s2);
            appendConditionPart(condition, s3);
            List<TableItem> foundItems;
            if (condition.length() > 0) {
                foundItems = dao.getByParameters(condition.toString());
            } else {
                foundItems = dao.getAll();
            }
            parentFrame.setVisible(true);
            ((TableFrame) parentFrame).updateItems(foundItems);
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