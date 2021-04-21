package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao;

import javafx.util.Pair;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Component;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.DrugComponent;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.DAO;
import java.sql.*;
import java.util.*;

public class DrugComponentDAO implements DAO<DrugComponent> {
    private final Connection connection;

    public DrugComponentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(DrugComponent item) throws SQLException {
        String sql = "INSERT INTO DRGSCMPS (id, drug_id, component_id, grams_of_component) VALUES (S_DRGSCMPS.nextval, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getDrugID());
        ps.setInt(2, item.getComponentID());
        ps.setFloat(3, item.getGramsOfComponent());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(DrugComponent item) throws SQLException {
        String sql = "UPDATE DRGSCMPS SET drug_id = ?, component_id = ?, grams_of_component = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getDrugID());
        ps.setInt(2, item.getComponentID());
        ps.setFloat(3, item.getGramsOfComponent());
        ps.setInt(4, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM DRGSCMPS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<DrugComponent> getAll() throws SQLException {
        List<DrugComponent> dcs = new LinkedList<>();
        String sql = "SELECT * FROM DRGSCMPS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int drugID = rs.getInt("drug_id");
            int componentID = rs.getInt("component_id");
            float gramsOfComponent = rs.getFloat("grams_of_component");
            DrugComponent dc = new DrugComponent(id, drugID, componentID, gramsOfComponent);
            dcs.add(dc);
        }
        ps.close();
        rs.close();
        return dcs;
    }

    @Override
    public void resetSequence() throws SQLException {
        String sql1 = "DROP SEQUENCE S_DRGSCMPS";
        PreparedStatement ps = connection.prepareStatement(sql1);
        ps.executeUpdate();
        String sql2 = "CREATE SEQUENCE S_DRGSCMPS START WITH 1 INCREMENT BY 1 NOMAXVALUE";
        ps = connection.prepareStatement(sql2);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public Map<Integer, Float> getComponentsForDrug(int id) throws SQLException {
        Map<Integer, Float> componentsAndGrams = new HashMap<>();
        String sql = "SELECT * FROM DRGSCMPS WHERE drug_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int cID = rs.getInt("component_id");
            componentsAndGrams.put(cID, rs.getFloat("grams_of_component"));
        }
        ps.close();
        rs.close();
        return componentsAndGrams;
    }
}