package wgu.stone.controller;

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
import java.net.URL;
import java.util.ResourceBundle;



public class ReportController implements Initializable {

    @FXML private TableView<Appointment> contactAppointmentsView;
    @FXML private TableColumn<Appointment, String> contactNameColumn;
    @FXML private TableColumn<Appointment, Integer> appIdColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> startColumn;
    @FXML private TableColumn<Appointment, String> endColumn;
    @FXML private TableColumn<Appointment, Integer> customerIdColumn;

    @FXML private TableView<Appointment> locationAppointmentsView;
    @FXML private TableColumn<Appointment, String> appIdColumnLoc;
    @FXML private TableColumn<Appointment, String> appTitleColumnLoc;
    @FXML private TableColumn<Appointment, String> appDescriptionColumnLoc;
    @FXML private TableColumn<Appointment, String> typeColumnLoc;
    @FXML private TableColumn<Appointment, String> startColumnLoc;
    @FXML private TableColumn<Appointment, String> endColumnLoc;
    @FXML private TableColumn<Appointment, Integer> customerIdColumnLoc;

    @FXML private TextArea textArea;

    @FXML private Button backToMainScreenButton;
    @FXML private ComboBox<Contact> contactsComboBox;
    @FXML private ComboBox<String> locationComboBox;
    @FXML private ComboBox<String> typesComboBox;

    @FXML private TabPane reportsPane;
    @FXML private Tab contactAppReport;
    @FXML private Tab totalAppReport;
    @FXML private Tab locationAppReport;

    private ObservableList<Appointment> contactAppointmentsList = FXCollections.observableArrayList();
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactAppointmentsList = appointmentDAO.getContactScheduleList();
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDatetime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDatetime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        appTitleColumnLoc.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appDescriptionColumnLoc.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appIdColumnLoc.setCellValueFactory(new PropertyValueFactory<>("appId"));
        typeColumnLoc.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startColumnLoc.setCellValueFactory(new PropertyValueFactory<>("startDatetime"));
        endColumnLoc.setCellValueFactory(new PropertyValueFactory<>("endDatetime"));
        customerIdColumnLoc.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        locationComboBox.setItems(AddAppointmentController.locations);
        contactsComboBox.setItems(appointmentDAO.getContactsList());
        appointmentDAO.getAppsByMonthAndType();
        textArea.setEditable(false);

        monthAndTypeReport();
    }

    @FXML
    private final void setContactAppTableView() {

        FilteredList<Appointment> filteredList = contactAppointmentsList.filtered(c -> c.getAppContact().equals(contactsComboBox.getValue().getContactName()));
        contactAppointmentsView.setItems(filteredList);
    }

    @FXML
    private final void setLocationAppTableView() {

        FilteredList<Appointment> filteredList = contactAppointmentsList.filtered(c -> c.getAppLocation().equals(locationComboBox.getValue()));
        locationAppointmentsView.setItems(filteredList);
    }

    private final void monthAndTypeReport() {

        ObservableList<String> reportStrings = appointmentDAO.getAppsByMonthAndType();
        StringBuffer stringBuffer = new StringBuffer();
        for(String s : reportStrings) {
            stringBuffer.append(s + "\n");
        }
        textArea.appendText(stringBuffer.toString());

    }
}
