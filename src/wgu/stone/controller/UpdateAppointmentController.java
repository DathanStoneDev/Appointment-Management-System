package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;
import wgu.stone.utility.Buttons;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;
import static wgu.stone.controller.AddAppointmentController.*;

/**
 * A controller for updating appointments.
 */
public class UpdateAppointmentController implements Initializable {

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

    //Main list to work with appointment data.
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    //List to work with contactData.
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     * Sets the hours the user can pick from in the Start and End ComboBoxes.
     */
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
     * Creates a LocalDateTime from the LocalDate and LocalTime selections from the <code>datePicker</code>and <code>startTimeComboBox</code>.
     * @return Returns the <code>start</code>LocalDateTime.
     */
    private LocalDateTime createStartLocaleDateTime() {

        LocalDate startDate = datePicker.getValue();
        LocalTime startTime = startTimeComboBox.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        return start;
    }

    /**
     * Creates a LocalDateTime from the LocalDate and LocalTime selections from the <code>datePicker</code>and <code>endTimeComboBox</code>.
     * @return Returns the <code>end</code> LocalDateTime.
     */
    private LocalDateTime createEndLocaleDateTime() {

        //Creates LocalDateTime to add to appointment object.
        LocalDate endDate = datePicker.getValue();
        LocalTime endTime = endTimeComboBox.getValue();
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        return end;
    }

    /**
     * Performs validation and updates the appointment.
     */
    public void updateAppointment() {

            if (isValidWithoutOverlaps()) {
                try {

                    int appId = Integer.parseInt(appIdField.getText());
                    String appTitle = titleField.getText();
                    String appDescription = descriptionField.getText();
                    String appLocation = locationComboBox.getValue();
                    String appType = typeComboBox.getValue();
                    LocalDateTime start = createStartLocaleDateTime();
                    LocalDateTime end = createEndLocaleDateTime();
                    String appContact = contactNameComboBox.getValue().getContactName();
                    int userId = LoginController.loggedInUser;
                    int customerId = Integer.parseInt(customerIdField.getText());
                    int contactId = contactNameComboBox.getValue().getContactId();


                    Appointment appointment = new Appointment(appId, appTitle, appDescription, appLocation, appType,
                            start, end, customerId, contactId, appContact, userId);

                    appointmentDAO.updateAppointment(appointment);

                    Buttons.toMainAppointmentForm(backToMainAppointmentButton);
                } catch (NullPointerException | IOException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Empty Fields");
                    alert.setContentText("Ensure that all fields have been filled and that all ComboBoxes and Customer" +
                            " has been selected.");
                    alert.show();
                    e.printStackTrace();
                    return;
                }
            } else {
                throw new IllegalArgumentException("Appointment Time is not valid");
            }
    }

    /**
     * Initializes the selected appointment data from the AppointmentMainForm.
     * @param appointment Object that will be updated.
     */
    public void initData(Appointment appointment) {

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationComboBox.setItems(AddAppointmentController.locations);
        contactList = appointmentDAO.getContactsList();
        appointments = appointmentDAO.getAppointmentsList();
        contactNameComboBox.setItems(contactList);
        customerIdField.setDisable(true);
        appIdField.setDisable(true);
        setTimesForComboBoxes();
        locationComboBox.setItems(locations);
        typeComboBox.setItems(types);

    }

    /**
     * Parses a LocalDateTime to retrieve a start Date.
     * @param dateTime LocalDateTime object passed from <code>isValidWithoutOverlaps()</code>method.
     * @return Returns a Date from a LocalDateTime object that will be compared to appointment times
     * to check for overlaps.
     */
    private LocalDate getDateFromDateTime(LocalDateTime dateTime) {
        LocalDate date = LocalDate.from(dateTime);
        return date;
    }
    /**
     * Parses a LocalDateTime to retrieve an end Date.
     * @param dateTime LocalDateTime object passed from <code>isValidWithoutOverlaps()</code>method.
     * @return Returns a Date from a LocalDateTime object that will be compared to appointment times
     * to check for overlaps.
     */
    private LocalTime getTimeFromDateTime(LocalDateTime dateTime) {
        LocalTime time = LocalTime.from(dateTime);
        return time;
    }

    /**
     * Exits the application.
     */
    @FXML
    private void exitApp() {
        Buttons.exitApplication(exitAppButton);
    }

    /**
     * Takes the user back to the main appointment form if the cancel button is clicked.
     * @throws IOException Throws exception if the AppointmentMainForm cannot be retrieved.
     */
    @FXML
    private void backToMainAppointmentForm() throws IOException {
        Buttons.toMainAppointmentForm(backToMainAppointmentButton);
    }


    /**
     * Validates that the appointment start and end times do not overlap with other appointments.
     * First, checks the appointment that is being made for valid start and end times.
     * Second, if the appointment has valid start and end times, those times are checked against all other appointments
     * to prevent overlapping appointments.
     * @return Returns Boolean: true for valid appointment times or false for an invalid appointment times.
     */
    private Boolean isValidWithoutOverlaps() {

        //grabs the appointment Start and End dates.
        LocalDateTime currentAppStart = createStartLocaleDateTime();
        LocalDateTime currentAppEnd = createEndLocaleDateTime();

        //First validates if the times are valid before checking for overlaps.
        //If not, returns false. Else, continue with validation.
        if(!isValidAppointmentTime(currentAppStart, currentAppEnd)) {
            return false;
        } else {
            for (Appointment a : appointments) {

                LocalDateTime listOfAppsStart = a.getStartDatetime();
                LocalDateTime listOfAppsEnd = a.getEndDatetime();

                if (currentAppStart.isAfter(listOfAppsStart) && currentAppStart.isBefore(listOfAppsEnd)
                        || currentAppEnd.isAfter(listOfAppsStart) && currentAppEnd.isBefore(listOfAppsEnd)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Appointment Time: Overlap");
                    alert.setContentText("The appointment has an overlap with another customer. Please try another selection");
                    alert.show();
                    return false;
                }
            } return true;
        }
    }

    /**
     * Validates that the appointment start and end times against EST hours.
     * Validates: Ensures times are within EST Business hours, have a start time before the end time, and checks if the
     * appointment is before the current LocalDateTime.
     * @param start LocalDateTime from the createStartTime Method.
     * @param end LocalDateTime from the createEndTime Method.
     * @return Returns Boolean: true for valid appointment times or false for an invalid appointment times.
     */
    private Boolean isValidAppointmentTime(LocalDateTime start, LocalDateTime end) {

        //EST Time ZoneId
        ZoneId estZoneId = ZoneId.of("America/New_York");

        //Times to check against
        LocalTime startTimeEst = LocalTime.of(8, 0);
        LocalTime endTimeEst = LocalTime.of(22, 0);

        //Current DateTime to check against.
        LocalDateTime currentDateTime = LocalDateTime.now();

        //LocalDateTimes converted to ZoneDateTimes to convert to EST Time, then back to a LocalDateTime.
        ZonedDateTime estCheckZoneStart = start.atZone(ZoneId.systemDefault()).withZoneSameInstant(estZoneId);
        LocalDateTime estCheckLocStart = estCheckZoneStart.toLocalDateTime();
        ZonedDateTime estCheckZoneEnd = end.atZone(ZoneId.systemDefault()).withZoneSameInstant(estZoneId);
        LocalDateTime estCheckLocEnd = estCheckZoneEnd.toLocalDateTime();

        //General Information alert that will be shown based on the error.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (estCheckLocStart.toLocalTime().isBefore(startTimeEst) || estCheckLocStart.toLocalTime().isAfter(endTimeEst)) {
            alert.setTitle("Invalid Appointment Time: Business Hours Conflict");
            alert.setContentText("The start time is not within EST Business Hours");
            alert.show();
            return false;
        } else if(estCheckLocEnd.toLocalTime().isBefore(startTimeEst) || estCheckLocEnd.toLocalTime().isAfter(endTimeEst)) {
            alert.setTitle("Invalid Appointment Time: Business Hours Conflict");
            alert.setContentText("The end time is not within EST Business Hours");
            alert.show();
            return false;
        } else if (estCheckLocStart.isAfter(estCheckLocEnd) || estCheckLocStart.isEqual(estCheckLocEnd)) {
            alert.setTitle("Invalid Appointment Time");
            alert.setContentText("The times you have are invalid. The Start time must be before the end time " +
                    "and the times must not equal each other.");
            alert.show();
            return false;
        } else if (start.isBefore(currentDateTime)) {
            alert.setTitle("Invalid Appointment Time");
            alert.setContentText("The appointment time must not be before the current date and time.");
            alert.show();
            return false;
        } return true;
    }

}
