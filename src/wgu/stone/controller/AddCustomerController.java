package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wgu.stone.DAO.CustomerDAO;
import wgu.stone.DAO.CustomerDAOImpl;
import wgu.stone.DAO.UserDAOImpl;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.FirstLevelDivisions;
import java.io.IOException;
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

    //ComboBoxes in the AddCustomerForm.
    @FXML private ComboBox<FirstLevelDivisions> divisionCombo;
    @FXML private ComboBox<Country> countryCombo;

    private CustomerDAO customerDAO = new CustomerDAOImpl();

    /**
     * Adds a new customer to the database when hitting the save button.
     */
    public void addNewCustomer() throws IOException {

        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        String postalCode = customerPostalField.getText();
        String customerPhoneNumber = customerPhoneNumberField.getText();
        String loggedInUser = LoginController.loggedIn;
        String lastUpdatedBy = LoginController.loggedIn;
        int divisionId = divisionCombo.getSelectionModel().getSelectedItem().getDivisionId();
        String divisionName = divisionCombo.getSelectionModel().getSelectedItem().getDivisionName();
        String countryName = countryCombo.getSelectionModel().getSelectedItem().getCountry();

        Customer customer = new Customer(customerName, customerAddress, postalCode, customerPhoneNumber,
                loggedInUser, lastUpdatedBy, divisionId, countryName, divisionName);

        customerDAO.insertNewCustomer(customer);


        //make this a Util method.
        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) saveNewCustomerButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    /**
     * Sets the divisionComboBox selection to the generated list from the filterDivisionList method.
     * The selection is based on the country that is selected in the country combobox.
     */
    public void setDivisionCombo() {
        customerDAO.filterDivisionList(countryCombo.getSelectionModel().getSelectedItem().toString());
        divisionCombo.setItems(FirstLevelDivisions.getDivisions());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerDAO.getAllCountries();
        countryCombo.setItems(Country.getCountries());
    }
}
