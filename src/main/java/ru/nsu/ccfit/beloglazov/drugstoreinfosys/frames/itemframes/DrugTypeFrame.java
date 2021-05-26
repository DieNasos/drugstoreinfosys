package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.itemframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import javax.swing.*;
import java.util.List;

public class DrugTypeFrame extends ItemFrame {
    private final JLabel nameLabel = new JLabel("NAME:");
    private final JTextField nameTextField = new JTextField();

    public DrugTypeFrame(ItemFrameType type, TableItem ti, JFrame parentFrame, DAOFactory daoFactory) {
        super(type, ti, parentFrame, daoFactory, daoFactory.dtDAO);
        initComponents();
        setBounds(10, 10, 300, 230);
    }

    @Override
    protected void setTextOnTextFields() {
        if (type == ItemFrameType.EDIT) {
            nameTextField.setText(((DrugType) ti).getName());
        } else if (type == ItemFrameType.FIND) {
            nameTextField.setText("= 'type_1'");
        }
    }

    @Override
    protected void setLocationAndSizeForCustom() {
        nameLabel.setBounds(10, 130, 260, 30);
        nameTextField.setBounds(70, 130, 200, 30);
    }

    @Override
    protected void addCustomComponentsToContainer() {
        container.add(nameLabel);
        container.add(nameTextField);
    }

    @Override
    protected void create() {
        try {
            String name = nameTextField.getText();
            DrugType dt = new DrugType(name);
            dao.add(dt);
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
            String name = nameTextField.getText();
            DrugType newDT = new DrugType(((DrugType)ti).getID(), name);
            dao.update(newDT);
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
            StringBuilder condition = new StringBuilder();
            String s1 = null;
            if (!nameTextField.getText().equals("")) {
                s1 = "name " + nameTextField.getText();
            }
            appendConditionPart(condition, s1);
            List<TableItem> foundItems;
            if (condition.length() > 0) {
                foundItems = dao.getByParameters(condition.toString());
            } else {
                foundItems = dao.getAll();
            }
            parentFrame.setVisible(true);
            ((TableFrame) parentFrame).updateItems(foundItems);
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