package component.details.components;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import component.details.details.DetailsComponentController;

public class ComponentsController {
    public final static String ENTITIES_COMPONENT_NAME = "entities";
    public final static String GENERAL_COMPONENT_NAME = "general";
    public final static String ENVIRONMENT_VARIABLES_COMPONENT_NAME = "environment_var";
    public final static String RULES_COMPONENT_NAME = "rules";

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

    private DetailsComponentController mainController;
    private String selectedComponent = ENTITIES_COMPONENT_NAME;

    public void setMainController(DetailsComponentController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void entitiesOnAction(ActionEvent event) {
        mainController.entitiesOnAction(event);
        selectedComponent = ENTITIES_COMPONENT_NAME;
    }

    @FXML
    private void environmentVarOnAction(ActionEvent event) {
        mainController.environmentVarOnAction(event);
        selectedComponent = ENVIRONMENT_VARIABLES_COMPONENT_NAME;
    }

    @FXML
    private void generalOnAction(ActionEvent event) {
        mainController.generalOnAction(event);
        selectedComponent = GENERAL_COMPONENT_NAME;
    }

    @FXML
    private void rulesOnAction(ActionEvent event) {
        mainController.rulesOnAction(event);
        selectedComponent = RULES_COMPONENT_NAME;
    }

    public void clickOnSelectedComponent() {
        switch (selectedComponent) {
            case ENTITIES_COMPONENT_NAME:
                entitiesOnAction(null);
                break;
            case GENERAL_COMPONENT_NAME:
                generalOnAction(null);
                break;
            case RULES_COMPONENT_NAME:
                rulesOnAction(null);
                break;
            case ENVIRONMENT_VARIABLES_COMPONENT_NAME:
                environmentVarOnAction(null);
                break;
            default:
                break;
        }
    }
}
