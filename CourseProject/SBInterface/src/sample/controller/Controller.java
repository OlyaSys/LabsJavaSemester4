package sample.controller;

import entity.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.*;
import sample.StageConfig;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Stage primaryStage;
    public Stage stage;
    public AnchorPane root;

    public TextField textUsername;
    public TextField textPassword;
    public Button btnSignIn;

    String bearerToken;
    String role;

    public void setStage(Stage primaryStage) {this.primaryStage = primaryStage;}

    public void setRoot(AnchorPane root) {this.root = root;}

    public void createNewStage(String fxml, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        TabPane page = loader.load();

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(name);
        stage.setScene(new Scene(page, 1000, 550));

        WorkController controller = loader.getController();
        controller.setStage(stage);

        stage.showAndWait();
        stage.close();
    }

    public Boolean checkLogIn(String userName, String password) throws Exception {
        String json = "{\n\t\"userName\":\"" + userName + "\",\n\t\"password\":\"" + password + "\"\n}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/bt/auth/singin")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        System.out.println(jsonObject);
        if (jsonObject.has("token")) {
            User user = new User(jsonObject.getString("userName"), jsonObject.getString("token"));
            this.role = jsonObject.getJSONArray("role").get(0).toString();
            this.bearerToken = user.getToken();
            return true;
        }
        return false;
    }

    public void createLabel(String text) {
        Label label = new Label(text);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setLayoutX(57);
        label.setLayoutY(240);
        label.setTextFill(Color.web("#FF0000"));
        root.getChildren().add(label);
    }


    public void getAccess(){
        try {
            if (!checkLogIn(textUsername.getText(), textPassword.getText())) {
                createLabel("UserName or password are wrong!\nTry again!");
            } else {
                if (role.equals("ROLE_USER")) {
                    primaryStage.close();
                    createNewStage("../fxml/userWindow.fxml", "User");
                }
                else if(role.equals("ROLE_ADMIN")) {
                    primaryStage.close();
                    createNewStage("../fxml/userWindow.fxml", "Admin");
                }
            }
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageConfig.setController(this);
    }
}
