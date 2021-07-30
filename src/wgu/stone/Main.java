package wgu.stone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wgu.stone.database.DatabaseConnection;

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
    public static void main(String[] args) {

        //starts the database connection.
        DatabaseConnection.startConnection();


        launch(args);


        //closes the database connection once application is closed.
        DatabaseConnection.closeConnection();
    }

}
