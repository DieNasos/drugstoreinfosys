package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class RoleDAO {
    private final Connection connection;

    public RoleDAO(Connection connection) {
        this.connection = connection;
    }

    public List<String> getForUser() throws SQLException {
        List<String> roles = new LinkedList<>();
        String sql = "SELECT GRANTED_ROLE FROM SYS.USER_ROLE_PRIVS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            roles.add(rs.getString("GRANTED_ROLE"));
        }
        ps.close();
        rs.close();
        return roles;
    }
}