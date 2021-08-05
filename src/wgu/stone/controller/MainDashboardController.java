package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainDashboardController {

    @FXML Button reportFormButton;

    @FXML
    private void goToReportForm() throws IOException {
        Parent report = FXMLLoader.load(getClass().getResource("/wgu/stone/view/ReportsForm.fxml"));
        Scene reportScene = new Scene(report);
        Stage window = (Stage) reportFormButton.getScene().getWindow();
        window.setScene(reportScene);
        window.show();

    }
}
