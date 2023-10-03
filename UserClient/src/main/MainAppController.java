
package main;

import component.details.main.MainDetailsController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import login.LoginController;


import java.io.IOException;
import java.net.URL;

public class MainAppController {

    @FXML
    private Pane mainPanel;

    @FXML
    private TabPane tabPane;

    @FXML
    private BorderPane borderPane;

    private MainDetailsController detailsTabController;
    //private NewExecutionController newExecutionTabController;

    private LoginController logicController;
    private ScrollPane loginComponent;

    @FXML
    public void initialize() {
        loadLoginPage();
    }

    private void loadLoginPage() {
        URL loginPageUrl = getClass().getResource("/login/login.fxml");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            loginComponent = fxmlLoader.load();
            logicController = fxmlLoader.getController();
            logicController.setChatAppMainController(this);
            setMainPanelTo(loginComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setMainPanelTo(Parent pane) {
        mainPanel.getChildren().clear();
        mainPanel.getChildren().add(pane);

        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 1.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);


    }

    public void switchToTabs() {
        setDetailsTab();
        setMainPanelTo(tabPane);
    }

    private void setDetailsTab() {
        try {
            Tab details = new Tab("Details");

            URL resource = getClass().getResource("/component/details/main/MainDetails.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent detailsContent = loader.load();
            details.setContent(detailsContent);


            detailsTabController = loader.getController();
            tabPane.getTabs().add(details);
        } catch (Exception ignored) {
            System.out.println(ignored.getStackTrace());
        }
    }
}

