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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {

    @FXML Button reportFormButton;
    @FXML Button mainAppointmentFormButton;

    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    @FXML
    private void goToReportForm() throws IOException {
        Parent report = FXMLLoader.load(getClass().getResource("/wgu/stone/view/ReportsForm.fxml"));
        Scene reportScene = new Scene(report);
        Stage window = (Stage) reportFormButton.getScene().getWindow();
        window.setScene(reportScene);
        window.show();

    }

    @FXML
    private void goToMainAppointmentForm() throws IOException {
        Parent mainApp = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AppointmentMainForm.fxml"));
        Scene mainAppScene = new Scene(mainApp);
        Stage window = (Stage) mainAppointmentFormButton.getScene().getWindow();
        window.setScene(mainAppScene);
        window.show();

    }

    private void alertForUpcomingAppointments() {

        ObservableList<Appointment> appointmentsIn15Minutes = appointmentDAO.getAppointmentsOnLogin();
        String app = "";

        if(!appointmentsIn15Minutes.isEmpty()) {
            for(Appointment a : appointmentsIn15Minutes) {
                int id = a.getAppId();
                String start = a.getStartDatetime();
                String end = a.getEndDatetime();

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
