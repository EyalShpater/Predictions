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
    @Override
    public void start(Stage primaryStage) {
        LoginController loginController;
        MainAppController mainAppController;

        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(400);
        primaryStage.setMaxHeight(200);
        primaryStage.setMaxWidth(400);
        primaryStage.setTitle("Login");

        URL loginPage = getClass().getResource("/login/Login.fxml");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            Parent root = fxmlLoader.load();
            loginController = fxmlLoader.getController();

            loginController.setPrimaryStage(primaryStage);
            Scene scene = new Scene(root, 400, 200);
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
