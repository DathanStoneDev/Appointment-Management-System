package wgu.stone.dao.interfaces;

import javafx.collections.ObservableMap;
import wgu.stone.model.Appointment;

import java.sql.SQLException;

public interface AppointmentDAO extends GenericDAO<Appointment> {
    ObservableMap<Integer, String> getContactsMap() throws SQLException;
}
