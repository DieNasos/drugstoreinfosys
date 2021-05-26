package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.tablesdao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import java.sql.*;
import java.util.*;

public class DrugDAO implements TableDAO<Drug> {
    private final Connection connection;

    public DrugDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Drug item) throws SQLException {
        String sql = "INSERT INTO DRUGS (id, type_id, technology_id, price, amount, crit_norma) VALUES (S_DRUGS.nextval, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getTypeID());
        ps.setInt(2, item.getTechnologyID());
        ps.setFloat(3, item.getPrice());
        ps.setInt(4, item.getAmount());
        ps.setInt(5, item.getCritNorma());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(Drug item) throws SQLException {
        String sql = "UPDATE DRUGS SET type_id = ?, technology_id = ?, price = ?, amount = ?, crit_norma = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getTypeID());
        ps.setInt(2, item.getTechnologyID());
        ps.setFloat(3, item.getPrice());
        ps.setInt(4, item.getAmount());
        ps.setInt(5, item.getCritNorma());
        ps.setInt(6, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM DRUGS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<TableItem> getAll() throws SQLException {
        List<TableItem> drugs = new LinkedList<>();
        String sql = "SELECT * FROM DRUGS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int typeID = rs.getInt("type_id");
            int technologyID = rs.getInt("technology_id");
            float price = rs.getFloat("price");
            int amount = rs.getInt("amount");
            int critNorma = rs.getInt("crit_norma");
            Drug d = new Drug(id, typeID, technologyID, price, amount, critNorma);
            drugs.add(d);
        }
        ps.close();
        rs.close();
        return drugs;
    }

    @Override
    public List<TableItem> getByParameters(String condition) throws SQLException {
        List<TableItem> drugs = new LinkedList<>();
        String sql = "SELECT * FROM DRUGS WHERE 1 = 1 AND " + condition;
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int typeID = rs.getInt("type_id");
            int technologyID = rs.getInt("technology_id");
            float price = rs.getFloat("price");
            int amount = rs.getInt("amount");
            int critNorma = rs.getInt("crit_norma");
            Drug d = new Drug(id, typeID, technologyID, price, amount, critNorma);
            drugs.add(d);
        }
        ps.close();
        rs.close();
        return drugs;
    }

    public Drug getByTechnologyID(int technologyID) throws SQLException {
        Drug drug = null;
        String sql = "SELECT * FROM DRUGS WHERE technology_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, technologyID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int dID = rs.getInt("id");
            int typeID = rs.getInt("type_id");
            float price = rs.getFloat("price");
            int amount = rs.getInt("amount");
            int critNorma = rs.getInt("crit_norma");
            drug = new Drug(dID, typeID, technologyID, price, amount, critNorma);
        }
        ps.close();
        rs.close();
        return drug;
    }

    public Drug getByID(int id) throws SQLException {
        Drug drug = null;
        String sql = "SELECT * FROM DRUGS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int typeID = rs.getInt("type_id");
            int technologyID = rs.getInt("technology_id");
            float price = rs.getFloat("price");
            int amount = rs.getInt("amount");
            int critNorma = rs.getInt("crit_norma");
            drug = new Drug(id, typeID, technologyID, price, amount, critNorma);
        }
        ps.close();
        rs.close();
        return drug;
    }
}