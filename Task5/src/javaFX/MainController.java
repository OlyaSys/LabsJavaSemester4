package javaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import jdbc.utils.JDBCUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;

public class MainController {
    public Button btnShow;
    public Button btnAdd;
    public Button btnFill;
    public TextField textNum;
    public Button btnDel;
    public Button btnPrice;
    public Button btnChange;
    public TextArea outWindow;
    public Button btnFilter;

    @FXML
    public TableView<Table> tableUsers;

    @FXML
    private TableColumn<Table, Integer> idColumn;

    @FXML
    private TableColumn<Table, String> prodidColumn;

    @FXML
    private TableColumn<Table, String> titleColumn;

    @FXML
    private TableColumn<Table, String> costColumn;

    private boolean isPressed = false;

    public void createNewStage(String fxml, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        AnchorPane page = (AnchorPane) loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(name);
        stage.setScene(new Scene(page, 300, 300));

        ExtraController controller = loader.getController();
        controller.setTable(tableUsers);
        controller.setStage(stage);

        stage.showAndWait();
        stage.close();
    }

    public void showAll(javafx.event.ActionEvent actionEvent) {
        try (Connection connection = JDBCUtils.getNewConnection()) {
            if (JDBCUtils.showAll(connection) == null) {
                outWindow.appendText("\nNo records");
            }
            else {
                idColumn.setCellValueFactory(new PropertyValueFactory<Table, Integer>("id"));
                prodidColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("prodid"));
                titleColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("title"));
                costColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("cost"));

                tableUsers.setItems(JDBCUtils.showAll(connection));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void fillTable() {
        try(Connection connection = JDBCUtils.getNewConnection()) {
            if (!isPressed) {
                if (!textNum.getText().equals("")) {
                    JDBCUtils.fillInTable(connection, Integer.parseInt(textNum.getText()));
                    isPressed = true;
                    outWindow.appendText("\nTable is filled successful");
                } else {
                    outWindow.appendText("\nEnter number!");
                }
            } else {
                outWindow.appendText("\nTable is empty!");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void add() throws IOException {
        if (isPressed)
            createNewStage("fxml/add.fxml", "Add");
        else
            outWindow.appendText("\nTable is empty!");
    }

    public void delete() throws IOException {
        if (isPressed)
            createNewStage("fxml/delete.fxml", "Delete");
        else
            outWindow.appendText("\nTable is empty!");
    }

    public void getPrice() throws IOException {
        if (isPressed)
            createNewStage("fxml/price.fxml", "Find out price");
        else
            outWindow.appendText("\nTable is empty!");
    }

    public void changePrice() throws IOException {
        if (isPressed)
            createNewStage("fxml/change.fxml", "Change price");
        else
            outWindow.appendText("\nTable is empty!");
    }

    public void filterByPrice() throws IOException {
        if (isPressed)
            createNewStage("fxml/filter.fxml", "Filter by price");
        else
            outWindow.appendText("\nTable is empty!");
    }

}


