package javafx.tab.details.details;

import execution.simulation.api.PredictionsLogic;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.layout.FlowPane;
import javafx.tab.details.components.ComponentsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.tab.details.environment.variables.EnvironmentVariablesController;
import javafx.tab.details.general.GeneralController;

import java.io.IOException;
import java.net.URL;

public class DetailsController {


    @FXML
    private BorderPane borderPane;
    @FXML
    private StackPane sceneSwitcher;
    @FXML
    private ScrollPane buttonsComponent;
    @FXML
    private ComponentsController buttonsComponentController;

    private PredictionsLogic engine;

    @FXML
    void initialize() {
        buttonsComponentController.setMainController(this);
    }

    public void entitiesOnAction(ActionEvent event) {
        try {
            TitledPane tile1 = FXMLLoader.load(getClass().getResource("../entities/Entities.fxml"));
            TitledPane tile2 = FXMLLoader.load(getClass().getResource("../entities/Entities.fxml"));
            TitledPane tile3 = FXMLLoader.load(getClass().getResource("../entities/Entities.fxml"));
            TitledPane tile4 = FXMLLoader.load(getClass().getResource("../entities/Entities.fxml"));
            TitledPane tile5 = FXMLLoader.load(getClass().getResource("../entities/Entities.fxml"));

            tile2.setText("Ent-2");
            tile3.setText("Ent-3");
            tile4.setText("Ent-4");
            tile5.setText("Ent-5");

            Accordion newScene = new Accordion(tile1, tile2, tile3, tile4, tile5);

            sceneSwitcher.getChildren().clear();
            sceneSwitcher.getChildren().add(newScene);
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    public void environmentVarOnAction(ActionEvent event) {
        FlowPane fp = new FlowPane();
        fp.setHgap(5);
        fp.setVgap(5);

        engine.getEnvironmentVariablesToSet().forEach(env -> {
            try {
                URL resource = getClass().getResource("/javafx/tab/details/environment/variables/EnvironmentVariables.fxml");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(resource);
                StackPane stackPane = loader.load();
                EnvironmentVariablesController envVarController = loader.getController();

                envVarController.setDataFromDTO(env);
                fp.getChildren().add(stackPane);
            } catch (IOException ignored) {
            }
        });

        ScrollPane sp = new ScrollPane(fp);
        sp.setFitToWidth(true);
        setScene(sp);
    }

    public void rulesOnAction(ActionEvent event) {
        try {
            TitledPane tile1 = FXMLLoader.load(getClass().getResource("../rules/Rules.fxml"));
            TitledPane tile2 = FXMLLoader.load(getClass().getResource("../rules/Rules.fxml"));
            TitledPane tile3 = FXMLLoader.load(getClass().getResource("../rules/Rules.fxml"));
            TitledPane tile4 = FXMLLoader.load(getClass().getResource("../rules/Rules.fxml"));
            TitledPane tile5 = FXMLLoader.load(getClass().getResource("../rules/Rules.fxml"));

            tile2.setText("Rule-2");
            tile3.setText("Rule-3");
            tile4.setText("Rule-4");
            tile5.setText("Rule-5");

            Accordion newScene = new Accordion(tile1, tile2, tile3, tile4, tile5);

            sceneSwitcher.getChildren().clear();
            sceneSwitcher.getChildren().add(newScene);
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    public void generalOnAction(ActionEvent event) {
        try {
            URL resource = getClass().getResource("/javafx/tab/details/general/General.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            StackPane general = loader.load();
            GeneralController generalController = loader.getController();

            generalController.setEngine(engine);
            generalController.setPropertiesFromEngine();
            setScene(general);

        } catch (Exception ignored) {
        }

    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    private void setScene(Node newScene) {
        sceneSwitcher.getChildren().clear();
        sceneSwitcher.getChildren().add(newScene);
    }
}
