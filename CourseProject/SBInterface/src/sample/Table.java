package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class Table {
    public static void createTable(TableView<?> table, List<String> columnName) {
        ObservableList<?> columns = table.getColumns();

        for (int i = 0; i < columns.size(); i++) {
            ((TableColumn<?, ?>) columns.get(i)).setCellValueFactory(new PropertyValueFactory<>(columnName.get(i)));
        }
    }
}
