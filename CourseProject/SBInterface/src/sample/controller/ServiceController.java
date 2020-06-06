package sample.controller;

import entity.Services;
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

public class ServiceController implements Initializable {
    public TableView<Services> tableService;
    public TextArea outWindow;
    public TextField textId;
    public Button btnAdd;
    public Stage stage;
    public TextField textName;
    public TextField textOurCost;
    public TextField textForeignCost;
    public TextField newTextId;

    private void setOutWindow(TextArea outWindow) { this.outWindow = outWindow; }

    private void setTableCar(TableView<Services> tableService) { this.tableService = tableService; }

    private void setStage(Stage stage) { this.stage = stage; }

    public void showServices() throws IOException {
        List<String> servicesColumn = Arrays.asList("id", "name", "costOur", "costForeign");
        Table.createTable(tableService, servicesColumn);

        String url = "http://localhost:8080/bt/services";
        ObservableList<Services> serviceRows = FXCollections.observableArrayList();
        ObservableList<JSONObject> servicesObject = JSON.getListOfJSONObject(url);

        for (int i = 0; i < JSON.getListOfJSONObject(url).size(); i++) {
            serviceRows.add(JSON.getService(servicesObject.get(i)));
        }
        tableService.setItems(serviceRows);
    }

    public void findServiceById() {
        try {
            JSONObject jsonObject = JSON.getId("http://localhost:8080/bt/services/byId/", textId);

            if (!jsonObject.isEmpty()) {
                showServiceInfo(jsonObject);
            } else {
                outWindow.appendText("\nSuch record does not exist!");
            }

        } catch(NumberFormatException | IOException e) {
            outWindow.appendText("\nWrong number of parameters!");
        }
    }

    public Integer getIntegerField(TextField text) {
        if (text.getText().isEmpty()) {
            return null;
        } else {
            return Integer.parseInt(text.getText());
        }
    }

    public void setCar() {
        try {
            if (!newTextId.getText().isEmpty()
                    && !(textName.getText().isEmpty() && textOurCost.getText().isEmpty() && textForeignCost.getText().isEmpty())) {

                Integer foreignCost = getIntegerField(textForeignCost);
                Integer ourCost = getIntegerField(textOurCost);

                String json = "{\n" +
                        "\t\"name\": \"" + textName.getText() + "\",\n" +
                        "\t\"costOur\": " + ourCost + ",\n" +
                        "\t\"costForeign\": " + foreignCost + "\n}";

                JSONObject jsonObject = new JSONObject(JSON.doPOSTRequest("http://localhost:8080/bt/setService/" + newTextId.getText(), json));

                System.out.println(jsonObject);
                ObservableList<Services> serviceRow = FXCollections.observableArrayList();
                serviceRow.add(JSON.getService(jsonObject));
                tableService.setItems(serviceRow);
                stage.close();
                outWindow.appendText("\nService is update successfully");
            } else {
                outWindow.appendText("\nWrong record of Service!");
            }

        } catch (IOException | JSONException | NumberFormatException e) {
            outWindow.appendText("\nWrong record of Service!");
        }
    }

    public void showServiceInfo(JSONObject jsonObject) {
        try {
            Services service = JSON.getService(jsonObject);
            outWindow.appendText("\n" + service.toString());
        } catch (JSONException e) {
            outWindow.appendText("\nSuch record does not exist!");
        }
    }

    public void openAddStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(sample.Stage.class.getResource("../fxml/addService.fxml"));
        AnchorPane newPage = loader.load();

        stage = sample.Stage.createStage(newPage, "Add", stage, 300, 300);

        ServiceController controller = loader.getController();
        controller.setStage(stage);
        controller.setTableCar(tableService);
        controller.setOutWindow(outWindow);

        stage.showAndWait();
        stage.close();
    }

    public void addService() {
        try {
            if (!(textName.getText().isEmpty() && textOurCost.getText().isEmpty() && textForeignCost.getText().isEmpty())) {
                String json = "{\n\t\"name\": \"" + textName.getText()
                        + "\",\n\t\"costOur\": " + Integer.parseInt(textOurCost.getText())
                        + ",\n\t\"costForeign\": " + Integer.parseInt(textForeignCost.getText()) + "\n}";

                JSONObject jsonObject = new JSONObject(JSON.doPOSTRequest("http://localhost:8080/bt/addService", json));

                ObservableList<Services> serviceRow = FXCollections.observableArrayList();
                serviceRow.add(JSON.getService(jsonObject));
                System.out.println(serviceRow);
                tableService.setItems(serviceRow);
                stage.close();
                outWindow.appendText("\nService is added successfully");
            } else {
                outWindow.appendText("\nWrong record of Service");
            }

        } catch (IOException | JSONException | NumberFormatException e) {
            outWindow.appendText("\nWrong record of Service");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageConfig.setServiceController(this);
        if (StageConfig.getController().role.equals("ROLE_ADMIN")) {
            btnAdd.setVisible(true);
        }
    }
}
