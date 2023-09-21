package javafx.mainScene.header;

import execution.simulation.api.PredictionsLogic;
import javafx.animation.RotateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.mainScene.main.PredictionsMainAppController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class HeaderController {

    @FXML
    private Button loadFileButton;

    @FXML
    private TextField selectedFilePath;

    @FXML
    private Button simulationsQueueButton;

    @FXML
    private MenuButton themeMenuButton;

    @FXML
    private RadioButton animationRadioButton;


    private PredictionsLogic engine;
    private Stage primaryStage;
    private PredictionsMainAppController mainAppController;

    private SimpleStringProperty filePath;
    private SimpleBooleanProperty isNewFileSelected;
    private SimpleBooleanProperty isAnimated;

    private TabPane tabPane;

    public HeaderController() {
        filePath = new SimpleStringProperty("load file here...");
        isNewFileSelected = new SimpleBooleanProperty(false);
        isAnimated = new SimpleBooleanProperty();
    }

    @FXML
    private void initialize() {
        selectedFilePath.textProperty().bind(filePath);
        selectedFilePath.disableProperty().bind(isNewFileSelected.not());
        isAnimated.bind(animationRadioButton.selectedProperty());
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

        if (isAnimated.get()) {
            flipButtonAnimation(loadFileButton);
        }

        Tab resultsTab = findTabByName("New Execution");
        if (resultsTab != null) {
            tabPane.getSelectionModel().select(resultsTab);
        }
    }

    private Tab findTabByName(String tabName) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(tabName)) {
                return tab;
            }
        }
        return null;
    }

    private void flipButtonAnimation(Button button) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), button);

        rotateTransition.setByAngle(360);

        String buttonText = button.getText();

        rotateTransition.setOnFinished(event -> {
            button.setText(buttonText);
        });

        rotateTransition.play();
    }

    private File getFileUsingFileChooserDialog() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        return fileChooser.showOpenDialog(primaryStage);
    }

    private void loadFileToEngine(String absolutePath) {
        try {
            String lastFilePath = filePath.get();

            engine.loadXML(absolutePath);
            filePath.set(absolutePath);
            isNewFileSelected.set(!lastFilePath.equals(absolutePath));
        } catch (Exception e) {
            showErrorPopUp(e.getMessage() == null ?
                    "General Error has occurred." :
                    e.getMessage());
            isNewFileSelected.set(false);
        }
    }

    @FXML
    void simulationsQueueButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SimulationQueuePopup.fxml"));
            Stage popupStage = new Stage();
            popupStage.setTitle("Simulation Queue Details");

            loader.setController(new SimulationQueuePopUpController());
            popupStage.setScene(new Scene(loader.load()));

            SimulationQueuePopUpController popupController = loader.getController();
            popupController.setEngine(this.engine);

            popupStage.showAndWait();
        } catch (IOException ignored) {
        }
    }

    public SimpleBooleanProperty getIsFileSelectedProperty() {
        return isNewFileSelected;
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

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public boolean isIsAnimated() {
        return isAnimated.get();
    }

    public SimpleBooleanProperty isAnimatedProperty() {
        return isAnimated;
    }
}
