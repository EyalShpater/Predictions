package tab.managment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import general.constants.GeneralConstants;
import impl.WorldDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.Configuration.HTTP_CLIENT;

public class ManagementController {

    @FXML
    private TextField filePathTextField;

    @FXML
    private Button loadFileButton;

    @FXML
    private ListView<String> simulationsListView;

    private OkHttpClient httpClient;

    private StringProperty filePath;
    private Stage primaryStage;

    @FXML
    private void initialize() {
        filePath = new SimpleStringProperty("");

        filePathTextField.textProperty().bind(filePath);
    }

    @FXML
    void onLoadFileClicked(ActionEvent event) throws IOException {
        uploadFile();
        updateLoadedWorldsInTable();
    }

    private void updateLoadedWorldsInTable() throws IOException {
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(GeneralConstants.BASE_URL + GeneralConstants.GET_LOADED_WORLDS_RESOURCE)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        JsonArray worldsJson = JsonParser.parseString(response.body().string()).getAsJsonArray();
        List<WorldDTO> worlds = new ArrayList<>();

        worldsJson.forEach(json -> worlds.add(gson.fromJson(json, WorldDTO.class)));

        simulationsListView.getItems().clear();
        worlds.forEach(world -> simulationsListView.getItems().add(world.getName()));
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

            System.out.println(response.body().string());
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
}
