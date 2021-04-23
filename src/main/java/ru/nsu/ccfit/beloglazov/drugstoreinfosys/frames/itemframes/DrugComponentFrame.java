package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.DrugComponentDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.DrugComponent;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class DrugComponentFrame extends ItemFrame {
    private final JLabel drugIDLabel = new JLabel("DRUG ID:");
    private final JTextField drugIDTextField = new JTextField();
    private final JLabel componentIDLabel = new JLabel("COMP. ID:");
    private final JTextField componentIDTextField = new JTextField();
    private final JLabel gramsLabel = new JLabel("GRAMS OF COMP.:");
    private final JTextField gramsTextField = new JTextField();

    public DrugComponentFrame(ItemFrameType type, TableItem ti, TableFrame tf, Connection connection) {
        super(type, ti, tf, connection);
        initComponents();
        setBounds(10, 10, 300, 330);
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            drugIDTextField.setText(String.valueOf(((DrugComponent) ti).getDrugID()));
            componentIDTextField.setText(String.valueOf(((DrugComponent) ti).getComponentID()));
            gramsTextField.setText(String.valueOf(((DrugComponent) ti).getGramsOfComponent()));
        } else if (type == ItemFrameType.FIND) {
            drugIDTextField.setText("= 1");
            componentIDTextField.setText("= 3");
            gramsTextField.setText("> 3.0");
        }
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
        } catch (Exception exception) {
            exception.printStackTrace();
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
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not edit item!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    protected void find() {
        try {
            DrugComponentDAO dao = (DrugComponentDAO) DAOFactory.createDAO(tf.getTableName(), connection);
            StringBuilder condition = new StringBuilder();
            String s1 = null, s2 = null, s3 = null;
            if (!drugIDTextField.getText().equals("")) {
                s1 = "drug_id " + drugIDTextField.getText();
            }
            if (!componentIDTextField.getText().equals("")) {
                s2 = "component_id " + componentIDTextField.getText();
            }
            if (!gramsTextField.getText().equals("")) {
                s3 = "grams_of_component " + gramsTextField.getText();
            }
            appendConditionPart(condition, s1);
            appendConditionPart(condition, s2);
            appendConditionPart(condition, s3);
            List<TableItem> foundItems = dao.getByParameters(condition.toString());
            tf.setVisible(true);
            tf.updateItems(foundItems);
            dispose();
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not find items!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}