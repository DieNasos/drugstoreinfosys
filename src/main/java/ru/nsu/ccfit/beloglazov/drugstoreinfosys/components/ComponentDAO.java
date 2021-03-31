package ru.nsu.ccfit.beloglazov.drugstoreinfosys.components;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.DAO;
import java.sql.*;
import java.util.*;

public class ComponentDAO implements DAO<Component> {
    private final Connection connection;

    public ComponentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Component item) throws SQLException {
        String name = item.getName();
        int amount = item.getAmount();
        String sql = "INSERT INTO CMPNNTS (id, name, amount) VALUES (S_CMPNNTS.nextval, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, amount);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(Component item) throws SQLException {
        String sql = "UPDATE CMPNNTS SET name = ?, amount = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getName());
        ps.setInt(2, item.getAmount());
        ps.setInt(3, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM CMPNNTS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<Component> getAll() throws SQLException {
        List<Component> components = new LinkedList<>();
        String sql = "SELECT * FROM CMPNNTS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int amount = rs.getInt("amount");
            Component c = new Component(id, name, amount);
            components.add(c);
        }
        ps.close();
        rs.close();
        return components;
    }

    @Override
    public void resetSequence() throws SQLException {
        String sql1 = "DROP SEQUENCE S_CMPNNTS";
        PreparedStatement ps = connection.prepareStatement(sql1);
        ps.executeUpdate();
        String sql2 = "CREATE SEQUENCE S_CMPNNTS START WITH 1 INCREMENT BY 1 NOMAXVALUE";
        ps = connection.prepareStatement(sql2);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }
}