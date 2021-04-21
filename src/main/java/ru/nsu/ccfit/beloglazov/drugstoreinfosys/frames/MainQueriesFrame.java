package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MainQueriesFrame extends JFrame implements ActionListener {
    private final Container container = getContentPane();
    private final String[] queriesArray = new String[] {
            "Customers who haven't received drugs",
            "Cost of ready drug / its components",
            "Info about drug"
    };
    private final JList<String> queriesJList = new JList<>(queriesArray);
    private final JScrollPane scrollPane = new JScrollPane(queriesJList);
    private final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    private final JLabel tipLabel = new JLabel("AVAILABLE QUERIES");
    private final JButton executeButton = new JButton("Execute");
    private final JButton backButton = new JButton("Back");
    private final MainFrame mf;
    private final QueryFrame qf;

    public MainQueriesFrame(MainFrame mf, Connection connection) {
        this.mf = mf;
        qf = new QueryFrame(this, connection);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("DIS :: Main Queries Frame");
        setVisible(true);
        setBounds(10,10,300,420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 260, 30);
        tipLabel.setBounds(10, 50, 260, 30);
        scrollPane.setBounds(10, 90, 260, 180);
        executeButton.setBounds(10, 290, 260, 30);
        backButton.setBounds(10, 330, 260, 30);
    }

    private void addActionEvent() {
        executeButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(tipLabel);
        container.add(scrollPane);
        container.add(executeButton);
        container.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            mf.setVisible(true);
        } else if (e.getSource() == executeButton) {
            int[] ids = queriesJList.getSelectedIndices();
            if (ids.length != 1) {
                return;
            }
            qf.executeQuery(queriesArray[ids[0]], null);
            setVisible(false);
        }
    }
}