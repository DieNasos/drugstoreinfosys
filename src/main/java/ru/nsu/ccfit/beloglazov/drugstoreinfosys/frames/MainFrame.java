package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public class MainFrame extends JFrame implements ActionListener {
    private final Container container = getContentPane();
    private final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    private final JLabel tipLabel = new JLabel("CHOOSE OPERATION");
    private final JButton seeTablesButton = new JButton("See tables");
    private final JButton executeQueryButton = new JButton("Execute query");
    private final JButton backButton = new JButton("Back");
    private final Connection connection;
    private final LoginFrame lf;
    private MainTablesFrame mtf = null;
    private MainQueriesFrame mqf = null;

    public MainFrame(LoginFrame lf, Connection connection) {
        this.lf = lf;
        this.connection = connection;
        container.setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("DIS :: Main Frame");
        setVisible(true);
        setBounds(10,10,300,260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 260, 30);
        tipLabel.setBounds(10, 50, 260, 30);
        seeTablesButton.setBounds(10, 100, 260, 30);
        executeQueryButton.setBounds(10, 140, 260, 30);
        backButton.setBounds(10, 180, 260, 30);
    }

    private void addActionEvent() {
        seeTablesButton.addActionListener(this);
        executeQueryButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(tipLabel);
        container.add(seeTablesButton);
        container.add(executeQueryButton);
        container.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            lf.setVisible(true);
        } else if (e.getSource() == seeTablesButton) {
            if (mtf == null) {
                mtf = new MainTablesFrame(this, connection);
            }
            mtf.setVisible(true);
            setVisible(false);
        } else if (e.getSource() == executeQueryButton) {
            if (mqf == null) {
                mqf = new MainQueriesFrame(this, connection);
            }
            mqf.setVisible(true);
            setVisible(false);
        }
    }
}