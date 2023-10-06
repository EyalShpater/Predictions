package tab.history;

import component.results.analyze.AnalyzePaginationController;
import component.results.helper.Category;
import component.results.list.SimulationsListController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;

public class ExecutionsHistoryController {

    @FXML
    private ListView<Category> simulationsListViewComponent;

    @FXML
    private SimulationsListController simulationsListViewComponentController;

    @FXML
    private Pagination analyzePaginationComponent;

    @FXML
    private AnalyzePaginationController analyzePaginationComponentController;
}
