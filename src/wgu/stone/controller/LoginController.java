package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wgu.stone.dao.databaseConnection.Login;
import wgu.stone.utility.Buttons;
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
    @FXML
    private void loginToApp() throws IOException {

        //Gets the login time.
        DateTimeFormatter d = DateTimeFormatter.ISO_DATE_TIME;
        String loginTime = d.format(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        //initializes userName and userPassword.
        String userName = userNameField.getText();
        String userPassword = passwordField.getText();

        //creates the login_activity.txt file to write login attempts.
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        //Checks for valid userName and userPassword and logs the attempt.
        if(login.checkUserInfo(userName, userPassword)) {
            loggedInUser = login.getUserId(userName);
            bufferedWriter.write("Time: " + loginTime + " - Success");
            bufferedWriter.newLine();
            bufferedWriter.close();

            Buttons.toMainDashboard(loginButton);
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
    private void determineUserLocation() {
        locationLabel.setText(ZoneId.systemDefault().toString());
    }

    /**
     * Determines the language of the user's computer and sets it for the form.
     */
    private void determineUserLanguage() {
        usernameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        titleLabel.setText(rb.getString("titleLabel"));
        loginButton.setText(rb.getString("loginButton"));
    }

    /**
     * Exits the application.
     */
    @FXML
    private void exitApplication() {
        Buttons.exitApplication(exitAppButton);
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
