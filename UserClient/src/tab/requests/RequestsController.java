package tab.requests;

import javafx.scene.layout.StackPane;
import tab.requests.component.submit.SubmitComponentController;
import tab.requests.component.table.RequestsComponentController;
import impl.RequestedSimulationDataDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.MainAppController;


import java.util.TimerTask;

public class RequestsController {

    @FXML
    private StackPane submitComponent;

    @FXML
    private SubmitComponentController submitComponentController;

    @FXML
    private TableView<RequestedSimulationDataDTO> requestsTableView;

    @FXML
    private RequestsComponentController requestsTableViewController;

    MainAppController mainAppController;

    private String userName;

    @FXML
    private void initialize() {

    }

    public void setUserName(String userName) {
        this.userName = userName;
        requestsTableViewController.setUserName(userName);
        submitComponentController.setUserName(userName);
    }

    @FXML
    void onExecuteButtonClicked(ActionEvent event) {
        RequestedSimulationDataDTO selectedRequest = requestsTableViewController.getSelectedRequest();
        mainAppController.onExecutionButtonClicked(selectedRequest);
    }

    @FXML
    void onSubmitButtonClicked(ActionEvent event) {

    }

    public void setMainAppController(MainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }
}
