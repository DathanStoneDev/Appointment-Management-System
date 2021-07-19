package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wgu.stone.DAO.UserDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    //fields for username and password.
    @FXML private TextField userNameField;
    @FXML private PasswordField passwordField;

    //button for logging in.
    @FXML private Button loginButton;
    @FXML private Button exitAppButton;

    //labels for the login screen
    @FXML private Label locationLabel;
    @FXML private Label loginErrorLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label titleLabel;

    //Resource bundle that gets the locale of a user.
    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

    /**
     * Logging into the application. Linked to the loginButton.
     */
    public void loginToApp() throws IOException {
        String userName = userNameField.getText();
        String userPassword = passwordField.getText();
        if(UserDAOImpl.checkUserInfo(userName, userPassword)) {
            Parent addProduct = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
            Scene addProductScene = new Scene(addProduct);
            Stage window = (Stage) loginButton.getScene().getWindow();
            window.setScene(addProductScene);
            window.show();
        } else {
            loginErrorLabel.setText(rb.getString("loginErrorLabel"));
        }
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
        usernameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        titleLabel.setText(rb.getString("titleLabel"));
        loginButton.setText(rb.getString("loginButton"));
    }

    public void exitApplication() {
        Stage window = (Stage) exitAppButton.getScene().getWindow();
        window.close();
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
