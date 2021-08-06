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

    //may put these in the model.
    protected static final String[] types = {"Consult", "Business", "Project"};
    protected static final ObservableList<String> locations = FXCollections.observableArrayList("Phoenix Arizona",
            "White Plains New York", "Montreal Canada", "London England");

    //Types buttons.
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

    //DateTimeFormatters
    protected static DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //possibly edit this. Works for now. This can go to a hashmap.
    private String selectAppType() {
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

    /**
     * Creates a LocalDateTime from the LocalDate and LocalTime selections from the date picker and startTimeComboBox.
     * Converts the LocalDateTime to a ZoneDateTime and with a zoneID of UTC.
     * Lastly, Formats the ZoneDateTime.
     * @return returns a string of the final formatted UTC time to be persisted to the Database.
     */
    private String createStartLocaleDateTime() {

        LocalDate startDate = datePicker.getValue();
        LocalTime startTime = startTimeComboBox.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(start, ZoneId.of("UTC"));
        String startFinal = zonedDateTime.format(d1);
        return startFinal;
    }

    /**
     * Creates a LocalDateTime from the LocalDate and LocalTime selections from the date picker and endTimeComboBox.
     * Converts the LocalDateTime to a ZoneDateTime and with a zoneID of UTC.
     * Lastly, Formats the ZoneDateTime.
     * @return returns a string of the final formatted UTC time to be persisted to the Database.
     */
    private String createEndLocaleDateTime() {

        LocalDate endDate = datePicker.getValue();
        LocalTime endTime = endTimeComboBox.getValue();
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(end, ZoneId.of("UTC"));
        String endFinal = zonedDateTime.format(d1);
        return endFinal;
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
        appointment.setAppType(selectAppType());
        appointment.setStartDatetime(createStartLocaleDateTime());
        appointment.setEndDatetime(createEndLocaleDateTime());
        appointment.setCustomerId(customerTable.getSelectionModel().getSelectedItem().getCustomerId());
        appointment.setUserId(LoginController.loggedInUser);

        appointmentDAO.saveAppointment(appointment);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(customerDAO.getCustomerIdAndNamesList());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        setTimesForComboBoxes();
        contactNameComboBox.setItems(appointmentDAO.getContactsList());
        typeGroup = new ToggleGroup();
        businessType.setToggleGroup(typeGroup);
        projectType.setToggleGroup(typeGroup);
        consultType.setToggleGroup(typeGroup);
        locationComboBox.setItems(locations);
    }
}
