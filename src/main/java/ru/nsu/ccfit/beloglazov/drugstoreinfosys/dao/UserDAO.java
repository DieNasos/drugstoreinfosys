package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDAO implements DAO<User> {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(User item) throws SQLException {
        String sql = "INSERT INTO USERS (id, login, role_id) VALUES (S_TCHNLGS.nextval, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getLogin());
        ps.setInt(2, item.getRoleID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void update(User item) throws SQLException {
        String sql = "UPDATE USERS SET login = ?, role_id = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, item.getLogin());
        ps.setInt(2, item.getRoleID());
        ps.setInt(3, item.getID());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM USERS WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    @Override
    public List<TableItem> getAll() throws SQLException {
        List<TableItem> users = new LinkedList<>();
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String login = rs.getString("login");
            int roleID = rs.getInt("role_id");
            User u = new User(id, login, roleID);
            users.add(u);
        }
        ps.close();
        rs.close();
        return users;
    }

    @Override
    public List<TableItem> getByParameters(String condition) throws SQLException {
        List<TableItem> users = new LinkedList<>();
        String sql = "SELECT * FROM USERS WHERE 1 = 1 AND " + condition;
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String login = rs.getString("login");
            int roleID = rs.getInt("role_id");
            User u = new User(id, login, roleID);
            users.add(u);
        }
        ps.close();
        rs.close();
        return users;
    }

    public User getByLogin(String login) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM USERS WHERE login = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int roleID = rs.getInt("role_id");
            user = new User(id, login, roleID);
        }
        ps.close();
        rs.close();
        return user;
    }
}