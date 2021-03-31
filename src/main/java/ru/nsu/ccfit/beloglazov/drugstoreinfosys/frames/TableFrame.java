package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TableFrame extends JFrame implements ActionListener {
    private final Container container = getContentPane();
    private final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    private final JLabel tableNameLabel;
    private final JList<String> itemsJList;
    private final JButton createButton = new JButton("Create");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton restartSeqButton = new JButton("Restart indices");
    private final JButton backButton = new JButton("Back");
    private final MainFrame mf;
    private final String tableName;
    private final Connection connection;
    private final DAO dao;
    private List<TableItem> itemsList;

    public TableFrame(MainFrame mf, String tableName, Connection connection) throws SQLException {
        this.mf = mf;
        this.tableName = tableName;
        this.connection = connection;
        tableNameLabel = new JLabel("TABLE: '" + tableName + "'");
        dao = DAOFactory.createDAO(tableName, connection);
        itemsList = dao.getAll();
        List<String> values = new LinkedList<>();
        for (TableItem item : itemsList) {
            values.add(item.getValues().toString());
        }
        itemsJList = new JList<>(values.toArray(new String[0]));

        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("DIS :: Table '" + tableName + "'");
        setVisible(true);
        setBounds(10,10,300,460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 260, 30);
        tableNameLabel.setBounds(10, 50, 260, 30);
        itemsJList.setBounds(10, 90, 260, 100);
        createButton.setBounds(10, 210, 260, 30);
        editButton.setBounds(10, 250, 260, 30);
        deleteButton.setBounds(10, 290, 260, 30);
        restartSeqButton.setBounds(10, 330, 260, 30);
        backButton.setBounds(10, 370, 260, 30);
    }

    private void addActionEvent() {
        createButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        restartSeqButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(tableNameLabel);
        container.add(itemsJList);
        container.add(createButton);
        container.add(editButton);
        container.add(deleteButton);
        container.add(restartSeqButton);
        container.add(backButton);
    }

    private void updateItems() {
        List<String> values = new LinkedList<>();
        for (TableItem item : itemsList) {
            values.add(item.getValues().toString());
        }
        itemsJList.setListData(values.toArray(new String[0]));
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            setVisible(false);
            CreateFrame cf = FrameFactory.getCreateFrame(tableName, this, connection);
        } else if (e.getSource() == editButton) {
            int[] ids = itemsJList.getSelectedIndices();
            if (ids.length != 1) {
                return;
            }
            TableItem ti = itemsList.get(ids[0]);
            setVisible(false);
            EditFrame ef = FrameFactory.getEditFrame(tableName, ti, this, connection);
        } else if (e.getSource() == deleteButton) {
            int[] ids = itemsJList.getSelectedIndices();
            int i = ids.length - 1;
            while (i >= 0) {
                TableItem ti = itemsList.get(ids[i]);
                Integer id = (Integer) ti.getValues().get("id");
                try {
                    dao.delete(id);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Could not delete item!");
                }
                itemsList.remove(ids[i]);
                i--;
            }
            updateItems();
        } else if (e.getSource() == restartSeqButton) {
            try {
                dao.resetSequence();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(this, "Could not restart indices!");
            }
        } else if (e.getSource() == backButton) {
            setVisible(false);
            mf.setVisible(true);
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        try {
            itemsList = dao.getAll();
            updateItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}