
package main;

import component.details.main.MainDetailsController;
import component.results.ResultsController;
import impl.RequestedSimulationDataDTO;
import impl.TerminationDTO;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

import javafx.stage.Stage;
import login.LoginController;
import servlet.request.RequestHandler;
import tab.newExecution.mainComponent.NewExecutionController;
import tab.requests.RequestsController;


import java.io.IOException;
import java.net.URL;

public class MainAppController {

    @FXML
    private StackPane mainPanel;

    @FXML
    private StackPane requestsTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private RequestsController requestsTabController;

    @FXML
    private ScrollPane resultsScrollPane;

    @FXML
    private ResultsController resultsScrollPaneController;

    @FXML
    private BorderPane newExecutionComponent;

    @FXML
    private NewExecutionController newExecutionComponentController;

    private String userName;
    private LoginController logicController;
    private ScrollPane loginComponent;
    private Parent mainApp;
    private RequestedSimulationDataDTO selectedRequest;
    private Stage primaryStage;

    @FXML
    public void initialize() {
        newExecutionComponent.disableProperty().set(true);
    }

    private void setMainPanelTo(Parent pane) {
        mainPanel.getChildren().clear();
        mainPanel.getChildren().add(pane);

        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 1.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    private void setSelectedRequest(RequestedSimulationDataDTO selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public void switchToTabs() {
        setMainPanelTo(mainApp);
    }

    public void setUserName(String userName) {
        this.userName = userName;
        requestsTabController.setUserName(userName);
        requestsTabController.setMainAppController(this);
        resultsScrollPaneController.setUserName(userName);
    }

    public void onExecutionButtonClicked(RequestedSimulationDataDTO selectedRequest) {
        setSelectedRequest(selectedRequest);
        newExecutionComponent.disableProperty().set(false);
        newExecutionComponentController.onNewExecutionClicked(selectedRequest.getWorldName());
        newExecutionComponentController.setMainAppController(this);
        newExecutionComponentController.setTabPane(tabPane);
    }

    public TerminationDTO getSelectedSimulationTermination() {
        return selectedRequest.getTermination();
    }

    public void onStartButtonClick(int simulationSerialNumber) {
        newExecutionComponent.disableProperty().set(true);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupCloseEvent();
    }

    private void setupCloseEvent() {
        if (primaryStage != null) {
            primaryStage.setOnCloseRequest(request -> {
                try {
                    RequestHandler.logoutUser(userName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Platform.exit();
                System.exit(0);
            });
        }
    }

    public String getUserName() {
        return userName;
    }

    public Integer getRequestId() {
        return selectedRequest.getRequestSerialNumber();
    }

    public TabPane getTabPane() {
        return this.tabPane;
    }
}

