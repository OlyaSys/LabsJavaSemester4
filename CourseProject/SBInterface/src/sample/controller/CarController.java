package sample.controller;

import entity.Cars;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.stage.Stage;
import sample.JSON;
import sample.StageConfig;
import sample.Table;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CarController implements Initializable {
    public TableView<Cars> tableCars;
    public TextArea outWindow;
    public Button btnAdd;
    public TextField textId;
    public TextField textMark;

    public Stage stage;
    public TextField textColor;
    public TextField textNumber;
    public TextField textForeign;
    public TextField newTextId;
    public Button btnSet;

    private void setStage(Stage stage) {this.stage = stage;}
    private void setTableCar(TableView<Cars> tableCars) {this.tableCars = tableCars;}
    private void setOutWindow(TextArea outWindow) {this.outWindow = outWindow;}

    public void showCars() throws IOException {
        List<String> carsColumn = Arrays.asList("id", "number", "color", "mark", "foreign");
        Table.createTable(tableCars, carsColumn);

        String url = "http://localhost:8080/bt/cars";
        ObservableList<Cars> carRows = FXCollections.observableArrayList();
        ObservableList<JSONObject> carsObject = JSON.getListOfJSONObject(url);

        for (int i = 0; i < JSON.getListOfJSONObject(url).size(); i++) {
            carRows.add(JSON.getCar(carsObject.get(i)));
        }
        tableCars.setItems(carRows);
    }

    public void findCarById() {
        try {
            JSONObject jsonObject = JSON.getId("http://localhost:8080/bt/cars/byId/", textId);

            if (!jsonObject.isEmpty()) {
                showCarInfo(jsonObject);
            } else {
                outWindow.appendText("\nSuch record does not exist!");
            }

        } catch(NumberFormatException | IOException e) {
            outWindow.appendText("\nWrong number of parameters!");
        }
    }

    public void createStage(String name, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(sample.Stage.class.getResource(fxml));

        AnchorPane newPage = loader.load();
        stage = sample.Stage.createStage(newPage, name, stage, 310, 310);
        CarController controller = loader.getController();
        controller.setStage(stage);
        controller.setTableCar(tableCars);
        controller.setOutWindow(outWindow);

        stage.showAndWait();
        stage.close();
    }

    public void openStage() throws IOException {
        createStage("Add&Set", "../fxml/addCar.fxml");
    }

    public void addCar() {
        try {
            if (!(textColor.getText().isEmpty() && textMark.getText().isEmpty()
                    && textNumber.getText().isEmpty() && textForeign.getText().isEmpty())) {
                String json = "{\n\t\"number\": \"" + textNumber.getText()
                        + "\",\n\t\"color\": \"" + textColor.getText()
                        + "\",\n\t\"mark\": \"" + textMark.getText()
                        + "\",\n\t\"foreign\": " + Integer.parseInt(textForeign.getText()) + "\n}";

                JSONObject jsonObject = new JSONObject(JSON.doPOSTRequest("http://localhost:8080/bt/addCar", json));

                ObservableList<Cars> carRow = FXCollections.observableArrayList();
                carRow.add(JSON.getCar(jsonObject));
                tableCars.setItems(carRow);
                stage.close();
                outWindow.appendText("\nCar is added successfully");
            } else {
                outWindow.appendText("\nWrong record of Car");
            }

        } catch (IOException | JSONException | NumberFormatException e) {
            outWindow.appendText("\nWrong record of Car");
        }
    }

    public void setCar() {
        try {
            if (!newTextId.getText().isEmpty() && !(textNumber.getText().isEmpty() && textColor.getText().isEmpty()
                    && textMark.getText().isEmpty() && textForeign.getText().isEmpty())) {
                Integer foreign;
                if (textForeign.getText().isEmpty()) {
                    foreign = null;
                } else {
                    foreign = Integer.parseInt(textForeign.getText());
                }

                String json = "{\n" +
                        "        \"number\": \"" + textNumber.getText() + "\",\n" +
                        "        \"color\": \" " + textColor.getText() + " \",\n" +
                        "        \"mark\": \"" + textMark.getText() + "\",\n" +
                        "        \"foreign\": " + foreign + "\n" +
                        "    }";
                System.out.println(json);

                JSONObject jsonObject = new JSONObject(JSON.doPOSTRequest("http://localhost:8080/bt/setCar/" + newTextId.getText(), json));

                ObservableList<Cars> carRow = FXCollections.observableArrayList();
                carRow.add(JSON.getCar(jsonObject));
                tableCars.setItems(carRow);
                stage.close();
                outWindow.appendText("\nCar is update successfully");
            } else {
                outWindow.appendText("\nWrong record of Car!");
            }

        } catch (IOException | JSONException | NumberFormatException e) {
            outWindow.appendText("\nWrong record of Car!");
        }
    }

    public void showCarInfo(JSONObject jsonObject) {
        try {
            Cars car = JSON.getCar(jsonObject);
            outWindow.appendText("\n" + car.toString());
        } catch (JSONException e) {
            outWindow.appendText("\nSuch record does not exist!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageConfig.setCarController(this);
        if (StageConfig.getController().role.equals("ROLE_ADMIN")) {
            btnAdd.setVisible(true);
        }
    }
}
