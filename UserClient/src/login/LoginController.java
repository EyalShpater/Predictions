package login;

import general.constants.GeneralConstants;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.MainAppController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML
    private Label errorMessageLabel;

    @FXML
    private Button loginButton;

    @FXML
    private TextField userNameTextField;

    private String userName;
    private Stage primaryStage;
    private MainAppController mainAppController;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();

    @FXML
    public void initialize() {
        errorMessageLabel.textProperty().bind(errorMessageProperty);
    }

    @FXML
    private void loginButtonClicked(ActionEvent event) {
        userName = userNameTextField.getText();

        if (userName.isEmpty()) {
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        String finalUrl = HttpUrl
                .parse((GeneralConstants.BASE_URL + GeneralConstants.LOGIN_RESOURCE))
                .newBuilder()
                .addQueryParameter("username", userName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> {
                        errorMessageProperty.set("Something went wrong " + responseBody);
                        System.out.println(responseBody);
                    });
                } else {
                    Platform.runLater(() -> {
                        mainAppController.switchToTabs();
                    });
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }
        });

    }

    private void loadMainApp() {
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.setMaxWidth(Integer.MAX_VALUE);
        primaryStage.setMaxHeight(Integer.MAX_VALUE);
        primaryStage.setTitle("Predictions User Application");

        URL loginPage = getClass().getResource("/main/MainApp.fxml");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            Parent root = fxmlLoader.load();
            mainAppController = fxmlLoader.getController();
            mainAppController.setUserName(userName);

            Scene scene = new Scene(root, 960, 640);
            primaryStage.setScene(scene);

            primaryStage.setOnCloseRequest(request -> {
                Platform.exit();
                System.exit(0);
            });

        } catch (Exception e) {
        }
    }
    @FXML
    void userNameKeyTyped(KeyEvent event) {

    }

    public void setChatAppMainController(MainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
