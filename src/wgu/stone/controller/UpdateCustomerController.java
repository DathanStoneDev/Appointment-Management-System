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
import wgu.stone.dao.implementations.CountryDAOImpl;
import wgu.stone.dao.implementations.FirstLevelDivisionsDAOImpl;
import wgu.stone.dao.interfaces.CountryDAO;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.dao.implementations.CustomerDAOImpl;
import wgu.stone.dao.interfaces.FirstLevelDivisionsDAO;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;
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

    @FXML private ComboBox<Division> divisionCombo;
    @FXML private ComboBox<Country> countryCombo;

    @FXML private Button saveUpdatedCustomerButton;
    @FXML private Button cancelUpdateButton;

    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private CountryDAO countryDAO = new CountryDAOImpl();
    private FirstLevelDivisionsDAO firstLevelDivisionsDAO = new FirstLevelDivisionsDAOImpl();
    private ObservableList<Division> divList = FXCollections.observableArrayList();
    private ObservableList<Country> countryList = FXCollections.observableArrayList();


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
                lastUpdatedBy, divisionId, divisionName, countryName);

        customerDAO.update(customer);
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
        String divisionName = customer.getDivisionName();
        String countryName = customer.getCountryName();
        for(Country c : countryList) {
            if(c.getCountry().equals(countryName)) {
                countryCombo.setValue(c);
            }
        }
        for(Division d : divList) {
            if(d.getDivisionName().equals(divisionName)) {
                divisionCombo.setValue(d);
            }
        }
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
        countryList = countryDAO.getCountries();
        countryCombo.setItems(countryList);
        divList = firstLevelDivisionsDAO.getDivisions();
        customerIdField.setDisable(true);

    }

    public void setDivisionCombo() {

        int selection = countryCombo.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<Division> filtered = divList.filtered(d -> d.getCountryId() == selection);
        divisionCombo.setItems(filtered);
    }
}
