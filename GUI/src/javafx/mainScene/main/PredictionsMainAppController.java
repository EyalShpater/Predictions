package javafx.mainScene.main;

import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import impl.SimulationInitDataFromUserDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.mainScene.header.HeaderController;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.tab.details.details.DetailsController;

import java.io.IOException;
import java.net.URL;

import javafx.tab.newExecution.mainComponent.NewExecutionController;
import javafx.tab.results.ResultsController;
import javafx.tab.results.helper.Category;


public class PredictionsMainAppController {

    @FXML
    private TabPane tabPane;
    @FXML
    private GridPane headerComponent;
    @FXML
    private HeaderController headerComponentController;

    private DetailsController detailsTabController;
    private NewExecutionController newExecutionTabController;
    private ResultsController resultsTabController;

    private PredictionsLogic engine = new PredictionsLogicImpl();
    private BooleanProperty isFileSelected = new SimpleBooleanProperty();
    // private ObjectProperty<Category> selectSimulation;

    @FXML
    private void initialize() {
        headerComponentController.setEngine(engine);
        headerComponentController.setMainAppController(this);

        setDetailsTab();
        setNewExecutionTab();
        setResultsTab();

        isFileSelected.bind(headerComponentController.getIsFileSelectedProperty());
        detailsTabController.getTabDisableProperty().bind(isFileSelected.not());
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
            tabPane.getTabs().add(details);
        } catch (Exception ignored) {

        }
    }

    private void setNewExecutionTab() {
        try {
            Tab newExecution = new Tab("New Execution");
            URL resource = getClass().getResource("/javafx/tab/newExecution/mainComponent/NewExecution.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent newExecutionContent = loader.load();
            newExecution.setContent(newExecutionContent);

            newExecutionTabController = loader.getController();
            newExecutionTabController.setEngine(engine);
            newExecutionTabController.setMainAppController(this);
            newExecutionTabController.setTabPane(tabPane);
            //newExecutionTabContller.setIsFileSelectedProperty(headerComponentController.getIsFileSelectedProperty());

            tabPane.getTabs().add(newExecution);
        } catch (Exception ignored) {

        }
    }

    private void setResultsTab() {
        try {
            Tab results = new Tab("Results");

            URL resource = getClass().getResource("/javafx/tab/results/Results.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent resultsContent = loader.load();
            results.setContent(resultsContent);

            resultsTabController = loader.getController();
            resultsTabController.setEngine(engine);
            resultsTabController.setPredictionsMainAppController(this);

            tabPane.getTabs().add(results);
        } catch (IOException ignored) {

        }
    }

    public void onStartButtonClick(int newSimulationSerialNumber) {
        resultsTabController.onStartButtonClicked(newSimulationSerialNumber);
    }

    public void onNewFileLoaded() {
        newExecutionTabController.onNewFileLoaded();
        resultsTabController.onNewFileLoaded();
    }

    public void restoreDataValuesToTiles(SimulationInitDataFromUserDTO userInputOfSimulationBySerialNumber) {
        newExecutionTabController.restoreDataValuesToTiles(userInputOfSimulationBySerialNumber);
    }
//
//    public Category getSelectSimulation() {
//        return selectSimulation.get();
//    }
//
//    public ObjectProperty<Category> getSelectSimulationProperty() {
//        return selectSimulation;
//    }
}
