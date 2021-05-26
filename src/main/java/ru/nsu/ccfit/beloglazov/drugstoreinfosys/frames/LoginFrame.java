package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.Role;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class LoginFrame extends JFrame implements ActionListener {
    private final Container container = getContentPane();
    private final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM (ver. S4)");
    private final JLabel tipLabel = new JLabel("CONNECTION TO DATABASE");
    private final JLabel urlLabel = new JLabel("URL:");
    private final JLabel loginLabel = new JLabel("LOGIN:");
    private final JLabel passwordLabel = new JLabel("PASSWORD:");
    private final JLabel adminLabel = new JLabel("ADMIN:");
    private final JTextField urlTextField = new JTextField();
    private final JTextField loginTextField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JTextField adminTextField = new JTextField();
    private final JButton loginButton = new JButton("LOGIN");
    private final JButton resetButton = new JButton("RESET");
    private final JButton defaultLocalButton = new JButton("DEFAULT LOCAL CONNECTION");
    private final JButton defaultNSUButton = new JButton("DEFAULT NSU CONNECTION");
    private final JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
    private final JRadioButton initEmptyRButton = new JRadioButton("Create empty tables");
    private final JRadioButton initAndFillRButton = new JRadioButton("Create tables + fill them with test data");
    private final Properties properties;
    private boolean initEmpty = false;
    private boolean initAndFill = false;

    public LoginFrame() {
        container.setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("Login Form");
        setBounds(10,10,300,520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(
                    "/connection.properties"
            ));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load properties! Default connections are not available!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
            container.remove(defaultLocalButton);
            container.remove(defaultNSUButton);
        }
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 260, 30);
        tipLabel.setBounds(10, 50, 250, 30);
        urlLabel.setBounds(10, 90, 100, 30);
        loginLabel.setBounds(10,130,150,30);
        passwordLabel.setBounds(10,170,150,30);
        adminLabel.setBounds(10,210,260,30);
        urlTextField.setBounds(50, 90, 220, 30);
        loginTextField.setBounds(70,130,200,30);
        passwordField.setBounds(90,170,180,30);
        adminTextField.setBounds(70,210,200,30);
        showPasswordCheckBox.setBounds(10,250,260,30);
        initEmptyRButton.setBounds(10, 280, 260, 30);
        initAndFillRButton.setBounds(10, 310, 260, 30);
        defaultLocalButton.setBounds(10, 350, 260, 30);
        defaultNSUButton.setBounds(10, 390, 260, 30);
        loginButton.setBounds(10,430,125,30);
        resetButton.setBounds(145,430,125,30);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(tipLabel);
        container.add(urlLabel);
        container.add(loginLabel);
        container.add(passwordLabel);
        container.add(adminLabel);
        container.add(urlTextField);
        container.add(loginTextField);
        container.add(passwordField);
        container.add(adminTextField);
        container.add(showPasswordCheckBox);
        container.add(initEmptyRButton);
        container.add(initAndFillRButton);
        container.add(defaultLocalButton);
        container.add(defaultNSUButton);
        container.add(loginButton);
        container.add(resetButton);
    }

    private void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPasswordCheckBox.addActionListener(this);
        initEmptyRButton.addActionListener(this);
        initAndFillRButton.addActionListener(this);
        defaultLocalButton.addActionListener(this);
        defaultNSUButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String urlText = urlTextField.getText();
            String loginText = loginTextField.getText();
            String passwordText = String.copyValueOf(passwordField.getPassword());
            connect(urlText, loginText, passwordText);
        } else if (e.getSource() == resetButton) {
            urlTextField.setText("");
            loginTextField.setText("");
            passwordField.setText("");
            adminTextField.setText("");
        } else if (e.getSource() == defaultLocalButton) {
            urlTextField.setText(properties.getProperty("LOCAL_URL"));
            loginTextField.setText(properties.getProperty("LOCAL_LOGIN"));
            passwordField.setText(properties.getProperty("LOCAL_PASSWORD"));
            adminTextField.setText(properties.getProperty("LOCAL_ADMIN"));
        } else if (e.getSource() == defaultNSUButton) {
            urlTextField.setText(properties.getProperty("NSU_URL"));
            loginTextField.setText(properties.getProperty("NSU_LOGIN"));
            passwordField.setText(properties.getProperty("NSU_PASSWORD"));
            adminTextField.setText(properties.getProperty("NSU_ADMIN"));
        } else if (e.getSource() == showPasswordCheckBox) {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        } else if (e.getSource() == initEmptyRButton) {
            initEmpty = initEmptyRButton.isSelected();
            if (initEmpty) {
                initAndFill = false;
                initAndFillRButton.setSelected(false);
            }
        } else if (e.getSource() == initAndFillRButton) {
            initAndFill = initAndFillRButton.isSelected();
            if (initAndFill) {
                initEmpty = false;
                initEmptyRButton.setSelected(false);
            }
        }
    }

    private void connect(String url, String login, String password) {
        Connection connection;
        try {
            connection = ConnectionFactory.getConnection(url, "\"" + login + "\"", password);
            connection.setAutoCommit(false);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not connect!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            setCurrentSchema(connection, adminTextField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not set current schema!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        DAOFactory daoFactory = new DAOFactory(connection);
        Role myRole;
        try {
            List<String> myRoles = daoFactory.rDAO.getForUser();
            if (myRoles.contains("ROLE_DRUGSTORE_ADMIN")) {
                myRole = Role.ADMIN;
            } else if (myRoles.contains("ROLE_DRUGSTORE_WORKER")) {
                myRole = Role.STORE_WORKER;
            } else if (myRoles.contains("ROLE_DRUGSTORE_CUSTOMER")) {
                myRole = Role.CUSTOMER;
            } else {
                JOptionPane.showMessageDialog(
                        this, "You are not admin/worker/customer! Please set one of this roles!",
                        "Error!", JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not get roles for user!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this, "Connected successfully"
        );

        if (myRole != Role.ADMIN && (initEmpty || initAndFill)) {
            JOptionPane.showMessageDialog(
                    this, "Only admin has permission to init!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }

        if (myRole == Role.ADMIN) {
            init(connection);
        }

        try {
            MainFrame mf = FrameFactory.getMainFrame(this, daoFactory, myRole, loginTextField.getText());
            setVisible(false);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not get role of user!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void executeSQL(String resourceName, Connection connection) throws IOException, SQLException {
        InputStream is = this.getClass().getResourceAsStream(resourceName);
        String script = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
        Arrays.stream(script.split("\\r?\\n\\r?\\n"))
                .filter(q -> !q.isEmpty() && !q.startsWith("--"))
                .map(q -> q.replace("\r\n", " ")
                        .replace("\t", " "))
                .forEach(q -> {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(q);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(
                                this, "Could not execute query!",
                                "Error!", JOptionPane.ERROR_MESSAGE
                        );
                    }
                });
        connection.commit();
        is.close();
    }

    private void setCurrentSchema(Connection connection, String schemaName) throws SQLException {
        String s = "alter session set current_schema = " + schemaName;
        PreparedStatement ps = connection.prepareStatement(s);
        ps.execute();
        connection.commit();
        ps.close();
    }
    
    private void init(Connection connection) {
        if (initEmpty && initAndFill) {
            JOptionPane.showMessageDialog(
                    this, "Select only one init-option!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (initEmpty) {
            try {
                executeSQL("/sql/create.sql", connection);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        this, "Could not initialize tables!",
                        "Error!", JOptionPane.ERROR_MESSAGE
                );
            }
        } else if (initAndFill) {
            try {
                executeSQL("/sql/create.sql", connection);
                executeSQL("/sql/insert.sql", connection);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Could not initialize tables and/or fill them with test data!",
                        "Error!", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}