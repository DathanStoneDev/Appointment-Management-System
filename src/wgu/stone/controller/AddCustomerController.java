package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import wgu.stone.dao.implementations.CustomerDAOImpl;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;
import wgu.stone.utility.Buttons;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A controller for adding customers.
 */
public class AddCustomerController implements Initializable {

    //TextFields.
    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPostalField;
    @FXML private TextField customerPhoneNumberField;

    //Buttons.
    @FXML private Button saveNewCustomerButton;
    @FXML private Button exitAppButton;
    @FXML private Button cancelButton;

    //ComboBoxes.
    @FXML private ComboBox<Division> divisionCombo;
    @FXML private ComboBox<Country> countryCombo;

    //CustomerDAO Interface to call methods.
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    //List of division objects for the division ComboBox.
    private ObservableList<Division> divList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(customerDAO.getCountryList());
        divList = customerDAO.getDivisionList();
    }

    /**
     * Adds a new customer.
     * @throws IOException Throws exception if the MainCustomerForm cannot be retrieved.
     */
    @FXML
    private void addNewCustomer() throws IOException {
        try {

            String customerName = customerNameField.getText();
            String customerAddress = customerAddressField.getText();
            String customerPostalCode = customerPostalField.getText();
            String customerPhoneNumber = customerPhoneNumberField.getText();
            String countryName = countryCombo.getSelectionModel().getSelectedItem().getCountryName();
            String divisionName = divisionCombo.getSelectionModel().getSelectedItem().getDivName();
            int divisionId = divisionCombo.getSelectionModel().getSelectedItem().getDivId();

            Customer customer = new Customer(customerName, customerAddress, customerPostalCode, customerPhoneNumber,
                    countryName, divisionName, divisionId);

            customerDAO.saveCustomer(customer);

            Buttons.toMainCustomerForm(saveNewCustomerButton);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Fields");
            alert.setContentText("Please ensure fields are not blank and ComboBoxes have a selected item");
            alert.show();
            e.printStackTrace();
            return;
        }

    }

    /**
     * -LAMBDA- Filters the <code>divisionCombo</code> for divisions based on the selection of the country from the <code>countryCombo</code>.
     */
    @FXML
    private void setDivisionCombo() {

        int selection = countryCombo.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<Division> filtered = divList.filtered(d -> d.getCountryID() == selection);
        divisionCombo.setItems(filtered);
    }

    /**
     * Exits the application.
     */
    @FXML
    private void exitApp() {
        Buttons.exitApplication(exitAppButton);
    }

    /**
     * Goes back to the MainCustomerForm if the cancel button is clicked.
     * @throws IOException Throws exception if the MainCustomerForm cannot be retrieved.
     */
    @FXML
    private void backToMainCustomerForm() throws IOException {
        Buttons.toMainCustomerForm(cancelButton);
    }
}
