package javaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import jdbc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try (Connection connection = JDBCUtils.getNewConnection()) {
            JDBCUtils.createTable(connection);
            JDBCUtils.clearTable(connection);

            Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
            primaryStage.setTitle("Shop");
            primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));
            primaryStage.show();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
