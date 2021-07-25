package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.stone.DAO.AppointmentDAO;
import wgu.stone.DAO.AppointmentDAOImpl;
import wgu.stone.DAO.GenericDAO;
import wgu.stone.model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentMainController implements Initializable {

    //Appointment tableview components.
    @FXML private TableView <Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> locationColumn;
    @FXML private TableColumn<Appointment, String> contactColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> startDateColumn;
    @FXML private TableColumn<Appointment, String> endDateColumn;
    @FXML private TableColumn<Appointment, Integer> customerIdColumn;

    //Appointment form buttons.
    @FXML private Button addAppointmentButton;
    @FXML private Button updateAppointmentButton;
    @FXML private Button deleteAppointmentButton;
    @FXML private Button cancelButton;
    @FXML private Button exitAppButton;

    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    @FXML
    private void goToAddAppForm() throws IOException {
        Parent addCustomer = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AddAppointmentForm.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);
        Stage window = (Stage) addAppointmentButton.getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    @FXML
    private void goToUpdateForm() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/wgu/stone/view/UpdateAppointmentForm.fxml"));
        Parent updateApp = loader.load();

        Scene updateCustomerScene = new Scene(updateApp);
        UpdateAppointmentController controller = loader.getController();
        try {
            controller.initData(appointmentTableView.getSelectionModel().getSelectedItem());
            Stage window = (Stage) updateAppointmentButton.getScene().getWindow();
            window.setScene(updateCustomerScene);
            window.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }













    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new AddAppointmentController();
        appointmentTableView.setItems(appointmentDAO.getAll());
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDatetime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDatetime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }
}
