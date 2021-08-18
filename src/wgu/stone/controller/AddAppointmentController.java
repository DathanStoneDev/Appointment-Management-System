package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.implementations.CustomerDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;
import wgu.stone.model.Customer;
import wgu.stone.utility.Buttons;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    //Customer tableview
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerIdColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;

    //TextFields.
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;

    //Date Picker.
    @FXML private DatePicker datePicker;

    //ComboBoxes.
    @FXML private ComboBox<LocalTime> startTimeComboBox;
    @FXML private ComboBox<LocalTime> endTimeComboBox;
    @FXML private ComboBox<String> locationComboBox;
    @FXML private ComboBox<Contact> contactNameComboBox;
    @FXML private ComboBox<String> typeComboBox;

    //Type and Locations lists that be used within this package.
    protected static final ObservableList<String> types = FXCollections.observableArrayList(new String[]{"Consult", "Business", "Project"});
    protected static final ObservableList<String> locations = FXCollections.observableArrayList("Phoenix Arizona",
            "White Plains New York", "Montreal Canada", "London England");

    //Buttons.
    @FXML private Button exitAppButton;
    @FXML private Button backToMainAppointmentButton;
    @FXML private Button saveAppointmentButton;

    //DAO Interface Instances
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    //Main list to work with appointment data.
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * Sets the times for the start and end ComboBoxes.
     */
    private void setTimesForComboBoxes() {

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);

        while (start.isBefore(end.plusSeconds(1))) {
            startTimeComboBox.getItems().add(start);
            endTimeComboBox.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }

    /**
     * Creates a LocalDateTime from the LocalDate and LocalTime selections from the date picker and startTimeComboBox.
     * @return returns the Start LocalDateTime.
     */
    private LocalDateTime createStartLocaleDateTime() {

        //Creates the start LocalDateTime to add to appointment object.
        LocalDate startDate = datePicker.getValue();
        LocalTime startTime = startTimeComboBox.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        return start;
    }

        /**
         * Creates a LocalDateTime from the LocalDate and LocalTime selections from the date picker and endTimeComboBox.
         * @return returns the End LocalDateTime.
         */
        private LocalDateTime createEndLocaleDateTime () {

            //Creates the end LocalDateTime to add to appointment object.
            LocalDate endDate = datePicker.getValue();
            LocalTime endTime = endTimeComboBox.getValue();
            LocalDateTime end = LocalDateTime.of(endDate, endTime);
            return end;
        }

        /**
         * Adds a new appointment to the Database.
         */
        @FXML
        private void addNewAppointment () throws IOException {
            if (isValidWithoutOverlaps()) {
                try {
                    String appTitle = titleField.getText();
                    String appDescription = descriptionField.getText();
                    String appLocation = locationComboBox.getValue();
                    String appType = typeComboBox.getValue();
                    LocalDateTime start = createStartLocaleDateTime();
                    LocalDateTime end = createEndLocaleDateTime();
                    String appContact = contactNameComboBox.getValue().getContactName();
                    int userId = LoginController.loggedInUser;
                    int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
                    int contactId = contactNameComboBox.getValue().getContactId();

                    Appointment appointment = new Appointment(appTitle, appDescription, appLocation, appType, start, end,
                            appContact, userId, customerId, contactId);

                    appointmentDAO.saveAppointment(appointment);

                    Buttons.toMainAppointmentForm(saveAppointmentButton);
                } catch (NullPointerException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Empty Fields");
                    alert.setContentText("Please ensure fields are not blank and ComboBoxes have a selected item");
                    alert.show();
                    e.printStackTrace();
                    return;
                }

            } else {
                throw new IllegalArgumentException("Appointment Time is not valid");
            }
        }

    /**
     * Initializes the components on the Add Appointment form.
     * @param url
     * @param resourceBundle
     */
    @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            customerTable.setItems(customerDAO.getCustomerIdAndNamesList());
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            setTimesForComboBoxes();
            contactNameComboBox.setItems(appointmentDAO.getContactsList());
            locationComboBox.setItems(locations);
            typeComboBox.setItems(types);
            appointments = appointmentDAO.getAppointmentsList();
        }

        /**
         * Exits the application.
         */
        @FXML
        private void exitApp () {
            Buttons.exitApplication(exitAppButton);
        }

        /**
         * Goes to the MainAppointmentForm.
         *
         * @throws IOException
         */
        @FXML
        private void backToMainAppointment () throws IOException {
            Buttons.toMainAppointmentForm(backToMainAppointmentButton);
        }

        /**
         * First, checks the appointment that is being made for valid start and end times.
         * Second, if the appointment has valid start and end times, those times are checked against all other appointments
         * to prevent overlapping appointments.
         *
         * @return returns true for valid appointment times or false for non valid appointment time.
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
     * Validation to ensure the start and end times are valid.
     * Validates: Ensures times are within EST Business hours, have a start time before the end time, and checks if the
     * appointment is before the current LocalDateTime.
     * @param start LocalDateTime from the createStartTime Method.
     * @param end LocalDateTime from the createEndTime Method.
     * @return Returns Boolean.
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
