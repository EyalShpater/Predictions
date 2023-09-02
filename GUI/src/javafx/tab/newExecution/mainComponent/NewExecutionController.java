package javafx.tab.newExecution.mainComponent;

import execution.simulation.api.PredictionsLogic;
import impl.EntityDefinitionDTO;
import impl.PropertyDefinitionDTO;
import impl.SimulationRunDetailsDTO;
import impl.WorldDTO;
import javafx.tab.newExecution.entity.EntityController;
import javafx.tab.newExecution.environmentVariable.EnvironmentVariableController;
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
    List<EnvironmentVariableController> envVarControllerList;
    private static final String NO_VALUE_WAS_GIVEN = "";

    private PredictionsLogic engine;


    public NewExecutionController() {
        selectedFileProperty = new SimpleStringProperty();
        //isFileSelected = new SimpleBooleanProperty(false);
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

    public void setFileSelection(SimpleBooleanProperty fileSelection) {
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
    }

    private void showEnvVariables() {
        //List<PropertyDefinitionDTO> environmentVariables = logic.getEnvironmentVariablesToSet();
        List<PropertyDefinitionDTO> environmentVariables = engine.getEnvironmentVariablesToSet();
        environmentVariables.forEach(
                envVar -> {
                    createEnvVarTile(envVar.getName());
                }
        );
    }

    private void showEntities() {
        /*UIAdapter uiAdapter = createUIAdapter();
        logic.collectEntitiesData(uiAdapter);*/
//      List<EntityDefinitionDTO> entitiyList = logic.getEntityList();
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
        playAudio();
        runSimulation();

    }

    private void runSimulation() {
        List<PropertyDefinitionDTO> updatedEnvironmentVariables;
        SimulationRunDetailsDTO runDetails;
        PrintToScreen printer = new PrintToScreen();
        updatedEnvironmentVariables = setEnvVariablesFromTextFields();
        runDetails = engine.runNewSimulation(updatedEnvironmentVariables);
        /*runDetails = logic.runNewSimulation(updatedEnvironmentVariables);
        printer.printRunDetailsDTO(runDetails);
        System.out.println();*/
    }

    private List<PropertyDefinitionDTO> setEnvVariablesFromTextFields() {

        final int[] index = {0};
        List<PropertyDefinitionDTO> environmentVariables = engine.getEnvironmentVariablesToSet();
        environmentVariables.forEach(
                envVar -> {
                    EnvironmentVariableController controller = envVarControllerList.get(index[0]);
                    if (!controller.getEnvValue().equals(NO_VALUE_WAS_GIVEN)) {
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

    private void createEnvVarTile(String envVarName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/newExecution/environmentVariable/EnvironmentVariable.fxml"));
            Node singleEnvVarTile = loader.load();

            EnvironmentVariableController environmentVariableController = loader.getController();
            environmentVariableController.setEnvVarName(envVarName);

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
}


