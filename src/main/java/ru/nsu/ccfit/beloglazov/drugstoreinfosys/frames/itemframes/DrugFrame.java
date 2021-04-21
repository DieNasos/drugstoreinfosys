package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.DrugDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Drug;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.sql.*;

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

    public DrugFrame(TableItem ti, TableFrame tf, Connection connection) {
        super(ti, tf, connection);
        initComponents();
        setBounds(10, 10, 300, 420);
    }

    @Override
    protected void setTextOnTextFields() {
        typeIDTextField.setText(String.valueOf(((Drug)ti).getTypeID()));
        technologyIDTextField.setText(String.valueOf(((Drug)ti).getTechnologyID()));
        priceTextField.setText(String.valueOf(((Drug)ti).getPrice()));
        critNormaTextField.setText(String.valueOf(((Drug)ti).getCritNorma()));
        amountTextField.setText(String.valueOf(((Drug)ti).getAmount()));
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
            DrugDAO dao = (DrugDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            int type_id = Integer.parseInt(typeIDTextField.getText());
            int technology_id = Integer.parseInt(technologyIDTextField.getText());
            float price = Float.parseFloat(priceTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());
            int crit_norma = Integer.parseInt(critNormaTextField.getText());
            Drug d = new Drug(type_id, technology_id, price, amount, crit_norma);
            dao.add(d);
            tf.setVisible(true);
            dispose();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not create item!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    protected void edit() {
        try {
            DrugDAO dao = (DrugDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            int type_id = Integer.parseInt(typeIDTextField.getText());
            int technology_id = Integer.parseInt(technologyIDTextField.getText());
            float price = Float.parseFloat(priceTextField.getText());
            int crit_norma = Integer.parseInt(critNormaTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());
            Drug d = new Drug(((Drug)ti).getID(), type_id, technology_id, price, amount, crit_norma);
            dao.update(d);
            tf.setVisible(true);
            dispose();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not edit item!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}