package wgu.stone.DAO.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Report {

    public ObservableList<Appointment> getContactSchedule(String contactName) {
        ObservableList<Appointment> contactScheduleList = FXCollections.observableArrayList();
        String sql = "SELECT c.Contact_Name, a.Appointment_ID, a.Title, a.Type, a.Description, a.Start, a.End, a.Customer_ID " +
                "FROM appointments a " +
                "JOIN contacts c " +
                "ON c.Contact_ID = a.Contact_ID " +
                "WHERE c.Contact_Name = ?";

        try(PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = p.executeQuery()) {
            while(rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppContact(rs.getString("Contact_Name"));
                appointment.setAppId(rs.getInt("Appointment_ID"));
                appointment.setAppTitle(rs.getString("Title"));
                appointment.setAppType(rs.getString("Type"));
                appointment.setAppDescription(rs.getString("Description"));
                appointment.setStartDatetime(rs.getString("Start"));
                appointment.setEndDatetime(rs.getString("End"));
                appointment.setCustomerId(rs.getInt("Customer_ID"));
                contactScheduleList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactScheduleList;
    }
}
