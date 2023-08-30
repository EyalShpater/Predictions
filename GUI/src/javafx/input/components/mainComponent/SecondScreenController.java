package javafx.input.components.mainComponent;

import execution.simulation.api.PredictionsLogic;
import javafx.input.components.singleEntity.SingleEntityController;
import javafx.input.logic.SecondScreenLogic;
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

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

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


    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private SecondScreenLogic logic;
    private Stage primaryStage;
    /*Media media;
    MediaPlayer mediaPlayer ;*/


    public SecondScreenController() {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);

    }

    @FXML
    private void initialize() {
        selectedFileName.textProperty().bind(selectedFileProperty);
        startButton.disableProperty().bind(isFileSelected.not());
    }

    @FXML
    void openFileButtonActionListener(ActionEvent event) {
        openFileAction();
        showEntities();

    }

    private void showEntities() {
        UIAdapter uiAdapter = createUIAdapter();
        logic.collectEntitiesData(uiAdapter);
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
        try {
            logic.loadXML(absolutePath);
            //TODO: implement a UI adapter
            xmlGUISuccessActions(absolutePath);
            isFileSelected.set(true);
        } catch (IllegalArgumentException e) {
            selectedFileName.setTextFill(Color.RED);
            selectedFileName.setFont(Font.font("System", FontWeight.BOLD, 14));
            selectedFileProperty.set("File did not load. " + e.getMessage());
            isFileSelected.set(false);
        }
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
            loader.setLocation(getClass().getResource("/gui/details/components/singleEntity/single-entity.fxml"));
            Node singleEntityTile = loader.load();

            SingleEntityController singleEntityController = loader.getController();
            singleEntityController.setPopulation(population);
            singleEntityController.setEntity(entityName);

            entitiesFlowPane.getChildren().add(singleEntityTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
