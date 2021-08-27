package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
 * A controller for updating customers.
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
    @FXML private Button exitAppButton;

    //CustomerDAO Interface to call methods.
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    //Customer object that was passed from the CustomerMainForm.
    private Customer customerToUpdate;

    //Lists of country and division objects for the ComboBoxes.
    private ObservableList<Division> divList = FXCollections.observableArrayList();
    private ObservableList<Country> countryList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryList = customerDAO.getCountryList();
        countryCombo.setItems(countryList);
        divList = customerDAO.getDivisionList();
        customerIdField.setDisable(true);

    }

    /**
     * Updates a selected customer.
     * @throws IOException Throws exception if the CustomerMainForm cannot be retrieved.
     */
    @FXML
    private void updateCustomer() throws IOException {

        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            String customerName = customerNameField.getText();
            String customerAddress = customerAddressField.getText();
            String customerPostalCode = customerPostalField.getText();
            String customerPhoneNumber = customerPhoneNumberField.getText();
            String countryName = countryCombo.getSelectionModel().getSelectedItem().getCountryName();
            String divisionName = divisionCombo.getSelectionModel().getSelectedItem().getDivName();
            int divisionId = divisionCombo.getSelectionModel().getSelectedItem().getDivId();

            Customer customer = new Customer(customerId, customerName, customerAddress, customerPostalCode, customerPhoneNumber,
                    countryName, divisionName, divisionId);

            customerDAO.updateCustomer(customer);

            Buttons.toMainCustomerForm(saveUpdatedCustomerButton);
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
     * @throws IOException Throws exception if the CustomerMainForm cannot be retrieved.
     */
    @FXML
    private void backToMainCustomerForm() throws IOException {

        Parent mainCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene mainCustomerScene = new Scene(mainCustomer);
        Stage window = (Stage) cancelUpdateButton.getScene().getWindow();
        window.setScene(mainCustomerScene);
        window.show();
    }

    /**
     * Exits the application.
     */
    @FXML
    private final void exitApp() {
        Stage window = (Stage) exitAppButton.getScene().getWindow();
        window.close();
    }

    /**
     * Sets the <code>divisionCombo</code> to a filtered list based on the <code>countryCombo</code>selection.
     */
    @FXML
    private void setDivisionCombo() {

        int selection = countryCombo.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<Division> filtered = divList.filtered(d -> d.getCountryID() == selection);
        divisionCombo.setItems(filtered);
    }
}
