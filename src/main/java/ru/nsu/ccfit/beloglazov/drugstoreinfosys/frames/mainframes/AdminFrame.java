package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.TableAccessType;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.User;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import javax.swing.*;
import java.sql.*;

public class AdminFrame extends MainFrame {
    public AdminFrame(LoginFrame lf, Connection connection, User user) {
        super(lf, connection, user);
        roleLabel.setText("You are ADMIN");
        setBounds(10,10,300,450);
    }

    @Override
    protected void initOptions() {
        optionsArray = new String[] {
                "Open Components",
                "Open Drugs components",
                "Open Drugs",
                "Open Drug types",
                "Open All orders",
                "Open Orders in process",
                "Open Technologies"
        };
    }

    @Override
    protected void executeOption(String option) {
        try {
            switch (option) {
                case "Open Components":
                    TableFrame tf1 = new TableFrame(this, "CMPNNTS", connection, TableAccessType.READ_AND_WRITE);
                    break;
                case "Open Drugs components":
                    TableFrame tf2 = new TableFrame(this, "DRGSCMPS", connection, TableAccessType.READ_AND_WRITE);
                    break;
                case "Open Drugs":
                    TableFrame tf3 = new TableFrame(this, "DRUGS", connection, TableAccessType.READ_AND_WRITE);
                    break;
                case "Open Drug types":
                    TableFrame tf4 = new TableFrame(this, "DRGTYPES", connection, TableAccessType.READ_AND_WRITE);
                    break;
                case "Open All orders":
                    TableFrame tf5 = new TableFrame(this, "ORDERS", connection, TableAccessType.READ_AND_WRITE);
                    break;
                case "Open Orders in process":
                    TableFrame tf6 = new TableFrame(this, "INPRCSS", connection, TableAccessType.READ_AND_WRITE);
                    break;
                case "Open Technologies":
                    TableFrame tf7 = new TableFrame(this, "TCHNLGS", connection, TableAccessType.READ_AND_WRITE);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Could not execute option!",
                    "Error!", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}