package ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.mainframes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.TableAccessType;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories.DAOFactory;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.*;
import javax.swing.*;
import java.sql.*;

public class AdminFrame extends MainFrame {
    public AdminFrame(LoginFrame lf, DAOFactory daoFactory) {
        super(lf, daoFactory);
        roleLabel.setText("You are ADMIN");
        setBounds(10,10,300,450);
    }

    @Override
    protected void initOptions() {
        optionsArray = new String[] {
                "Open COMPONENTS",
                "Open DRUGS/COMPONENTS",
                "Open DRUGS",
                "Open DRUG TYPES",
                "Open ALL ORDERS",
                "Open ORDERS IN PROCESS",
                "Open TECHNOLOGIES",
                "Open CUSTOMERS"
        };
    }

    @Override
    protected void executeOption(String option) {
        try {
            switch (option) {
                case "Open COMPONENTS":
                    TableFrame tf1 = new TableFrame(this, "CMPNNTS",
                            TableAccessType.READ_AND_WRITE, daoFactory, daoFactory.coDAO);
                    break;
                case "Open DRUGS/COMPONENTS":
                    TableFrame tf2 = new TableFrame(this, "DRGSCMPS",
                            TableAccessType.READ_AND_WRITE, daoFactory, daoFactory.dcDAO);
                    break;
                case "Open DRUGS":
                    TableFrame tf3 = new TableFrame(this, "DRUGS",
                            TableAccessType.READ_AND_WRITE, daoFactory, daoFactory.dDAO);
                    break;
                case "Open DRUG TYPES":
                    TableFrame tf4 = new TableFrame(this, "DRGTYPES",
                            TableAccessType.READ_AND_WRITE, daoFactory, daoFactory.dtDAO);
                    break;
                case "Open ALL ORDERS":
                    TableFrame tf5 = new TableFrame(this, "ORDERS",
                            TableAccessType.READ_AND_WRITE, daoFactory, daoFactory.oDAO);
                    break;
                case "Open ORDERS IN PROCESS":
                    TableFrame tf6 = new TableFrame(this, "INPRCSS",
                            TableAccessType.READ_AND_WRITE, daoFactory, daoFactory.oipDAO);
                    break;
                case "Open TECHNOLOGIES":
                    TableFrame tf7 = new TableFrame(this, "TCHNLGS",
                            TableAccessType.READ_AND_WRITE, daoFactory, daoFactory.tDAO);
                    break;
                case "Open CUSTOMERS":
                    TableFrame tf8 = new TableFrame(this, "CUSTOMERS",
                            TableAccessType.READ_ONLY, daoFactory, daoFactory.cuDAO);
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