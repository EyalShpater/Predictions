package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginController;
import servlet.request.RequestHandler;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        LoginController loginController;
        MainAppController mainAppController;

        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(400);
        primaryStage.setMaxHeight(250);
        primaryStage.setMaxWidth(400);
        primaryStage.setTitle("Login");

        URL loginPage = getClass().getResource("/login/Login.fxml");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            Parent root = fxmlLoader.load();
            loginController = fxmlLoader.getController();

            loginController.setPrimaryStage(primaryStage);
            Scene scene = new Scene(root, 400, 250);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
