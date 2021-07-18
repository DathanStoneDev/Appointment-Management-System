package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wgu.stone.DAO.UserDAOImpl;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    //fields for user name and password.
    @FXML private TextField userNameField;
    @FXML private PasswordField passwordField;

    //button for logging in.
    @FXML private Button loginButton;

    //logging into the application. Linked to loginButton


    @FXML
    public void loginToApp() {
            String userName = userNameField.getText();
            String userPassword = passwordField.getText();
            UserDAOImpl.checkUserInfo(userName, userPassword);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
