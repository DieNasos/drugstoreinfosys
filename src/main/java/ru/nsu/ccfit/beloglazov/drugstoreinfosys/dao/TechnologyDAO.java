package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Technology;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.DAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.sql.*;
import java.util.*;

public class TechnologyDAO implements DAO<Technology> {
    private final Connection connection;

    public TechnologyDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Technology item) throws SQLException {
        String sql = "INSERT INTO TCHNLGS (id, drug_name, description) VALUES (S_TCHNLGS.nextval, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getDrugName());
        ps.setString(2, item.getDescription());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(Technology item) throws SQLException {
        String sql = "UPDATE TCHNLGS SET drug_name = ?, description = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getDrugName());
        ps.setString(2, item.getDescription());
        ps.setInt(3, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM TCHNLGS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<TableItem> getAll() throws SQLException {
        List<TableItem> technologies = new LinkedList<>();
        String sql = "SELECT * FROM TCHNLGS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String drugName = rs.getString("drug_name");
            String description = rs.getString("description");
            Technology t = new Technology(id, drugName, description);
            technologies.add(t);
        }
        ps.close();
        rs.close();
        return technologies;
    }

    @Override
    public List<TableItem> getByParameters(String condition) throws SQLException {
        List<TableItem> technologies = new LinkedList<>();
        String sql = "SELECT * FROM TCHNLGS WHERE 1 = 1 AND " + condition;
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String drugName = rs.getString("drug_name");
            String description = rs.getString("description");
            Technology t = new Technology(id, drugName, description);
            technologies.add(t);
        }
        ps.close();
        rs.close();
        return technologies;
    }

    @Override
    public void resetSequence() throws SQLException {
        String sql1 = "DROP SEQUENCE S_TCHNLGS";
        PreparedStatement ps = connection.prepareStatement(sql1);
        ps.executeUpdate();
        String sql2 = "CREATE SEQUENCE S_TCHNLGS START WITH 1 INCREMENT BY 1 NOMAXVALUE";
        ps = connection.prepareStatement(sql2);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public Technology getByDrugName(String drugName) throws SQLException {
        Technology technology = null;
        String sql = "SELECT * FROM TCHNLGS WHERE drug_name = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, drugName);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int tID = rs.getInt("id");
            String tDrugName = rs.getString("drug_name");
            String description = rs.getString("description");
            technology = new Technology(tID, tDrugName, description);
        }
        ps.close();
        rs.close();
        return technology;
    }
}