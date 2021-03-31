package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public abstract class CreateFrame extends JFrame implements ActionListener {
    protected final Container container = getContentPane();
    protected final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    protected final JLabel tipLabel = new JLabel("CREATING NEW ITEM");
    protected final JButton backButton = new JButton("Back");
    protected final JButton createButton = new JButton("Create");
    protected final TableFrame tf;
    protected final Connection connection;

    public CreateFrame(TableFrame tf, Connection connection) {
        this.tf = tf;
        this.connection = connection;
        setLayoutManager();
        setLocationAndSizeForDefault();
        addDefaultComponentsToContainer();
        addActionEvent();
        setTitle("DIS :: Create form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void setLocationAndSizeForDefault() {
        titleLabel.setBounds(10, 10, 260, 30);
        tipLabel.setBounds(10, 50, 260, 30);
        backButton.setBounds(10, 90, 260, 30);
        createButton.setBounds(10, 130, 260, 30);
    }

    private void addDefaultComponentsToContainer() {
        container.add(titleLabel);
        container.add(tipLabel);
        container.add(backButton);
        container.add(createButton);
    }

    private void addActionEvent() {
        backButton.addActionListener(this);
        createButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            tf.setVisible(true);
            dispose();
        } else {
            actionPerformedForCreate(e);
        }
    }

    abstract public void actionPerformedForCreate(ActionEvent e);
}