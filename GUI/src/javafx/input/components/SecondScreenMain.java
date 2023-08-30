package javafx.input.components;

import javafx.input.components.mainComponent.SecondScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.input.logic.SecondScreenLogic;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class SecondScreenMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        // load main fxml
        URL mainFXML = getClass().getResource("mainComponent/second-screen.fxml");
        loader.setLocation(mainFXML);
        BorderPane root = loader.load();

        // wire up controller
        SecondScreenController secController = loader.getController();
        SecondScreenLogic logic = new SecondScreenLogic(secController);
        secController.setPrimaryStage(primaryStage);
        secController.setLogic(logic);

        // set stage
        primaryStage.setTitle("Predictions");
        Scene scene = new Scene(root, 950, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
