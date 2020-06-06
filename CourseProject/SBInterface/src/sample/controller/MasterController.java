package sample.controller;

import entity.Masters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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

public class MasterController implements Initializable {
    public TableView<Masters> tableMaster;
    public TextArea outWindow;
    public TextField textId;
    public Button btnAdd;
    public Stage stage;
    public TextField textName;
    public TextField newTextId;

    public void setTableMaster(TableView<Masters> tableMaster) {this.tableMaster = tableMaster;}

    public void setStage(Stage stage) {this.stage = stage;}

    public void setOutWindow(TextArea outWindow) {this.outWindow = outWindow;}

    public void showMasters() throws IOException {
        List<String> mastersColumn = Arrays.asList("id", "nameMaster");
        Table.createTable(tableMaster, mastersColumn);

        String url = "http://localhost:8080/bt/masters";
        ObservableList<Masters> masterRows = FXCollections.observableArrayList();
        ObservableList<JSONObject> mastersObject = JSON.getListOfJSONObject(url);

        for (int i = 0; i < JSON.getListOfJSONObject(url).size(); i++) {
            masterRows.add(JSON.getMaster(mastersObject.get(i)));
        }
        tableMaster.setItems(masterRows);
    }

    public void findMasterById() {
        try {
            JSONObject jsonObject = JSON.getId("http://localhost:8080/bt/masters/byId/", textId);

            if (!jsonObject.isEmpty()) {
                showMasterInfo(jsonObject);
            } else {
                outWindow.appendText("\nSuch record does not exist!");
            }

        } catch(NumberFormatException | IOException e) {
            outWindow.appendText("\nWrong number of parameters!");
        }
    }

    public void openAddStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(sample.Stage.class.getResource("../fxml/addMaster.fxml"));
        AnchorPane page = loader.load();

        stage = sample.Stage.createStage(page, "Add", stage, 290, 290);

        MasterController controller = loader.getController();
        controller.setStage(stage);
        controller.setTableMaster(tableMaster);
        controller.setOutWindow(outWindow);

        stage.showAndWait();
        stage.close();
    }

    public void addMaster() {
        try {
            if (!textName.getText().isEmpty()) {
                String json = "{\n\t\"nameMaster\": \"" + textName.getText() + "\"\n}";

                JSONObject jsonObject = new JSONObject(JSON.doPOSTRequest("http://localhost:8080/bt/addMaster", json));

                ObservableList<Masters> masterRow = FXCollections.observableArrayList();
                masterRow.add(JSON.getMaster(jsonObject));
                System.out.println(masterRow);
                tableMaster.setItems(masterRow);
                stage.close();
                outWindow.appendText("\nMaster is added successfully");
            } else {
                outWindow.appendText("\nWrong record of Master");
            }

        } catch (IOException | JSONException e) {
            outWindow.appendText("\nWrong record of Master");
        }
    }

    public void setMaster() {
        try {
            if (!newTextId.getText().isEmpty() && !textName.getText().isEmpty()) {
                String json = "{\n\"nameMaster\": \"" + textName.getText() + "\"\n}";

                JSONObject jsonObject = new JSONObject(JSON.doPOSTRequest("http://localhost:8080/bt/setMaster/" + newTextId.getText(), json));

                ObservableList<Masters> masterRow = FXCollections.observableArrayList();
                masterRow.add(JSON.getMaster(jsonObject));
                tableMaster.setItems(masterRow);
                stage.close();
                outWindow.appendText("\nMaster is update successfully");
            } else {
                outWindow.appendText("\nWrong record of Master!");
            }

        } catch (IOException | JSONException | NumberFormatException e) {
            outWindow.appendText("\nWrong record of Master!");
        }
    }

    private void showMasterInfo(JSONObject jsonObject) {
        try {
            Masters master = JSON.getMaster(jsonObject);
            outWindow.appendText("\n" + master.toString());
        } catch (JSONException e) {
            outWindow.appendText("\nSuch record does not exist!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageConfig.setMasterController(this);
        if (StageConfig.getController().role.equals("ROLE_ADMIN")) {
            btnAdd.setVisible(true);
        }
    }
}
