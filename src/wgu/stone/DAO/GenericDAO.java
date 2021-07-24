package wgu.stone.DAO;

import javafx.collections.ObservableList;

public interface GenericDAO<T> {

    ObservableList<T> getAll();
    void delete(T t);
    void save(T t);
    void update(T t);
}
