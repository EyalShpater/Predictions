package tab.management.component.details.details;

import impl.EntityDefinitionDTO;
import impl.PropertyDefinitionDTO;
import impl.RuleDTO;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import servlet.request.RequestHandler;
import tab.management.component.details.components.ComponentsController;
import tab.management.component.details.entities.EntitiesController;
import tab.management.component.details.general.GeneralController;
import tab.management.component.details.rules.RulesController;
import tab.management.component.details.variables.EnvironmentVariablesController;


import java.io.IOException;
import java.net.URL;

public class DetailsComponentController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private StackPane sceneSwitcher;
    @FXML
    private ScrollPane buttonsComponent;
    @FXML
    private ComponentsController buttonsComponentController;

    private String worldName;

    @FXML
    void initialize() {
        buttonsComponentController.setMainController(this);
    }

    public void entitiesOnAction(ActionEvent event) {
        try {
            Accordion newScene = new Accordion();

            RequestHandler.getEntities(worldName).forEach(entity -> setNewEntity(entity, newScene));

            sceneSwitcher.getChildren().clear();
            sceneSwitcher.getChildren().add(newScene);
        } catch (Exception ignored) {
        }
    }

    public void environmentVarOnAction(ActionEvent event) {
        FlowPane fp = new FlowPane();
        fp.setHgap(5);
        fp.setVgap(5);
        fp.setPadding(new Insets(5));

        try {
            RequestHandler.getEnvironmentVariablesToSet(worldName)
                    .forEach(env -> setNewEnvironmentVariableTile(env, fp));
        } catch (Exception ignored) {
        }


        ScrollPane sp = new ScrollPane(fp);
        sp.setFitToWidth(true);
        setScene(sp);
    }

    public void rulesOnAction(ActionEvent event) {
        try {
            Accordion newScene = new Accordion();

            RequestHandler.getWorld(worldName)
                    .getRules()
                    .forEach(rule -> setNewRule(rule, newScene));

            sceneSwitcher.getChildren().clear();
            sceneSwitcher.getChildren().add(newScene);
        } catch (Exception ignored) {
        }
    }

    public void generalOnAction(ActionEvent event) {
        try {
            URL resource = getClass().getResource("/component/details/general/General.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            StackPane general = loader.load();
            GeneralController generalController = loader.getController();

            generalController.setSelectedWorld(worldName);
            generalController.setPropertiesFromEngine();
            setScene(general);

        } catch (Exception ignored) {
        }

    }

    private void setScene(Node newScene) {
        sceneSwitcher.getChildren().clear();
        sceneSwitcher.getChildren().add(newScene);
    }

    private void setNewEntity(EntityDefinitionDTO entity, Accordion accordion) {
        try {
            URL resource = getClass().getResource("/component/details/entities/Entities.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            TitledPane titledPane = loader.load();
            EntitiesController entitiesController = loader.getController();

            entitiesController.setDataFromDTO(entity);
            accordion.getPanes().add(titledPane);
        } catch (IOException ignored) {
        }
    }

    private void setNewEnvironmentVariableTile(PropertyDefinitionDTO env, FlowPane flowPane) {
        try {
            URL resource = getClass().getResource("/component/details/environment/variables/EnvironmentVariables.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            StackPane stackPane = loader.load();
            EnvironmentVariablesController envVarController = loader.getController();

            envVarController.setDataFromDTO(env);
            flowPane.getChildren().add(stackPane);
        } catch (IOException ignored) {
        }
    }

    private void setNewRule(RuleDTO rule, Accordion accordion) {
        try {
            URL resource = getClass().getResource("/component/details/rules/Rules.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            TitledPane rulePane = loader.load();
            RulesController rulesController = loader.getController();

            rulesController.setDataFromDTO(rule);
            accordion.getPanes().add(rulePane);
        } catch (IOException ignored) {

        }
    }

    public BooleanProperty getTabDisableProperty() {
        return buttonsComponent.disableProperty();
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }
}
