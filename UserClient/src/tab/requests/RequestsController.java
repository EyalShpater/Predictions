package tab.requests;

import javafx.scene.layout.StackPane;
import tab.requests.component.submit.SubmitComponentController;
import tab.requests.component.table.RequestsComponentController;
import impl.RequestedSimulationDataDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.MainAppController;

public class RequestsController {

    @FXML
    private StackPane submitComponent;

    @FXML
    private SubmitComponentController submitComponentController;

    @FXML
    private StackPane requestsTableView;

    @FXML
    private RequestsComponentController requestsTableViewController;

    private String userName;

    @FXML
    private void initialize() {

    }

    public void setUserName(String userName) {
        this.userName = userName;
        requestsTableViewController.setUserName(userName);
        submitComponentController.setUserName(userName);
    }


    public void setMainAppController(MainAppController mainAppController) {
        requestsTableViewController.setMainAppController(mainAppController);
    }
}
