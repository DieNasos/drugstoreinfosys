package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.GivenOrder;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.DAO;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.TableItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GivenOrderDAO implements DAO<GivenOrder> {
    private final Connection connection;

    public GivenOrderDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(GivenOrder item) throws SQLException {
        String sql = "INSERT INTO GIVEN (id, order_id) VALUES (S_GIVEN.nextval, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getOrderID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(GivenOrder item) throws SQLException {
        String sql = "UPDATE GIVEN SET order_id = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getOrderID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM GIVEN WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<TableItem> getAll() throws SQLException {
        List<TableItem> orders = new LinkedList<>();
        String sql = "SELECT * FROM GIVEN";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int orderID = rs.getInt("order_id");
            GivenOrder o = new GivenOrder(id, orderID);
            orders.add(o);
        }
        ps.close();
        rs.close();
        return orders;
    }

    @Override
    public List<TableItem> getByParameters(String condition) throws SQLException {
        List<TableItem> orders = new LinkedList<>();
        String sql = "SELECT * FROM GIVEN WHERE 1 = 1 AND " + condition;
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int orderID = rs.getInt("order_id");
            GivenOrder o = new GivenOrder(id, orderID);
            orders.add(o);
        }
        ps.close();
        rs.close();
        return orders;
    }

    @Override
    public void resetSequence() throws SQLException {
        String sql1 = "DROP SEQUENCE S_GIVEN";
        PreparedStatement ps = connection.prepareStatement(sql1);
        ps.executeUpdate();
        String sql2 = "CREATE SEQUENCE S_GIVEN START WITH 1 INCREMENT BY 1 NOMAXVALUE";
        ps = connection.prepareStatement(sql2);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }
}