package component.results.list;

import component.results.helper.Category;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimulationsListController {

    @FXML
    private ListView<Category> simulationsListView;

    private Consumer<Category> onSelectionChange;
//    private Supplier<List<Category>>

    @FXML
    private void initialize() {
        simulationsListView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onSelectionChange.accept(newValue));
    }

    public void setOnSelectionChange(Consumer<Category> onSelectionChange) {
        this.onSelectionChange = onSelectionChange;
    }
}
