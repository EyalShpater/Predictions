package component.requests;

import impl.RequestedSimulationDataDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import servlet.request.RequestHandler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RequestsComponentController {

    @FXML
    private TableView<RequestedSimulationDataDTO> allRequestsTableView;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, Integer> serialNumberCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, String> nameCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, Integer> runAmountCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, String> statusCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, Integer> runningCol;

    @FXML
    private TableColumn<RequestedSimulationDataDTO, Integer> endedCol;

    private String userName;
    private RequestedSimulationDataDTO selectedRequest;
    private TimerTask refreshRequestsTable;

    @FXML
    private void initialize() {
        refreshRequestsTable = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> setAllRequestsTableView());
            }
        };
        Timer timer = new Timer();
        timer.schedule(refreshRequestsTable, 1000, 2000);
    }

    private void initTableView() {
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, Integer>("requestSerialNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, String>("worldName"));
        runAmountCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, Integer>("numOfSimulationInstances"));
        statusCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, String>("status"));
        runningCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, Integer>("numOfRunningSimulations"));
        endedCol.setCellValueFactory(new PropertyValueFactory<RequestedSimulationDataDTO, Integer>("numOfEndedSimulations"));
        allRequestsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    void onSelectedRequest(MouseEvent event) {
        RequestedSimulationDataDTO selected = allRequestsTableView.getSelectionModel().getSelectedItem();
        selectedRequest = selected != null ? selected : selectedRequest;

        System.out.println("on selected id#" + (selectedRequest != null ? selectedRequest.getRequestSerialNumber() : null));
    }

    private void setAllRequestsTableView() {
        List<RequestedSimulationDataDTO> requests = null;

        try {
            requests = RequestHandler.getRequestsByUserName(userName);
        } catch (Exception ignored) {
            System.out.println("ignored"); //todo: delete
        }

        initTableView();
        allRequestsTableView.getItems().clear();

        if (requests != null) {
            requests.forEach(request -> allRequestsTableView.getItems().add(request));
        }
    }

    public RequestedSimulationDataDTO getSelectedRequest() {
        return selectedRequest;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
