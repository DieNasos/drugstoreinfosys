package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.DrugTypeDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.DrugType;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.sql.*;

public class DrugTypeFrame extends ItemFrame {
    private final JLabel nameLabel = new JLabel("NAME:");
    private final JTextField nameTextField = new JTextField();

    public DrugTypeFrame(TableItem ti, TableFrame tf, Connection connection) {
        super(ti, tf, connection);
        initComponents();
        setBounds(10, 10, 300, 260);
    }

    @Override
    protected void setTextOnTextFields() {
        nameTextField.setText(((DrugType)ti).getName());
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        nameLabel.setBounds(10, 170, 260, 30);
        nameTextField.setBounds(70, 170, 200, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(nameLabel);
        container.add(nameTextField);
    }

    @Override
    protected void create() {
        try {
            DrugTypeDAO dao = (DrugTypeDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String name = nameTextField.getText();
            DrugType dt = new DrugType(name);
            dao.add(dt);
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
            DrugTypeDAO dao = (DrugTypeDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String name = nameTextField.getText();
            DrugType newDT = new DrugType(((DrugType)ti).getID(), name);
            dao.update(newDT);
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