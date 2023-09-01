package javafx.tab.details.details;

import javafx.geometry.Insets;
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

public class DetailsController {


    @FXML
    private BorderPane borderPane;
    @FXML
    private StackPane sceneSwitcher;
    @FXML
    private ScrollPane buttonsComponent;
    @FXML
    private ComponentsController buttonsComponentController;


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
        try {
            StackPane var1 = FXMLLoader.load(getClass().getResource("/javafx/tab/details/environment/variables/EnvironmentVariables.fxml"));
            StackPane var2 = FXMLLoader.load(getClass().getResource("/javafx/tab/details/environment/variables/EnvironmentVariables.fxml"));
            StackPane var3 = FXMLLoader.load(getClass().getResource("/javafx/tab/details/environment/variables/EnvironmentVariables.fxml"));

            FlowPane fp = new FlowPane();
            fp.getChildren().addAll(var1, var2, var3);
            fp.setHgap(5);
            fp.setVgap(5);

            ScrollPane sp = new ScrollPane(fp);
            sp.setFitToWidth(true);

            sceneSwitcher.getChildren().clear();
            sceneSwitcher.getChildren().add(sp);
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    public void generalOnAction(ActionEvent event) {
        try {
            StackPane sp = FXMLLoader.load(getClass().getResource("/javafx/tab/details/general/General.fxml"));
            ScrollPane s = new ScrollPane(sp);
            sceneSwitcher.getChildren().clear();
            sceneSwitcher.getChildren().add(s);

        } catch (Exception e) {
            System.out.println("failed");
        }
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

}
