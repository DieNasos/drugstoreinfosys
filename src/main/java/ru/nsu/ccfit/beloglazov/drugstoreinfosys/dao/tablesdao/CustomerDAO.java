package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao.tablesdao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerDAO implements TableDAO<Customer> {
    private final Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Customer item) throws SQLException {
        String sql = "INSERT INTO CSTMRS (id, login, name, phone_number, address) VALUES (S_TCHNLGS.nextval, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getLogin());
        ps.setString(2, item.getName());
        ps.setString(3, item.getPhoneNumber());
        ps.setString(4, item.getAddress());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(Customer item) throws SQLException {
        String sql = "UPDATE CSTMRS SET login = ?, name = ?, phone_number = ?, address = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getLogin());
        ps.setString(2, item.getName());
        ps.setString(3, item.getPhoneNumber());
        ps.setString(4, item.getAddress());
        ps.setInt(5, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM CSTMRS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<TableItem> getAll() throws SQLException {
        List<TableItem> customers = new LinkedList<>();
        String sql = "SELECT * FROM CSTMRS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String login = rs.getString("login");
            String name = rs.getString("name");
            String phoneNumber = rs.getString("phone_number");
            String address = rs.getString("address");
            Customer c = new Customer(id, login, name, phoneNumber, address);
            customers.add(c);
        }
        ps.close();
        rs.close();
        return customers;
    }

    @Override
    public List<TableItem> getByParameters(String condition) throws SQLException {
        List<TableItem> customers = new LinkedList<>();
        String sql = "SELECT * FROM CSTMRS WHERE 1 = 1 AND " + condition;
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String login = rs.getString("login");
            String name = rs.getString("name");
            String phoneNumber = rs.getString("phone_number");
            String address = rs.getString("address");
            Customer c = new Customer(id, login, name, phoneNumber, address);
            customers.add(c);
        }
        ps.close();
        rs.close();
        return customers;
    }

    public Customer getByLogin(String login) throws SQLException {
        Customer customer = null;
        String sql = "SELECT * FROM CSTMRS WHERE login = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String phoneNumber = rs.getString("phone_number");
            String address = rs.getString("address");
            customer = new Customer(id, login, name, phoneNumber, address);
        }
        ps.close();
        rs.close();
        return customer;
    }

    public Customer getByID(int id) throws SQLException {
        Customer customer = null;
        String sql = "SELECT * FROM CSTMRS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String login = rs.getString("login");
            String name = rs.getString("name");
            String phoneNumber = rs.getString("phone_number");
            String address = rs.getString("address");
            customer = new Customer(id, login, name, phoneNumber, address);
        }
        ps.close();
        rs.close();
        return customer;
    }
}