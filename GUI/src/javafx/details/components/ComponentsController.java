package javafx.details.components;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.details.details.DetailsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ComponentsController {
    private DetailsController mainController;

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
    private Button gridButton;

    @FXML
    private Button terminationButton;

    public void setMainController(DetailsController mainController) {
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
    private void gridOnAction(ActionEvent event) {
        mainController.gridOnAction(event);
    }

    @FXML
    private void rulesOnAction(ActionEvent event) {
        mainController.rulesOnAction(event);
    }

    @FXML
    private void terminationOnAction(ActionEvent event) {
        mainController.terminationOnAction(event);
    }

    @FXML
    private void initialize() {
        assert entitiesButton != null : "fx:id=\"entitiesButton\" was not injected: check your FXML file 'Components.fxml'.";
        assert environmentVariablesButton != null : "fx:id=\"environmentVariablesButton\" was not injected: check your FXML file 'Components.fxml'.";
        assert rulesButton != null : "fx:id=\"rulesButton\" was not injected: check your FXML file 'Components.fxml'.";
        assert gridButton != null : "fx:id=\"gridButton\" was not injected: check your FXML file 'Components.fxml'.";
        assert terminationButton != null : "fx:id=\"terminationButton\" was not injected: check your FXML file 'Components.fxml'.";

    }
}