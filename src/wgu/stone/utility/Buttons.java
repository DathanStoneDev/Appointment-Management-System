package wgu.stone.utility;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Holds the methods that are associated with going to the Main Dashboard or exiting the app.
 * These are the two common actions associated with each view.
 */
public final class Buttons {

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private Buttons() {

    }

    public static void toMainDashboard(Button button) throws IOException {
        Parent mainDash = FXMLLoader.load(Buttons.class.getResource("/wgu/stone/view/MainDashboard.fxml"));
        Scene mainDashScene = new Scene(mainDash);
        Stage window = (Stage) button.getScene().getWindow();
        window.setScene(mainDashScene);
        window.show();
    }

    public static void exitApplication(Button button) {
        Stage window = (Stage) button.getScene().getWindow();
        window.close();
    }
}
