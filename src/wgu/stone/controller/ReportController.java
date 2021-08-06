package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.stone.DAO.implementations.AppointmentDAOImpl;
import wgu.stone.DAO.interfaces.AppointmentDAO;
import wgu.stone.model.Appointment;
import wgu.stone.model.Contact;
import java.net.URL;
import java.util.ResourceBundle;



public class ReportController implements Initializable {

    @FXML TableView<Appointment> contactAppointmentsView;
    @FXML TableColumn<Appointment, String> contactNameColumn;
    @FXML TableColumn<Appointment, Integer> appIdColumn;
    @FXML TableColumn<Appointment, String> titleColumn;
    @FXML TableColumn<Appointment, String> typeColumn;
    @FXML TableColumn<Appointment, String> descriptionColumn;
    @FXML TableColumn<Appointment, String> startColumn;
    @FXML TableColumn<Appointment, String> endColumn;
    @FXML TableColumn<Appointment, Integer> customerIdColumn;

    @FXML TableView<Appointment> locationAppointmentsView;
    @FXML TableColumn<Appointment, String> appIdColumnLoc;
    @FXML TableColumn<Appointment, String> appTitleColumnLoc;
    @FXML TableColumn<Appointment, String> appDescriptionColumnLoc;
    @FXML TableColumn<Appointment, String> typeColumnLoc;
    @FXML TableColumn<Appointment, String> startColumnLoc;
    @FXML TableColumn<Appointment, String> endColumnLoc;
    @FXML TableColumn<Appointment, Integer> customerIdColumnLoc;


    @FXML private Button backToMainScreenButton;
    @FXML private ComboBox<Contact> contactsComboBox;
    @FXML private ComboBox<String> locationComboBox;

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
    }

    @FXML
    private void setContactAppTableView() {

        FilteredList<Appointment> filteredList = contactAppointmentsList.filtered(c -> c.getAppContact().equals(contactsComboBox.getValue().getContactName()));
        contactAppointmentsView.setItems(filteredList);
        System.out.println(filteredList);
    }

    @FXML
    private void setLocationAppTableView() {

        FilteredList<Appointment> filteredList = contactAppointmentsList.filtered(c -> c.getAppLocation().equals(locationComboBox.getValue()));
        locationAppointmentsView.setItems(filteredList);
    }
}
