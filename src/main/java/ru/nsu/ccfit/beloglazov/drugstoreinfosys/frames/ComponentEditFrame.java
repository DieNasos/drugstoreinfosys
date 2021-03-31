package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.components.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ComponentEditFrame extends EditFrame {
    private final JLabel nameLabel = new JLabel("NAME:");
    private final JTextField nameTextField = new JTextField();
    private final JLabel amountLabel = new JLabel("AMOUNT:");
    private final JTextField amountTextField = new JTextField();

    public ComponentEditFrame(Component c, TableFrame tf, Connection connection) {
        super(c, tf, connection);
        setBounds(10,10,300,300);
        setLocationAndSize();
        addComponentsToContainer();
        nameTextField.setText(c.getName());
        amountTextField.setText(String.valueOf(c.getAmount()));
    }

    private void setLocationAndSize() {
        nameLabel.setBounds(10, 170, 260, 30);
        nameTextField.setBounds(70, 170, 200, 30);
        amountLabel.setBounds(10, 210, 260, 30);
        amountTextField.setBounds(70, 210, 200, 30);
    }

    private void addComponentsToContainer() {
        container.add(nameLabel);
        container.add(nameTextField);
        container.add(amountLabel);
        container.add(amountTextField);
    }

    @Override
    public void actionPerformedForEdit(ActionEvent e) {
        try {
            ComponentDAO dao = (ComponentDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String name = nameTextField.getText();
            int amount = Integer.parseInt(amountTextField.getText());
            Component newC = new Component(((Component)ti).getID(), name, amount);
            dao.update(newC);
            tf.setVisible(true);
            dispose();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not edit item!");
        }
    }
}