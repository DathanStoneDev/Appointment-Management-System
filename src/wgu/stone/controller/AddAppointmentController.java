package wgu.stone.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.stone.DAO.implementations.AppointmentDAOImpl;
import wgu.stone.DAO.implementations.ContactDAOImpl;
import wgu.stone.DAO.implementations.CustomerDAOImpl;
import wgu.stone.DAO.implementations.UserDAOImpl;
import wgu.stone.DAO.interfaces.AppointmentDAO;
import wgu.stone.DAO.interfaces.ContactDAO;
import wgu.stone.DAO.interfaces.CustomerDAO;
import wgu.stone.DAO.interfaces.UserDAO;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;
import wgu.stone.model.Customer;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    //Customer tableview
    @FXML private TableView customerTable;
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
    //these may go into the model. - if so, they will need to be public.
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
    private UserDAO userDAO = new UserDAOImpl();
    private ContactDAO contactDAO = new ContactDAOImpl();
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    //possibly edit this. Works for now.
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

    //may not need this method and just do locationComboBox.setItems to locations.
    private void setLocationComboBox() {
        for(String s : locations) {
            locationComboBox.getItems().add(s);
        }
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


    private LocalDateTime createStartLocaleDateTime() {

        LocalDate startDate = datePicker.getValue();
        LocalTime startTime = startTimeComboBox.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        System.out.println(start);
        return start;
    }

    private LocalDateTime createEndLocaleDateTime() {

        LocalDate endDate = datePicker.getValue();
        LocalTime endTime = endTimeComboBox.getValue();
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        return end;
    }

    @FXML
    private void addNewAppointment() {

            String appTitle = titleField.getText();
            String appDesc = descriptionField.getText();
            LocalDateTime startTime= createStartLocaleDateTime();
            LocalDateTime endTime = createEndLocaleDateTime();
            String location = locationComboBox.getValue();
            String type = selectAppType();
            String contactName = contactNameComboBox.getValue().getContactName();
            int contactId = contactNameComboBox.getValue().getContactId();
            String lastUpdatedBy = LoginController.loggedIn;
            String createdBy = LoginController.loggedIn;
            int userId = userDAO.getUserInfo(LoginController.loggedIn);
            Customer customer = (Customer) customerTable.getSelectionModel().getSelectedItem();
            int customerId = customer.getCustomerId();

            Appointment appointment = new Appointment(appTitle, appDesc, location, type, startTime,
                    endTime, lastUpdatedBy, createdBy, contactName, contactId, userId, customerId);

            try {
                appointmentDAO.save(appointment);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(customerDAO.getCustomerIdAndName());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        setLocationComboBox();
        setTimesForComboBoxes();
        contactNameComboBox.setItems(contactDAO.getAllContacts());
        typeGroup = new ToggleGroup();
        businessType.setToggleGroup(typeGroup);
        projectType.setToggleGroup(typeGroup);
        consultType.setToggleGroup(typeGroup);
    }
}
