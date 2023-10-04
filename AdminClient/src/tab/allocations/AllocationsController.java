package tab.allocations;

import impl.RequestedSimulationDataDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    @FXML
    private void initialize() {

    }

    @FXML
    void onAcceptClicked(ActionEvent event) {

    }

    @FXML
    void onDeclineClicked(ActionEvent event) {

    }

}
