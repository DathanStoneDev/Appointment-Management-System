package wgu.stone.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
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
import wgu.stone.utility.Buttons;
import wgu.stone.utility.DateTimeFormatterUtility;
import java.io.IOException;
import java.net.URL;
import java.time.*;
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

    //Instance of AppointmentDAO Interface.
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    //List of appointments.
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * Goes to the AddAppointmentForm
     * @throws IOException
     */
    @FXML
    private void goToAddAppForm() throws IOException {
        Parent addApp = FXMLLoader.load(getClass().getResource("/wgu/stone/view/AddAppointmentForm.fxml"));
        Scene addAppScene = new Scene(addApp);
        Stage window = (Stage) addAppointmentButton.getScene().getWindow();
        window.setScene(addAppScene);
        window.show();
    }

    /**
     * Goes to the UpdateAppointmentForm
     * @throws IOException
     */
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

    /**
     * Deletes a selected appointment and then removes the appointment from the list.
     */
    @FXML
    private void deleteAppointment() {

        try {
            int appId = appointmentTableView.getSelectionModel().getSelectedItem().getAppId();
            appointmentDAO.deleteAppointment(appId);
            appointments.removeIf(a -> a.getAppId() == appId);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("A selection was not made");
            //make an alert
        }
    }

    /**
     * Exits the application
     */
    @FXML
    private void exitApp() {
        Buttons.exitApplication(exitAppButton);
    }

    /**
     * Goes to the MainDashboard
     * @throws IOException
     */
    @FXML
    private void backToMainDashboard() throws IOException {
        Buttons.toMainDashboard(backToMainScreenButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointments = appointmentDAO.getAppointmentsList();
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startDateColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getStartTimeFormatted()));
        endDateColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getEndTimeFormatted()));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        filterAppsGroup = new ToggleGroup();
        monthlyRadioButton.setToggleGroup(filterAppsGroup);
        weeklyRadioButton.setToggleGroup(filterAppsGroup);
        allAppointmentsRadioButton.setToggleGroup(filterAppsGroup);
        allAppointmentsRadioButton.setSelected(true);
        appointmentTableView.setItems(appointments);
    }

    /**
     * set to the monthlyRadioButton - if selected, this method is called.
     * Gets the current month and then creates a filtered list of appointments that consist of only appointments in that
     * month. The list is then set as the tableview.
     */
    @FXML
    private void getAppointmentsByCurrentMonth() {

        //gets current month.
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();

        //creates filtered list.
        FilteredList<Appointment> filteredList = appointments
                .filtered(a -> a.getStartDatetime()
                        .getMonth().equals(currentMonth));

        //sets tableview to filtered list.
        appointmentTableView.setItems(filteredList);
    }

    /**
     * set to the weeklyRadioButton - if selected, this method is called.
     * Gets the current week and then displays a list of appointments only in the current week.
     * Sets the tableview to the list.
     */
    @FXML
    private void getAppointmentsByCurrentWeek() {
        ObservableList<Appointment> filteredWeek = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());

        for(Appointment a : appointments) {
            LocalDateTime appDateTime = a.getStartDatetime();
            int week = appDateTime.get(weekFields.weekOfWeekBasedYear());


            if(week == currentWeek) {
               filteredWeek.add(a);
            }
        appointmentTableView.setItems(filteredWeek);
        }
    }

    /**
     * Gets all the appointments and displays them. This method is initialized when this view is selected.
     * Method is set to the allAppointmentsRadioButton in case the user toggled to another filter, and would like to
     * see all appointments again.
     */
    @FXML
    private void getAllAppointments() {
        appointmentTableView.setItems(appointments);
    }
}
