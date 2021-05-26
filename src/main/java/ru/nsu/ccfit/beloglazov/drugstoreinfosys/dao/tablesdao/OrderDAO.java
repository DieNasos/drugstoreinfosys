package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.tablesdao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderDAO implements TableDAO<Order> {
    private final Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Order item) throws SQLException {
        String sql = "INSERT INTO ORDERS (id, customer_id, drug_id, amount, given) VALUES (S_ORDERS.nextval, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getCustomerID());
        ps.setInt(2, item.getDrugID());
        ps.setInt(3, item.getAmount());
        int given = item.isGiven() ? 1 : 0;
        ps.setInt(4, given);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(Order item) throws SQLException {
        String sql = "UPDATE ORDERS SET customer_id = ?, drug_id = ?, amount = ?, given = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getCustomerID());
        ps.setInt(2, item.getDrugID());
        ps.setInt(3, item.getAmount());
        int given = item.isGiven() ? 1 : 0;
        ps.setInt(4, given);
        ps.setInt(5, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ORDERS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<TableItem> getAll() throws SQLException {
        List<TableItem> orders = new LinkedList<>();
        String sql = "SELECT * FROM ORDERS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int customerID = rs.getInt("customer_id");
            int drugID = rs.getInt("drug_id");
            int amount = rs.getInt("amount");
            boolean given = rs.getInt("given") == 1;
            Order o = new Order(id, customerID, drugID, amount, given);
            orders.add(o);
        }
        ps.close();
        rs.close();
        return orders;
    }

    @Override
    public List<TableItem> getByParameters(String condition) throws SQLException {
        List<TableItem> orders = new LinkedList<>();
        String sql = "SELECT * FROM ORDERS WHERE 1 = 1 AND " + condition;
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int customerID = rs.getInt("customer_id");
            int drugID = rs.getInt("drug_id");
            int amount = rs.getInt("amount");
            boolean given = rs.getInt("given") == 1;
            Order o = new Order(id, customerID, drugID, amount, given);
            orders.add(o);
        }
        ps.close();
        rs.close();
        return orders;
    }

    public Order getByID(int id) throws SQLException {
        Order order = null;
        String sql = "SELECT * FROM ORDERS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerID = rs.getInt("customer_id");
            int drugID = rs.getInt("drug_id");
            int amount = rs.getInt("amount");
            boolean given = rs.getInt("given") == 1;
            order = new Order(id, customerID, drugID, amount, given);
        }
        ps.close();
        rs.close();
        return order;
    }

    public List<TableItem> getByCustomerID(int customerID) throws SQLException {
        List<TableItem> orders = new LinkedList<>();
        String sql = "SELECT * FROM ORDERS WHERE customer_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int drugID = rs.getInt("drug_id");
            int amount = rs.getInt("amount");
            boolean given = rs.getInt("given") == 1;
            Order o = new Order(id, customerID, drugID, amount, given);
            orders.add(o);
        }
        ps.close();
        rs.close();
        return orders;
    }

    public void setGiven(int orderID) throws SQLException {
        Order order = getByID(orderID);
        order.setGiven();
        update(order);
    }
}