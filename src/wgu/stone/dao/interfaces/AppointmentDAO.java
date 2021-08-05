package wgu.stone.DAO.interfaces;

import javafx.collections.ObservableList;
import wgu.stone.model.Appointment;

public interface AppointmentDAO {
    ObservableList<Appointment> getAppointmentsList();
    void deleteAppointment(int id);
    void updateAppointment(Appointment appointment);
    void saveAppointment(Appointment appointment);
    ObservableList<String> getContactsList();
    ObservableList<Appointment> getContactScheduleList();
}
