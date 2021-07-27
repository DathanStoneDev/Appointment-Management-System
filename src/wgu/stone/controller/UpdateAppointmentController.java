package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;

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
    @FXML private ComboBox<Contact> contactNameComboBox;
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







    public void initData(Appointment appointment) {

        appToBeUpdated = appointment;
        customerIdField.setText(Integer.toString(appointment.getCustomerId()));
        appIdField.setText(Integer.toString(appointment.getAppId()));
        descriptionField.setText(appointment.getAppDescription());
        //will need to use formatter.
        //datePicker.setValue(LocalDate.parse(appointment.getStartDatetime()));
        locationComboBox.setValue(appointment.getAppLocation());
        titleField.setText(appointment.getAppTitle());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationComboBox.setItems(AddAppointmentController.locations);
        customerIdField.setDisable(true);
        appIdField.setDisable(true);
    }
}
