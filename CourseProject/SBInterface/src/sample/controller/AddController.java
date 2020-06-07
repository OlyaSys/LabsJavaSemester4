package sample.controller;

import entity.Works;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import sample.JSON;
import sample.StageConfig;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public TextField textDate;

    public TableView<Works> tableWorks;
    public TextArea outWindow;

    public ComboBox<String> carsComboBox;
    public ComboBox<String> mastersComboBox;
    public ComboBox<String> servicesComboBox;
    public Stage stage;

    public void setTextDate(TextField textDate) {this.textDate = textDate;}
    public void setCarsComboBox(ComboBox<String> carsComboBox) {this.carsComboBox = carsComboBox;}
    public void setMastersComboBox(ComboBox<String> mastersComboBox) {this.mastersComboBox = mastersComboBox;}
    public void setServicesComboBox(ComboBox<String> servicesComboBox) {this.servicesComboBox = servicesComboBox;}
    public void setStage(Stage stage) {this.stage = stage;}

    public void addWork() {
        try {
            String date = textDate.getText();
            int carID = carsComboBox.getSelectionModel().getSelectedIndex() + 1;
            int masterID = mastersComboBox.getSelectionModel().getSelectedIndex() + 1;
            int serviceID = servicesComboBox.getSelectionModel().getSelectedIndex() + 1;

            if (!date.isEmpty()) {
                String json = "{\n\t\"dateWork\": \"" + date
                        + "\",\n\t\"masters\": {\n\t\t\"id\": " + masterID + "\n\t}," +
                        "\n\t\"cars\": {\n\t\t\"id\": " + carID + "\n\t}," +
                        "\n\t\"services\": {\n\t\t\"id\": " + serviceID + "\n\t}\n}";

                JSONObject jsonObject = new JSONObject(JSON.doPOSTRequest("http://localhost:8080/bt/addWork", json));

                ObservableList<Works> workRows = FXCollections.observableArrayList();
                workRows.add(JSON.getWork(jsonObject));
                tableWorks.setItems(workRows);
                stage.close();
                outWindow.appendText("\nWork is added successfully");
            } else {
                outWindow.appendText("\nWrong record of Work");
            }

        } catch (IOException | JSONException e) {
            outWindow.appendText("\nWrong record of Work");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageConfig.setAddController(this);
        outWindow = StageConfig.getWorkController().outWindow;
        tableWorks = StageConfig.getWorkController().tableWorks;
    }
}
