package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public abstract class MainFrame extends JFrame implements ActionListener {
    protected final Container container = getContentPane();
    protected final JLabel titleLabel = new JLabel("DRUGSTORE INFORMATION SYSTEM");
    protected final JLabel tipLabel = new JLabel("CHOOSE OPERATION");
    protected final JLabel roleLabel = new JLabel();
    protected final JButton chooseButton = new JButton();
    protected final JButton backButton = new JButton();
    protected final DAOFactory daoFactory;
    protected final LoginFrame lf;
    protected final QueryFrame qf;
    protected JList<String> optionsJList;
    protected JScrollPane scrollPane;
    protected String[] optionsArray;

    public MainFrame(LoginFrame lf, DAOFactory daoFactory) {
        this.lf = lf;
        this.daoFactory = daoFactory;
        qf = new QueryFrame(this, daoFactory);
        container.setLayout(null);
        setTitle("Main Frame");
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
        scrollPane.setBounds(10, 130, 260, 220);
        chooseButton.setBounds(10, 370, 50, 30);
        backButton.setBounds(70, 370, 50, 30);
        try {
            URL chooseResource = getClass().getClassLoader().getResource("images/check.gif");
            URL backResource = getClass().getClassLoader().getResource("images/back.gif");
            if (chooseResource != null && backResource != null) {
                ImageIcon chooseIcon = new ImageIcon(chooseResource);
                chooseButton.setIcon(chooseIcon);
                ImageIcon backIcon = new ImageIcon(backResource);
                backButton.setIcon(backIcon);
            } else {
                throw new Exception("Error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            chooseButton.setText("V");
            backButton.setText("<");
        }
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