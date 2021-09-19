package wgu.stone.dao.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Database connection methods.
 */
public final class DatabaseConnection {

    /**
     * Empty private constructor to prevent instantiation.
     */
    private DatabaseConnection() {

    }

    //connection variable.
    private static Connection conn;

    /**
     * Starts the initial connection.
     * @return connection to conn variable.
     */
    public static Connection startConnection(){

        final String url = "jdbc:mysql://wgudb.ucertify.com:3306/WJ08BhZ?autoReconnect=true&useSSL=false";
        final String username = "U08BhZ";
        final String password = "53689239164";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully connected!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
        } return conn;
    }

    /**
     * Gets the connection without restarting the process through the DriverManager.
     * @return returns connection.
     */
    public static Connection getConnection() { return conn; }


    /**
     * Closes the connection.
     * @return returns null if the connection could not be closed.
     */
    public static Connection closeConnection() {
        try {
            if(conn != null) {
                conn.close();
                System.out.println("Connection is closed.");
            }
        } catch (SQLException e) {
            System.out.println("Could not close the connection: " + e.getMessage());
            e.printStackTrace();
        } return null;
    }
}
