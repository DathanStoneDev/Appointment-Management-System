package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.implementations.CustomerDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.model.Appointment;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

    private Appointment appToBeUpdated;

    @FXML private TextField appIdField;
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private TextField customerIdField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> locationComboBox;
    @FXML private ComboBox<String> contactNameComboBox;
    @FXML private ComboBox<LocalTime> startTimeComboBox;
    @FXML private ComboBox<LocalTime> endTimeComboBox;

    @FXML private RadioButton consultType;
    @FXML private RadioButton businessType;
    @FXML private RadioButton projectType;
    @FXML private ToggleGroup typeGroup;

    //Confirmation buttons.
    @FXML private Button addAppointmentButton;
    @FXML private Button cancelButton;
    @FXML private Button exitAppButton;

    //DAO Interface Instances
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private CustomerDAO customerDAO = new CustomerDAOImpl();



    private void setTimesForComboBoxes() {

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);

        while(start.isBefore(end.plusSeconds(1))) {
            startTimeComboBox.getItems().add(start);
            endTimeComboBox.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }



    public void initData(Appointment appointment) {

        appToBeUpdated = appointment;
        customerIdField.setText(Integer.toString(appointment.getCustomerId()));
        appIdField.setText(Integer.toString(appointment.getAppId()));
        descriptionField.setText(appointment.getAppDescription());
        //This needs to be figured out
        datePicker.setValue(LocalDate.parse(appointment.getStartDatetime()));
        locationComboBox.setValue(appointment.getAppLocation());
        titleField.setText(appointment.getAppTitle());
        contactNameComboBox.setValue(appointment.getAppContact());
        customerIdField.setText(Integer.toString(appointment.getCustomerId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationComboBox.setItems(AddAppointmentController.locations);
        customerIdField.setDisable(true);
        appIdField.setDisable(true);
        setTimesForComboBoxes();
    }
}
