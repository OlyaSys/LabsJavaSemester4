package sample.controller;

import entity.Cars;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
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
    @FXML
    public Button btnAdd;

    public TextField textId;
    public Stage stage;

    @FXML
    public Button btnSet;

    public TextField idField;
    public TextField numberField;
    public TextField colorField;
    public TextField markField;
    public TextField foreignField;
    public Button newBtnAdd;

    public void setBtnSet(Button btnSet) { this.btnSet = btnSet; }

    public void setIdField(TextField idField) {
        this.idField = idField;
    }

    public void setNumberField(TextField numberField) {
        this.numberField = numberField;
    }

    public void setColorField(TextField colorField) {
        this.colorField = colorField;
    }

    public void setMarkField(TextField markField) {
        this.markField = markField;
    }

    public void setForeignField(TextField foreignField) {
        this.foreignField = foreignField;
    }

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

    public Button createButton(String name) {
        Button btn = new Button(name);
        btn.setVisible(true);
        btn.setLayoutX(105);
        btn.setLayoutY(260);
        btn.setMinWidth(100);
        btn.setMinHeight(30);
        return btn;
    }

    public TextField createTextField(String text, int y, AnchorPane page) {
        TextField textField = new TextField(text);
        textField.setLayoutX(100);
        textField.setLayoutY(y);
        page.getChildren().add(textField);

        return textField;
    }

    public void createStage(String name, String fxml, List<String> text, int w, int h) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(sample.Stage.class.getResource(fxml));

        AnchorPane page = loader.load();
        stage = sample.Stage.createStage(page, name, stage, w, h);
        CarController controller = loader.getController();
        controller.setStage(stage);
        controller.setTableCar(tableCars);
        controller.setOutWindow(outWindow);
        controller.setBtnSet(btnSet);

        numberField = createTextField(text.get(1), 70, page);
        colorField = createTextField(text.get(2), 120, page);
        markField = createTextField(text.get(3), 170, page);
        foreignField = createTextField(text.get(4), 220, page);

        foreignField.setPromptText("1 or 0");

        controller.setIdField(idField);
        controller.setNumberField(numberField);
        controller.setColorField(colorField);
        controller.setMarkField(markField);
        controller.setForeignField(foreignField);

        if (name.equals("Set")) {
            Label idLabel = new Label("Id:");
            idLabel.setLayoutX(30);
            idLabel.setLayoutY(20);
            //idLabel.setStyle("name:Verdana");
            idLabel.setFont(new Font("Verdana", 14));
            page.getChildren().add(idLabel);

            idField = createTextField(text.get(0), 20, page);

            btnSet = createButton("Set");
            page.getChildren().add(btnSet);

            btnSet.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    setCar();
                }
            });
        } else {
            newBtnAdd = createButton("Add");
            page.getChildren().add(newBtnAdd);

            newBtnAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    addCar();
                }
            });
        }

        stage.showAndWait();
        stage.close();
    }

    public void openStage() throws IOException {
        List<String> text = Arrays.asList("", "", "", "", "");
        createStage("Add", "fxml/addCar.fxml", text, 300, 300);
    }

    public void addCar() {
        try {
            if (!(colorField.getText().isEmpty() && markField.getText().isEmpty()
                    && numberField.getText().isEmpty() && foreignField.getText().isEmpty())) {
                String json = "{\n\t\"number\": \"" + numberField.getText()
                        + "\",\n\t\"color\": \"" + colorField.getText()
                        + "\",\n\t\"mark\": \"" + markField.getText()
                        + "\",\n\t\"foreign\": " + Integer.parseInt(foreignField.getText()) + "\n}";

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
            if (!idField.getText().isEmpty() && !(numberField.getText().isEmpty() && colorField.getText().isEmpty()
                    && markField.getText().isEmpty() && foreignField.getText().isEmpty())) {

                Integer foreign;
                if (foreignField.getText().isEmpty()) {
                    foreign = null;
                } else {
                    foreign = Integer.parseInt(foreignField.getText());
                }

                String json = "{\n" +
                        "        \"number\": \"" + numberField.getText() + "\",\n" +
                        "        \"color\": \"" + colorField.getText() + "\",\n" +
                        "        \"mark\": \"" + markField.getText() + "\",\n" +
                        "        \"foreign\": " + foreign + "\n" +
                        "    }";

                JSONObject jsonObject = new JSONObject(JSON.doPOSTRequest("http://localhost:8080/bt/setCar/" + idField.getText(), json));

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

    public void selectContractContractTab() throws IOException {
        try {
            ObservableList<JSONObject> carsObject = JSON.getListOfJSONObject("http://localhost:8080/bt/cars");
            Cars car = tableCars.getSelectionModel().getSelectedItem();

            List<String> text = Arrays.asList(car.getId().toString(), car.getNumber(), car.getColor(),
                    car.getMark(), car.getForeign().toString());
            createStage("Set", "fxml/addCar.fxml", text, 310, 310);

        } catch (JSONException e) {
            outWindow.appendText("\nField is empty!");
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
