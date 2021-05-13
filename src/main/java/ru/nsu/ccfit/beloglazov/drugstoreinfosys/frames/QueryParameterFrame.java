package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QueryParameterFrame extends JFrame implements ActionListener {
    private final Container container = getContentPane();
    private final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    private final JLabel tipLabel = new JLabel("PRINT QUERY PARAMETER");
    private final JButton executeButton = new JButton("Execute");
    private final JButton backButton = new JButton("Back");
    private final JLabel parameterLabel = new JLabel("PARAMETER:");
    private final JTextField parameterTextField = new JTextField();
    private final QueryFrame qf;
    private final String queryName;

    public QueryParameterFrame(QueryFrame qf, String queryName) {
        this.queryName = queryName;
        this.qf = qf;
        qf.setVisible(false);
        container.setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("DIS :: Query Parameter");
        setBounds(10,10,300,260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 260, 30);
        tipLabel.setBounds(10, 50, 260, 30);
        parameterLabel.setBounds(10, 90, 260, 30);
        parameterTextField.setBounds(90, 90, 180, 30);
        executeButton.setBounds(10, 130, 260, 30);
        backButton.setBounds(10, 170, 260, 30);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(tipLabel);
        container.add(parameterLabel);
        container.add(parameterTextField);
        container.add(executeButton);
        container.add(backButton);
    }

    private void addActionEvent() {
        executeButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        if (e.getSource() == backButton) {
            qf.getMainFrame().setVisible(true);
        }
        if (e.getSource() == executeButton) {
            qf.setVisible(true);
            qf.executeQuery(queryName, parameterTextField.getText());
        }
    }
}