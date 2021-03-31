package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.drugtypes.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class DrugTypeCreateFrame extends CreateFrame {
    private final JLabel nameLabel = new JLabel("NAME:");
    private final JTextField nameTextField = new JTextField();

    public DrugTypeCreateFrame(TableFrame tf, Connection connection) {
        super(tf, connection);
        setBounds(10,10,300,260);
        setLocationAndSize();
        addComponentsToContainer();
        setVisible(true);
    }

    private void setLocationAndSize() {
        nameLabel.setBounds(10, 170, 260, 30);
        nameTextField.setBounds(70, 170, 200, 30);
    }

    private void addComponentsToContainer() {
        container.add(nameLabel);
        container.add(nameTextField);
    }

    @Override
    public void actionPerformedForCreate(ActionEvent e) {
        try {
            DrugTypeDAO dao = (DrugTypeDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String name = nameTextField.getText();
            DrugType dt = new DrugType(name);
            dao.add(dt);
            tf.setVisible(true);
            dispose();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not create item!");
        }
    }
}