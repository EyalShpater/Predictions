package javafx.mainScene.header;

import execution.simulation.api.PredictionsLogic;
import impl.SimulationQueueDto;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class SimulationQueuePopUpController {

    @FXML
    private Label runningLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label waitingLabel;

    PredictionsLogic engine;

    SimpleStringProperty currentlyRunning;
    SimpleStringProperty totalSimulations;
    SimpleStringProperty currentlyWaiting;

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    public SimulationQueuePopUpController() {
        currentlyRunning = new SimpleStringProperty("");
        totalSimulations = new SimpleStringProperty("");
        currentlyWaiting = new SimpleStringProperty("");
    }

    public void initialize() {
        runningLabel.textProperty().bind(currentlyRunning);
        totalLabel.textProperty().bind(totalSimulations);
        waitingLabel.textProperty().bind(currentlyWaiting);
        createScheduledService();
    }

    private void updateUI() {
        Platform.runLater(() -> {
            SimulationQueueDto simulationQueueDto = engine.getSimulationQueueDetails();
            int running = simulationQueueDto.getRunning();
            int total = simulationQueueDto.getTotal();
            int waiting = simulationQueueDto.getWaiting();

            totalSimulations.set(String.valueOf(total));
            currentlyRunning.set(String.valueOf(running));
            currentlyWaiting.set(String.valueOf(waiting));
        });
    }

    private void createScheduledService() {
        ScheduledService<Void> service = new ScheduledService<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        updateUI();
                        return null;
                    }
                };
            }
        };
        service.setPeriod(Duration.seconds(1));
        service.start();
    }
}
