package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;

import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;
import static wgu.stone.controller.AddAppointmentController.locations;
import static wgu.stone.controller.AddAppointmentController.types;

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

    //DAO Interface Instances
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    //private final DateTimeFormatter custom = DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd");



    private String selectAppType() {
        types.clone();
        try {
            if(consultType.isSelected()) {
                return types[0];
            }
            if(businessType.isSelected()) {
                return types[1];
            }
            if(projectType.isSelected()) {
                return types[2];
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } return null;
    }

    private void setTimesForComboBoxes() {

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);

        while(start.isBefore(end.plusSeconds(1))) {
            startTimeComboBox.getItems().add(start);
            endTimeComboBox.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }

    private String createStartLocaleDateTime() {

        LocalDate startDate = datePicker.getValue();
        LocalTime startTime = startTimeComboBox.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        System.out.println(start);
        return start.toString();
    }

    private String createEndLocaleDateTime() {

        LocalDate endDate = datePicker.getValue();
        LocalTime endTime = endTimeComboBox.getValue();
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        return end.toString();
    }

    public void updateAppointment() {

        Appointment appointment = new Appointment();
        appointment.setAppId(Integer.parseInt(appIdField.getText()));
        appointment.setCustomerId(Integer.parseInt(customerIdField.getText()));
        appointment.setAppDescription(descriptionField.getText());
        appointment.setAppContact(contactNameComboBox.getValue().getContactName());
        appointment.setContactId(contactNameComboBox.getValue().getContactId());
        appointment.setAppLocation(locationComboBox.getValue());
        appointment.setAppTitle(titleField.getText());
        appointment.setStartDatetime(createStartLocaleDateTime());
        appointment.setEndDatetime(createEndLocaleDateTime());
        appointment.setAppType(selectAppType());
        appointment.setUserId(LoginController.loggedInUser);

        appointmentDAO.updateAppointment(appointment);
    }

    public void initData(Appointment appointment) {

        appToBeUpdated = appointment;
        customerIdField.setText(Integer.toString(appointment.getCustomerId()));
        appIdField.setText(Integer.toString(appointment.getAppId()));
        descriptionField.setText(appointment.getAppDescription());
        //LocalDateTime dateTime = LocalDateTime.parse(appointment.getStartDatetime()); //this can be deleted

        //**Need to focus on getting ZoneDateTimes Instead and formatting those.
        //**Need to be in the yyyy-MM-dd HH:mm:ss format for mySql -> DateTimeFormatter.
       // startTimeComboBox.setValue();
       // endTimeComboBox.setValue(); - try to grab total localDateTime and parse it individually.
        locationComboBox.setValue(appointment.getAppLocation());
       // datePicker.setValue();
        titleField.setText(appointment.getAppTitle());
        //contactNameComboBox.setValue(appointment.getAppContact());
        customerIdField.setText(Integer.toString(appointment.getCustomerId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationComboBox.setItems(AddAppointmentController.locations);
        contactNameComboBox.setItems(appointmentDAO.getContactsList());
        customerIdField.setDisable(true);
        appIdField.setDisable(true);
        setTimesForComboBoxes();
        typeGroup = new ToggleGroup();
        businessType.setToggleGroup(typeGroup);
        projectType.setToggleGroup(typeGroup);
        consultType.setToggleGroup(typeGroup);
        locationComboBox.setItems(locations);
    }
}
