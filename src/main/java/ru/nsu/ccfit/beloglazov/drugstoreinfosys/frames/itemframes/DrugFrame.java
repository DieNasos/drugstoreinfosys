package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class DrugFrame extends ItemFrame {
    private final JLabel typeIDLabel = new JLabel("Type ID:");
    private final JTextField typeIDTextField = new JTextField();
    private final JLabel technologyIDLabel = new JLabel("Tech ID:");
    private final JTextField technologyIDTextField = new JTextField();
    private final JLabel priceLabel = new JLabel("Price:");
    private final JTextField priceTextField = new JTextField();
    private final JLabel critNormaLabel = new JLabel("Crit. norma:");
    private final JTextField critNormaTextField = new JTextField();
    private final JLabel amountLabel = new JLabel("Amount:");
    private final JTextField amountTextField = new JTextField();

    public DrugFrame(ItemFrameType type, String tableName, TableItem ti, JFrame parentFrame, Connection connection) {
        super(type, tableName, ti, parentFrame, connection);
        initComponents();
        setBounds(10, 10, 300, 420);
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            typeIDTextField.setText(String.valueOf(((Drug) ti).getTypeID()));
            technologyIDTextField.setText(String.valueOf(((Drug) ti).getTechnologyID()));
            priceTextField.setText(String.valueOf(((Drug) ti).getPrice()));
            amountTextField.setText(String.valueOf(((Drug) ti).getAmount()));
            critNormaTextField.setText(String.valueOf(((Drug) ti).getCritNorma()));
        } else if (type == ItemFrameType.FIND) {
            typeIDTextField.setText("= 2");
            technologyIDTextField.setText("= 2");
            priceTextField.setText("<= 200.0");
            amountTextField.setText("> 3");
            critNormaTextField.setText("< 10");
        }
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        typeIDLabel.setBounds(10, 170, 260, 30);
        typeIDTextField.setBounds(70, 170, 200, 30);
        technologyIDLabel.setBounds(10, 210, 260, 30);
        technologyIDTextField.setBounds(70, 210, 200, 30);
        priceLabel.setBounds(10, 250, 260, 30);
        priceTextField.setBounds(70, 250, 200, 30);
        critNormaLabel.setBounds(10, 290, 260, 30);
        critNormaTextField.setBounds(100, 290, 170, 30);
        amountLabel.setBounds(10, 330, 260, 30);
        amountTextField.setBounds(70, 330, 200, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(typeIDLabel);
        container.add(typeIDTextField);
        container.add(technologyIDLabel);
        container.add(technologyIDTextField);
        container.add(priceLabel);
        container.add(priceTextField);
        container.add(critNormaLabel);
        container.add(critNormaTextField);
        container.add(amountLabel);
        container.add(amountTextField);
    }

    @Override
    protected void create() {
        try {
            DrugDAO dao = (DrugDAO) DAOFactory.createDAO(tableName, connection);
            int type_id = Integer.parseInt(typeIDTextField.getText());
            int technology_id = Integer.parseInt(technologyIDTextField.getText());
            float price = Float.parseFloat(priceTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());
            int crit_norma = Integer.parseInt(critNormaTextField.getText());
            Drug d = new Drug(type_id, technology_id, price, amount, crit_norma);
            dao.add(d);
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
            DrugDAO dao = (DrugDAO) DAOFactory.createDAO(tableName, connection);
            int type_id = Integer.parseInt(typeIDTextField.getText());
            int technology_id = Integer.parseInt(technologyIDTextField.getText());
            float price = Float.parseFloat(priceTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());
            int crit_norma = Integer.parseInt(critNormaTextField.getText());
            Drug d = new Drug(((Drug)ti).getID(), type_id, technology_id, price, amount, crit_norma);
            dao.update(d);
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
            DrugDAO dao = (DrugDAO) DAOFactory.createDAO(tableName, connection);
            StringBuilder condition = new StringBuilder();
            String s1 = null, s2 = null, s3 = null, s4 = null, s5 = null;
            if (!typeIDTextField.getText().equals("")) {
                s1 = "type_id " + typeIDTextField.getText();
            }
            if (!technologyIDTextField.getText().equals("")) {
                s2 = "technology_id " + technologyIDTextField.getText();
            }
            if (!priceTextField.getText().equals("")) {
                s3 = "price " + priceTextField.getText();
            }
            if (!amountTextField.getText().equals("")) {
                s4 = "amount " + amountTextField.getText();
            }
            if (!critNormaTextField.getText().equals("")) {
                s5 = "crit_norma " + critNormaTextField.getText();
            }
            appendConditionPart(condition, s1);
            appendConditionPart(condition, s2);
            appendConditionPart(condition, s3);
            appendConditionPart(condition, s4);
            appendConditionPart(condition, s5);
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
}