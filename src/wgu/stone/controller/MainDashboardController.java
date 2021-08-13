package wgu.stone.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.model.Appointment;
import wgu.stone.utility.DateTimeFormatterUtility;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * User Interface that is presented to the user upon successful login.
 * Allows the user to navigate to the Report, Customers, or Appointment forms.
 */
public class MainDashboardController implements Initializable {

    //Buttons on the dashboard.
    @FXML Button reportFormButton;
    @FXML Button mainAppointmentFormButton;
    @FXML Button customerMainFormButton;

    //Instance of the appointmentDAO.
    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    /**
     * Goes to the ReportForm.
     * @throws IOException
     */
    @FXML
    private void goToReportForm() throws IOException {
        Parent report = FXMLLoader.load(getClass().getResource("/wgu/stone/view/ReportsForm.fxml"));
        Scene reportScene = new Scene(report);
        Stage window = (Stage) reportFormButton.getScene().getWindow();
        window.setScene(reportScene);
        window.show();

    }

    /**
     * Goes to the MainAppointmentForm
     * @throws IOException
     */
    @FXML
    private void goToMainAppointmentForm() throws IOException {
        Parent mainApp = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AppointmentMainForm.fxml"));
        Scene mainAppScene = new Scene(mainApp);
        Stage window = (Stage) mainAppointmentFormButton.getScene().getWindow();
        window.setScene(mainAppScene);
        window.show();

    }

    /**
     * Goes to the CustomerMainForm.
     * @throws IOException
     */
    @FXML
    private void goToCustomerMainForm() throws IOException {
        Parent customer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene customerScene = new Scene(customer);
        Stage window = (Stage) customerMainFormButton.getScene().getWindow();
        window.setScene(customerScene);
        window.show();
    }

    /**
     * Provides an alert once the Dashboard appears to show a user if an appointment is within 15 minutes or not.
     * If an appointment is upcoming, a description of the appointment is shown.
     */
    private void alertForUpcomingAppointments() {

        ObservableList<Appointment> appointmentsIn15Minutes = appointmentDAO.getAppointmentsOnLogin();
        String app = "";

        if(!appointmentsIn15Minutes.isEmpty()) {
            for(Appointment a : appointmentsIn15Minutes) {
                int id = a.getAppId();
                String start = DateTimeFormatterUtility.formatDateTimeForTableview(a.getStartDatetime());
                String end = DateTimeFormatterUtility.formatDateTimeForTableview(a.getEndDatetime());

                app = "App ID: " + id + " Start: " + start + " End: " + end;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(app);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertForUpcomingAppointments();
    }
}
