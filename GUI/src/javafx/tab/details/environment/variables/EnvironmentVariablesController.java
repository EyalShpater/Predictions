package javafx.tab.details.environment.variables;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class EnvironmentVariablesController {

    @FXML
    private Label nameLabel;

    @FXML
    private TreeView<String> propertiesTreeView;


    @FXML
    void initialize() {
        TreeItem<String> root = new TreeItem<>("Properties");

        TreeItem<String> p1 = new TreeItem<>("P1");
        TreeItem<String> type = new TreeItem<>("Type: String");
        TreeItem<String> value = new TreeItem<>("Value: \"Eyal\"");
        TreeItem<String> p2 = new TreeItem<>("P2");
        TreeItem<String> p3 = new TreeItem<>("P3");

        p1.getChildren().addAll(type, value);
        p2.getChildren().addAll(type, value);
        p3.getChildren().addAll(type, value);
        root.getChildren().addAll(p1, p2, p3);

        propertiesTreeView.setRoot(root);
        propertiesTreeView.setShowRoot(false);

        nameLabel.textProperty().set("Eyal");


    }
}
