package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A controller for the Main Dashboard.
 */
public class MainDashboardController implements Initializable {

    //Buttons on the dashboard.
    @FXML Button reportFormButton;
    @FXML Button mainAppointmentFormButton;
    @FXML Button customerMainFormButton;

    /**
     * Goes to the ReportForm.
     * @throws IOException Throws exception if ReportsForm cannot be retrieved.
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
     * Goes to the AppointmentMainForm
     * @throws IOException Throws exception if AppointmentMainForm cannot be retrieved.
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
     * @throws IOException Throws exception if CustomerMainForm cannot be retrieved.
     */
    @FXML
    private void goToCustomerMainForm() throws IOException {
        Parent customer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene customerScene = new Scene(customer);
        Stage window = (Stage) customerMainFormButton.getScene().getWindow();
        window.setScene(customerScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
