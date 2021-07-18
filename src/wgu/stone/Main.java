package wgu.stone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wgu.stone.database.DatabaseConnection;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/wgu/stone/view/LoginForm.fxml"));
        primaryStage.setTitle("User Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        DatabaseConnection.startConnection();
        launch(args);
        DatabaseConnection.closeConnection();
    }

}
