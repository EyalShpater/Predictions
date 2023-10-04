package tab.allocations;

import component.requests.RequestsComponentController;
import impl.RequestedSimulationDataDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class AllocationsController {

    @FXML
    private Button acceptButton;

    @FXML
    private Button declineButton;

    @FXML
    private TableView<RequestedSimulationDataDTO> requestsTableView;

    @FXML
    private RequestsComponentController requestsTableViewController;

    @FXML
    void onAcceptClicked(ActionEvent event) {

    }

    @FXML
    void onDeclineClicked(ActionEvent event) {

    }
}
