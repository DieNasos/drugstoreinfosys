package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import java.sql.*;
import oracle.jdbc.OracleDriver;

public class ConnectionFactory {
    public static Connection getConnection(String url, String user, String pass) throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        return DriverManager.getConnection(url, user, pass);
    }
}