package wgu.stone.dao.interfaces;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface GenericDAO<T> {

    ObservableList<T> getAll();
    void delete(int id) throws SQLException;
    void save(T t);
    void update(T t);
}
