package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

import static wgu.stone.controller.AddAppointmentController.*;

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
    @FXML private ComboBox<String> typeComboBox;

    //Confirmation buttons.
    @FXML private Button backToMainAppointmentButton;
    @FXML private Button exitAppButton;

    //DAO Interface Instances
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();


    private void setTimesForComboBoxes() {

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);

        while(start.isBefore(end.plusSeconds(1))) {
            startTimeComboBox.getItems().add(start);
            endTimeComboBox.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }

    /**
     * Creates a LocalDateTime from the LocalDate and LocalTime selections from the date picker and startTimeComboBox.
     * Converts the LocalDateTime to a ZoneDateTime and with a zoneID of UTC.
     * Lastly, Formats the ZoneDateTime.
     * @return returns a string of the final formatted UTC time to be persisted to the Database.
     */
    private LocalDateTime createStartLocaleDateTime() {

        LocalDate startDate = datePicker.getValue();
        LocalTime startTime = startTimeComboBox.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        return start;
    }

    /**
     * Creates a LocalDateTime from the LocalDate and LocalTime selections from the date picker and endTimeComboBox.
     * Converts the LocalDateTime to a ZoneDateTime and with a zoneID of UTC.
     * Lastly, Formats the ZoneDateTime.
     * @return returns a string of the final formatted UTC time to be persisted to the Database.
     */
    private LocalDateTime createEndLocaleDateTime() {

        LocalDate endDate = datePicker.getValue();
        LocalTime endTime = endTimeComboBox.getValue();
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        return end;
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
        appointment.setAppType(typeComboBox.getValue());
        appointment.setUserId(LoginController.loggedInUser);

        appointmentDAO.updateAppointment(appointment);
    }

    public void initData(Appointment appointment) {

        appToBeUpdated = appointment;
        customerIdField.setText(Integer.toString(appointment.getCustomerId()));
        appIdField.setText(Integer.toString(appointment.getAppId()));
        descriptionField.setText(appointment.getAppDescription());
        locationComboBox.setValue(appointment.getAppLocation());
        titleField.setText(appointment.getAppTitle());
        customerIdField.setText(Integer.toString(appointment.getCustomerId()));
        typeComboBox.setValue(appointment.getAppType());
        datePicker.setValue(getDateFromDateTime(appointment.getStartDatetime()));
        startTimeComboBox.setValue(getTimeFromDateTime(appointment.getStartDatetime()));
        endTimeComboBox.setValue(getTimeFromDateTime(appointment.getEndDatetime()));
        for(Contact c : contactList) {
            if(c.getContactName().equals(appointment.getAppContact())) {
                contactNameComboBox.setValue(c);
            }
        }

        System.out.println(getDateFromDateTime(appointment.getStartDatetime()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationComboBox.setItems(AddAppointmentController.locations);
        contactList = appointmentDAO.getContactsList();
        contactNameComboBox.setItems(contactList);
        customerIdField.setDisable(true);
        appIdField.setDisable(true);
        setTimesForComboBoxes();
        locationComboBox.setItems(locations);
        typeComboBox.setItems(types);

    }

    private LocalDate getDateFromDateTime(LocalDateTime dateTime) {
        LocalDate date = LocalDate.from(dateTime);
        return date;
    }

    private LocalTime getTimeFromDateTime(LocalDateTime dateTime) {
        LocalTime time = LocalTime.from(dateTime);
        return time;
    }

    @FXML
    private void exitApp() {
        Stage window = (Stage) exitAppButton.getScene().getWindow();
        window.close();
    }

    @FXML
    private void backToMainDashboard() throws IOException {
        Parent mainApp = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AppointmentMainForm.fxml"));
        Scene mainAppScene = new Scene(mainApp);
        Stage window = (Stage) backToMainAppointmentButton.getScene().getWindow();
        window.setScene(mainAppScene);
        window.show();
    }

}
