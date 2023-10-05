package task;

import component.results.ResultsController;
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
import task.helper.EntityPopulationData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateEntitiesAmountTask extends Task<Boolean> {
    private int serialNumber;
    private ResultsController resultsController;

    private IntegerProperty entityAmount;
    private StringProperty entityName;

    private TableView<EntityPopulationData> tableView; // Each task has its own TableView

    public UpdateEntitiesAmountTask(int serialNumber, TableView<EntityPopulationData> entityPopulationDataTableView) {
        this.serialNumber = serialNumber;
        this.entityAmount = new SimpleIntegerProperty();
        this.entityName = new SimpleStringProperty();
        this.tableView = entityPopulationDataTableView;

    }

    @Override
    protected Boolean call() throws Exception {
        do {
            if (simulationStarted(serialNumber)) {
                EntitiesAmountDTO entitiesAmountDTO = engine.getSimulationEntitiesAmountMap(serialNumber);
                Map<String, Long> entityNameToAmount = entitiesAmountDTO.getEntityToPopulationMap();

                Platform.runLater(() -> {
                    tableView.setVisible(true);
                    updateTableView(entityNameToAmount);
                });
            } else {
                Platform.runLater(() -> {
                    tableView.setVisible(false); // Hide the TableView
                });
                sleepIfSimulationHasNotStarted(serialNumber);
            }

            Thread.sleep(100);
        } while (!engine.isEnded(serialNumber));

        return true;
    }

    private boolean simulationStarted(int serialNumber) {
        return engine.hasStarted(serialNumber);
    }


    private void updateTableView(Map<String, Long> entityNameToAmount) {
        ObservableList<EntityPopulationData> entityPopulationList = FXCollections.observableArrayList();

        for (String entityName : entityNameToAmount.keySet()) {
            Long population = entityNameToAmount.get(entityName);
            EntityPopulationData entityPopulationData = new EntityPopulationData(entityName, population);
            entityPopulationList.add(entityPopulationData);
        }

        tableView.setItems(entityPopulationList);
    }

    private boolean sleepIfSimulationHasNotStarted(int serialNumber) throws InterruptedException {
        while (!engine.hasStarted(serialNumber)) {
            Thread.sleep(1000);
        }
        return engine.hasStarted(serialNumber);
    }


    public void setController(ResultsController controller) {
        this.resultsController = controller;
    }
}
