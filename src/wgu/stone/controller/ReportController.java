package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.stone.DAO.implementations.AppointmentDAOImpl;
import wgu.stone.DAO.interfaces.AppointmentDAO;
import wgu.stone.model.Appointment;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


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

    @FXML private Button mainScreenButton;
    @FXML private ComboBox<String> contactsComboBox;

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
        contactsComboBox.setItems(appointmentDAO.getContactsList());


    }

    @FXML
    private void setTableView() {

        contactAppointmentsView.setItems(contactAppointmentsList
                .filtered(a -> a.getAppContact()
                .equals(contactsComboBox.getValue())));
    }
}
