package wgu.stone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wgu.stone.dao.implementations.AppointmentDAOImpl;
import wgu.stone.dao.implementations.CustomerDAOImpl;
import wgu.stone.dao.interfaces.AppointmentDAO;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Appointment;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;

import java.sql.SQLException;

/**
 * Main Application class that starts the application.
 */
public class Main extends Application {

    /**
     * Application starts up on the LoginForm page.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/wgu/stone/view/LoginForm.fxml"));
        primaryStage.setTitle("User Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) throws SQLException {

        //starts the database connection.
        DatabaseConnection.startConnection();
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        appointmentDAO.getContactsMap();
        CustomerDAO customerDAO = new CustomerDAOImpl();
        customerDAO.getAll();
        Division division = new Division();
        division.setDivId(56);
        division.setCountryID(34);
        division.setDivName("yeet");
        Customer customer = new Customer();
        customer.setDivision(division);
        System.out.println(customer.getDivision());

        launch(args);


        //closes the database connection once application is closed.
        DatabaseConnection.closeConnection();
    }

}
