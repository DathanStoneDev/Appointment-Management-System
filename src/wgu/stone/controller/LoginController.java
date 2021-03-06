package wgu.stone.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wgu.stone.dao.databaseConnection.Login;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.model.Appointment;
import wgu.stone.utility.Buttons;
import wgu.stone.utility.DateTimeFormatterUtility;
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

/**
 * A controller for logins.
 */
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

    //Instance of the appointmentDAO.
    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    //Resource bundle that gets the locale of a user.
    private ResourceBundle rb = ResourceBundle.getBundle("Resources/Nat", Locale.getDefault());

    //Logged in user.
    protected static int loggedInUser;
    protected static String loggedInUserName;



    /**
     * Logs into the application. Linked to the loginButton and
     * Logs all attempts into a login_activity.txt file.
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
        String enteredUsername = userNameField.getText();

        //Checks for valid userName and userPassword and logs the attempt.
        if(login.checkUserInfo(userName, userPassword)) {
            loggedInUser = login.getUserId(userName);
            loggedInUserName = enteredUsername;
            bufferedWriter.write("Username: " + enteredUsername + " Time: " + loginTime + " - Success");
            bufferedWriter.newLine();
            bufferedWriter.close();

            alertForUpcomingAppointments();
            Buttons.toMainDashboard(loginButton);
        } else {
            bufferedWriter.write("Username: " + enteredUsername + "Time: " + loginTime + " - Unsuccessful");
            bufferedWriter.newLine();
            bufferedWriter.close();
            loginErrorLabel.setText(rb.getString("loginErrorLabel"));
        }
    }


    /**
     * Sets the location of the user and displays it on the locationLabel.
     */
    private void determineUserLocation() {
        locationLabel.setText(ZoneId.systemDefault().toString());
    }

    /**
     * Sets the language of the user's computer and sets it for the login form.
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
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No Appointments coming up!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        determineUserLocation();
        determineUserLanguage();
    }
}
