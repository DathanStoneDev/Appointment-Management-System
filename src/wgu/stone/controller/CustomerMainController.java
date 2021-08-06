package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.stone.dao.implementations.CustomerDAOImpl;
import wgu.stone.dao.interfaces.CustomerDAO;
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

    //Buttons.
    @FXML private Button addCustomerButton;
    @FXML private Button updateCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button exitAppButton;
    @FXML private Button addAppointmentsButton;

    //CustomerDAO interface to call methods.
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    //List of customer objects to populate the tableview.
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     * Initializes the Customer Records Tableview.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initialize tableview and columns
        customers = customerDAO.getCustomerList();
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerFLDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        customerRecords.setItems(customers);
    }

    /**
     * Deletes a customer from the database and the tableview.
     */
    @FXML
    private void deleteCustomer() {
        int customerId = customerRecords.getSelectionModel().getSelectedItem().getCustomerId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to delete this customer?");
        Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                //may need a check here to make sure customer was deleted.
                customers.removeIf(c -> c.getCustomerId() == customerId);
                customerDAO.deleteCustomer(customerId);
            }

    }

    /**
     * Exits the application.
     */
    @FXML
    private void exitApplication() {
        Stage window = (Stage) exitAppButton.getScene().getWindow();
        window.close();
    }

    /**
     * Takes the user to the AddAppointmentForm
     * @throws IOException
     */
    @FXML
    private void goToMainAppointmentForm() throws IOException {
        Parent addAppointment = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AppointmentMainForm.fxml"));
        Scene addAppointmentScene = new Scene(addAppointment);
        Stage window = (Stage) addCustomerButton.getScene().getWindow();
        window.setScene(addAppointmentScene);
        window.show();

    }

    /**
     * Takes the user to the CustomerAddForm.
     * @throws IOException
     */
    @FXML
    private void goToCustomerAddForm() throws IOException {
            Parent addAppointment = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AddCustomerForm.fxml"));
            Scene addAppointmentScene = new Scene(addAppointment);
            Stage window = (Stage) addCustomerButton.getScene().getWindow();
            window.setScene(addAppointmentScene);
            window.show();
    }

    /**
     * Takes the user to the CustomerUpdateForm.
     * @throws IOException
     */
    @FXML
    private void goToCustomerUpdateForm() throws IOException {
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
}
