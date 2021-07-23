package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.stone.DAO.CustomerDAOImpl;
import wgu.stone.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerMainController implements Initializable {

    //Tableview of records and their columns.
    @FXML private TableView<Customer> customerRecords;
    @FXML private TableColumn<Customer, Integer> customerIdColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;
    @FXML private TableColumn<Customer, String> customerPostalCodeColumn;
    @FXML private TableColumn<Customer, String> customerPhoneColumn;
    @FXML private TableColumn<Customer, String> customerFLDColumn;
    @FXML private TableColumn<Customer, String> customerAddressColumn;
    @FXML private TableColumn<Customer, String> customerCountryColumn;

    //Buttons on Customer Record Form.
    @FXML private Button addCustomerButton;
    @FXML private Button updateCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button exitAppButton;
    @FXML private Button viewAppointmentsButton;

    /**
     * Sends the user to the addCustomerForm when the addCustomerButton is clicked.
     * @throws IOException
     */
    public void goToAddCustomerForm() throws IOException {
        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AddCustomerForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) addCustomerButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    public void deleteCustomer() {
        Customer customer = customerRecords.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to delete this customer?");
        Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                CustomerDAOImpl.deleteCustomer(customer.getCustomerId());
            }
    }

    public void goToUpdateCustomerForm() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/wgu/stone/view/UpdateCustomerForm.fxml"));
        Parent updateCustomer = loader.load();

        Scene updateCustomerScene = new Scene(updateCustomer);
        UpdateCustomerController controller = loader.getController();
        try {
            controller.initData(customerRecords.getSelectionModel().getSelectedItem());
            Stage window = (Stage) updateCustomerButton.getScene().getWindow();
            window.setScene(updateCustomerScene);
            window.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void exitApplication() {
        Stage window = (Stage) exitAppButton.getScene().getWindow();
        window.close();
    }

    public void goToAppointmentMainForm() throws IOException {
        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AppointmentMainForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) viewAppointmentsButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

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
        customerFLDColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("divisionName"));
        customerCountryColumn.setCellValueFactory((new PropertyValueFactory<Customer, String>("customerCountry")));

        //need to figure out how I want to populate the country, and First division data.
    }
}
