package ru.nsu.ccfit.beloglazov.drugstoreinfosys.factories;

import java.sql.*;
import java.util.TimeZone;
import oracle.jdbc.OracleDriver;

public class ConnectionFactory {
    public static Connection getConnection(String url, String login, String pass) throws SQLException {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Novosibirsk");
        TimeZone.setDefault(timeZone);
        DriverManager.registerDriver(new OracleDriver());
        return DriverManager.getConnection(url, login, pass);
    }
}