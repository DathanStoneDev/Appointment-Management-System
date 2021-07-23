package wgu.stone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.stone.DAO.CustomerDAOImpl;
import wgu.stone.model.Appointment;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentMainController implements Initializable {

    @FXML private TableView <Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> locationColumn;
    @FXML private TableColumn<Appointment, String> contactColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> startDateColumn;
    @FXML private TableColumn<Appointment, String> endDateColumn;
    @FXML private TableColumn<Appointment, Integer> customerIdColumn;












    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTableView.setItems(CustomerDAOImpl.getAllAppointments());
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDatetime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDatetime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }
}
