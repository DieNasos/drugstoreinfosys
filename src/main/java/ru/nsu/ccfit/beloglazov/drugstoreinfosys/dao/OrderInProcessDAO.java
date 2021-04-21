package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.OrderInProcess;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.DAO;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderInProcessDAO implements DAO<OrderInProcess> {
    private final Connection connection;

    public OrderInProcessDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(OrderInProcess item) throws SQLException {
        String sql = "INSERT INTO INPRCSS (id, order_id, ready_time)" +
                "VALUES (S_INPRCSS.nextval, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getOrderID());
        ps.setTimestamp(2, item.getReadyTime());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(OrderInProcess item) throws SQLException {
        String sql = "UPDATE INPRCSS SET order_id = ?, ready_time = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getOrderID());
        ps.setTimestamp(2, item.getReadyTime());
        ps.setInt(3, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM INPRCSS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<OrderInProcess> getAll() throws SQLException {
        List<OrderInProcess> orders = new LinkedList<>();
        String sql = "SELECT * FROM INPRCSS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int orderID = rs.getInt("order_id");
            Timestamp readyTime = rs.getTimestamp("ready_time");
            OrderInProcess o = new OrderInProcess(id, orderID, readyTime);
            orders.add(o);
        }
        ps.close();
        rs.close();
        return orders;
    }

    @Override
    public void resetSequence() throws SQLException {
        String sql1 = "DROP SEQUENCE S_INPRCSS";
        PreparedStatement ps = connection.prepareStatement(sql1);
        ps.executeUpdate();
        String sql2 = "CREATE SEQUENCE S_INPRCSS START WITH 1 INCREMENT BY 1 NOMAXVALUE";
        ps = connection.prepareStatement(sql2);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public List<OrderInProcess> getForgottenOrders() throws SQLException {
        List<OrderInProcess> orders = new LinkedList<>();
        String sql = "SELECT * FROM INPRCSS WHERE ready_time < CURRENT_TIMESTAMP";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int orderID = rs.getInt("order_id");
            Timestamp readyTime = rs.getTimestamp("ready_time");
            OrderInProcess o = new OrderInProcess(id, orderID, readyTime);
            orders.add(o);
        }
        ps.close();
        rs.close();
        return orders;
    }
}