package wgu.stone.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Database connection methods.
 */
public class DatabaseConnection {

    //connection variable.
    private static Connection conn;

    /**
     * Starts the initial connection.
     * @return connection to conn variable.
     */
    public static Connection startConnection() {
        ResourceBundle reader;
        reader = ResourceBundle.getBundle("db");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(reader.getString("url"), reader.getString("username"), reader.getString("password"));
            System.out.println("Successfully connected!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
        } return conn;
    }

    /**
     * gets the connection without restarting the process through the DriverManager.
     * @return conn/
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * closes the connection
     * @return null if the connection could not be closed.
     */
    public static Connection closeConnection() {
        try {
            if(conn != null) {
                conn.close();
                System.out.println("Connection is closed.");
            }
        } catch (SQLException e) {
            System.out.println("Could not close the connection: " + e.getMessage());
        } return null;
    }
}
