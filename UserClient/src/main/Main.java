package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tab.details.main.MainDetailsController;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainDetailsController managementController;

        primaryStage.setTitle("Predictions");

        URL resource = getClass().getResource("/tab/details/main/MainDetails.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(resource);
        Parent mainApp = loader.load();

        managementController = loader.getController();
        Scene scene = new Scene(mainApp, 960, 640);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(request -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }
}
