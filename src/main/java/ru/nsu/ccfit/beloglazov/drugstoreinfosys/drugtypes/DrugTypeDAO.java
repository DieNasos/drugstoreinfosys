package ru.nsu.ccfit.beloglazov.drugstoreinfosys.drugtypes;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.DAO;
import java.sql.*;
import java.util.*;

public class DrugTypeDAO implements DAO<DrugType> {
    private final Connection connection;

    public DrugTypeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(DrugType item) throws SQLException {
        String name = item.getName();
        String sql = "INSERT INTO DRGTYPES (id, name) VALUES (S_DRGTYPES.nextval, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(DrugType item) throws SQLException {
        String sql = "UPDATE DRGTYPES SET name = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getName());
        ps.setInt(2, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM DRGTYPES WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<DrugType> getAll() throws SQLException {
        List<DrugType> drugTypes = new LinkedList<>();
        String sql = "SELECT * FROM DRGTYPES";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            DrugType dt = new DrugType(id, name);
            drugTypes.add(dt);
        }
        ps.close();
        rs.close();
        return drugTypes;
    }

    @Override
    public void resetSequence() throws SQLException {
        String sql1 = "DROP SEQUENCE S_DRGTYPES";
        PreparedStatement ps = connection.prepareStatement(sql1);
        ps.executeUpdate();
        String sql2 = "CREATE SEQUENCE S_DRGTYPES START WITH 1 INCREMENT BY 1 NOMAXVALUE";
        ps = connection.prepareStatement(sql2);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }
}