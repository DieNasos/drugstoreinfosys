package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.TableItem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public abstract class ItemFrame extends JFrame implements ActionListener {
    protected final Container container = getContentPane();
    protected final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    protected final JLabel tipLabel = new JLabel();
    protected final JButton backButton = new JButton("Back");
    protected final JButton actionButton = new JButton();
    protected final TableItem ti;
    protected final JFrame parentFrame;
    protected final Connection connection;
    protected final ItemFrameType type;
    protected final String tableName;

    public ItemFrame(ItemFrameType type, String tableName, TableItem ti, JFrame parentFrame, Connection connection) {
        this.ti = ti;
        this.tableName = tableName;
        this.parentFrame = parentFrame;
        this.connection = connection;
        this.type = type;

        switch (type) {
            case CREATE:
                setTitle("DIS :: Create form");
                tipLabel.setText("Creating new item");
                actionButton.setText("Create");
                break;
            case EDIT:
                setTitle("DIS :: Edit form");
                tipLabel.setText("Editing item");
                actionButton.setText("Edit");
                break;
            case FIND:
                setTitle("DIS :: Find form");
                tipLabel.setText("Finding items by parameters");
                actionButton.setText("Find");
        }

        container.setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    protected void initComponents() {
        if (type == ItemFrameType.EDIT
                || type == ItemFrameType.FIND) {
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
            parentFrame.setVisible(true);
            dispose();
        } else {
            switch (type) {
                case CREATE:
                    create();
                    break;
                case EDIT:
                    edit();
                    break;
                case FIND:
                    find();
            }
        }
    }

    protected void appendConditionPart(StringBuilder condition, String part) {
        if (part != null) {
            if (condition.length() > 0) {
                condition.append(" AND ").append(part);
            } else {
                condition.append(part);
            }
        }
    }

    protected abstract void setTextOnTextFields();
    protected abstract void setLocationAndSizeForCustom();
    protected abstract void addCustomComponentsToContainer();
    protected abstract void create();
    protected abstract void edit();
    protected abstract void find();
}