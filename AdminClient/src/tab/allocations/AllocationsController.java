package tab.allocations;

import general.constants.GeneralConstants;
import impl.RequestedSimulationDataDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import servlet.request.RequestHandler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AllocationsController {

    @FXML
    private TableView<RequestedSimulationDataDTO> requestsTableView;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, Integer> serialNumberCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, String> simulationNameCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, String> userNameCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, Integer> numOfRunsCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, String> terminationCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, String> statusCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, Integer> numOfRunningCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, Integer> numOfEndedCol;

    @FXML
    private Button declineButton;

    @FXML
    private Button acceptButton;

    private RequestedSimulationDataDTO selectedRequest;
    private final ObservableList<RequestedSimulationDataDTO> allRequests = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        TimerTask refreshRequestsTable = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> setAllRequestsTableView());
            }
        };
        Timer timer = new Timer();
        timer.schedule(refreshRequestsTable, 1000, 2000);
        initTableView();
    }

    @FXML
    private void onAcceptClicked(ActionEvent event) {
        try {
            RequestHandler.changeRequestStatus(selectedRequest.getRequestSerialNumber(), true, selectedRequest.getUserName());
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void onDeclineClicked(ActionEvent event) {
        try {
            RequestHandler.changeRequestStatus(selectedRequest.getRequestSerialNumber(), false, selectedRequest.getUserName());
        } catch (Exception ignored) {
        }
    }

    @FXML
    void onSelectedRequest(MouseEvent event) {
        RequestedSimulationDataDTO selected = requestsTableView.getSelectionModel().getSelectedItem();

        selectedRequest = selected != null ? selected : selectedRequest;
    }

    private void initTableView() {
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, Integer>("requestSerialNumber"));
        simulationNameCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, String>("worldName"));
        numOfRunsCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, Integer>("numOfSimulationInstances"));
        statusCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, String>("status"));
        numOfRunningCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, Integer>("numOfRunningSimulations"));
        numOfEndedCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, Integer>("numOfEndedSimulations"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, String>("userName"));
        terminationCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, String>("termination"));

        requestsTableView.setItems(allRequests);
        requestsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void setAllRequestsTableView() {
        List<RequestedSimulationDataDTO> updatedRequests = null;

        try {
            updatedRequests = RequestHandler.getRequestsByUserName(GeneralConstants.ALL_REQUESTS_KEY_WORD_NAME);
        } catch (Exception ignored) {
            return;
        }

        updatedRequests.forEach(updated -> {
            if (allRequests.contains(updated)) {
                int index = allRequests.indexOf(updated);
                allRequests.set(index, updated);
            } else {
                allRequests.add(updated);
            }
        });
    }

}
