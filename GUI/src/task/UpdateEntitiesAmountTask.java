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
import task.helper.EntityPopulationData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateEntitiesAmountTask extends Task<Boolean> {

    private PredictionsLogic engine;
    private int serialNumber;
    private ResultsController resultsController;

    private IntegerProperty entityAmount;
    private StringProperty entityName;


    private TableView<EntityPopulationData> tableView; // Each task has its own TableView

    public UpdateEntitiesAmountTask(PredictionsLogic engine, int serialNumber, TableView<EntityPopulationData> entityPopulationDataTableView) {
        this.engine = engine;
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
                    //updateTableViewNew(entityNameToAmount , serialNumber);
                });
            } else {
                Platform.runLater(() -> {
                    tableView.setVisible(false); // Hide the TableView
                });
                sleepIfSimulationHasNotStarted(serialNumber); // todo: use wait and notify ?
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
            //System.out.println("Entity: " + entityName + ", Population: " + population);
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
        //controller.bindTaskProperties(this);
    }
    /*private void updateTableViewNew(Map<String, Integer> entityNameToAmount , int serialNumber)  {
        ObservableList<EntityPopulationData> entityPopulationList = FXCollections.observableArrayList();

        if (simulationStarted(serialNumber)){
            for (String entityName : entityNameToAmount.keySet()) {
                Integer population = entityNameToAmount.getOrDefault(entityName, 0); // Use 0 if not found
                EntityPopulationData entityPopulationData = new EntityPopulationData(entityName, population);
                entityPopulationList.add(entityPopulationData);
                System.out.println("Entity: " + entityName + ", Population: " + population);
            }
        }else{
            Map<String, Integer> entityPopulationFirstInit = engine.getEntitiesToPopulation();
            for (String entityName : entityPopulationFirstInit.keySet()) {
                Integer initialPopulation = entityPopulationFirstInit.getOrDefault(entityName, 0); // Use 0 as the initial value
                EntityPopulationData entityPopulationData = new EntityPopulationData(entityName, initialPopulation);
                entityPopulationList.add(entityPopulationData);
                try {
                    sleepIfSimulationHasNotStarted(serialNumber);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }*/
    /*private TableView<EntityPopulationData> createTableView() {
        TableView<EntityPopulationData> tableView = new TableView<>();
        TableColumn<EntityPopulationData, String> entityNameCol = new TableColumn<>("Entity Name");
        TableColumn<EntityPopulationData, Integer> populationCol = new TableColumn<>("Population");

        entityNameCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        populationCol.setCellValueFactory(new PropertyValueFactory<>("population"));

        tableView.getColumns().addAll(entityNameCol, populationCol);

        return tableView;
    }*/
}
