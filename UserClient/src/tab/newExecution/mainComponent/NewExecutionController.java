package tab.newExecution.mainComponent;

import impl.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import main.MainAppController;
import servlet.request.RequestHandler;
import tab.newExecution.entity.EntityController;
import tab.newExecution.entity.EntityData;
import tab.newExecution.environmentVariable.BasicEnvironmentVariableData;
import tab.newExecution.environmentVariable.BooleanEnvironmentVariableController;
import tab.newExecution.environmentVariable.NumericEnvironmentVariableController;
import tab.newExecution.environmentVariable.StringEnvironmentVariableController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewExecutionController {

    @FXML
    private Button clearButton;

    @FXML
    private Button simulationsQueueButton;

    @FXML
    private Button startButton;

    @FXML
    private FlowPane entitiesFlowPane;

    @FXML
    private FlowPane envVarsFlowPane;

    private List<BasicEnvironmentVariableData> envVarControllerList;

    private List<EntityData> entityList;

    private TabPane tabPane;

    private String worldName;

    private MainAppController mainAppController;

    @FXML
    private void initialize() {
    }

    @FXML
    void clearButtonActionListener(ActionEvent event) {
        for (BasicEnvironmentVariableData controller : envVarControllerList) {
            controller.clear();
        }
        for (EntityData entityData : entityList) {
            entityData.clear();
        }
    }

    @FXML
    void simulationsQueueButtonActionListener(ActionEvent event) {

    }

    @FXML
    void startButtonActionListener(ActionEvent event) {
        Map<String, Integer> entityNameToPopulation = setNewPopulationOfEntities();
        if (isValidPopulation(entityNameToPopulation)) {
            startSimulationAction(entityNameToPopulation);
        }

       /* Map<String, Integer> entityNameToPopulation = setNewPopulationOfEntities();

        if (isValidPopulation(entityNameToPopulation)) {
            startSimulationAction(entityNameToPopulation);
        } else {
            alertAction();
        }*/
    }

    private void startSimulationAction(Map<String, Integer> entityNameToPopulation) {
        int simulationSerialNumber;

        simulationSerialNumber = runSimulation(entityNameToPopulation);
        mainAppController.onStartButtonClick(simulationSerialNumber);
        //TODO: When eyal finishes resultsTab
        /*Tab resultsTab = findResultsTabByName("Results");
        if (resultsTab != null) {
            tabPane.getSelectionModel().select(resultsTab);
        }*/
    }

    private int runSimulation(Map<String, Integer> entityNameToPopulation) {
        List<PropertyDefinitionDTO> updatedEnvironmentVariables;
        try {
            updatedEnvironmentVariables = setEnvVariablesFromTextFields();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SimulationInitDataFromUserDTO simulationInitDataFromUserDTO = new SimulationInitDataFromUserDTO(
                updatedEnvironmentVariables,
                entityNameToPopulation,
                mainAppController.getSelectedSimulationTermination(),
                worldName,
                mainAppController.getUserName());

        try {
            return RequestHandler.runSimulation(simulationInitDataFromUserDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PropertyDefinitionDTO> setEnvVariablesFromTextFields() throws IOException {
        final int[] index = {0};
        List<PropertyDefinitionDTO> environmentVariables = RequestHandler.getEnvironmentVariablesToSet(worldName);
        environmentVariables.forEach(
                envVar -> {
                    BasicEnvironmentVariableData controller = envVarControllerList.get(index[0]);
                    if (!controller.getInitRandom()) {
                        PropertyDefinitionDTO envVarToUpdate = environmentVariables.get(index[0]);
                        environmentVariables.set(index[0], initEnvironmentVariableDTOFromTextField(envVarToUpdate, controller.getEnvValue()));
                    }
                    index[0]++;
                }
        );
        return environmentVariables;
    }

    private PropertyDefinitionDTO initEnvironmentVariableDTOFromTextField(PropertyDefinitionDTO envVarToUpdate, String envValue) {
        Object value = envValue;

        switch (envVarToUpdate.getType()) {
            case "INT":
                try {
                    value = Integer.parseInt(envValue);
                } catch (NumberFormatException e) {
                }
                break;
            case "DOUBLE":
                try {
                    value = Double.parseDouble(envValue);
                } catch (NumberFormatException e) {
                }
                break;
            case "BOOLEAN":
                value = Boolean.parseBoolean(envValue);
                break;
            default:
                value = envValue;
        }

        return new PropertyDefinitionDTO(
                envVarToUpdate.getName(),
                envVarToUpdate.getType(),
                envVarToUpdate.getFrom(),
                envVarToUpdate.getTo(),
                false,
                value
        );
    }

    private boolean isValidPopulation(Map<String, Integer> entityNameToPopulation) {
        int totalEntities = calculateTotalEntities();
        int currentTotalPopulation = entityNameToPopulation.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        return currentTotalPopulation <= totalEntities;
    }

    private int calculateTotalEntities() {
        int totalEntities = -1;

        WorldDTO worldData = null;
        try {
            worldData = RequestHandler.getWorld(worldName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (worldData != null) {
            int gridRows = worldData.getGridNumOfRows();
            int gridCols = worldData.getGridNumOfCols();
            totalEntities = gridRows * gridCols;
        }
        return totalEntities;
    }

    private Map<String, Integer> setNewPopulationOfEntities() {
        Map<String, Integer> newEntityNameToPopulation = new HashMap<>();

        entityList.forEach(entityData -> newEntityNameToPopulation.put(entityData.getEntity(), Integer.parseInt(entityData.getPopulation())));
        return newEntityNameToPopulation;
    }

    private void cleanOldResults() {
        entitiesFlowPane.getChildren().clear();
        envVarsFlowPane.getChildren().clear();
    }

    private void clearControllersList() {
        if (entityList != null) {
            entityList.clear();
        }
        if (envVarControllerList != null) {
            envVarControllerList.clear();
        }
    }

    private void showEnvVariables() {
        List<PropertyDefinitionDTO> environmentVariables = null;
        try {
            environmentVariables = RequestHandler.getEnvironmentVariablesToSet(worldName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        environmentVariables.forEach(this::createEnvVarTile);
    }

    private void createEnvVarTile(PropertyDefinitionDTO envVar) {
        switch (envVar.getType()) {
            case "INT":
            case "DOUBLE":
                createEnvVarNumericTile(envVar);
                break;
            case "BOOLEAN":
                createEnvVarBooleanTile(envVar);
                break;
            default:
                createEnvVarStringTile(envVar);
                break;
        }
    }

    private void createEnvVarBooleanTile(PropertyDefinitionDTO envVar) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/tab/newExecution/environmentVariable/EnvironmentVariableBooleanTile.fxml"));
            Node singleEnvVarTile = loader.load();

            BooleanEnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.setEnvVarName(envVar.getName());

            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(environmentVariableController);
        } catch (IOException ignored) {
        }
    }

    private void createEnvVarNumericTile(PropertyDefinitionDTO envVar) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/tab/newExecution/environmentVariable/EnvironmentVariableNumericTile.fxml"));
            Node singleEnvVarTile = loader.load();

            NumericEnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.setEnvVarName(envVar.getName());

            environmentVariableController.setEnvVarValueSpinnerValueFactory(envVar);

            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(environmentVariableController);
        } catch (IOException ignored) {
        }
    }

    private void showEntities() {

        List<EntityDefinitionDTO> entitiesList = null;
        try {
            entitiesList = RequestHandler.getEntities(worldName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        entitiesList.forEach(entity -> {
            createTile(entity.getName());
        });
    }

    private void createEnvVarStringTile(PropertyDefinitionDTO envVar) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/tab/newExecution/environmentVariable/EnvironmentVariableStringTile.fxml"));
            Node singleEnvVarTile = loader.load();

            StringEnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.setEnvVarName(envVar.getName());

            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(environmentVariableController);
        } catch (IOException ignored) {
        }
    }

    private void createTile(String entityName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/tab/newExecution/entity/EntityTile.fxml"));
            Node singleEntityTile = loader.load();

            EntityController entityController = loader.getController();

            entityController.setEntity(entityName);
            entityList.add(entityController);

            entitiesFlowPane.getChildren().add(singleEntityTile);
        } catch (IOException ignored) {
        }
    }

    public void onNewExecutionClicked(String worldName) {
        setWorldName(worldName);
        cleanOldResults();
        clearControllersList();
        showEnvVariables();
        showEntities();
    }

    private void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setMainAppController(MainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }
}
