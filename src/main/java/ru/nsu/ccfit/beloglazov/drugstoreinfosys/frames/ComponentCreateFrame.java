package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.components.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ComponentCreateFrame extends CreateFrame {
    private final JLabel nameLabel = new JLabel("NAME:");
    private final JTextField nameTextField = new JTextField();

    public ComponentCreateFrame(TableFrame tf, Connection connection) {
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
            ComponentDAO dao = (ComponentDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            String name = nameTextField.getText();
            Component c = new Component(name, 0);
            dao.add(c);
            tf.setVisible(true);
            dispose();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not create item!");
        }
    }
}