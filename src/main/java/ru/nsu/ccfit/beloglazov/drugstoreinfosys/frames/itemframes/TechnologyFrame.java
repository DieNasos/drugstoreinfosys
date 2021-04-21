package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.TechnologyDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Technology;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TechnologyFrame extends ItemFrame {
    private final JLabel drugNameLabel = new JLabel("DRUG NAME:");
    private final JTextField drugNameTextField = new JTextField();
    private final JLabel descriptionLabel = new JLabel("DESCRIPTION:");
    private final JTextField descriptionTextField = new JTextField();

    public TechnologyFrame(TableItem ti, TableFrame tf, Connection connection) {
        super(ti, tf, connection);
        initComponents();
        setBounds(10, 10, 300, 300);
    }

    @Override
    protected void setTextOnTextFields() {
        drugNameTextField.setText(((Technology)ti).getDrugName());
        descriptionTextField.setText(((Technology)ti).getDescription());
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        drugNameLabel.setBounds(10, 170, 260, 30);
        drugNameTextField.setBounds(100, 170, 170, 30);
        descriptionLabel.setBounds(10, 210, 260, 30);
        descriptionTextField.setBounds(100, 210, 170, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(drugNameLabel);
        container.add(drugNameTextField);
        container.add(descriptionLabel);
        container.add(descriptionTextField);
    }

    @Override
    protected void create() {
        try {
            TechnologyDAO dao = (TechnologyDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String drug_name = drugNameTextField.getText();
            String description = descriptionTextField.getText();
            Technology t = new Technology(drug_name, description);
            dao.add(t);
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
            TechnologyDAO dao = (TechnologyDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String drug_name = drugNameTextField.getText();
            String description = descriptionTextField.getText();
            Technology t = new Technology(((Technology)ti).getID(), drug_name, description);
            dao.update(t);
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