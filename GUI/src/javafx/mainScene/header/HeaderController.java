package javafx.mainScene.header;

import execution.simulation.api.PredictionsLogic;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.mainScene.main.PredictionsMainAppController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HeaderController {

    @FXML
    private Button loadFileButton;

    @FXML
    private TextField selectedFilePath;

    @FXML
    private Button simulationsQueueButton;

    private PredictionsLogic engine;
    private Stage primaryStage;
    private PredictionsMainAppController mainAppController;
    private SimpleStringProperty filePath;
    private SimpleBooleanProperty isFileSelected;

    public HeaderController() {
        filePath = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        selectedFilePath.textProperty().bind(filePath);
    }

    @FXML
    void loadFileButtonOnAction(ActionEvent event) {
        File selectedFile = getFileUsingFileChooserDialog();
        String previousFilePath = filePath.get();

        if (selectedFile != null) {
            String absolutePath = selectedFile.getAbsolutePath();
            loadFileToEngine(absolutePath);

            if (!previousFilePath.equals(filePath.get())) {
                mainAppController.onNewFileLoaded();
            }
        }
    }

    private File getFileUsingFileChooserDialog() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        return fileChooser.showOpenDialog(primaryStage);
    }

    private void loadFileToEngine(String absolutePath) {
        try {
            engine.loadXML(absolutePath);
            //TODO: implement a UI adapter
            filePath.set(absolutePath);
            isFileSelected.set(true);
        } catch (Exception e) {
            showErrorPopUp(e.getMessage() == null ?
                    "General Error has occurred." :
                    e.getMessage());

            isFileSelected.set(false);
        }
    }

    @FXML
    void simulationsQueueButtonOnAction(ActionEvent event) {

    }

    public SimpleBooleanProperty getIsFileSelectedProperty() {
        return isFileSelected;
    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMainAppController(PredictionsMainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }

    private void showErrorPopUp(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error!");
        alert.setContentText(message);
        alert.setHeaderText("Error Has Occurred!");
        alert.showAndWait();
    }
}
