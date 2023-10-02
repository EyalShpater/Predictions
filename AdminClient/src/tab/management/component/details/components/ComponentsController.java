package tab.management.component.details.components;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import tab.management.component.details.details.DetailsComponentController;


public class ComponentsController {
    private DetailsComponentController mainController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button entitiesButton;

    @FXML
    private Button environmentVariablesButton;

    @FXML
    private Button rulesButton;

    @FXML
    private Button generalButton;


    public void setMainController(DetailsComponentController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void entitiesOnAction(ActionEvent event) {
        mainController.entitiesOnAction(event);
    }

    @FXML
    private void environmentVarOnAction(ActionEvent event) {
        mainController.environmentVarOnAction(event);
    }

    @FXML
    private void generalOnAction(ActionEvent event) {
        mainController.generalOnAction(event);
    }

    @FXML
    private void rulesOnAction(ActionEvent event) {
        mainController.rulesOnAction(event);
    }
}
