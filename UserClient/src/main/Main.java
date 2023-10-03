package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginController;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    /*@Override
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
    }*/

    @Override
    public void start(Stage primaryStage) {

        MainAppController mainAppController;

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.setTitle("Chat App Client");

        URL loginPage = getClass().getResource("/main/MainApp.fxml");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            Parent root = fxmlLoader.load();
            mainAppController = fxmlLoader.getController();

            Scene scene = new Scene(root, 960, 600);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(request -> {
                Platform.exit();
                System.exit(0);
            });

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
