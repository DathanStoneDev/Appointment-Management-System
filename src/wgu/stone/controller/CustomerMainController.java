package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.stone.DAO.interfaces.CustomerDAO;
import wgu.stone.DAO.implementations.CustomerDAOImpl;
import wgu.stone.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    @FXML private Button addAppointmentsButton;

    private CustomerDAO customerDAO = new CustomerDAOImpl();

    @FXML
    private void deleteCustomer() throws SQLException {
        Customer customer = customerRecords.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to delete this customer?");
        Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                customerDAO.delete(customer);
            }
    }

    @FXML
    private void exitApplication() {
        Stage window = (Stage) exitAppButton.getScene().getWindow();
        window.close();
    }

    @FXML
    private void goToAppointmentAddForm() throws IOException {
        Parent addAppointment = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AddAppointmentForm.fxml"));
        Scene addAppointmentScene = new Scene(addAppointment);
        Stage window = (Stage) addAppointmentsButton.getScene().getWindow();
        window.setScene(addAppointmentScene);
        window.show();
    }

    @FXML
    private void goToCustAdd() throws IOException {
            Parent addAppointment = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AddCustomerForm.fxml"));
            Scene addAppointmentScene = new Scene(addAppointment);
            Stage window = (Stage) addCustomerButton.getScene().getWindow();
            window.setScene(addAppointmentScene);
            window.show();
    }



    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initialize tableview and columns
        customerRecords.setItems(customerDAO.getAll());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPostalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPhoneNumber"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));
        customerFLDColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("divisionName"));
        customerCountryColumn.setCellValueFactory((new PropertyValueFactory<Customer, String>("customerCountry")));
    }
}
