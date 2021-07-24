package wgu.stone.DAO;

import javafx.collections.ObservableList;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;

public interface AppointmentDAO {

    void insertNewAppointment(Appointment appointment);
    ObservableList<Appointment> getAllAppointments();
    ObservableList<Contact> getAllContacts();
}
