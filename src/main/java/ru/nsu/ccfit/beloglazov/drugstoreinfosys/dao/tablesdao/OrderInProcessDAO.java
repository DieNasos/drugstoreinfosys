package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.tablesdao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderInProcessDAO implements TableDAO<OrderInProcess> {
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
    public List<TableItem> getAll() throws SQLException {
        List<TableItem> orders = new LinkedList<>();
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
    public List<TableItem> getByParameters(String condition) throws SQLException {
        List<TableItem> orders = new LinkedList<>();
        String sql = "SELECT * FROM INPRCSS WHERE 1 = 1 AND " + condition;
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

    public OrderInProcess getByOrderID(int orderID) throws SQLException {
        OrderInProcess oip = null;
        String sql = "SELECT * FROM INPRCSS WHERE order_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, orderID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            Timestamp readyTime = rs.getTimestamp("ready_time");
            oip = new OrderInProcess(id, orderID, readyTime);
        }
        ps.close();
        rs.close();
        return oip;
    }
}