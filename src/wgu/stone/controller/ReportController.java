package wgu.stone.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;
import wgu.stone.utility.Buttons;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Provides reports to the user.
 */
public class ReportController implements Initializable {

    //Tableview components for the reports based on contact owner.
    @FXML private TableView<Appointment> contactAppointmentsView;
    @FXML private TableColumn<Appointment, String> contactNameColumn;
    @FXML private TableColumn<Appointment, Integer> appIdColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> startColumn;
    @FXML private TableColumn<Appointment, String> endColumn;
    @FXML private TableColumn<Appointment, Integer> customerIdColumn;

    //Tableview components for the report based on locations.
    @FXML private TableView<Appointment> locationAppointmentsView;
    @FXML private TableColumn<Appointment, String> appIdColumnLoc;
    @FXML private TableColumn<Appointment, String> appTitleColumnLoc;
    @FXML private TableColumn<Appointment, String> appDescriptionColumnLoc;
    @FXML private TableColumn<Appointment, String> typeColumnLoc;
    @FXML private TableColumn<Appointment, String> startColumnLoc;
    @FXML private TableColumn<Appointment, String> endColumnLoc;
    @FXML private TableColumn<Appointment, Integer> customerIdColumnLoc;

    //TextArea where the total appointments based on type and month are shown.
    @FXML private TextArea textArea;

    //Buttons on the report form.
    @FXML private Button backToMainScreenButton;
    @FXML private ComboBox<Contact> contactsComboBox;
    @FXML private ComboBox<String> locationComboBox;

    //List that will serve as the base for the Contact and Location reports to reduce calls to the database.
    private ObservableList<Appointment> contactAppointmentsList = FXCollections.observableArrayList();

    //Instance of the appointmentDAO.
    private final AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    /**
     * Initializes the Contact, Location and Month/Type Reports
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Contact Report TableView
        contactAppointmentsList = appointmentDAO.getContactScheduleList();
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getStartTimeFormatted()));
        endColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getEndTimeFormatted()));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        //Location Report Tableview
        appTitleColumnLoc.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appDescriptionColumnLoc.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appIdColumnLoc.setCellValueFactory(new PropertyValueFactory<>("appId"));
        typeColumnLoc.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startColumnLoc.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getStartTimeFormatted()));
        endColumnLoc.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getEndTimeFormatted()));
        customerIdColumnLoc.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        //ComboBoxes
        locationComboBox.setItems(AddAppointmentController.locations);
        contactsComboBox.setItems(appointmentDAO.getContactsList());

        //All Appointments by month and type Report
        textArea.setEditable(false);
        monthAndTypeReport();

    }

    /**
     * Creates a filtered list from the contactAppointmentList based on the contactComboBox selection.
     * Sets the contactAppointmentsView to the filtered list.
     */
    @FXML
    private void setContactAppTableView() {

        FilteredList<Appointment> filteredList = contactAppointmentsList.filtered(c -> c.getAppContact().equals(contactsComboBox.getValue().getContactName()));
        contactAppointmentsView.setItems(filteredList);
    }

    /**
     * Creates a filtered list from the contactAppointmentList based on the locationComboBox selection.
     * Sets the contactAppointmentsView to the filtered list.
     */
    @FXML
    private void setLocationAppTableView() {

        FilteredList<Appointment> filteredList = contactAppointmentsList.filtered(c -> c.getAppLocation().equals(locationComboBox.getValue()));
        locationAppointmentsView.setItems(filteredList);
    }

    /**
     * Appends text to the TextArea to show the total appointments grouped by Type and Month.
     */
    private void monthAndTypeReport() {

        ObservableList<String> reportStrings = appointmentDAO.getAppsByMonthAndType();
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : reportStrings) {
            stringBuilder.append(s).append("\n");
        }
        textArea.appendText(stringBuilder.toString());

    }

    /**
     * Takes the user back to the Dashboard.
     * @throws IOException
     */
    @FXML
    private void backToMainDashboard() throws IOException {
        Buttons.toMainDashboard(backToMainScreenButton);
    }

}
