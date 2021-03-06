package wgu.stone.dao.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.dao.databaseConnection.DatabaseConnection;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;
import wgu.stone.utility.DateTimeFormatterUtility;
import java.sql.*;

public class AppointmentDAOImpl implements AppointmentDAO {

    /**
     * Retrieves a list of Appointment Objects.
     * @return Returns an ObservableList of appointment objects.
     */
    @Override
    public ObservableList<Appointment> getAppointmentsList() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, " +
                "a.End, a.Customer_ID, a.User_ID, a.Contact_ID " +
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
                appointment.setStartDatetime(DateTimeFormatterUtility.formatDateTimeFromDatabase(rs.getTimestamp("Start")));
                appointment.setEndDatetime(DateTimeFormatterUtility.formatDateTimeFromDatabase(rs.getTimestamp("End")));
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

    /**
     * Deletes an appointment.
     * @param id Parameter used to select the Appointment to delete.
     */
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

    /**
     * Updates an appointment.
     * @param appointment Parameter used to provide the appointment object to update.
     */
    @Override
    public void updateAppointment(Appointment appointment) {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, `Type` = ?, `Start` = ?, " +
                "`End` = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

        try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {

            preparedStatement.setInt(9, appointment.getAppId());
            preparedStatement.setString(1, appointment.getAppTitle());
            preparedStatement.setString(2, appointment.getAppDescription());
            preparedStatement.setString(3, appointment.getAppLocation());
            preparedStatement.setString(4, appointment.getAppType());
            preparedStatement.setString(5, DateTimeFormatterUtility.formatLocalDateTimeUTCForDatabase(appointment.getStartDatetime()));
            preparedStatement.setString(6, DateTimeFormatterUtility.formatLocalDateTimeUTCForDatabase(appointment.getEndDatetime()));
            preparedStatement.setInt(7, appointment.getUserId());
            preparedStatement.setInt(8, appointment.getContactId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Saves a new appointment.
     * @param appointment Parameter used to provide the appointment object to save.
     */
    @Override
    public void saveAppointment(Appointment appointment) {

        String sql = "INSERT INTO appointments(Title, Description, Location, `Type`, `Start`, `End`, " +
                "Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, appointment.getAppTitle());
            ps.setString(2, appointment.getAppDescription());
            ps.setString(3, appointment.getAppLocation());
            ps.setString(4, appointment.getAppType());
            ps.setString(5, DateTimeFormatterUtility.formatLocalDateTimeUTCForDatabase(appointment.getStartDatetime()));
            ps.setString(6, DateTimeFormatterUtility.formatLocalDateTimeUTCForDatabase(appointment.getEndDatetime()));
            ps.setInt(7, appointment.getCustomerId());
            ps.setInt(8, appointment.getUserId());
            ps.setInt(9, appointment.getContactId());
            System.out.println(appointment.getStartDatetime());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of contact objects.
     * @return Returns an observableList of contact objects.
     */
    @Override
    public ObservableList<Contact> getContactsList() {

        String sql = "SELECT Contact_ID, Contact_Name FROM contacts";
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                Contact contact = new Contact();
                contact.setContactId(rs.getInt("Contact_ID"));
                contact.setContactName(rs.getString("Contact_Name"));
                contactsList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsList;
    }

    /**
     * Retrieves a list of Appointment Objects specifically for the Contact Appointments Report.
     * @return Returns an ObservableList of appointment objects.
     */
    @Override
    public ObservableList<Appointment> getContactScheduleList() {

        ObservableList<Appointment> contactScheduleList = FXCollections.observableArrayList();

        String sql = "SELECT c.Contact_Name, a.Location, a.Appointment_ID, a.Title, a.Type, a.Description, a.Start, a.End, a.Customer_ID " +
                "FROM appointments a " +
                "JOIN contacts c " +
                "ON c.Contact_ID = a.Contact_ID";

        try(Statement s = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = s.executeQuery(sql)) {
            while(rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppContact(rs.getString("Contact_Name"));
                appointment.setAppId(rs.getInt("Appointment_ID"));
                appointment.setAppTitle(rs.getString("Title"));
                appointment.setAppType(rs.getString("Type"));
                appointment.setAppDescription(rs.getString("Description"));
                appointment.setStartDatetime(DateTimeFormatterUtility.formatDateTimeFromDatabase(rs.getTimestamp("Start")));
                appointment.setEndDatetime(DateTimeFormatterUtility.formatDateTimeFromDatabase(rs.getTimestamp("End")));
                appointment.setCustomerId(rs.getInt("Customer_ID"));
                appointment.setAppLocation(rs.getString("Location"));
                contactScheduleList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactScheduleList;
    }

    /**
     * Returns an observable list of Strings that are composed of appointments grouped by month and type.
     * @return ObservableList of Strings.
     */
    public ObservableList<String> getAppsByMonthAndType() {

        ObservableList<String> reportStringList = FXCollections.observableArrayList();

        String sql = "SELECT `Type` as type, month(`Start`) as mstart, COUNT(Type) as ctype FROM appointments " +
                "GROUP BY `Type`, month(`Start`)";

        try(Statement s = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = s.executeQuery(sql)) {
            while(rs.next()) {
                String typeKey = rs.getString("type");
                int monthValue = rs.getInt("mstart");
                int count = rs.getInt("ctype");
                String reportString = "Type: " + typeKey + " Month: " + monthValue + " Count: " + count;
                reportStringList.add(reportString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportStringList;
    }

    /**
     * Gets all appointments within 15 minutes of login.
     * @return ObservableList of appointments.
     */
    public ObservableList<Appointment> getAppointmentsOnLogin() {

        ObservableList<Appointment> appointmentsWithin15Minutes = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, `Start`, `End` FROM appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 15 MINUTE)";

        try(Statement s = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = s.executeQuery(sql)) {
            while(rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppId(rs.getInt("Appointment_ID"));
                appointment.setStartDatetime(DateTimeFormatterUtility.formatDateTimeFromDatabase(rs.getTimestamp("Start")));
                appointment.setEndDatetime(DateTimeFormatterUtility.formatDateTimeFromDatabase(rs.getTimestamp("End")));
                appointmentsWithin15Minutes.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsWithin15Minutes;
    }
}
