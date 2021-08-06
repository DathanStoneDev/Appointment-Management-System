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
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
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
    @FXML private RadioButton monthlyRadioButton;
    @FXML private RadioButton weeklyRadioButton;
    @FXML private ToggleGroup filterAppsGroup;

    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    @FXML
    private void goToAddAppForm() throws IOException {
        Parent addApp = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AddAppointmentForm.fxml"));
        Scene addAppScene = new Scene(addApp);
        Stage window = (Stage) addAppointmentButton.getScene().getWindow();
        window.setScene(addAppScene);
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

    @FXML
    private void deleteAppointment() {
        appointmentDAO.deleteAppointment(appointmentTableView.getSelectionModel().getSelectedItem().getAppId());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointments = appointmentDAO.getAppointmentsList();
        appointmentTableView.setItems(appointments);
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDatetime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDatetime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        filterAppsGroup = new ToggleGroup();
        monthlyRadioButton.setToggleGroup(filterAppsGroup);
        weeklyRadioButton.setToggleGroup(filterAppsGroup);
        monthlyRadioButton.setSelected(true);
    }
}
