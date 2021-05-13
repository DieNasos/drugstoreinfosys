package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.TechnologyDAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Technology;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.TableItem;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class TechnologyFrame extends ItemFrame {
    private final JLabel drugNameLabel = new JLabel("DRUG NAME:");
    private final JTextField drugNameTextField = new JTextField();
    private final JLabel descriptionLabel = new JLabel("DESCRIPTION:");
    private final JTextField descriptionTextField = new JTextField();

    public TechnologyFrame(ItemFrameType type, String tableName, TableItem ti, JFrame parentFrame, Connection connection) {
        super(type, tableName, ti, parentFrame, connection);
        initComponents();
        setBounds(10, 10, 300, 300);
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            drugNameTextField.setText(((Technology) ti).getDrugName());
            descriptionTextField.setText(((Technology) ti).getDescription());
        } else if (type == ItemFrameType.FIND) {
            drugNameTextField.setText("= 'drug_1'");
            descriptionTextField.setText("= 'description_1'");
        }
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        drugNameLabel.setBounds(10, 170, 260, 30);
        drugNameTextField.setBounds(100, 170, 170, 30);
        descriptionLabel.setBounds(10, 210, 260, 30);
        descriptionTextField.setBounds(100, 210, 170, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(drugNameLabel);
        container.add(drugNameTextField);
        container.add(descriptionLabel);
        container.add(descriptionTextField);
    }

    @Override
    protected void create() {
        try {
            TechnologyDAO dao = (TechnologyDAO) DAOFactory.createDAO(tableName, connection);
            String drug_name = drugNameTextField.getText();
            String description = descriptionTextField.getText();
            Technology t = new Technology(drug_name, description);
            dao.add(t);
            parentFrame.setVisible(true);
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
            TechnologyDAO dao = (TechnologyDAO) DAOFactory.createDAO(tableName, connection);
            String drug_name = drugNameTextField.getText();
            String description = descriptionTextField.getText();
            Technology t = new Technology(((Technology)ti).getID(), drug_name, description);
            dao.update(t);
            parentFrame.setVisible(true);
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
            TechnologyDAO dao = (TechnologyDAO) DAOFactory.createDAO(tableName, connection);
            StringBuilder condition = new StringBuilder();
            String s1 = null, s2 = null;
            if (!drugNameLabel.getText().equals("")) {
                s1 = "drug_name " + drugNameTextField.getText();
            }
            if (!descriptionTextField.getText().equals("")) {
                s2 = "description " + descriptionTextField.getText();
            }
            appendConditionPart(condition, s1);
            appendConditionPart(condition, s2);
            List<TableItem> foundItems;
            if (condition.length() > 0) {
                foundItems = dao.getByParameters(condition.toString());
            } else {
                foundItems = dao.getAll();
            }
            parentFrame.setVisible(true);
            if (parentFrame instanceof TableFrame) {
                ((TableFrame) parentFrame).updateItems(foundItems);
            }
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