package wgu.stone.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppointmentDAOImpl implements AppointmentDAO{


    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Customer_ID, " +
                " c.Contact_Name FROM appointments a JOIN contacts c ON c.Contact_ID = a.Contact_ID";

        try(Statement statement = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppId(rs.getInt("Appointment_ID"));
                appointment.setAppTitle(rs.getString("Title"));
                appointment.setAppDescription(rs.getString("Description"));
                appointment.setAppLocation(rs.getString("Location"));
                appointment.setAppContact(rs.getString("Contact_Name"));
                appointment.setAppType(rs.getString("Type"));
                appointment.setStartDatetime(rs.getTimestamp("Start").toLocalDateTime());
                appointment.setEndDatetime(rs.getTimestamp("End").toLocalDateTime());
                appointment.setCustomerId(rs.getInt("Customer_ID"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name, Contact_ID FROM contacts";

        try(Statement statement = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                Contact contact = new Contact();
                    contact.setContactId(rs.getInt("Contact_ID"));
                    contact.setContactName(rs.getString("Contact_Name"));
                    contacts.add(contact);
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public void insertNewAppointment(Appointment appointment) {
        System.out.println("Doing this insertNew Method:"  + appointment);
        String sql = "INSERT INTO appointments(Title, Description, Location, `Type`, `Start`, `End`, Created_By, " +
                "Last_Updated_By, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, appointment.getAppTitle());
            ps.setString(2, appointment.getAppDescription());
            ps.setString(3, appointment.getAppLocation());
            ps.setString(4, appointment.getAppType());
            ps.setString(5, appointment.getStartDatetime());
            ps.setString(6, appointment.getEndDatetime());
            ps.setString(7, appointment.getCreatedBy());
            ps.setString(8, appointment.getLastUpdateBy());
            ps.setInt(9, appointment.getContactId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
