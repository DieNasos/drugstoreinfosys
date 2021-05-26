package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class QueryParameterFrame extends JFrame implements ActionListener {
    private final Container container = getContentPane();
    private final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    private final JLabel tipLabel = new JLabel("PRINT QUERY PARAMETER");
    private final JButton executeButton = new JButton();
    private final JButton backButton = new JButton();
    private final JLabel parameterLabel = new JLabel("PARAMETER:");
    private final JTextField parameterTextField = new JTextField();
    private final QueryFrame qf;
    private final String queryName;
    private final String[] currentArgs;

    public QueryParameterFrame(QueryFrame qf, String queryName, String tipText, String[] currentArgs) {
        this.currentArgs = currentArgs;
        this.queryName = queryName;
        this.qf = qf;
        qf.setVisible(false);
        tipLabel.setText(tipText);
        container.setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("DIS :: Query Parameter");
        setBounds(10,10,300,220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 260, 30);
        tipLabel.setBounds(10, 50, 260, 30);
        parameterLabel.setBounds(10, 90, 260, 30);
        parameterTextField.setBounds(90, 90, 180, 30);
        executeButton.setBounds(10, 130, 50, 30);
        backButton.setBounds(70, 130, 50, 30);
        try {
            URL executeResource = getClass().getClassLoader().getResource("images/check.gif");
            URL backResource = getClass().getClassLoader().getResource("images/back.gif");
            if (executeResource != null && backResource != null) {
                ImageIcon chooseIcon = new ImageIcon(executeResource);
                executeButton.setIcon(chooseIcon);
                ImageIcon backIcon = new ImageIcon(backResource);
                backButton.setIcon(backIcon);
            } else {
                throw new Exception("Error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            executeButton.setText("V");
            backButton.setText("<");
        }
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
            String[] parameters;
            if (currentArgs == null) {
                parameters = new String[1];
                parameters[0] = parameterTextField.getText();
            } else {
                parameters = new String[currentArgs.length + 1];
                System.arraycopy(currentArgs, 0, parameters, 0, currentArgs.length);
                parameters[currentArgs.length] = parameterTextField.getText();
            }
            qf.executeQuery(queryName, parameters);
        }
    }
}