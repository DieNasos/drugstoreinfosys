package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.DrugComponentDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.DrugComponent;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DrugComponentFrame extends ItemFrame {
    private final JLabel drugIDLabel = new JLabel("DRUG ID:");
    private final JTextField drugIDTextField = new JTextField();
    private final JLabel componentIDLabel = new JLabel("COMP. ID:");
    private final JTextField componentIDTextField = new JTextField();
    private final JLabel gramsLabel = new JLabel("GRAMS OF COMP.:");
    private final JTextField gramsTextField = new JTextField();

    public DrugComponentFrame(TableItem ti, TableFrame tf, Connection connection) {
        super(ti, tf, connection);
        initComponents();
        setBounds(10, 10, 300, 330);
    }

    @Override
    protected void setTextOnTextFields() {
        drugIDTextField.setText(String.valueOf(((DrugComponent)ti).getDrugID()));
        componentIDTextField.setText(String.valueOf(((DrugComponent)ti).getComponentID()));
        gramsTextField.setText(String.valueOf(((DrugComponent)ti).getGramsOfComponent()));
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        drugIDLabel.setBounds(10, 170, 260, 30);
        drugIDTextField.setBounds(80, 170, 190, 30);
        componentIDLabel.setBounds(10, 210, 260, 30);
        componentIDTextField.setBounds(80, 210, 190, 30);
        gramsLabel.setBounds(10, 250, 260, 30);
        gramsTextField.setBounds(120, 250, 150, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(drugIDLabel);
        container.add(drugIDTextField);
        container.add(componentIDLabel);
        container.add(componentIDTextField);
        container.add(gramsLabel);
        container.add(gramsTextField);
    }

    @Override
    protected void create() {
        try {
            DrugComponentDAO dao = (DrugComponentDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            int drugID = Integer.parseInt(drugIDTextField.getText());
            int componentID = Integer.parseInt(componentIDTextField.getText());
            float gramsOfComponent = Float.parseFloat(gramsTextField.getText());
            DrugComponent dc = new DrugComponent(drugID, componentID, gramsOfComponent);
            dao.add(dc);
            tf.setVisible(true);
            dispose();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not create item!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    protected void edit() {
        try {
            DrugComponentDAO dao = (DrugComponentDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            int drugID = Integer.parseInt(drugIDTextField.getText());
            int componentID = Integer.parseInt(componentIDTextField.getText());
            float gramsOfComponent = Float.parseFloat(gramsTextField.getText());
            DrugComponent dc = new DrugComponent(((DrugComponent)ti).getID(), drugID, componentID, gramsOfComponent);
            dao.update(dc);
            tf.setVisible(true);
            dispose();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not edit item!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}