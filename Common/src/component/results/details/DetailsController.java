package component.results.details;

import component.results.analyze.AnalyzePaginationController;
import component.results.helper.Category;
import component.results.progress.ProgressController;
import component.results.task.helper.EntityPopulationData;
import impl.SimulationDataDTO;
import impl.WorldDTO;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import servlet.request.RequestHandler;

import java.io.IOException;
import java.util.Map;

public class DetailsController {

    @FXML
    private TableView<EntityPopulationData> entitiesPopulationTableView;

    @FXML
    private TableColumn<EntityPopulationData, String> entitiesCol;

    @FXML
    private TableColumn<EntityPopulationData, Integer> populationCol;

    @FXML
    private ChoiceBox<String> entityChoiceBox;

    @FXML
    private ChoiceBox<String> propertyChoiceBox;

    @FXML
    private TitledPane entityAmountTitlePane;

    @FXML
    private TitledPane dataAnalyzeTitlePane;

    @FXML
    private Pagination analyzePagination;

    @FXML
    private AnalyzePaginationController analyzePaginationController;

    private ProgressController progressController;

    private StringProperty propertyToView;
    private StringProperty entityToView;
    private BooleanProperty isSelectedSimulationEnded;
    private ObjectProperty<Category> selectedSimulation;

    public DetailsController() {
        propertyToView = new SimpleStringProperty();
        entityToView = new SimpleStringProperty();
        isSelectedSimulationEnded = new SimpleBooleanProperty(false);
        selectedSimulation = new SimpleObjectProperty<>();
    }

    @FXML
    private void initialize() {
        propertyToView.bind(propertyChoiceBox.valueProperty());
        entityToView.bind(entityChoiceBox.valueProperty());
        dataAnalyzeTitlePane.disableProperty().bind(isSelectedSimulationEnded.not());

        entityToView.addListener((observable, oldValue, newValue) -> onSelectedEntity(newValue));

        entitiesCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        populationCol.setCellValueFactory(new PropertyValueFactory<>("population"));

        analyzePaginationController.setDetailsController(this);

        if (progressController != null) {
            progressController.setTableView(this.entitiesPopulationTableView);
        }
    }

    public void onSelectedSimulationChange(Category newSimulation) {
        if (newSimulation != null) {
            selectedSimulation.set(newSimulation);
            setEntitiesChoiceBox();
            analyzePaginationController.onSelectedSimulationChange(newSimulation.getId());
        }
    }

    private void setPropertyChoiceBox() {
        propertyChoiceBox.getItems().clear();

        try {
            if (entityToView.isNotNull().get()) {
                WorldDTO simulation = RequestHandler.getWorld(selectedSimulation.get().getSimulationName());
                simulation
                        .getEntities()
                        .stream()
                        .filter(entity -> entity.getName().equals(entityToView.get()))
                        .findAny()
                        .get()
                        .getProperties()
                        .forEach(property -> propertyChoiceBox.getItems().add(property.getName()));
            }
        } catch (Exception ignored) {
        }
    }

    private void setEntitiesChoiceBox() {
        entityChoiceBox.getItems().clear();

        try {
            WorldDTO simulationDTO = RequestHandler.getWorld(selectedSimulation.get().getSimulationName());
            simulationDTO
                    .getEntities()
                    .forEach(entity -> entityChoiceBox.getItems().add(entity.getName()));
        } catch (Exception ignored) {
        }
    }

    public void setDisableEntityChoiceBoxValue(boolean disable) {
        String value = entityChoiceBox.getValue();

        entityChoiceBox.setDisable(disable);
        entityChoiceBox.valueProperty().set(disable ? null : value);

    }

    public void setDisablePropertyChoiceBoxValue(boolean disable) {
        String value = propertyChoiceBox.getValue();

        propertyChoiceBox.setDisable(disable);
        propertyChoiceBox.valueProperty().set(disable ? null : value);
    }

    public Map<String, Double> getConsistency() {
        try {
            return selectedSimulation.isNotNull().get() ?
                    RequestHandler.getConsistencyByEntityName(selectedSimulation.get().getId(), entityToView.get()) :
                    null;
        } catch (Exception e) {
            return null;
        }
    }

    public Double getAverageProperty() {
        try {
            return RequestHandler.getFinalNumericPropertyAvg(selectedSimulation.get().getId(), entityToView.get(), propertyToView.get());
        } catch (Exception e) {
            return null;
        }
    }

    public SimulationDataDTO getSimulationData() throws IOException {
        return selectedSimulation.isNotNull().get() ?
                RequestHandler.getSimulationData(
                        selectedSimulation.get().getId(),
                        entityToView.get(),
                        propertyChoiceBox.getValue()
                ) :
                null;
    }

    public Map<String, Map<Integer, Long>> getPopulationData() {
        try {
            return RequestHandler.getPopulationCountSortedByName(selectedSimulation.get().getId());
        } catch (Exception e) {
            return null;
        }
    }

    private void onSelectedEntity(String newValue) {
        try {
            setPropertyChoiceBox();
            analyzePaginationController.setConsistency(RequestHandler.getConsistencyByEntityName(selectedSimulation.get().getId(), newValue));
        } catch (Exception ignored) {
        }
    }

    public String getPropertyToView() {
        return propertyToView.get();
    }

    public StringProperty propertyToViewProperty() {
        return propertyToView;
    }

    public String getEntityToView() {
        return entityToView.get();
    }

    public StringProperty entityToViewProperty() {
        return entityToView;
    }

    public void setProgressController(ProgressController progressController) {
        this.progressController = progressController;
    }

    public void setSelectedSimulation(Category selectedSimulation) {
        this.selectedSimulation.set(selectedSimulation);
    }

    public boolean isIsSelectedSimulationEnded() {
        return isSelectedSimulationEnded.get();
    }

    public BooleanProperty isSelectedSimulationEndedProperty() {
        return isSelectedSimulationEnded;
    }

    public TableView<EntityPopulationData> getEntitiesPopulationTableView() {
        return entitiesPopulationTableView;
    }

    public void disableEntityAmountTable() {
        entityAmountTitlePane.setDisable(true);
    }
}
