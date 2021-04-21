package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.OrderInProcessDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.OrderInProcess;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.sql.*;

public class OrderInProcessFrame extends ItemFrame {
    private final JLabel orderIDLabel = new JLabel("ORDER ID:");
    private final JTextField orderIDTextField = new JTextField();
    private final JLabel readyTimeLabel = new JLabel("READY TIME*:");
    private final JTextField readyTimeTextField = new JTextField();
    private final JLabel timeTip = new JLabel("* - print time in format: yyyy-mm-dd hh:mm:ss");

    public OrderInProcessFrame(TableItem ti, TableFrame tf, Connection connection) {
        super(ti, tf, connection);
        initComponents();
        setBounds(10, 10, 300, 330);
    }

    @Override
    protected void setTextOnTextFields() {
        orderIDTextField.setText(String.valueOf(((OrderInProcess)ti).getOrderID()));
        readyTimeLabel.setText(String.valueOf(((OrderInProcess)ti).getReadyTime()));
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        orderIDLabel.setBounds(10, 170, 260, 30);
        orderIDTextField.setBounds(80, 170, 190, 30);
        readyTimeLabel.setBounds(10, 210, 260, 30);
        readyTimeTextField.setBounds(100, 210, 170, 30);
        timeTip.setBounds(10, 250, 260, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(orderIDLabel);
        container.add(orderIDTextField);
        container.add(readyTimeLabel);
        container.add(readyTimeTextField);
        container.add(timeTip);
    }

    @Override
    protected void create() {
        try {
            OrderInProcessDAO dao = (OrderInProcessDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            int orderID = Integer.parseInt(orderIDTextField.getText());
            Timestamp readyTime = Timestamp.valueOf(readyTimeTextField.getText());
            OrderInProcess o = new OrderInProcess(orderID, readyTime);
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
            OrderInProcessDAO dao = (OrderInProcessDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            int orderID = Integer.parseInt(orderIDTextField.getText());
            Timestamp readyTime = Timestamp.valueOf(readyTimeTextField.getText());
            OrderInProcess o = new OrderInProcess(((OrderInProcess)ti).getID(), orderID, readyTime);
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