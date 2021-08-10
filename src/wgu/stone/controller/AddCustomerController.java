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
import wgu.stone.dao.implementations.CustomerDAOImpl;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    //TextFields.
    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPostalField;
    @FXML private TextField customerPhoneNumberField;

    //Buttons.
    @FXML private Button saveNewCustomerButton;
    @FXML private Button exitAppButton;
    @FXML private Button backToMainCustomerButton;

    //ComboBoxes.
    @FXML private ComboBox<Division> divisionCombo;
    @FXML private ComboBox<Country> countryCombo;

    //CustomerDAO Interface to call methods.
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    //List of division objects for the division ComboBox.
    private ObservableList<Division> divList = FXCollections.observableArrayList();

    /**
     * Initializes the ComboBox Lists.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(customerDAO.getCountryList());
        divList = customerDAO.getDivisionList();
    }

    /**
     * Adds a new customer.
     * @throws IOException
     */
    public void addNewCustomer() throws IOException {

        Customer customer = new Customer();
        customer.setCustomerName(customerNameField.getText());
        customer.setCustomerAddress(customerAddressField.getText());
        customer.setCustomerPostalCode(customerPostalField.getText());
        customer.setCustomerPhoneNumber(customerPhoneNumberField.getText());
        customer.setDivisionName(divisionCombo.getSelectionModel().getSelectedItem().getDivName());
        customer.setCountryName(countryCombo.getSelectionModel().getSelectedItem().getCountryName());
        customer.setDivisionId(divisionCombo.getSelectionModel().getSelectedItem().getDivId());

        customerDAO.saveCustomer(customer);

        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) saveNewCustomerButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    /**
     * Filters the ComboBox for divisions based on the selection of the country from the country ComboBox.
     */
    public void setDivisionCombo() {

        int selection = countryCombo.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<Division> filtered = divList.filtered(d -> d.getCountryID() == selection);
        divisionCombo.setItems(filtered);
    }

    @FXML
    private final void exitApp() {
        Stage window = (Stage) exitAppButton.getScene().getWindow();
        window.close();
    }

    @FXML
    private final void backToMainDashboard() throws IOException {
        Parent mainCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene mainCustomerScene = new Scene(mainCustomer);
        Stage window = (Stage) backToMainCustomerButton.getScene().getWindow();
        window.setScene(mainCustomerScene);
        window.show();
    }


}
