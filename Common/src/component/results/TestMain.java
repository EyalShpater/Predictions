package component.results;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TestMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Predictions");

        Parent load = FXMLLoader.load(getClass().getResource("Results.fxml"));
        Scene scene = new Scene(load, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
//        Application.launch(args);
        Gson gson = new Gson();

        Map<String, Map<Integer, Long>> map = new HashMap<>();

        map.put("Eyal", new HashMap<>());
        map.put("Shavit", new HashMap<>());

        map.get("Eyal").put(1, 1L);
        map.get("Eyal").put(2, 2L);
        map.get("Eyal").put(3, 3L);
        map.get("Eyal").put(4, 4L);

        map.get("Shavit").put(4, 1L);
        map.get("Shavit").put(7, 2L);
        map.get("Shavit").put(5, 3L);
        map.get("Shavit").put(6, 4L);

        String json = gson.toJson(map);

        Type mapType = new TypeToken<Map<String, Map<Integer, Long>>>() {
        }.getType();


        Map<String, Map<Integer, Long>> fromJson = gson.fromJson(json, mapType);

        Long num = fromJson.get("Eyal").get(1);
    }
}