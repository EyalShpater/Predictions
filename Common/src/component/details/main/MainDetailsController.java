package component.details.main;

import component.details.details.DetailsComponentController;
import impl.WorldDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import servlet.request.RequestHandler;

import java.io.IOException;
import java.util.List;

public class MainDetailsController {

    @FXML
    private ChoiceBox<String> worldChoiceBox;

    @FXML
    private BorderPane detailsComponent;

    @FXML
    private DetailsComponentController detailsComponentController;

    private StringProperty selectedWorld = new SimpleStringProperty();

    @FXML
    private void initialize() throws IOException {
        selectedWorld.bind(worldChoiceBox.valueProperty());
        selectedWorld.addListener((observable, oldValue, newValue) -> onValueChanged(newValue));
    }

    @FXML
    private void onMouseClicked(MouseEvent event) throws IOException {
        getWorldsUpdateFromServer();
        System.out.println("clicked");
    }

    private void getWorldsUpdateFromServer() throws IOException {
        List<WorldDTO> worlds = RequestHandler.getWorlds(); // todo: make async

        worldChoiceBox.getItems().clear();
        worlds.forEach(world -> worldChoiceBox.getItems().add(world.getName()));
    }

    private void onValueChanged(String newValue) {
        if (newValue != null) {
            detailsComponentController.setWorldName(newValue);
            //logic

            System.out.println(newValue);
        }
    }
}
