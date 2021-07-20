package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    //TextFields on the Add customer form.
    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPostalField;
    @FXML private TextField customerPhoneNumberField;

    //Buttons on the Add customer form.
    @FXML private Button saveNewCustomerButton;
    @FXML private Button exitAppButton;
    @FXML private Button cancelButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
