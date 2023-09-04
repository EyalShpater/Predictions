package javafx.tab.newExecution.mainComponent;

import execution.simulation.api.PredictionsLogic;
import impl.EntityDefinitionDTO;
import impl.PropertyDefinitionDTO;
import impl.SimulationRunDetailsDTO;
import impl.WorldDTO;
import javafx.mainScene.main.PredictionsMainAppController;
import javafx.tab.newExecution.entity.EntityController;
import javafx.tab.newExecution.environmentVariable.BasicEnvironmentVariableData;
import javafx.tab.newExecution.environmentVariable.BooleanEnvironmentVariableController;
import javafx.tab.newExecution.environmentVariable.NumericEnvironmentVariableController;
import javafx.tab.newExecution.environmentVariable.StringEnvironmentVariableController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import menu.helper.PrintToScreen;
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

    private PredictionsLogic engine;
    private PredictionsMainAppController mainAppController;


    public NewExecutionController() {
        selectedFileProperty = new SimpleStringProperty();
        envVarControllerList = new ArrayList<>();
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
            createTile(entity.getName(), entity.getPopulation());
        });
    }


    @FXML
    void clearButtonActionListener(ActionEvent event) {

    }

    @FXML
    void simulationsQueueButtonActionListener(ActionEvent event) {

    }

    @FXML
    void startButtonActionListener(ActionEvent event) {
        //playAudio();
        runSimulation();

        mainAppController.onStartButtonClick();
    }

    private void runSimulation() {
        List<PropertyDefinitionDTO> updatedEnvironmentVariables;
        SimulationRunDetailsDTO runDetails;

        updatedEnvironmentVariables = setEnvVariablesFromTextFields();
        // runDetails = engine.runNewSimulation(updatedEnvironmentVariables);

        new Thread(new RunSimulationTask(engine, updatedEnvironmentVariables)).start();
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

    private void createTile(String entityName, int population) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/entity/EntityTile.fxml"));
            Node singleEntityTile = loader.load();

            EntityController entityController = loader.getController();
            entityController.setPopulation(population);
            entityController.setEntity(entityName);

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

}


