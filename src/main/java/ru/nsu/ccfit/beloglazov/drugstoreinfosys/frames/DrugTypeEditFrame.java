package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.drugtypes.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class DrugTypeEditFrame extends EditFrame {
    private final JLabel nameLabel = new JLabel("NAME:");
    private final JTextField nameTextField = new JTextField();

    public DrugTypeEditFrame(DrugType dt, TableFrame tf, Connection connection) {
        super(dt, tf, connection);
        setBounds(10,10,300,260);
        setLocationAndSize();
        addComponentsToContainer();
        nameTextField.setText(dt.getName());
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
    public void actionPerformedForEdit(ActionEvent e) {
        try {
            DrugTypeDAO dao = (DrugTypeDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String name = nameTextField.getText();
            DrugType newDT = new DrugType(((DrugType)ti).getID(), name);
            dao.update(newDT);
            tf.setVisible(true);
            dispose();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not edit item!");
        }
    }
}