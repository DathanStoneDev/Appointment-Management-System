package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import wgu.stone.DAO.CustomerDAOImpl;
import wgu.stone.DAO.UserDAOImpl;
import wgu.stone.model.Customer;
import wgu.stone.model.FirstLevelDivisions;

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

    @FXML private ComboBox<FirstLevelDivisions> divisionCombo;

    /**
     * Adds a new customer to the database when hitting the save button.
     */
    public void addNewCustomer() {

        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        String postalCode = customerPostalField.getText();
        String customerPhoneNumber = customerPhoneNumberField.getText();
        String loggedInUser = UserDAOImpl.loggedInUser;
        String lastUpdatedBy = UserDAOImpl.loggedInUser;
        int divisionId = divisionCombo.getSelectionModel().getSelectedItem().getDivisionId();

        Customer customer = new Customer(customerName, customerAddress, postalCode, customerPhoneNumber,
                loggedInUser, lastUpdatedBy, divisionId);

        CustomerDAOImpl.insertNewCustomer(customer);
    }

    public void initDivisionComboBox() {
        try {
            CustomerDAOImpl.populateDivisionList();
            divisionCombo.setItems(FirstLevelDivisions.getDivisionList());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDivisionComboBox();
    }
}
