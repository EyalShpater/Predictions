package javafx.mainscene.header;

import execution.simulation.api.PredictionsLogic;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private TextField selectedFileName;

    @FXML
    private Button simulationsQueueButton;

    private PredictionsLogic engine;
    private Stage primaryStage;
    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;

    public HeaderController() {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        selectedFileName.textProperty().bind(selectedFileProperty);
    }

    @FXML
    void loadFileButtonOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        try {
            engine.loadXML(absolutePath);
            //TODO: implement a UI adapter
            selectedFileProperty.set(absolutePath);
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

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void showErrorPopUp(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error!");
        alert.setContentText(message);
        alert.setHeaderText("Error Has Occurred!");
        alert.showAndWait();
    }
}
