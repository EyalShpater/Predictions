package tab.requests;

import com.google.gson.Gson;
import component.requests.RequestsComponentController;
import general.constants.GeneralConstants;
import impl.RequestedSimulationDataDTO;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import servlet.request.RequestHandler;

import java.util.List;
import java.util.Observable;
import java.util.Timer;
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
    private TableView<RequestedSimulationDataDTO> requestsTableView;

    @FXML
    private RequestsComponentController requestsTableViewController;

    private String userName;
    private TimerTask refreshRequestsTable;

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
}
