package javafx.details.details;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.details.components.ComponentsController;
import javafx.embed.swing.JFXPanel;
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
        System.out.println("environment");
    }

    public void gridOnAction(ActionEvent event) {

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

    public void terminationOnAction(ActionEvent event) {

    }
}
