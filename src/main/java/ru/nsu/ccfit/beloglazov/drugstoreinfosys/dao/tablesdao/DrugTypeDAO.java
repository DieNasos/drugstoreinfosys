package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.tablesdao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import java.sql.*;
import java.util.*;

public class DrugTypeDAO implements TableDAO<DrugType> {
    private final Connection connection;

    public DrugTypeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(DrugType item) throws SQLException {
        String sql = "INSERT INTO DRGTYPES (id, name) VALUES (S_DRGTYPES.nextval, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getName());
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
    public List<TableItem> getAll() throws SQLException {
        List<TableItem> drugTypes = new LinkedList<>();
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
    public List<TableItem> getByParameters(String condition) throws SQLException {
        List<TableItem> drugTypes = new LinkedList<>();
        String sql = "SELECT * FROM DRGTYPES WHERE 1 = 1 AND " + condition;
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

    public DrugType getByID(int id) throws SQLException {
        DrugType dt = null;
        String sql = "SELECT * FROM DRGTYPES WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int dtID = rs.getInt("id");
            String name = rs.getString("name");
            dt = new DrugType(dtID, name);
        }
        ps.close();
        rs.close();
        return dt;
    }
}