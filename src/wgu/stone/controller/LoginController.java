package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wgu.stone.DAO.UserDAOImpl;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    //fields for user name and password.
    @FXML private TextField userNameField;
    @FXML private PasswordField passwordField;

    //button for logging in.
    @FXML private Button loginButton;

    //label for user's location
    @FXML private Label locationLabel;

    /**
     * Logging into the application. Linked to the loginButton.
     */
    public void loginToApp() {
            String userName = userNameField.getText();
            String userPassword = passwordField.getText();
            UserDAOImpl.checkUserInfo(userName, userPassword);
    }

    /**
     * Determines the location of the user and displays it on the locationLabel.
     */
    public void determineUserLocation() {
        locationLabel.setText(ZoneId.systemDefault().toString());
    }

    /**
     * Determines the language of the user's computer and sets it for the form.
     */
    public void determineUserLanguage() {

    }

    /**
     * Initializes starting data on the form.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        determineUserLocation();
        determineUserLanguage();
    }
}
