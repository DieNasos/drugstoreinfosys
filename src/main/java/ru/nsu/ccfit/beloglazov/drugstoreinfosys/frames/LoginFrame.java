package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.ConnectionFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class LoginFrame extends JFrame implements ActionListener {
    private final Container container = getContentPane();
    private final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    private final JLabel tipLabel = new JLabel("CONNECTION TO DATABASE");
    private final JLabel urlLabel = new JLabel("URL:");
    private final JLabel userLabel = new JLabel("USERNAME:");
    private final JLabel passwordLabel = new JLabel("PASSWORD:");
    private final JTextField urlTextField = new JTextField();
    private final JTextField userTextField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginButton = new JButton("LOGIN");
    private final JButton resetButton = new JButton("RESET");
    private final JButton defaultLocalButton = new JButton("DEFAULT LOCAL CONNECTION");
    private final JButton defaultNSUButton = new JButton("DEFAULT NSU CONNECTION");
    private final JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
    private final JCheckBox initCheckBox = new JCheckBox("Initialize");
    private final Properties properties;
    private MainFrame mf = null;
    private boolean init = false;

    public LoginFrame() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("DIS :: Login Form");
        setVisible(true);
        setBounds(10,10,300,420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/connection.properties"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not load properties! Default connections are not available!");
            container.remove(defaultLocalButton);
            container.remove(defaultNSUButton);
        }
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(10, 10, 250, 30);
        tipLabel.setBounds(10, 50, 250, 30);
        urlLabel.setBounds(10, 90, 100, 30);
        userLabel.setBounds(10,130,100,30);
        passwordLabel.setBounds(10,170,100,30);
        urlTextField.setBounds(50, 90, 220, 30);
        userTextField.setBounds(90,130,180,30);
        passwordField.setBounds(90,170,180,30);
        showPasswordCheckBox.setBounds(10,210,120,30);
        initCheckBox.setBounds(145, 210, 150, 30);
        loginButton.setBounds(10,250,125,30);
        resetButton.setBounds(145,250,125,30);
        defaultLocalButton.setBounds(10, 290, 260, 30);
        defaultNSUButton.setBounds(10, 330, 260, 30);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(tipLabel);
        container.add(urlLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(urlTextField);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPasswordCheckBox);
        container.add(initCheckBox);
        container.add(loginButton);
        container.add(resetButton);
        container.add(defaultLocalButton);
        container.add(defaultNSUButton);
    }

    private void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPasswordCheckBox.addActionListener(this);
        initCheckBox.addActionListener(this);
        defaultLocalButton.addActionListener(this);
        defaultNSUButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String urlText = urlTextField.getText();
            String userText = userTextField.getText();
            String passwordText = String.copyValueOf(passwordField.getPassword());
            connect(urlText, userText, passwordText);
        } else if (e.getSource() == resetButton) {
            urlTextField.setText("");
            userTextField.setText("");
            passwordField.setText("");
        } else if (e.getSource() == defaultLocalButton) {
            urlTextField.setText(properties.getProperty("LOCAL_URL"));
            userTextField.setText(properties.getProperty("LOCAL_USER"));
            passwordField.setText(properties.getProperty("LOCAL_PASSWORD"));
        } else if (e.getSource() == defaultNSUButton) {
            urlTextField.setText(properties.getProperty("NSU_URL"));
            userTextField.setText(properties.getProperty("NSU_USER"));
            passwordField.setText(properties.getProperty("NSU_PASSWORD"));
        } else if (e.getSource() == showPasswordCheckBox) {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        } else if (e.getSource() == initCheckBox) {
            init = initCheckBox.isSelected();
        }
    }

    private void connect(String url, String user, String password) {
        Connection connection;
        try {
            connection = ConnectionFactory.getConnection(url, user, password);
            connection.setAutoCommit(false);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not connect!");
            return;
        }
        JOptionPane.showMessageDialog(this, "Connected successfully");
        setVisible(false);

        if (init) {
            initialize(connection);
        }

        if (mf == null) {
            mf = new MainFrame(this, connection);
        } else {
            mf.setVisible(true);
        }
    }

    private void initialize(Connection connection) {
        try (InputStream is = this.getClass().getResourceAsStream("/sql/create.sql")) {
            String script = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            Arrays.stream(script.split("\\r?\\n\\r?\\n"))
                    .filter(q -> !q.isEmpty() && !q.startsWith("--"))
                    .map(q -> q.replace("\r\n", " ").replace("\t", " "))
                    .forEach(q -> {
                        try (Statement statement = connection.createStatement()) {
                            statement.execute(q);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Could not initialize!");
                        }
                    });
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Could not initialize!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}