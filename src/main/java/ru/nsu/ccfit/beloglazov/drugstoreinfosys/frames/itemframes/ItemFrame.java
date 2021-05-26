package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.tablesdao.TableDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.TableItem;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public abstract class ItemFrame extends JFrame implements ActionListener {
    protected final Container container = getContentPane();
    protected final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    protected final JLabel tipLabel = new JLabel();
    protected final JButton backButton = new JButton();
    protected final JButton actionButton = new JButton();
    protected final ItemFrameType type;
    protected final TableItem ti;
    protected final JFrame parentFrame;
    protected final DAOFactory daoFactory;
    protected final TableDAO dao;

    public ItemFrame(ItemFrameType type,TableItem ti, JFrame parentFrame, DAOFactory daoFactory, TableDAO dao) {
        this.type = type;
        this.ti = ti;
        this.parentFrame = parentFrame;
        this.daoFactory = daoFactory;
        this.dao = dao;

        switch (type) {
            case CREATE:
                setTitle("Create form");
                tipLabel.setText("Creating new item");
                break;
            case EDIT:
                setTitle("Edit form");
                tipLabel.setText("Editing item");
                break;
            case FIND:
                setTitle("Find form");
                tipLabel.setText("Finding items by parameters");
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
        actionButton.setBounds(10, 90, 50, 30);
        backButton.setBounds(70, 90, 50, 30);
        try {
            URL acceptResource = getClass().getClassLoader().getResource("images/check.gif");
            URL backResource = getClass().getClassLoader().getResource("images/back.gif");
            if (acceptResource != null && backResource != null) {
                ImageIcon acceptIcon = new ImageIcon(acceptResource);
                actionButton.setIcon(acceptIcon);
                ImageIcon backIcon = new ImageIcon(backResource);
                backButton.setIcon(backIcon);
            } else {
                throw new Exception("Error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            actionButton.setText("V");
            backButton.setText("<");
        }
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