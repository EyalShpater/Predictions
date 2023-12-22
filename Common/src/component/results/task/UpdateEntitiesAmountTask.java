package component.results.task;

import component.results.ResultsController;
import impl.EntitiesAmountDTO;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import servlet.request.RequestHandler;
import component.results.task.helper.EntityPopulationData;

import java.io.IOException;
import java.util.Map;

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
                EntitiesAmountDTO entitiesAmountDTO = RequestHandler.getSimulationEntitiesAmountMap(serialNumber);
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
        } while (!RequestHandler.isEnded(serialNumber));

        return true;
    }

    private boolean simulationStarted(int serialNumber) {
        try {
            return RequestHandler.hasStarted(serialNumber);
        } catch (Exception e) {
            return false;
        }
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

    private boolean sleepIfSimulationHasNotStarted(int serialNumber) throws InterruptedException, IOException {
        while (!RequestHandler.hasStarted(serialNumber)) {
            Thread.sleep(1000);
        }

        return true;
    }


    public void setController(ResultsController controller) {
        this.resultsController = controller;
    }
}
