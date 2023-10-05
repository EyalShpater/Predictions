package tab.management;

import component.details.details.DetailsComponentController;
import general.constants.GeneralConstants;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;
import servlet.request.RequestHandler;
import tab.management.component.threadpoolInfo.SimulationQueueController;

import java.io.File;
import java.io.IOException;

import static general.configuration.Configuration.HTTP_CLIENT;

public class ManagementController {

    @FXML
    private TextField filePathTextField;

    @FXML
    private Button loadFileButton;

    @FXML
    private Button setThreadPoolSizeButton;

    @FXML
    private Spinner<Integer> threadPoolSizeSpinner;

    @FXML
    private GridPane queueDetails;

    @FXML
    private StackPane detailsComponent;

    @FXML
    private SimulationQueueController queueDetailsController;

    @FXML
    private DetailsComponentController detailsComponentController;

    private Stage primaryStage;

    private StringProperty filePath;
    private IntegerProperty threadPoolSize;

    @FXML
    private void initialize() {
        filePath = new SimpleStringProperty("");
        threadPoolSize = new SimpleIntegerProperty(100);

        setSpinner();

        filePathTextField.textProperty().bind(filePath);
        threadPoolSizeSpinner.accessibleTextProperty().bind(threadPoolSize.asString());
        queueDetails.setStyle(("-fx-border-color: #000000; -fx-border-width: 3px;"));
    }

    private void setSpinner() {
        threadPoolSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, GeneralConstants.MAX_THREAD_POOL_SIZE));
        threadPoolSizeSpinner.getValueFactory().setWrapAround(true);

        threadPoolSizeSpinner.getEditor().setOnAction(event -> threadPoolSizeSpinner.increment(0));
        threadPoolSizeSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                threadPoolSizeSpinner.increment(0);
            }
        });

        threadPoolSizeSpinner.getEditor().setOnKeyTyped(keyEvent -> {
            String input = keyEvent.getCharacter();
            if (!input.matches("[0-9]") && !(input.equals(".") && !threadPoolSizeSpinner.getEditor().getText().contains("."))) {
                keyEvent.consume();
            }
        });
    }

    @FXML
    void onLoadFileClicked(ActionEvent event) throws IOException {
        uploadFile();
    }

    private void uploadFile() throws IOException {
        File selectedFile = getFileUsingFileChooserDialog();
        String previousFilePath = filePath.get();

        if (selectedFile != null) {
            filePath.set(selectedFile.getAbsolutePath());

            RequestBody body =
                    new MultipartBody.Builder()
                            .addFormDataPart(
                                    GeneralConstants.UPLOADED_FILE_NAME,
                                    selectedFile.getName(),
                                    RequestBody.create(selectedFile, MediaType.parse("text/plain")))
                            .build();
            Request request = new Request.Builder()
                    .url(GeneralConstants.BASE_URL + GeneralConstants.NEW_WORLD_UPLOAD_RESOURCE)
                    .post(body)
                    .build();

            Call call = HTTP_CLIENT.newCall(request);

            Response response = call.execute();

            if (response.code() != 200) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error!");
                alert.setContentText(response.body().string());
                alert.setHeaderText("Error Has Occurred!");
                alert.showAndWait();
            }
        }
    }

    private File getFileUsingFileChooserDialog() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        return fileChooser.showOpenDialog(primaryStage);
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void onSetClicked(ActionEvent actionEvent) {
        try {
            if (RequestHandler.setThreadPoolSize(threadPoolSizeSpinner.getValue()) != 200) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error!");
                alert.setContentText("Illegal Argument!");
                alert.setHeaderText("Error Has Occurred!");
                alert.showAndWait();
            }
        } catch (Exception ignored) {
        }
    }
}
