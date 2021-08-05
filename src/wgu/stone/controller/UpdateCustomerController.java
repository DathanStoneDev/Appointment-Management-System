package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wgu.stone.DAO.implementations.CustomerDAOImpl;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class responsible for updating customers.
 */
public class UpdateCustomerController implements Initializable {

    //TextFields.
    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPostalField;
    @FXML private TextField customerPhoneNumberField;
    @FXML private TextField customerIdField;

    //ComboBoxes.
    @FXML private ComboBox<Division> divisionCombo;
    @FXML private ComboBox<Country> countryCombo;

    //Buttons.
    @FXML private Button saveUpdatedCustomerButton;
    @FXML private Button cancelUpdateButton;

    //CustomerDAO Interface to call methods.
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    //Customer object that was passed from the CustomerMainForm.
    private Customer customerToUpdate;

    //Lists of country and division objects for the ComboBoxes.
    private ObservableList<Division> divList = FXCollections.observableArrayList();
    private ObservableList<Country> countryList = FXCollections.observableArrayList();

    /**
     * Initializes the Lists, Country ComboBox and disables the customerID field.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryList = customerDAO.getCountryList();
        countryCombo.setItems(countryList);
        divList = customerDAO.getDivisionList();
        customerIdField.setDisable(true);

    }

    /**
     * Updates a selected customer.
     * @throws IOException
     */
    @FXML
    private void updateCustomer() throws IOException {

        Customer customer = new Customer();
        customer.setCustomerId(Integer.parseInt(customerIdField.getText()));
        customer.setCustomerName(customerNameField.getText());
        customer.setCustomerAddress(customerAddressField.getText());
        customer.setCustomerPostalCode(customerPostalField.getText());
        customer.setCustomerPhoneNumber(customerPhoneNumberField.getText());
        customer.setDivisionName(divisionCombo.getSelectionModel().getSelectedItem().getDivName());
        customer.setCountryName(countryCombo.getSelectionModel().getSelectedItem().getCountryName());
        customer.setDivisionId(divisionCombo.getSelectionModel().getSelectedItem().getDivId());

        customerDAO.updateCustomer(customer);

        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) saveUpdatedCustomerButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    /**
     * Populates the data into the appropriate ComboBoxes and TextFields from a selected customer object.
     * @param customer Customer object that was selected from the CustomerMainForm Tableview.
     */
    protected void initData(Customer customer) {
        customerToUpdate = customer;
        customerIdField.setText(Integer.toString(customerToUpdate.getCustomerId()));
        customerAddressField.setText(customer.getCustomerAddress());
        customerPostalField.setText(customer.getCustomerPostalCode());
        customerPhoneNumberField.setText(customer.getCustomerPhoneNumber());
        customerNameField.setText(customer.getCustomerName());

        //Sets the selected value of the Country ComboBox.
        for(Country c : countryList) {
            if(c.getCountryName().equals(customer.getCountryName())) {
                countryCombo.setValue(c);
            }
        }

        //Sets the selected value of the Division ComboBox.
        for(Division d : divList) {
            if(d.getDivName().equals(customer.getDivisionName())) {
                divisionCombo.setValue(d);
                //Calls this method to set the rest of the options for the ComboBox
                setDivisionCombo();
            }
        }
    }

    /**
     * Cancels updating a customer and sends the user back to the CustomerMainForm.
     * @throws IOException
     */
    @FXML
    private void cancelUpdate() throws IOException {

        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) cancelUpdateButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    /**
     * Sets the Division ComboBox to a filtered list based on the Country ComboBox selection.
     */
    @FXML
    private void setDivisionCombo() {

        int selection = countryCombo.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<Division> filtered = divList.filtered(d -> d.getCountryID() == selection);
        divisionCombo.setItems(filtered);
    }
}
