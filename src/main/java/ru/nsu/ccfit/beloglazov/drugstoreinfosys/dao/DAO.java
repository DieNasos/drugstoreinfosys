package ru.nsu.ccfit.beloglazov.drugstoreinfosys.dao;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.entities.TableItem;
import java.sql.SQLException;
import java.util.List;

public interface DAO <T extends TableItem> {
    void add(T item) throws SQLException;
    void update(T item) throws SQLException;
    void delete(int id) throws SQLException;
    List<TableItem> getAll() throws SQLException;
    List<TableItem> getByParameters(String condition) throws SQLException;
}