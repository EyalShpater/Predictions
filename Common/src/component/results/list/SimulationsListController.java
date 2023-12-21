package component.results.list;

import component.results.helper.Category;
import impl.RequestedSimulationDataDTO;
import impl.SimulationDTO;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import servlet.request.RequestHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimulationsListController {

    @FXML
    private TableView<Category> simulationTableView;

    @FXML
    private TableColumn<Category, Integer> serialNumberCol;

    @FXML
    private TableColumn<Category, String> simulationNameCol;

    @FXML
    private TableColumn<Category, String> dateCol;

    private String userName;
    private Consumer<Category> onSelectionChange;
    private ObjectProperty<Category> selectedSimulation = new SimpleObjectProperty<>(null);
    private final ObservableList<Category> allSimulationsData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        TimerTask refreshRequestsTable = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> setAllSimulationsTableView());
            }
        };
        Timer timer = new Timer();
        timer.schedule(refreshRequestsTable, 1000, 500);

        selectedSimulation.addListener((observable, oldValue, newValue) -> onSelectionChange.accept(selectedSimulation.get()));
        initTableView();
    }

    public void setOnSelectionChange(Consumer<Category> onSelectionChange) {
        this.onSelectionChange = onSelectionChange;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private void initTableView() {
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<Category, Integer>("id"));
        simulationNameCol.setCellValueFactory(new PropertyValueFactory<Category, String>("simulationName"));
        simulationTableView.setItems(allSimulationsData);
        simulationTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        dateCol.setCellValueFactory(cellData -> {
            Date date = new Date(cellData.getValue().getTime());
            SimpleDateFormat customDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");
            String customFormattedDate = customDateFormat.format(date);

            return new SimpleObjectProperty<>(customFormattedDate);
        });

        simulationTableView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> selectedSimulation.set(newValue));
    }

    private void setAllSimulationsTableView() {
        List<SimulationDTO> updatedRequests = null;

        try {
            updatedRequests = RequestHandler.getSimulationsDTOByUserName(userName);
        } catch (Exception ignored) {
            return;
        }

        updatedRequests.forEach(updated -> {
            Category newCategory = new Category(updated.getWorld().getName(), updated.getStartDate(), updated.getSerialNumber());

            if (allSimulationsData.contains(newCategory)) {
                allSimulationsData.set(allSimulationsData.indexOf(newCategory), newCategory);
            } else {
                allSimulationsData.add(newCategory);
            }
        });
    }
}
