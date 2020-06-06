package sample.controller;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import sample.JSON;
import sample.StageConfig;
import sample.Table;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class WorkController implements Initializable {
    @FXML
    public TableView<Works> tableWorks;
    public TextArea outWindow;

    public Boolean isClicked = false;
    public Boolean isClicked2= false;

    public TextField textId;
    public Button btnLogOut;

    private Stage primaryStage;
    public Stage stage;

    public void setStage(Stage stage) {this.stage = stage;}

    public void showWorkInfo(JSONObject jsonObject) {
        try {
            Integer id = (Integer) jsonObject.get("id");
            String date = (String) jsonObject.get("dateWork");

            JSONObject master = jsonObject.getJSONObject("masters");
            JSONObject car = jsonObject.getJSONObject("cars");
            JSONObject service = jsonObject.getJSONObject("services");

            outWindow.appendText("\nid: " + id
                    + "\ndate: " + date
                    + "\nmaster:"
                    + "\n   id: " + master.getInt("id")
                    + "\n   name master: " + master.getString("nameMaster")
                    + "\ncar: "
                    + "\n   id: " + car.getInt("id")
                    + "\n   number: " + car.getString("number")
                    + "\n   color: " + car.getString("color")
                    + "\n   mark: " + car.getString("mark")
                    + "\n   foreign: " + car.getInt("foreign")
                    + "\nservice: "
                    + "\n   id: " + service.getInt("id")
                    + "\n   name: " + service.getString("name")
                    + "\n   our cost: " + service.getInt("costOur")
                    + "\n   foreign cost: " + service.getInt("costForeign")
                    + "\n--------------------------------"
            );
        } catch (JSONException e) {
            outWindow.appendText("\nSuch record does not exist!");
        }
    }

    public void selectContractContractTab() throws IOException {
        try {
            ObservableList<JSONObject> worksObject = JSON.getListOfJSONObject("http://localhost:8080/bt/works");
            Works work = tableWorks.getSelectionModel().getSelectedItem();

            showWorkInfo(worksObject.get(work.getId() - 1));
        } catch (NullPointerException e) {
            outWindow.appendText("\nField is empty!");
        }
    }

    public void showWorks() throws IOException {
        List<String> worksColumn = Arrays.asList("id", "dateWork", "masters_id", "cars_id", "services_id");
        Table.createTable(tableWorks, worksColumn);

        String url = "http://localhost:8080/bt/works";
        ObservableList<Works> workRows = FXCollections.observableArrayList();
        ObservableList<JSONObject> worksObject = JSON.getListOfJSONObject(url);

        for (int i = 0; i < JSON.getListOfJSONObject(url).size(); i++) {
            workRows.add(JSON.getWork(worksObject.get(i)));
        }

        tableWorks.setItems(workRows);
    }

    public ComboBox<String> createComboBox(String url, String key, Integer x, Integer y) throws IOException {
        ObservableList<String> nameList = FXCollections.observableArrayList();
        ObservableList<JSONObject> objectList = JSON.getListOfJSONObject(url);

        for (int i = 0; i < JSON.getListOfJSONObject(url).size(); i++) {
            nameList.add((String) objectList.get(i).get(key));
        }

        ComboBox<String> comboBox = new ComboBox<>(nameList);
        comboBox.setValue(nameList.get(0));
        comboBox.setLayoutX(x);
        comboBox.setLayoutY(y);
        comboBox.setMaxWidth(100);

        return comboBox;
    }

    public void createStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/addWindow.fxml"));
        AnchorPane page = loader.load();

        TextField textDate = new TextField();
        textDate.setLayoutX(150);
        textDate.setLayoutY(25);
        textDate.setMaxWidth(100);
        textDate.setPromptText("dd_mm_year");

        ComboBox<String> carsComboBox = createComboBox("http://localhost:8080/bt/cars", "number", 150, 80);
        ComboBox<String> mastersComboBox = createComboBox("http://localhost:8080/bt/masters", "nameMaster", 150, 135);
        ComboBox<String> servicesComboBox = createComboBox("http://localhost:8080/bt/services", "name", 150, 190);

        page.getChildren().add(textDate);
        page.getChildren().add(carsComboBox);
        page.getChildren().add(mastersComboBox);
        page.getChildren().add(servicesComboBox);

        Stage addStage = new Stage();
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.setTitle("Add");
        addStage.setScene(new Scene(page, 300, 300));

        AddController controller = loader.getController();
        controller.setTextDate(textDate);
        controller.setCarsComboBox(carsComboBox);
        controller.setMastersComboBox(mastersComboBox);
        controller.setServicesComboBox(servicesComboBox);
        controller.setStage(addStage);

        addStage.show();
    }

    public void findWorkById() {
        try {
            if (!isClicked2) {
                outWindow.appendText("\nTo find by id enter id in the right field");
                isClicked2 = true;
            } else {
                JSONObject jsonObject = JSON.getId("http://localhost:8080/bt/works/byId/", textId);

                if (!jsonObject.isEmpty()) {
                    showWorkInfo(jsonObject);
                } else {
                    outWindow.appendText("\nSuch record does not exist!");
                }
            }

        } catch(NumberFormatException | IOException e) {
            outWindow.appendText("\nWrong number of parameters!");
        }
    }

    public void deleteWork() {
        try {
            if (!isClicked) {
                outWindow.appendText("\nTo delete enter id in the left field");
                isClicked = true;
            } else {
                String body = JSON.doDELETERequest("http://localhost:8080/bt/deleteWork/"
                        + Integer.parseInt(textId.getText()));

                if (body.isEmpty()) {
                    outWindow.appendText("\nWork is deleted successfully");
                } else {
                    outWindow.appendText("\nSuch work does not exist!");
                }
            }
        } catch(NumberFormatException | IOException | JSONException e) {
            outWindow.appendText("\nWrong number of parameters!");
        }
    }

    public void leaveStage() {
        stage.close();
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageConfig.setWorkController(this);
        primaryStage = StageConfig.getController().primaryStage;
    }
}
