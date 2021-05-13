package ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities;

import java.util.*;

public class User extends TableItem {
    private final int id;
    private final String login;
    private final int roleID;

    public User(int id, String login, int roleID) {
        this.id = id;
        this.login = login;
        this.roleID = roleID;
    }

    public User(String login, int roleID) {
        this(-1, login, roleID);
    }

    public int getID() { return id; }
    public String getLogin() { return login; }
    public int getRoleID() { return roleID; }

    @Override
    public Map<String, Object> getValues() {
        if (values == null) {
            values = new HashMap<>();
            values.put("id", id);
            values.put("login", login);
            values.put("role_id", roleID);
        }
        return values;
    }

    @Override
    public String toString() {
        return "User{id = " + id + ", login = '" + login
                + "', roleID=" + roleID + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id
                && roleID == user.roleID
                && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, roleID);
    }
}