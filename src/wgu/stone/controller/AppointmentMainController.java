package wgu.stone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.model.Appointment;
import wgu.stone.utility.DateTimeFormatterUtility;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppointmentMainController implements Initializable {

    //Appointment tableview components.
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

    //Appointment form buttons.
    @FXML private Button addAppointmentButton;
    @FXML private Button updateAppointmentButton;
    @FXML private Button backToMainScreenButton;
    @FXML private Button exitAppButton;
    @FXML private RadioButton monthlyRadioButton;
    @FXML private RadioButton weeklyRadioButton;
    @FXML private RadioButton allAppointmentsRadioButton;
    @FXML private ToggleGroup filterAppsGroup;

    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    @FXML
    private void goToAddAppForm() throws IOException {
        Parent addApp = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AddAppointmentForm.fxml"));
        Scene addAppScene = new Scene(addApp);
        Stage window = (Stage) addAppointmentButton.getScene().getWindow();
        window.setScene(addAppScene);
        window.show();
    }

    @FXML
    private void goToUpdateForm() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/wgu/stone/view/UpdateAppointmentForm.fxml"));
        Parent updateApp = loader.load();

        Scene updateCustomerScene = new Scene(updateApp);
        UpdateAppointmentController controller = loader.getController();
        try {
            controller.initData(appointmentTableView.getSelectionModel().getSelectedItem());
            Stage window = (Stage) updateAppointmentButton.getScene().getWindow();
            window.setScene(updateCustomerScene);
            window.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteAppointment() {
        int appId = appointmentTableView.getSelectionModel().getSelectedItem().getAppId();
        appointmentDAO.deleteAppointment(appId);
        appointments.removeIf(a -> a.getAppId() == appId);
    }

    @FXML
    private final void exitApp() {
        Stage window = (Stage) exitAppButton.getScene().getWindow();
        window.close();
    }

    @FXML
    private final void backToMainDashboard() throws IOException {
        Parent main = FXMLLoader.load(getClass().getResource("/wgu/stone/view/MainDashboard.fxml"));
        Scene mainScene = new Scene(main);
        Stage window = (Stage) backToMainScreenButton.getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointments = appointmentDAO.getAppointmentsList();
        appointmentTableView.setItems(appointments);
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDatetime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDatetime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        filterAppsGroup = new ToggleGroup();
        monthlyRadioButton.setToggleGroup(filterAppsGroup);
        weeklyRadioButton.setToggleGroup(filterAppsGroup);
        allAppointmentsRadioButton.setToggleGroup(filterAppsGroup);
        allAppointmentsRadioButton.setSelected(true);
    }

    @FXML
    private void getAppointmentsByCurrentMonth() {

        //gets current month
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();

        FilteredList<Appointment> filteredList = appointments
                .filtered(a -> DateTimeFormatterUtility.formatLocalDateTimeForNewObject(a.getStartDatetime())
                        .getMonth().equals(currentMonth));
        appointmentTableView.setItems(filteredList);
    }

    @FXML
    private void getAppointmentsByCurrentWeek() {
        ObservableList<Appointment> filteredWeek = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());

        for(Appointment a : appointments) {
            LocalDateTime appDateTime = DateTimeFormatterUtility.formatLocalDateTimeForNewObject(a.getStartDatetime());
            int week = appDateTime.get(weekFields.weekOfWeekBasedYear());


            if(week == currentWeek) {
               filteredWeek.add(a);
            }
        appointmentTableView.setItems(filteredWeek);
        }
    }

    @FXML
    private void getAllAppointments() {
        appointmentTableView.setItems(appointments);
    }
}
