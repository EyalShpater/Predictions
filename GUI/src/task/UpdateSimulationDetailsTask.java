package task;

import execution.simulation.api.PredictionsLogic;
import impl.EntitiesAmountDTO;
import impl.SimulationRunDetailsDTO;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.tab.results.progress.ProgressController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateSimulationDetailsTask extends Task<Boolean> {

    private PredictionsLogic engine;
    private int serialNumber;

    private IntegerProperty ticks;
    private LongProperty seconds;

    private ProgressController controller;
    /*private StackPane stackPaneForEntitiesPopulation;
    private static final Map<Integer, TableView<EntityPopulationData>> tableViews = new HashMap<>();*/


    public UpdateSimulationDetailsTask(PredictionsLogic engine, int serialNumber, StackPane stackPaneForTableView) {
        this.engine = engine;
        this.serialNumber = serialNumber;
        //this.stackPaneForEntitiesPopulation = stackPaneForTableView;
        this.ticks = new SimpleIntegerProperty();
        this.seconds = new SimpleLongProperty();
    }

    @Override
    protected Boolean call() throws Exception {
        while (!engine.isEnded(serialNumber)) {
            SimulationRunDetailsDTO runDetails = engine.getSimulationRunDetail(serialNumber);
            EntitiesAmountDTO entitiesAmountDTO = engine.getSimulationEntitiesAmountMap(serialNumber);
            Map<String, Integer> entityNameToAmount = entitiesAmountDTO.getEntityToPopulationMap();

            System.out.println("runDetails");

            Platform.runLater(() -> ticks.set(runDetails.getTickNumber()));
            Platform.runLater(() -> seconds.set(runDetails.getRunningTime()));
            //Platform.runLater(() -> createOrUpdateTableViewForEntities(serialNumber, entityNameToAmount));

            updateProgress(runDetails.getStartProgress(), runDetails.getEndProgress());

            System.out.println("ticks: " + ticks.get());
            System.out.println("seconds: " + TimeUnit.MILLISECONDS.toSeconds(seconds.get()));

            Thread.sleep(200);
        }

        return true;
    }

   /* private void createOrUpdateTableViewForEntities(int simulationId, Map<String, Integer> entityNameToAmount) {
        TableView<EntityPopulationData> tableView = tableViews.computeIfAbsent(simulationId, id -> createTableView());

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

        // Ensure the TableView is displayed in the UI
        if (!stackPaneForEntitiesPopulation.getChildren().contains(tableView)) {
            stackPaneForEntitiesPopulation.getChildren().add(tableView);
        }

        // Hide other TableView instances if necessary
        tableViews.forEach((id, view) -> {
            if (id != simulationId) {
                if (stackPaneForEntitiesPopulation.getChildren().contains(view)) {
                    stackPaneForEntitiesPopulation.getChildren().remove(view);
                }
            }
        });
    }

    private TableView<EntityPopulationData> createTableView() {
        TableView<EntityPopulationData> tableView = new TableView<>();
        TableColumn<EntityPopulationData, String> entityNameCol = new TableColumn<>("Entity Name");
        TableColumn<EntityPopulationData, Integer> populationCol = new TableColumn<>("Population");

        entityNameCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        populationCol.setCellValueFactory(new PropertyValueFactory<>("population"));

        tableView.getColumns().addAll(entityNameCol, populationCol);

        return tableView;
    }*/

    public int getTicks() {
        return ticks.get();
    }

    public IntegerProperty ticksProperty() {
        return ticks;
    }

    public long getSeconds() {
        return seconds.get();
    }

    public LongProperty secondsProperty() {
        return seconds;
    }

    public void setController(ProgressController controller) {
        this.controller = controller;
        controller.bindTaskProperties(this);
    }
}
