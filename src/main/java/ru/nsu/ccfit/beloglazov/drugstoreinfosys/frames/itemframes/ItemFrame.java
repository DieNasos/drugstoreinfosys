package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public abstract class ItemFrame extends JFrame implements ActionListener {
    protected final Container container = getContentPane();
    protected final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    protected final JLabel tipLabel = new JLabel("CREATING/EDITING ITEM");
    protected final JButton backButton = new JButton("Back");
    protected final JButton actionButton;
    protected final TableItem ti;
    protected final TableFrame tf;
    protected final Connection connection;

    public ItemFrame(TableItem ti, TableFrame tf, Connection connection) {
        this.ti = ti;
        this.tf = tf;
        this.connection = connection;

        if (ti == null) {
            setTitle("DIS :: Create form");
            actionButton = new JButton("Create");
        } else {
            setTitle("DIS :: Edit form");
            actionButton = new JButton("Edit");
        }

        container.setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    protected void initComponents() {
        if (ti != null) {
            setTextOnTextFields();
        }

        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 260, 30);
        tipLabel.setBounds(10, 50, 260, 30);
        backButton.setBounds(10, 90, 260, 30);
        actionButton.setBounds(10, 130, 260, 30);
        setLocationAndSizeForCustom();
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(tipLabel);
        container.add(backButton);
        container.add(actionButton);
        addCustomComponentsToContainer();
    }

    private void addActionEvent() {
        backButton.addActionListener(this);
        actionButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            tf.setVisible(true);
            dispose();
        } else {
            if (ti == null) {
                create();
            } else {
                edit();
            }
        }
    }

    protected abstract void setTextOnTextFields();
    protected abstract void setLocationAndSizeForCustom();
    protected abstract void addCustomComponentsToContainer();
    protected abstract void create();
    protected abstract void edit();
}