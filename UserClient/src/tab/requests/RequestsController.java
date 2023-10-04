package tab.requests;

import tab.requests.component.table.RequestsComponentController;
import impl.RequestedSimulationDataDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.TimerTask;

public class RequestsController {

    @FXML
    private ComboBox<?> worldComboBox;

    @FXML
    private Spinner<?> runsAmountSpinner;

    @FXML
    private Spinner<?> secondsSpinner;

    @FXML
    private Spinner<?> ticksSpinner;

    @FXML
    private RadioButton terminateBySecondsRadioButton;

    @FXML
    private RadioButton terminateByTicksRadioButton;

    @FXML
    private Button submitButton;

    @FXML
    private TableView<RequestedSimulationDataDTO> requestsTableView;

    @FXML
    private RequestsComponentController requestsTableViewController;

    private String userName;

    @FXML
    private void initialize() {

    }

    public void setUserName(String userName) {
        this.userName = userName;
        requestsTableViewController.setUserName(userName);
    }

    @FXML
    void onExecuteButtonClicked(ActionEvent event) {
        RequestedSimulationDataDTO selectedRequest = requestsTableViewController.getSelectedRequest();

        if (selectedRequest != null) {
            System.out.println("request id #" + selectedRequest.getRequestSerialNumber());
        }
    }

    @FXML
    void onSubmitButtonClicked(ActionEvent event) {

    }
}
