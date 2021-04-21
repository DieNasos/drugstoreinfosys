package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.GivenOrderDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.GivenOrder;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.sql.*;

public class GivenOrderFrame extends ItemFrame {
    private final JLabel orderIDLabel = new JLabel("ORDER ID:");
    private final JTextField orderIDTextField = new JTextField();

    public GivenOrderFrame(TableItem ti, TableFrame tf, Connection connection) {
        super(ti, tf, connection);
        initComponents();
        setBounds(10, 10, 300, 260);
    }

    @Override
    protected void setTextOnTextFields() {
        orderIDTextField.setText(String.valueOf(((GivenOrder)ti).getOrderID()));
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        orderIDLabel.setBounds(10, 170, 260, 30);
        orderIDTextField.setBounds(90, 170, 180, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(orderIDLabel);
        container.add(orderIDTextField);
    }

    @Override
    protected void create() {
        try {
            GivenOrderDAO dao = (GivenOrderDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            int orderID = Integer.parseInt(orderIDTextField.getText());
            GivenOrder o = new GivenOrder(orderID);
            dao.add(o);
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
            GivenOrderDAO dao = (GivenOrderDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            int orderID = Integer.parseInt(orderIDTextField.getText());
            GivenOrder o = new GivenOrder(((GivenOrder)ti).getID(), orderID);
            dao.update(o);
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