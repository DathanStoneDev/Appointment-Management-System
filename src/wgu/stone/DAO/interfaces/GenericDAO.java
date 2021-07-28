package wgu.stone.DAO.interfaces;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface GenericDAO<T> {

    ObservableList<T> getAll();
    void delete(T t) throws SQLException;
    void save(T t);
    void update(T t);
}
