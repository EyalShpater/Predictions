package javafx.tab.input.components.mainComponent;

import impl.EntityDefinitionDTO;
import impl.PropertyDefinitionDTO;
import impl.SimulationRunDetailsDTO;
import javafx.tab.input.components.singleEntity.SingleEntityController;
import javafx.tab.input.components.singleEnvVar.SingleEnvVarController;
import javafx.tab.input.logic.SecondScreenLogic;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import menu.helper.PrintToScreen;

public class SecondScreenController {
    @FXML
    private Button clearButton;

    @FXML
    private Button openFileButton;

    @FXML
    private Label selectedFileName;

    @FXML
    private Button simulationsQueueButton;

    @FXML
    private Button startButton;

    @FXML
    private FlowPane entitiesFlowPane;

    @FXML
    private FlowPane envVarsFlowPane;


    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private SecondScreenLogic logic;
    private Stage primaryStage;
    List<SingleEnvVarController> envVarControllerList;
    private static final String NO_VALUE_WAS_GIVEN = "";


    public SecondScreenController() {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        envVarControllerList = new ArrayList<>();

    }

    @FXML
    private void initialize() {
        selectedFileName.textProperty().bind(selectedFileProperty);
        startButton.disableProperty().bind(isFileSelected.not());
    }

    @FXML
    void openFileButtonActionListener(ActionEvent event) {
        cleanOldResults();
        try {
            openFileAction();
            showEntities();
            showEnvVariables();
        } catch (IllegalArgumentException e) {
            selectedFileName.setTextFill(Color.RED);
            selectedFileName.setFont(Font.font("System", FontWeight.BOLD, 14));
            selectedFileProperty.set("File did not load. " + e.getMessage());
            isFileSelected.set(false);
        }


    }

    private void showEnvVariables() {
        List<PropertyDefinitionDTO> environmentVariables = logic.getEnvironmentVariablesToSet();
        environmentVariables.forEach(
                envVar -> {
                    createEnvVarTile(envVar.getName());
                }
        );
    }

    private void showEntities() {
        /*UIAdapter uiAdapter = createUIAdapter();
        logic.collectEntitiesData(uiAdapter);*/
        List<EntityDefinitionDTO> entitiyList = logic.getEntityList();
        entitiyList.forEach(entity -> {
            createTile(entity.getName(), entity.getPopulation());
        });
    }

    void openFileAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        logic.loadXML(absolutePath);
        xmlGUISuccessActions(absolutePath);
        isFileSelected.set(true);
    }

    private void xmlGUISuccessActions(String absolutePath) {
        selectedFileName.setTextFill(Color.PURPLE);
        selectedFileName.setFont(Font.font("System", FontWeight.BOLD, 14));
        selectedFileProperty.set(absolutePath);
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
        runDetails = logic.runNewSimulation(updatedEnvironmentVariables);
        /*printer.printRunDetailsDTO(runDetails);
        System.out.println();*/
    }

    private List<PropertyDefinitionDTO> setEnvVariablesFromTextFields() {

        final int[] index = {0};
        List<PropertyDefinitionDTO> environmentVariables = logic.getEnvironmentVariablesToSet();
        environmentVariables.forEach(
                envVar -> {
                    SingleEnvVarController controller = envVarControllerList.get(index[0]);
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
        Object value = null;

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

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLogic(SecondScreenLogic logic) {
        this.logic = logic;
    }

    private UIAdapter createUIAdapter() {
        return new UIAdapter(entityData -> {
            createTile(entityData.getEntity(), entityData.getPopulation());
        });
    }

    private void createTile(String entityName, int population) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/input/components/singleEntity/single-entity.fxml"));
            Node singleEntityTile = loader.load();

            SingleEntityController singleEntityController = loader.getController();
            singleEntityController.setPopulation(population);
            singleEntityController.setEntity(entityName);

            entitiesFlowPane.getChildren().add(singleEntityTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createEnvVarTile(String envVarName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/javafx/tab/input/components/singleEnvVar/single-env-var.fxml"));
            Node singleEnvVarTile = loader.load();

            SingleEnvVarController singleEnvVarController = loader.getController();
            singleEnvVarController.setEnvVarName(envVarName);

            envVarsFlowPane.getChildren().add(singleEnvVarTile);
            envVarControllerList.add(singleEnvVarController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cleanOldResults() {
        entitiesFlowPane.getChildren().clear();
        envVarsFlowPane.getChildren().clear();
    }
    /*

    private List<PropertyDefinitionDTO> getEnvironmentVariablesFromUser() {
        List<PropertyDefinitionDTO> environmentVariables = engine.getEnvironmentVariablesToSet();
        int choice = -1;

        printer.printTitle("Set Environment Variables", '~', '-', 0);
        System.out.println();

        while (choice != 0) {
            System.out.println("Please enter the number of variable you want to set, or 0 to finish the setup");
            System.out.println();

            printer.showEnvironmentVariablesChooseMenu(environmentVariables);
            choice = typeScanner.getIntFromUserInRange(0, environmentVariables.size());

            if (choice != 0) {
                PropertyDefinitionDTO toUpdate = environmentVariables.get(choice - 1);
                environmentVariables.set(choice - 1, initEnvironmentVariableDTOFromTextField(toUpdate));
            }
        }

        return environmentVariables;
    }


    @Override
    public List<PropertyDefinitionDTO> getEnvironmentVariablesToSet() {
        return world.getEnvironmentVariablesDTO();
    }

    public void showEnvironmentVariablesChooseMenu(List<PropertyDefinitionDTO> environmentVariables) {
        if (environmentVariables != null) {
            printPropertyDefinitionDTOList(environmentVariables, 0, true);
            System.out.print(System.lineSeparator());
            System.out.println("Please enter the number of variable you want to set, or 0 to finish the setup");
        }
    }


    public void printPropertyDefinitionDTOList(List<PropertyDefinitionDTO> properties, int indentation, boolean isEnvironmentVariable) {
        for (int i = 1; i <= properties.size(); i++) {
            printLine(indentation, ' ');
            System.out.print(i + ". ");
            printPropertyDefinitionDTO(properties.get(i - 1),
                    Integer
                            .valueOf(i)
                            .toString()
                            .length() + indentation + 2,
                    isEnvironmentVariable
            );
            System.out.print(System.lineSeparator());
        }
    }



    private PropertyDefinitionDTO initEnvironmentVariableDTOFromTextField(PropertyDefinitionDTO variableDTO) {
        Object value;

        System.out.println("Please enter a value");
        switch (variableDTO.getType()) {
            case "INT":
                value = variableDTO.getFrom() == null ?
                        typeScanner.getIntFromUser() :
                        typeScanner.getIntFromUserInRange(variableDTO.getFrom().intValue(), variableDTO.getTo().intValue());
                break;
            case "DOUBLE":
                value = variableDTO.getFrom() == null ?
                        typeScanner.getDoubleFromUser() :
                        typeScanner.getDoubleFromUserInRange(variableDTO.getFrom(), variableDTO.getTo());
                break;
            case "BOOLEAN":
                value = typeScanner.getBooleanFromUser();
                break;
            default:
                value = scanner.nextLine();
        }

        return new PropertyDefinitionDTO(
                variableDTO.getName(),
                variableDTO.getType(),
                variableDTO.getFrom(),
                variableDTO.getTo(),
                false,
                value
        );
    }
     */
}


