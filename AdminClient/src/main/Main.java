package main;

import com.google.gson.Gson;
import impl.RunRequestDTO;
import impl.TerminationDTO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tab.allocations.AllocationsController;
import tab.management.ManagementController;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Predictions Admin Application");

        URL resource = getClass().getResource("/main/AdminMainApp.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(resource);
        Parent mainApp = loader.load();

        Scene scene = new Scene(mainApp, 960, 640);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(request -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }
}
