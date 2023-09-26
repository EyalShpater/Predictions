package javafx.mainScene.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        PredictionsMainAppController predictionsMainAppController;

        primaryStage.setTitle("Predictions");

        URL resource = getClass().getResource("PredictionsMainApp.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(resource);
        Parent mainApp = loader.load();

        predictionsMainAppController = loader.getController();
        Scene scene = new Scene(mainApp, 960, 640);
        primaryStage.setScene(scene);
        predictionsMainAppController.setScene(scene);

        primaryStage.setOnCloseRequest(request -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
