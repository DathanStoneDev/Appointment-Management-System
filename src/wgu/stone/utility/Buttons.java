package wgu.stone.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Holds the methods that are associated with going to different views.
 *
 */
public final class Buttons {

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private Buttons() {

    }

    /**
     * Takes the user to the MainDashboard view.
     * @param button Assigned Button on the form for this method.
     * @throws IOException Throws exception if the MainDashboard cannot be retrieved.
     */
    public static void toMainDashboard(Button button) throws IOException {
        Parent mainDash = FXMLLoader.load(Buttons.class.getResource("/wgu/stone/view/MainDashboard.fxml"));
        Scene mainDashScene = new Scene(mainDash);
        Stage window = (Stage) button.getScene().getWindow();
        window.setScene(mainDashScene);
        window.show();
    }

    /**
     * Exits the application.
     * @param button Assigned Button on the form for this method.
     */
    public static void exitApplication(Button button) {
        Stage window = (Stage) button.getScene().getWindow();
        window.close();
    }

    /**
     * Takes the user to the MainCustomerForm view.
     * @param button Assigned Button on the form for this method.
     * @throws IOException Throws exception if the CustomerMainForm cannot be retrieved.
     */
    public static void toMainCustomerForm(Button button) throws IOException {
        Parent addCustomer = FXMLLoader.load(Buttons.class.getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) button.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    /**
     * Takes the user to the AppointmentMainForm view.
     * @param button Assigned Button on the form for this method.
     * @throws IOException Throws exception if the AppointmentMainForm cannot be retrieved.
     */
    public static void toMainAppointmentForm(Button button) throws IOException {
        Parent mainApp = FXMLLoader.load(Buttons.class.getResource("/wgu/stone/view/AppointmentMainForm.fxml"));
        Scene mainAppScene = new Scene(mainApp);
        Stage window = (Stage) button.getScene().getWindow();
        window.setScene(mainAppScene);
        window.show();
    }
}
