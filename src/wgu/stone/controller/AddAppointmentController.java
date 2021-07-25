package wgu.stone.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wgu.stone.DAO.*;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

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
    private final String[] types = {"Consult", "Business", "Project"};
    private final ObservableList<String> locations = FXCollections.observableArrayList("Phoenix Arizona",
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

            Appointment appointment = new Appointment(appTitle, appDesc, location, type, startTime,
                    endTime, lastUpdatedBy, createdBy, contactName, contactId, userId);

            try {
                appointmentDAO.save(appointment);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLocationComboBox();
        setTimesForComboBoxes();
        contactNameComboBox.setItems(contactDAO.getAllContacts());
        typeGroup = new ToggleGroup();
        businessType.setToggleGroup(typeGroup);
        projectType.setToggleGroup(typeGroup);
        consultType.setToggleGroup(typeGroup);
    }
}
