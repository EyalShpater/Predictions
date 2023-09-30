package tab.managment;

import general.constants.GeneralConstants;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

import static common.Configuration.HTTP_CLIENT;

public class ManagementController {

    @FXML
    private Button loadFileButton;

    @FXML
    private ListView<?> simulationsListView;

    private OkHttpClient httpClient;
    private StringProperty filePath;

    @FXML
    private void initialize() {
        filePath = new SimpleStringProperty("");

        loadFileButton.textProperty().bind(filePath);
    }

    @FXML
    void onLoadFileClicked(ActionEvent event) throws IOException {
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

            System.out.println(response.body().string());

        }
    }

    private File getFileUsingFileChooserDialog() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        return fileChooser.showOpenDialog(primaryStage);
    }
}
