package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class RoleDAO implements DAO<Role> {
    private final Connection connection;

    public RoleDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Role item) throws SQLException {
        String sql = "INSERT INTO ROLES (id, name) VALUES (S_ROLES.nextval, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getName());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(Role item) throws SQLException {
        String sql = "UPDATE ROLES SET name = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getName());
        ps.setInt(2, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ROLES WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<TableItem> getAll() throws SQLException {
        List<TableItem> roles = new LinkedList<>();
        String sql = "SELECT * FROM ROLES";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Role r = new Role(id, name);
            roles.add(r);
        }
        ps.close();
        rs.close();
        return roles;
    }

    @Override
    public List<TableItem> getByParameters(String condition) throws SQLException {
        List<TableItem> roles = new LinkedList<>();
        String sql = "SELECT * FROM ROLES WHERE 1 = 1 AND " + condition;
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Role r = new Role(id, name);
            roles.add(r);
        }
        ps.close();
        rs.close();
        return roles;
    }

    public Role getByID(int id) throws SQLException {
        Role role = null;
        String sql = "SELECT * FROM ROLES WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            role = new Role(id, name);
        }
        ps.close();
        rs.close();
        return role;
    }
}