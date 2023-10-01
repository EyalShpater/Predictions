package tab.details.main;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import general.constants.GeneralConstants;
import impl.WorldDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static general.configuration.Configuration.HTTP_CLIENT;

public class MainDetailsController {

    @FXML
    private ChoiceBox<String> worldChoiceBox;

    private StringProperty selectedWorld = new SimpleStringProperty();

    @FXML
    private void initialize() throws IOException {
        selectedWorld.bind(worldChoiceBox.valueProperty());
        selectedWorld.addListener((observable, oldValue, newValue) -> onValueChanged(newValue));
    }

    @FXML
    private void onMouseClicked(MouseEvent event) throws IOException {
        getWorldsUpdateFromServer();
        System.out.println("clicked");
    }

    private void getWorldsUpdateFromServer() throws IOException {
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(GeneralConstants.BASE_URL + GeneralConstants.GET_LOADED_WORLDS_RESOURCE)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        JsonArray worldsJson = JsonParser.parseString(response.body().string()).getAsJsonArray();
        List<WorldDTO> worlds = new ArrayList<>();

        worldsJson.forEach(json -> worlds.add(gson.fromJson(json, WorldDTO.class)));

        worldChoiceBox.getItems().clear();
        worlds.forEach(world -> worldChoiceBox.getItems().add(world.getName()));
    }

    private void onValueChanged(String newValue) {
        if (newValue != null) {
            
            //logic

            System.out.println(newValue);
        }
    }
}
