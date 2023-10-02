package tab.management.component.threadpoolInfo;

import general.constants.GeneralConstants;
import impl.SimulationQueueDto;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import servlet.request.RequestHandler;

public class SimulationQueueController {

    @FXML
    private Label runningLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label waitingLabel;

    @FXML
    private Label completedLabel;

    @FXML
    private Label threadsAmountLabel;

    private SimpleStringProperty currentlyRunning;
    private SimpleStringProperty totalSimulations;
    private SimpleStringProperty currentlyWaiting;
    private SimpleStringProperty completedSimulations;
    private SimpleStringProperty threadsAmount;

    public SimulationQueueController() {
        currentlyRunning = new SimpleStringProperty("0");
        totalSimulations = new SimpleStringProperty("0");
        currentlyWaiting = new SimpleStringProperty("0");
        completedSimulations = new SimpleStringProperty("0");
        threadsAmount = new SimpleStringProperty("0");
    }

    public void initialize() {
        runningLabel.textProperty().bind(currentlyRunning);
        totalLabel.textProperty().bind(totalSimulations);
        waitingLabel.textProperty().bind(currentlyWaiting);
        completedLabel.textProperty().bind(completedSimulations);
        threadsAmountLabel.textProperty().bind(threadsAmount);

        createScheduledService();
    }

    private void updateUI() {
        Platform.runLater(() -> {
            try {
                SimulationQueueDto simulationQueueDto = RequestHandler.getSimulationQueueData();

                if (simulationQueueDto != null) {
                    int running = simulationQueueDto.getRunning();
                    int total = simulationQueueDto.getTotal();
                    int waiting = simulationQueueDto.getWaiting();
                    int completed = total - waiting - running;
                    int numOfThreads = simulationQueueDto.getNumOfThreadsInThreadPool();

                    totalSimulations.set(String.valueOf(total));
                    currentlyRunning.set(String.valueOf(running));
                    currentlyWaiting.set(String.valueOf(waiting));
                    completedSimulations.set(String.valueOf(completed));
                    threadsAmount.set(String.valueOf(numOfThreads));
                }
            } catch (Exception ignored) {
            }
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
