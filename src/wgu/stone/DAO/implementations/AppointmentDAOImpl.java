package wgu.stone.DAO.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.DAO.interfaces.AppointmentDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Appointment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public ObservableList<Appointment> getAll() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";

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
                appointment.setCreatedDatetime(rs.getTimestamp("Create_Date").toLocalDateTime());
                appointment.setLastUpdate(rs.getTimestamp("Last_Update").toLocalDateTime());
                appointment.setCustomerId(rs.getInt("Customer_ID"));
                appointment.setUserId(rs.getInt("User_ID"));
                appointment.setContactId(rs.getInt("Contact_ID"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public void delete(Appointment appointment) {

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, appointment.getAppId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Appointment appointment) {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, `Type` = ?, `Start` = ?, " +
                "`End` = ?, Last_Updated_By = ?, Last_Update = ?, Contact_ID = ?, User_ID = ? WHERE Appointment_ID = ?";

        try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {

            preparedStatement.setInt(11, appointment.getAppId());
            preparedStatement.setString(1, appointment.getAppTitle());
            preparedStatement.setString(2, appointment.getAppDescription());
            preparedStatement.setString(3, appointment.getAppLocation());
            preparedStatement.setString(4, appointment.getAppType());
            preparedStatement.setObject(5, appointment.getStartDatetime());
            preparedStatement.setObject(6, appointment.getEndDatetime());
            preparedStatement.setString(7, appointment.getLastUpdateBy());
            preparedStatement.setObject(8, appointment.getLastUpdate());
            preparedStatement.setInt(9, appointment.getContactId());
            preparedStatement.setInt(10, appointment.getUserId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void save(Appointment appointment) {

        String sql = "INSERT INTO appointments(Title, Description, Location, `Type`, `Start`, `End`, Created_By, " +
                "Last_Updated_By, Customer_ID, Contact_ID, User_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, appointment.getAppTitle());
            ps.setString(2, appointment.getAppDescription());
            ps.setString(3, appointment.getAppLocation());
            ps.setString(4, appointment.getAppType());
            ps.setString(5, appointment.getStartDatetime());
            ps.setString(6, appointment.getEndDatetime());
            ps.setString(7, appointment.getCreatedBy());
            ps.setString(8, appointment.getLastUpdateBy());
            ps.setInt(9, appointment.getCustomerId());
            ps.setInt(10, appointment.getContactId());
            ps.setInt(11, appointment.getUserId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
