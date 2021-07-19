package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.stone.DAO.CustomerDAOImpl;
import wgu.stone.model.Customer;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    //Tableview of records and their columns.
    @FXML private TableView<Customer> customerRecords;
    @FXML private TableColumn<Customer, Integer> customerIdColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;
    @FXML private TableColumn<Customer, String> customerPostalCodeColumn;
    @FXML private TableColumn<Customer, String> customerPhoneColumn;
    @FXML private TableColumn<Customer, String> customerCountryColumn;
    @FXML private TableColumn<Customer, String> customerFLDColumn;
    @FXML private TableColumn<Customer, String> customerAddressColumn;

    //Buttons on Customer Record Form.
    @FXML private Button addCustomerButton;
    @FXML private Button updateCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button exitAppButton;
    @FXML private Button viewAppointmentsButton;

    /**
     * Initialize starting data for Customer Record Form.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initialize tableview and columns
        customerRecords.setItems(CustomerDAOImpl.getAllCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPostalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPhoneNumber"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));

        //need to figure out how I want to populate the country, and First division data.
    }
}
