package wgu.stone.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnection {

    private static Connection conn;

    public static Connection getConnection() {
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

    public static Connection closeConnection (){
        try {
            if(conn != null) {
                conn.close();
                System.out.println("Connection is closed.");
            }
        } catch (SQLException e) {
            System.out.println("Could not close the application: " + e.getMessage());
        } return null;
    }
}
