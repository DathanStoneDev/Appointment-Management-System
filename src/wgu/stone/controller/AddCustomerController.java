package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import wgu.stone.DAO.CustomerDAOImpl;
import wgu.stone.DAO.UserDAOImpl;
import wgu.stone.model.Customer;

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


    /**
     * Adds a new customer to the database.
     */
    public void addNewCustomer() {

        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        String postalCode = customerPostalField.getText();
        String customerPhoneNumber = customerPhoneNumberField.getText();
        String loggedInUser = UserDAOImpl.loggedInUser;
        String lastUpdatedBy = UserDAOImpl.loggedInUser;

        Customer customer = new Customer(customerName, customerAddress, postalCode, customerPhoneNumber,
                loggedInUser, lastUpdatedBy, 1);

        CustomerDAOImpl.insertNewCustomer(customer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
