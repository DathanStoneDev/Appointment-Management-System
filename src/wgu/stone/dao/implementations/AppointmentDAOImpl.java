package wgu.stone.dao.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Appointment;

import java.sql.*;


public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public ObservableList<Appointment> getAppointmentsList() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, " +
                "a.End, a.Customer_ID, a.User_ID " +
                "FROM appointments a " +
                "JOIN contacts c ON c.Contact_ID = a.Contact_ID";

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
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
                appointment.setUserId(rs.getInt("User_ID"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public void deleteAppointment(int id) {

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateAppointment(Appointment appointment) {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, `Type` = ?, `Start` = ?, " +
                "`End` = ?, Contact_ID = ?, User_ID = ? WHERE Appointment_ID = ?";

        try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {

            preparedStatement.setInt(9, appointment.getAppId());
            preparedStatement.setString(1, appointment.getAppTitle());
            preparedStatement.setString(2, appointment.getAppDescription());
            preparedStatement.setString(3, appointment.getAppLocation());
            preparedStatement.setString(4, appointment.getAppType());
            preparedStatement.setString(5, appointment.getStartDatetime());
            preparedStatement.setString(6, appointment.getEndDatetime());
            preparedStatement.setInt(7, appointment.getContactId());
            preparedStatement.setInt(8, appointment.getUserId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveAppointment(Appointment appointment) {

        String sql = "INSERT INTO appointments(Title, Description, Location, `Type`, `Start`, `End`, " +
                "Customer_ID, Contact_ID, User_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, appointment.getAppTitle());
            ps.setString(2, appointment.getAppDescription());
            ps.setString(3, appointment.getAppLocation());
            ps.setString(4, appointment.getAppType());
            ps.setString(5, appointment.getStartDatetime().toString());
            ps.setString(6, appointment.getEndDatetime().toString());
            ps.setInt(7, appointment.getCustomerId());
            ps.setInt(8, appointment.getContactId());
            ps.setInt(9, appointment.getUserId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<String> getContactsList() {

        String sql = "SELECT Contact_Name FROM contacts";
        ObservableList<String> contactsList = FXCollections.observableArrayList();

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                String name = rs.getString("Contact_Name");
                contactsList.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsList;
    }
}
