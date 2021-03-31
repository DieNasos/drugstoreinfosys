package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public abstract class EditFrame extends JFrame implements ActionListener {
    protected final Container container = getContentPane();
    protected final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    protected final JLabel tipLabel = new JLabel("EDITING ITEM");
    protected final JButton backButton = new JButton("Back");
    protected final JButton createButton = new JButton("Edit");
    protected final TableItem ti;
    protected final TableFrame tf;
    protected final Connection connection;

    public EditFrame(TableItem ti, TableFrame tf, Connection connection) {
        this.ti = ti;
        this.tf = tf;
        this.connection = connection;
        setLayoutManager();
        setLocationAndSizeForDefault();
        addDefaultComponentsToContainer();
        addActionEvent();
        setTitle("DIS :: Edit form");
        setVisible(true);
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
            actionPerformedForEdit(e);
        }
    }

    abstract public void actionPerformedForEdit(ActionEvent e);
}