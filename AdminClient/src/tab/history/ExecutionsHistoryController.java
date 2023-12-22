package tab.history;

import component.results.analyze.AnalyzePaginationController;
import component.results.details.DetailsController;
import component.results.helper.Category;
import component.results.list.SimulationsListController;
import general.constants.GeneralConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

public class ExecutionsHistoryController {

    @FXML
    private TableView<Category> simulationsListViewComponent;

    @FXML
    private SimulationsListController simulationsListViewComponentController;

    @FXML
    private Accordion detailsComponent;

    @FXML
    private DetailsController detailsComponentController;

    @FXML
    private void initialize() {
        simulationsListViewComponentController.setUserName(GeneralConstants.ADMIN_USER_NAME);
        simulationsListViewComponentController.setOnSelectionChange(this::onSelectedSimulationChange);
        detailsComponentController.isSelectedSimulationEndedProperty().set(true);
        detailsComponentController.disableEntityAmountTable();
    }

    private void onSelectedSimulationChange(Category newValue) {
        detailsComponentController.onSelectedSimulationChange(newValue);
    }
}
