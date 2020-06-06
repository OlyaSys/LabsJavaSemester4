package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.Controller;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/signIn.fxml"));
        AnchorPane root = loader.load();
        primaryStage.setTitle("Authorization");
        primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.show();

        Controller controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setRoot(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
