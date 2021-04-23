package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.GivenOrderDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.GivenOrder;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class GivenOrderFrame extends ItemFrame {
    private final JLabel orderIDLabel = new JLabel("ORDER ID:");
    private final JTextField orderIDTextField = new JTextField();

    public GivenOrderFrame(ItemFrameType type, TableItem ti, TableFrame tf, Connection connection) {
        super(type, ti, tf, connection);
        initComponents();
        setBounds(10, 10, 300, 260);
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            orderIDTextField.setText(String.valueOf(((GivenOrder) ti).getOrderID()));
        } else if (type == ItemFrameType.FIND) {
            orderIDTextField.setText("= 1");
        }
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
            GivenOrderDAO dao = (GivenOrderDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            int orderID = Integer.parseInt(orderIDTextField.getText());
            GivenOrder o = new GivenOrder(((GivenOrder)ti).getID(), orderID);
            dao.update(o);
            tf.setVisible(true);
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
            GivenOrderDAO dao = (GivenOrderDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            StringBuilder condition = new StringBuilder();
            String s1 = null;
            if (!orderIDTextField.getText().equals("")) {
                s1 = "order_id " + orderIDTextField.getText();
            }
            appendConditionPart(condition, s1);
            List<TableItem> foundItems = dao.getByParameters(condition.toString());
            tf.setVisible(true);
            tf.updateItems(foundItems);
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