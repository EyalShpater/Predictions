package task;

import execution.simulation.api.PredictionsLogic;
import impl.EntitiesAmountDTO;
import impl.SimulationRunDetailsDTO;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.tab.results.ResultsController;
import javafx.tab.results.progress.ProgressController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateEntitiesAmountTask extends Task<Boolean> {

    private PredictionsLogic engine;
    private int serialNumber;
    private ResultsController resultsController;

    private IntegerProperty entityAmount;
    private StringProperty entityName;
//    private StackPane stackPaneForEntitiesPopulation;

    private TableView<EntityPopulationData> tableView; // Each task has its own TableView

    public UpdateEntitiesAmountTask(PredictionsLogic engine, int serialNumber, TableView<EntityPopulationData> entityPopulationDataTableView) {
        this.engine = engine;
        this.serialNumber = serialNumber;
        this.entityAmount = new SimpleIntegerProperty();
        this.entityName = new SimpleStringProperty();
        this.tableView = entityPopulationDataTableView;
//        this.tableView = createTableView(); // Create a TableView for this task
    }

    @Override
    protected Boolean call() throws Exception {
        do {
            sleepIfSimulationHasNotStarted(serialNumber); // todo: use wait and notify ?
            EntitiesAmountDTO entitiesAmountDTO = engine.getSimulationEntitiesAmountMap(serialNumber);
            Map<String, Integer> entityNameToAmount = entitiesAmountDTO.getEntityToPopulationMap();

            Platform.runLater(() -> updateTableView(entityNameToAmount)); // Update the TableView

            Thread.sleep(100);
        } while (!engine.isEnded(serialNumber));

        return true;
    }

    private void sleepIfSimulationHasNotStarted(int serialNumber) throws InterruptedException {
        while (!engine.hasStarted(serialNumber)) {
            Thread.sleep(1000);
        }
    }

    private void updateTableView(Map<String, Integer> entityNameToAmount) {
        ObservableList<EntityPopulationData> entityPopulationList = FXCollections.observableArrayList();

        // Populate entityPopulationList with data from entityNameToAmount
        for (Map.Entry<String, Integer> entry : entityNameToAmount.entrySet()) {
            String entityName = entry.getKey();
            Integer population = entry.getValue();
            EntityPopulationData entityPopulationData = new EntityPopulationData(entityName, population);
            entityPopulationList.add(entityPopulationData);
        }

        // Update the data in the TableView
        tableView.setItems(entityPopulationList);

//        // Ensure the TableView is displayed in the UI
//        if (!stackPaneForEntitiesPopulation.getChildren().contains(tableView)) {
//            stackPaneForEntitiesPopulation.getChildren().add(tableView);
//        }
    }

    private TableView<EntityPopulationData> createTableView() {
        TableView<EntityPopulationData> tableView = new TableView<>();
        TableColumn<EntityPopulationData, String> entityNameCol = new TableColumn<>("Entity Name");
        TableColumn<EntityPopulationData, Integer> populationCol = new TableColumn<>("Population");

        entityNameCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        populationCol.setCellValueFactory(new PropertyValueFactory<>("population"));

        tableView.getColumns().addAll(entityNameCol, populationCol);

        return tableView;
    }

    public void setController(ResultsController controller) {
        this.resultsController = controller;
        //controller.bindTaskProperties(this);
    }
}
