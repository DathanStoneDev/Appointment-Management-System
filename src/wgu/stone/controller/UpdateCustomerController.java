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
import wgu.stone.DAO.interfaces.CustomerDAO;
import wgu.stone.DAO.implementations.CustomerDAOImpl;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.FirstLevelDivisions;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    //TextFields on the Add customer form.
    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPostalField;
    @FXML private TextField customerPhoneNumberField;
    @FXML private TextField customerIdField;

    @FXML private ComboBox<FirstLevelDivisions> divisionCombo;
    @FXML private ComboBox<Country> countryCombo;

    @FXML private Button saveUpdatedCustomerButton;
    @FXML private Button cancelUpdateButton;

    private CustomerDAO customerDAO = new CustomerDAOImpl();

    public void updateCustomer() throws IOException {
        int customerId = Integer.parseInt(customerIdField.getText());
        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        String postalCode = customerPostalField.getText();
        String customerPhoneNumber = customerPhoneNumberField.getText();
        String lastUpdatedBy = LoginController.loggedIn;
        int divisionId = divisionCombo.getSelectionModel().getSelectedItem().getDivisionId();
        String divisionName = divisionCombo.getSelectionModel().getSelectedItem().getDivisionName();
        String countryName = countryCombo.getSelectionModel().getSelectedItem().getCountry();

        Customer customer = new Customer(customerId, customerName, customerAddress, postalCode, customerPhoneNumber,
                lastUpdatedBy, divisionId, countryName, divisionName);

        customerDAO.updateCustomer(customer);
        //make this a Util method.
        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) saveUpdatedCustomerButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();

        //note: The create date is automatically updated to the same updated date. Need to figure out how to fix that.

    }

    private Customer customerToUpdate;

    public void initData(Customer customer) {
        customerToUpdate = customer;
        customerIdField.setText(Integer.toString(customerToUpdate.getCustomerId()));
        customerAddressField.setText(customer.getCustomerAddress());
        customerPostalField.setText(customer.getCustomerPostalCode());
        customerPhoneNumberField.setText(customer.getCustomerPhoneNumber());
        customerNameField.setText(customer.getCustomerName());
        String custContry = customerToUpdate.getCustomerCountry();
        String divisionName = customerToUpdate.getDivisionName();
        for(Country c : Country.getCountries()) {
            if(custContry.equals(c.getCountry())) {
                countryCombo.setValue(c);
            }
        }
        customerDAO.filterDivisionList(countryCombo.getSelectionModel().getSelectedItem().toString());
        for(FirstLevelDivisions d : FirstLevelDivisions.getDivisions()) {
            if(divisionName.equals(d.getDivisionName())) {
                divisionCombo.setValue(d);
            }
        }
    }

    public void setDivisionCombo() {
        customerDAO.filterDivisionList(countryCombo.getSelectionModel().getSelectedItem().toString());
        divisionCombo.setItems(FirstLevelDivisions.getDivisions());
    }

    public void cancelUpdate() throws IOException {
        //make this a Util method.
        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) cancelUpdateButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerDAO.getAllCountries();
        countryCombo.setItems(Country.getCountries());
        customerIdField.setDisable(true);

    }
}
