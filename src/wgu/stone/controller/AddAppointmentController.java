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
import java.time.format.DateTimeFormatter;
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

    //may put these in the model.
    protected static final ObservableList<String> types = FXCollections.observableArrayList(new String[]{"Consult", "Business", "Project"});
    protected static final ObservableList<String> locations = FXCollections.observableArrayList("Phoenix Arizona",
            "White Plains New York", "Montreal Canada", "London England");

    //Confirmation buttons.
    @FXML private Button exitAppButton;
    @FXML private Button backToMainAppointmentButton;
    @FXML private Button saveAppointmentButton;

    //DAO Interface Instances
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    //DateTimeFormatter
    //get rid of this
    protected static DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    //Needs to be edited to create Times in EST.
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

    /**
     * Adds a new appointment to the Database.
     */
    @FXML
    private void addNewAppointment() {

        Appointment appointment = new Appointment();
        appointment.setAppTitle(titleField.getText());
        appointment.setAppDescription(descriptionField.getText());
        appointment.setAppLocation(locationComboBox.getValue());
        appointment.setAppContact(contactNameComboBox.getValue().getContactName());
        appointment.setContactId(contactNameComboBox.getValue().getContactId());
        appointment.setAppType(typeComboBox.getValue());
        appointment.setStartDatetime(createStartLocaleDateTime());
        appointment.setEndDatetime(createEndLocaleDateTime());
        appointment.setCustomerId(customerTable.getSelectionModel().getSelectedItem().getCustomerId());
        appointment.setUserId(LoginController.loggedInUser);

        try {
            if(!doesAppointmentOverlap(appointment)) {
                appointmentDAO.saveAppointment(appointment);
                Parent mainApp = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AppointmentMainForm.fxml"));
                Scene mainAppScene = new Scene(mainApp);
                Stage window = (Stage) saveAppointmentButton.getScene().getWindow();
                window.setScene(mainAppScene);
                window.show();
                System.out.println("Saved");
            } else {
                System.out.println("Not saved");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Appointment Time: Overlap");
                alert.setContentText("The appointment has an overlap with another customer. Please try another selection");
                alert.show();
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    private void exitApp() {
        Buttons.exitApplication(exitAppButton);
    }

    /**
     * Goes to the MainAppointmentForm.
     * @throws IOException
     */
    @FXML
    private void backToMainAppointment() throws IOException {
        Parent mainApp = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AppointmentMainForm.fxml"));
        Scene mainAppScene = new Scene(mainApp);
        Stage window = (Stage) backToMainAppointmentButton.getScene().getWindow();
        window.setScene(mainAppScene);
        window.show();
    }

    //This is horribly written. Dirty and works.
    private Boolean doesAppointmentOverlap(Appointment appointment) {

        LocalDateTime addAppStartDateTime = appointment.getStartDatetime();
        LocalDateTime addAppEndDateTime = appointment.getEndDatetime();

        Boolean overlap = false;

        for (Appointment a : appointments) {

            LocalDateTime appListStart = a.getStartDatetime();
            LocalDateTime appListEnd = a.getEndDatetime();

           if (addAppStartDateTime.isAfter(appListStart) && addAppStartDateTime.isBefore(appListEnd)
                   || addAppEndDateTime.isAfter(appListStart) && addAppEndDateTime.isBefore(appListEnd)
           || addAppStartDateTime.isEqual(appListStart) && addAppEndDateTime.isEqual(appListEnd)) {
               overlap = true;
           }
        }
        return overlap;
    }
}
