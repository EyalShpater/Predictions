package component.details.main;

import component.details.details.DetailsComponentController;
import impl.RequestedSimulationDataDTO;
import impl.WorldDTO;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import servlet.request.RequestHandler;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainDetailsController {

    @FXML
    private ChoiceBox<String> worldChoiceBox;

    @FXML
    private BorderPane detailsComponent;

    @FXML
    private DetailsComponentController detailsComponentController;

    private StringProperty selectedWorld = new SimpleStringProperty();
    private final ObservableList<String> worldsData = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws IOException {
        TimerTask refreshRequestsTable = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        getWorldsUpdateFromServer();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(refreshRequestsTable, 1000, 500);


        worldChoiceBox.setItems(worldsData);
        selectedWorld.bind(worldChoiceBox.valueProperty());
        selectedWorld.addListener((observable, oldValue, newValue) -> onValueChanged(newValue));
    }

    @FXML
    private void onMouseClicked(MouseEvent event) throws IOException {
        //todo: can be deleted...
    }

    private void getWorldsUpdateFromServer() throws IOException {
        List<WorldDTO> worlds = RequestHandler.getWorlds(); // todo: make async

        worlds.forEach(updated -> {
            if (!worldsData.contains(updated.getName())) {
                worldsData.add(updated.getName());
            }
        });
    }

    private void onValueChanged(String newValue) {
        if (newValue != null) {
            detailsComponentController.setWorldName(newValue);
            //logic

            System.out.println(newValue);
        }
    }
}
