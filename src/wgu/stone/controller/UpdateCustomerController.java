package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import wgu.stone.DAO.CustomerDAOImpl;
import wgu.stone.DAO.UserDAOImpl;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.FirstLevelDivisions;
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

    public void updateCustomer() {
        int customerId = Integer.parseInt(customerIdField.getText());
        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        String postalCode = customerPostalField.getText();
        String customerPhoneNumber = customerPhoneNumberField.getText();
        String lastUpdatedBy = UserDAOImpl.loggedInUser;
        int divisionId = divisionCombo.getSelectionModel().getSelectedItem().getDivisionId();
        String divisionName = divisionCombo.getSelectionModel().getSelectedItem().getDivisionName();
        String countryName = countryCombo.getSelectionModel().getSelectedItem().getCountry();

        Customer customer = new Customer(customerId, customerName, customerAddress, postalCode, customerPhoneNumber,
                lastUpdatedBy, divisionId, countryName, divisionName);

        CustomerDAOImpl.updateCustomer(customer);

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
        CustomerDAOImpl.filterDivisionList(countryCombo.getSelectionModel().getSelectedItem().toString());
        for(FirstLevelDivisions d : FirstLevelDivisions.getDivisions()) {
            if(divisionName.equals(d.getDivisionName())) {
                divisionCombo.setValue(d);
            }
        }
    }

    public void setDivisionCombo() {
        CustomerDAOImpl.filterDivisionList(countryCombo.getSelectionModel().getSelectedItem().toString());
        divisionCombo.setItems(FirstLevelDivisions.getDivisions());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerDAOImpl.getAllCountries();
        countryCombo.setItems(Country.getCountries());
        customerIdField.setDisable(true);

    }
}
