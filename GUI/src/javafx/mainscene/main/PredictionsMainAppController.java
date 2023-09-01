package javafx.mainscene.main;

import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.mainscene.header.HeaderController;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class PredictionsMainAppController {

    @FXML
    private TabPane tabPane;
    @FXML
    private GridPane headerComponent;
    @FXML
    private HeaderController headerComponentController;

    private PredictionsLogic engine = new PredictionsLogicImpl();

    @FXML
    private void initialize() throws IOException {
        Tab details = new Tab("Details");
        Tab details1 = new Tab("Details2");

        headerComponentController.setEngine(engine);
        Parent load = FXMLLoader.load(getClass().getResource("/javafx/tab/details/details/Details.fxml"));
        details.setContent(load);
        load = FXMLLoader.load(getClass().getResource("/javafx/tab/details/details/Details.fxml"));
        details1.setContent(load);
        tabPane.getTabs().addAll(details, details1);
    }
}
