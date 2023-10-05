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
        AllocationsController managementController;

        primaryStage.setTitle("Predictions");

        URL resource = getClass().getResource("/tab/allocations/Allocations.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(resource);
        Parent mainApp = loader.load();

        managementController = loader.getController();
        Scene scene = new Scene(mainApp, 960, 640);
        primaryStage.setScene(scene);
        // managementController.setStage(primaryStage);

        primaryStage.setOnCloseRequest(request -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();

        System.out.println(new Gson().toJson(new RunRequestDTO("Eyal", "Smoker", 10, new TerminationDTO(10, 0, true, false))));
    }
}
