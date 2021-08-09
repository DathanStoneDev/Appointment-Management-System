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
import wgu.stone.dao.databaseConnection.Login;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;



public class LoginController implements Initializable {

    //TextFields.
    @FXML private TextField userNameField;
    @FXML private PasswordField passwordField;

    //Buttons.
    @FXML private Button loginButton;
    @FXML private Button exitAppButton;

    //labels.
    @FXML private Label locationLabel;
    @FXML private Label loginErrorLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label titleLabel;

    //Provides the login methods
    private Login login = new Login();

    //Resource bundle that gets the locale of a user.
    private ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

    //Logged in user.
    protected static int loggedInUser;



    /**
     * Logging into the application. Linked to the loginButton.
     * Logs all attempts.
     */
    public void loginToApp() throws IOException {
        DateTimeFormatter d = DateTimeFormatter.ISO_DATE_TIME;
        String loginTime = d.format(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        String userName = userNameField.getText();
        String userPassword = passwordField.getText();
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        if(login.checkUserInfo(userName, userPassword)) {
            loggedInUser = login.getUserId(userName);
            bufferedWriter.write("Time: " + loginTime + " - Success");
            bufferedWriter.newLine();
            bufferedWriter.close();

            Parent addProduct = FXMLLoader.load(getClass().getResource("/wgu/stone/view/MainDashboard.fxml"));
            Scene addProductScene = new Scene(addProduct);
            Stage window = (Stage) loginButton.getScene().getWindow();
            window.setScene(addProductScene);
            window.show();
        } else {
            bufferedWriter.write("Time: " + loginTime + " - Unsuccessful");
            bufferedWriter.newLine();
            bufferedWriter.close();
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

    /**
     * Exits the application.
     */
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
