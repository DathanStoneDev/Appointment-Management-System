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
import wgu.stone.DAO.implementations.CountryDAOImpl;
import wgu.stone.DAO.implementations.FirstLevelDivisionsDAOImpl;
import wgu.stone.DAO.interfaces.CountryDAO;
import wgu.stone.DAO.interfaces.CustomerDAO;
import wgu.stone.DAO.implementations.CustomerDAOImpl;
import wgu.stone.DAO.interfaces.FirstLevelDivisionsDAO;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;
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
    @FXML private ComboBox<Division> divisionCombo;
    @FXML private ComboBox<Country> countryCombo;

    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private CountryDAO countryDAO = new CountryDAOImpl();
    private FirstLevelDivisionsDAO firstLevelDivisionsDAO = new FirstLevelDivisionsDAOImpl();
    private ObservableList<Division> divList = FXCollections.observableArrayList();




    public void addNewCustomer() throws IOException {

        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        String postalCode = customerPostalField.getText();
        String customerPhoneNumber = customerPhoneNumberField.getText();
        String loggedInUser = LoginController.loggedIn;
        String lastUpdatedBy = LoginController.loggedIn;
        int divisionId = divisionCombo.getSelectionModel().getSelectedItem().getDivisionId();

        Customer customer = new Customer(customerName, customerAddress, postalCode, customerPhoneNumber,
                loggedInUser, lastUpdatedBy, divisionId);

        customerDAO.save(customer);


        //make this a Util method.
        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/CustomerMainForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) saveNewCustomerButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    public void setDivisionCombo() {

        int selection = countryCombo.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<Division> filtered = divList.filtered(d -> d.getCountryId() == selection);
        divisionCombo.setItems(filtered);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(countryDAO.getCountries());
        divList = firstLevelDivisionsDAO.getDivisions();
    }
}
