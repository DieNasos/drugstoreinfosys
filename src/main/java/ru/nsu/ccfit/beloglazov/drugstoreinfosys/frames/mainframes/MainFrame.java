package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.User;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public abstract class MainFrame extends JFrame implements ActionListener {
    protected final Container container = getContentPane();
    protected final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    protected final JLabel tipLabel = new JLabel("CHOOSE OPERATION");
    protected final JLabel roleLabel = new JLabel();
    protected final JButton chooseButton = new JButton("Choose");
    protected final JButton backButton = new JButton("Back");
    protected final Connection connection;
    protected final LoginFrame lf;
    protected final QueryFrame qf;
    protected final User user;
    protected JList<String> optionsJList;
    protected JScrollPane scrollPane;
    protected String[] optionsArray;

    public MainFrame(LoginFrame lf, Connection connection, User user) {
        this.lf = lf;
        this.connection = connection;
        this.user = user;
        qf = new QueryFrame(this, connection);
        container.setLayout(null);
        setTitle("DIS :: Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        initOptions();
        optionsJList = new JList<>(optionsArray);
        scrollPane = new JScrollPane(optionsJList);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 260, 30);
        tipLabel.setBounds(10, 50, 260, 30);
        roleLabel.setBounds(10, 90, 260, 30);
        scrollPane.setBounds(10, 130, 260, 180);
        chooseButton.setBounds(10, 330, 260, 30);
        backButton.setBounds(10, 370, 260, 30);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(tipLabel);
        container.add(roleLabel);
        container.add(scrollPane);
        container.add(chooseButton);
        container.add(backButton);
    }

    private void addActionEvent() {
        chooseButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            lf.setVisible(true);
        } else if (e.getSource() == chooseButton) {
            int[] ids = optionsJList.getSelectedIndices();
            if (ids.length != 1) {
                return;
            }
            executeOption(optionsArray[ids[0]]);
            setVisible(false);
        }
    }

    protected abstract void initOptions();
    protected abstract void executeOption(String option);
}