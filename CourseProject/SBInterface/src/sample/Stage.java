package sample;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

import java.io.IOException;

public class Stage {
    public static javafx.stage.Stage createStage(AnchorPane page, String name, javafx.stage.Stage stage, int w, int h) throws IOException {
        stage = new javafx.stage.Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(name);
        stage.setScene(new Scene(page, w, h));

        return stage;
    }
}
