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
import javafx.tab.details.details.DetailsController;

import java.io.IOException;
import java.net.URL;

public class PredictionsMainAppController {

    @FXML
    private TabPane tabPane;
    @FXML
    private GridPane headerComponent;
    @FXML
    private HeaderController headerComponentController;

    private PredictionsLogic engine = new PredictionsLogicImpl();
    private DetailsController detailsTabController;

    @FXML
    private void initialize() throws IOException {
        headerComponentController.setEngine(engine);

        setDetailsTab();
    }

    private void setDetailsTab() {
        try {
            Tab details = new Tab("Details");

            URL resource = getClass().getResource("/javafx/tab/details/details/Details.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent detailsContent = loader.load();
            details.setContent(detailsContent);

            detailsTabController = loader.getController();
            detailsTabController.setEngine(engine);
            tabPane.getTabs().addAll(details);
        } catch (Exception ignored) {

        }
    }
}
