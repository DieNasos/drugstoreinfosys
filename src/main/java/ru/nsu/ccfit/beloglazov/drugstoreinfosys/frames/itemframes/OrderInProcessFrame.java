package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.OrderInProcessDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.OrderInProcess;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.TableItem;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class OrderInProcessFrame extends ItemFrame {
    private final JLabel orderIDLabel = new JLabel("ORDER ID:");
    private final JTextField orderIDTextField = new JTextField();
    private final JLabel readyTimeLabel = new JLabel("READY TIME*:");
    private final JTextField readyTimeTextField = new JTextField();
    private final JLabel timeTip = new JLabel("* - print time in format: yyyy-mm-dd hh:mm:ss");

    public OrderInProcessFrame(ItemFrameType type, String tableName, TableItem ti, JFrame parentFrame, Connection connection) {
        super(type, tableName, ti, parentFrame, connection);
        initComponents();
        setBounds(10, 10, 300, 330);
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            orderIDTextField.setText(String.valueOf(((OrderInProcess) ti).getOrderID()));
            readyTimeTextField.setText(String.valueOf(((OrderInProcess) ti).getReadyTime()));
        } else if (type == ItemFrameType.FIND) {
            orderIDTextField.setText("= 1");
            readyTimeTextField.setText("= 2021-04-30 15:00:00");
        }
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
            OrderInProcessDAO dao = (OrderInProcessDAO) DAOFactory.createDAO(tableName, connection);
            int orderID = Integer.parseInt(orderIDTextField.getText());
            Timestamp readyTime = Timestamp.valueOf(readyTimeTextField.getText());
            OrderInProcess o = new OrderInProcess(orderID, readyTime);
            dao.add(o);
            parentFrame.setVisible(true);
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
            OrderInProcessDAO dao = (OrderInProcessDAO) DAOFactory.createDAO(tableName, connection);
            int orderID = Integer.parseInt(orderIDTextField.getText());
            Timestamp readyTime = Timestamp.valueOf(readyTimeTextField.getText());
            OrderInProcess o = new OrderInProcess(((OrderInProcess)ti).getID(), orderID, readyTime);
            dao.update(o);
            parentFrame.setVisible(true);
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
            OrderInProcessDAO dao = (OrderInProcessDAO) DAOFactory.createDAO(tableName, connection);
            StringBuilder condition = new StringBuilder();
            String s1 = null, s2 = null;
            if (!orderIDTextField.getText().equals("")) {
                s1 = "order_id " + orderIDTextField.getText();
            }
            if (!readyTimeTextField.getText().equals("")) {
                String rtCondition = readyTimeTextField.getText();
                int indexOfFirstNumber = 0;
                while (indexOfFirstNumber < rtCondition.length()) {
                    if (rtCondition.charAt(indexOfFirstNumber) >= 48
                            && rtCondition.charAt(indexOfFirstNumber) <= 57) {
                        break;
                    }
                    indexOfFirstNumber++;
                }
                s2 = "ready_time "
                        + rtCondition.substring(0, indexOfFirstNumber)
                        + "timestamp '"
                        + rtCondition.substring(indexOfFirstNumber)
                        + "'";
            }
            appendConditionPart(condition, s1);
            appendConditionPart(condition, s2);
            List<TableItem> foundItems;
            if (condition.length() > 0) {
                foundItems = dao.getByParameters(condition.toString());
            } else {
                foundItems = dao.getAll();
            }
            parentFrame.setVisible(true);
            if (parentFrame instanceof TableFrame) {
                ((TableFrame) parentFrame).updateItems(foundItems);
            }
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