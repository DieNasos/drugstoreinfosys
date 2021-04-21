package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MainTablesFrame extends JFrame implements ActionListener {
    private final Container container = getContentPane();
    private final String[] tablesArray = new String[] {
            "Drug types",
            "Technologies",
            "Components",
            "Drugs",
            "Drugs & components",
            "All orders",
            "Orders in process",
            "Given orders"
    };
    private final JList<String> tablesJList = new JList<>(tablesArray);
    private final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    private final JLabel tipLabel = new JLabel("DATABASE TABLES");
    private final JButton openButton = new JButton("Open");
    private final JButton backButton = new JButton("Back");
    private final Connection connection;
    private final MainFrame mf;
    private final TableFrame[] tfs;

    public MainTablesFrame(MainFrame mf, Connection connection) {
        this.mf = mf;
        this.connection = connection;
        tfs = new TableFrame[tablesArray.length];
        container.setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("DIS :: Main Tables Frame");
        setVisible(true);
        setBounds(10,10,300,420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 260, 30);
        tipLabel.setBounds(10, 50, 260, 30);
        tablesJList.setBounds(10, 90, 260, 180);
        openButton.setBounds(10, 290, 260, 30);
        backButton.setBounds(10, 330, 260, 30);
    }

    private void addActionEvent() {
        openButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(tipLabel);
        container.add(tablesJList);
        container.add(openButton);
        container.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            mf.setVisible(true);
        } else if (e.getSource() == openButton) {
            int[] ids = tablesJList.getSelectedIndices();
            if (ids.length != 1) {
                return;
            }
            String tableName;
            switch (tablesArray[ids[0]]) {
                case "Drug types":
                    tableName = "DRGTYPES";
                    break;
                case "Technologies":
                    tableName = "TCHNLGS";
                    break;
                case "Components":
                    tableName = "CMPNNTS";
                    break;
                case "Drugs":
                    tableName = "DRUGS";
                    break;
                case "Drugs & components":
                    tableName = "DRGSCMPS";
                    break;
                case "All orders":
                    tableName = "ORDERS";
                    break;
                case "Orders in process":
                    tableName = "INPRCSS";
                    break;
                case "Given orders":
                    tableName = "GIVEN";
                    break;
                default:
                    return;
            }
            if (tfs[ids[0]] == null) {
                try {
                    tfs[ids[0]] = new TableFrame(this, tableName, connection);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    JOptionPane.showMessageDialog(
                            this, "Could not open table!",
                            "Error!", JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                tfs[ids[0]].setVisible(true);
            }
            setVisible(false);
        }
    }
}