package main;

import general.constants.GeneralConstants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import servlet.request.RequestHandler;
import util.http.HttpClientUtil;

import java.io.IOException;

import static general.configuration.Configuration.HTTP_CLIENT;

public class AdminMainAppController {
    private Stage primaryStage;

    @FXML
    void initialize() {

    }

    public void onStart() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.LOGIN_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.USER_NAME_PARAMETER_NAME, GeneralConstants.ADMIN_USER_NAME);
        String finalUrl = urlBuilder.build().toString();

        RequestBody body = RequestBody.create("".getBytes());

        Request request = new Request.Builder()
                .url(finalUrl)
                .get()
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();

            if (response.code() != 200) {
                alert("Admin is already loged in!");
                Platform.exit();
                System.exit(0);
            }
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupCloseEvent();
    }

    private void setupCloseEvent() {
        if (primaryStage != null) {
            primaryStage.setOnCloseRequest(request -> {
                onExit();
            });
        }
    }

    private void onExit() {
        try {
            RequestHandler.logoutUser(GeneralConstants.ADMIN_USER_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Platform.exit();
        System.exit(0);
    }

    private void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


