package javafx.tab.newExecution.mainComponent;

import execution.simulation.api.PredictionsLogic;
import impl.*;
import javafx.mainScene.main.PredictionsMainAppController;
import javafx.scene.control.*;
import javafx.tab.newExecution.entity.EntityController;
import javafx.tab.newExecution.entity.EntityData;
import javafx.tab.newExecution.environmentVariable.BasicEnvironmentVariableData;
import javafx.tab.newExecution.environmentVariable.BooleanEnvironmentVariableController;
import javafx.tab.newExecution.environmentVariable.NumericEnvironmentVariableController;
import javafx.tab.newExecution.environmentVariable.StringEnvironmentVariableController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import task.RunSimulationTask;

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


    private SimpleStringProperty selectedFileProperty;
    private Stage primaryStage;
    List<BasicEnvironmentVariableData> envVarControllerList;
    List<EntityData> entityList;

    private TabPane tabPane;
    private PredictionsLogic engine;
    private PredictionsMainAppController mainAppController;


    public NewExecutionController() {
        selectedFileProperty = new SimpleStringProperty();
        envVarControllerList = new ArrayList<>();
        entityList = new ArrayList<>();
    }

    @FXML
    private void initialize() {
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    /*public void setIsFileSelectedProperty(SimpleBooleanProperty fileSelection) {
        startButton.disableProperty().bind(fileSelection.not());
        createFileSelectionListener(fileSelection);
    }

    private void createFileSelectionListener(SimpleBooleanProperty fileSelection) {
        fileSelection.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                cleanOldResults();
                showEntities();
                showEnvVariables();
            }
        });
    }*/
    public void onNewFileLoaded() {
        cleanOldResults();
        showEnvVariables();
        showEntities();
    }

    private void showEnvVariables() {
        List<PropertyDefinitionDTO> environmentVariables = engine.getEnvironmentVariablesToSet();
        environmentVariables.forEach(this::createEnvVarTile);
    }

    private void showEntities() {
        WorldDTO loadedSimulationDetails = engine.getLoadedSimulationDetails();
        List<EntityDefinitionDTO> entitiesList = loadedSimulationDetails.getEntities();
        entitiesList.forEach(entity -> {
            createTile(entity.getName());
        });
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
        } else {
            alertAction();
        }
    }

    private void alertAction() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        int totalEntities = calculateTotalEntities();
        alert.setTitle("Invalid Population");
        alert.setHeaderText("Invalid population values detected.");
        alert.setContentText("Please make sure that the total population does not exceed the limit: " + totalEntities);

        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait();
    }

    private void startSimulationAction(Map<String, Integer> entityNameToPopulation) {
        //playAudio();
        int simulationSerialNumber;

        simulationSerialNumber = runSimulation(entityNameToPopulation);
        mainAppController.onStartButtonClick(simulationSerialNumber);

        Tab resultsTab = findResultsTabByName("Results");
        if (resultsTab != null) {
            tabPane.getSelectionModel().select(resultsTab);
        }
    }

    private int calculateTotalEntities() {
        int gridRows = engine.getLoadedSimulationDetails().getGridNumOfRows();
        int gridCols = engine.getLoadedSimulationDetails().getGridNumOfCols();
        int totalEntities = gridRows * gridCols;

        return totalEntities;
    }

    private boolean isValidPopulation(Map<String, Integer> entityNameToPopulation) {
        int totalEntities = calculateTotalEntities();
        int currentTotalPopulation = entityNameToPopulation.values().stream().mapToInt(Integer::intValue).sum();

        return currentTotalPopulation <= totalEntities;
    }

    private Tab findResultsTabByName(String tabName) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(tabName)) {
                return tab;
            }
        }
        return null;
    }

    private int runSimulation(Map<String, Integer> entityNameToPopulation) {
        List<PropertyDefinitionDTO> updatedEnvironmentVariables;

        updatedEnvironmentVariables = setEnvVariablesFromTextFields();
        SimulationInitDataFromUserDTO simulationInitDataFromUserDTO = new SimulationInitDataFromUserDTO(updatedEnvironmentVariables, entityNameToPopulation);

        return engine.runNewSimulation(simulationInitDataFromUserDTO);
    }

    private Map<String, Integer> setNewPopulationOfEntities() {
        Map<String, Integer> newEntityNameToPopulation = new HashMap<>();

        entityList.forEach(entityData -> newEntityNameToPopulation.put(entityData.getEntity(), Integer.parseInt(entityData.getPopulation())));
        return newEntityNameToPopulation;
    }

    private List<PropertyDefinitionDTO> setEnvVariablesFromTextFields() {
        final int[] index = {0};
        List<PropertyDefinitionDTO> environmentVariables = engine.getEnvironmentVariablesToSet();
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


    public void playAudio() {
        URL audioFileUrl = getClass().getResource("aviad2.mp3");
        if (audioFileUrl != null) {
            String audioFilePath = audioFileUrl.toString();
            Media media = new Media(audioFilePath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            if (mediaPlayer.getStatus() != Status.PLAYING) {
                mediaPlayer.play();
            }
        }
    }

    private void createTile(String entityName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/entity/EntityTile.fxml"));
            Node singleEntityTile = loader.load();

            EntityController entityController = loader.getController();

            entityController.setEntity(entityName);
            entityList.add(entityController);

            entitiesFlowPane.getChildren().add(singleEntityTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/environmentVariable/EnvironmentVariableBooleanTile.fxml"));
            Node singleEnvVarTile = loader.load();

            BooleanEnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.setEnvVarName(envVar.getName());
            //environmentVariableController.
            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(environmentVariableController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createEnvVarNumericTile(PropertyDefinitionDTO envVar) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/environmentVariable/EnvironmentVariableNumericTile.fxml"));
            Node singleEnvVarTile = loader.load();

            NumericEnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.setEnvVarName(envVar.getName());

            environmentVariableController.setEnvVarValueSpinnerValueFactory(envVar);

            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(environmentVariableController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createEnvVarStringTile(PropertyDefinitionDTO envVar) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/environmentVariable/EnvironmentVariableStringTile.fxml"));
            Node singleEnvVarTile = loader.load();

            StringEnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.setEnvVarName(envVar.getName());

            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(environmentVariableController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cleanOldResults() {
        entitiesFlowPane.getChildren().clear();
        envVarsFlowPane.getChildren().clear();
    }

    public void setMainAppController(PredictionsMainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void restoreDataValuesToTiles(SimulationInitDataFromUserDTO userInputOfSimulationBySerialNumber) {
        cleanOldResults();
        entityList.clear();
        envVarControllerList.clear();

        restoreEntityTilesWithDataValues(userInputOfSimulationBySerialNumber.getEntityNameToPopulation());
        restoreEnvVarsTilesWithDataValues(userInputOfSimulationBySerialNumber.getEnvironmentVariables());
    }

    private void restoreEnvVarsTilesWithDataValues(List<PropertyDefinitionDTO> environmentVariables) {
        environmentVariables.forEach(this::restoreEnvVarTile);
    }

    private void restoreEnvVarTile(PropertyDefinitionDTO environmentVariable) {
        switch (environmentVariable.getType()) {
            case "INT":
            case "DOUBLE":
                restoreEnvVarNumericTile(environmentVariable);
                break;
            case "BOOLEAN":
                restoreEnvVarBooleanTile(environmentVariable);
                break;
            default:
                restoreEnvVarStringTile(environmentVariable);
                break;
        }
    }

    private void restoreEnvVarStringTile(PropertyDefinitionDTO environmentVariable) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/environmentVariable/EnvironmentVariableStringTile.fxml"));
            Node singleEnvVarTile = loader.load();

            StringEnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.restoreFromEnvDTO(environmentVariable);

            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(environmentVariableController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restoreEnvVarBooleanTile(PropertyDefinitionDTO environmentVariable) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/environmentVariable/EnvironmentVariableBooleanTile.fxml"));
            Node singleEnvVarTile = loader.load();

            BooleanEnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.restoreFromEnvDTO(environmentVariable);

            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(environmentVariableController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restoreEnvVarNumericTile(PropertyDefinitionDTO environmentVariable) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/environmentVariable/EnvironmentVariableNumericTile.fxml"));
            Node singleEnvVarTile = loader.load();

            NumericEnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.setEnvVarName(environmentVariable.getName());
            environmentVariableController.restoreFromEnvDTO(environmentVariable);

            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(environmentVariableController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void restoreEntityTilesWithDataValues(Map<String, Integer> entityNameToPopulation) {
        entityNameToPopulation.forEach((name, population) -> restoreEntityTile(name, population));
    }

    private void restoreEntityTile(String entityName, int population) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/entity/EntityTile.fxml"));
            Node singleEntityTile = loader.load();

            EntityController entityController = loader.getController();

            entityController.restoreTileFromPrevData(entityName, population);
            entityList.add(entityController);

            entitiesFlowPane.getChildren().add(singleEntityTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


