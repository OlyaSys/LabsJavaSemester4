package javaFX;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdbc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class ExtraController {

    public Button newBtnFilter;
    public TextField textFilter1;
    public TextField textFilter2;
    public TextArea filterWindow;

    public TextField textAdd1;
    public TextField textAdd2;
    public TextArea addWindow;

    public TextField textDel;
    public TextArea deleteWindow;

    public TextField textChange1;
    public TextField textChange2;
    public TextArea changeWindow;

    public TextField textPrice;
    public TextArea priceWindow;

    public TableView<Table> tableUsers;
    public Stage stage;

    public void setTable(TableView<Table> tableUsers) {
        this.tableUsers = tableUsers;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void extraAdd() {
        try (Connection connection = JDBCUtils.getNewConnection()) {
            try {
                String str = JDBCUtils.add(connection, textAdd1.getText(), Integer.parseInt(textAdd2.getText()));
                if (str.equals("")) {
                    tableUsers.setItems(JDBCUtils.showAll(connection));
                    stage.close();
                } else {
                    addWindow.setText(str);
                }
            } catch (Exception e) {
                addWindow.setText("Enter product!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void extraDelete() {
        try (Connection connection = JDBCUtils.getNewConnection()) {
            try {
                String str = JDBCUtils.delete(connection, textDel.getText());
                if (str.equals("")) {
                    tableUsers.setItems(JDBCUtils.showAll(connection));
                    stage.close();
                } else {
                    deleteWindow.setText(str);
                }
            } catch (Exception e) {
                deleteWindow.setText("Enter product!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void extraChange() {
        try (Connection connection = JDBCUtils.getNewConnection()) {
            try {
                String str = JDBCUtils.changePrice(connection, textChange1.getText(), Integer.parseInt(textChange2.getText()));
                if (str.equals("")) {
                    tableUsers.setItems(JDBCUtils.showAll(connection));
                    stage.close();
                } else {
                    changeWindow.setText(str);
                }
            } catch (Exception e) {
                changeWindow.setText("Enter product!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void extraGetPrice() {
        try (Connection connection = JDBCUtils.getNewConnection()) {
            try {
                priceWindow.setText(JDBCUtils.price(connection, textPrice.getText()));
            } catch (Exception e) {
                priceWindow.setText("Enter product!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void extraFilter() {
        try (Connection connection = JDBCUtils.getNewConnection()) {
            try {
                ObservableList<Table> newTable = JDBCUtils.filterByPrice(connection, Integer.parseInt(textFilter1.getText()),
                        Integer.parseInt(textFilter2.getText()));

                if (newTable != null) {
                    tableUsers.setItems(newTable);
                    stage.close();

                } else {
                    filterWindow.setText("Products are not found");
                }
            } catch (Exception e) {
                filterWindow.setText("Enter product!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
