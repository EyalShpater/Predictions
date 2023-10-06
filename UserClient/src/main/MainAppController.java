
package main;

import component.details.main.MainDetailsController;
import impl.RequestedSimulationDataDTO;
import impl.TerminationDTO;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

import login.LoginController;
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
    private BorderPane newExecutionComponent;

    @FXML
    private NewExecutionController newExecutionComponentController;

    private String userName;
    private LoginController logicController;
    private ScrollPane loginComponent;
    private Parent mainApp;
    private RequestedSimulationDataDTO selectedRequest;

    @FXML
    public void initialize() {
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
        System.out.println("Main app user name: " + userName);
    }

    public void onExecutionButtonClicked(RequestedSimulationDataDTO selectedRequest) {
        setSelectedRequest(selectedRequest);
        newExecutionComponentController.onNewExecutionClicked(selectedRequest.getWorldName());
        newExecutionComponentController.setMainAppController(this);
        newExecutionComponentController.setTabPane(tabPane);
    }

    public TerminationDTO getSelectedSimulationTermination() {
        return selectedRequest.getTermination();
    }

    public void onStartButtonClick(int simulationSerialNumber) {
        //TODO: When eyal finishes resultsTab
    }

    public String getUserName() {
        return userName;
    }

    public Integer getRequestId() {
        return selectedRequest.getRequestSerialNumber();
    }
}

