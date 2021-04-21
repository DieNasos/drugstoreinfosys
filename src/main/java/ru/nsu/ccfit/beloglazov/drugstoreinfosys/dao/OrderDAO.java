package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.Order;
import ru.nsu.ccfit.beloglazov.drugstoreinfosys.interfaces.DAO;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderDAO implements DAO<Order> {
    private final Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Order item) throws SQLException {
        String sql = "INSERT INTO ORDERS (id, customer_name, phone_number, address, drug_id, amount) VALUES (S_ORDERS.nextval, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getCustomerName());
        ps.setString(2, item.getPhoneNumber());
        ps.setString(3, item.getAddress());
        ps.setInt(4, item.getDrugID());
        ps.setInt(5, item.getAmount());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(Order item) throws SQLException {
        String sql = "UPDATE ORDERS SET customer_name = ?, phone_number = ?, address = ?, drug_id = ?, amount = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getCustomerName());
        ps.setString(2, item.getPhoneNumber());
        ps.setString(3, item.getAddress());
        ps.setInt(4, item.getDrugID());
        ps.setInt(5, item.getAmount());
        ps.setInt(6, item.getID());
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
    public List<Order> getAll() throws SQLException {
        List<Order> orders = new LinkedList<>();
        String sql = "SELECT * FROM ORDERS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String customerName = rs.getString("customer_name");
            String phoneNumber = rs.getString("phone_number");
            String address = rs.getString("address");
            int drugID = rs.getInt("drug_id");
            int amount = rs.getInt("amount");
            Order o = new Order(id, customerName, phoneNumber, address, drugID, amount);
            orders.add(o);
        }
        ps.close();
        rs.close();
        return orders;
    }

    @Override
    public void resetSequence() throws SQLException {
        String sql1 = "DROP SEQUENCE S_ORDERS";
        PreparedStatement ps = connection.prepareStatement(sql1);
        ps.executeUpdate();
        String sql2 = "CREATE SEQUENCE S_ORDERS START WITH 1 INCREMENT BY 1 NOMAXVALUE";
        ps = connection.prepareStatement(sql2);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public Order findByID(int id) throws SQLException {
        Order order = null;
        String sql = "SELECT * FROM ORDERS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int orderID = rs.getInt("id");
            String customerName = rs.getString("customer_name");
            String phoneNumber = rs.getString("phone_number");
            String address = rs.getString("address");
            int drugID = rs.getInt("drug_id");
            int amount = rs.getInt("amount");
            order = new Order(id, customerName, phoneNumber, address, drugID, amount);
        }
        ps.close();
        rs.close();
        return order;
    }
}