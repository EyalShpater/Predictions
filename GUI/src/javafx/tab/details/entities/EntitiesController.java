package javafx.tab.details.entities;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ContextMenuEvent;

public class EntitiesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TreeView<String> propertiesTreeView;

    @FXML
    private Label populationNumberLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private TitledPane titledPane;

    @FXML
    void propertiesOnContextRequested(ContextMenuEvent event) {

    }

//    @FXML
//    void initialize() {
//        assert propertiesTreeView != null : "fx:id=\"propertiesTreeView\" was not injected: check your FXML file 'Entities.fxml'.";
//        assert populationNumberLabel != null : "fx:id=\"populationNumberLabel\" was not injected: check your FXML file 'Entities.fxml'.";
//        assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'Entities.fxml'.";
//
//
//        TreeItem<String> root = new TreeItem<>("Properties");
//
//        TreeItem<String> name = new TreeItem<>("Name");
//        TreeItem<String> type = new TreeItem<>("String");
//
//        propertiesTreeView = new TreeView<>(root);
//        propertiesTreeView.setShowRoot(true);
//
//        nameLabel.setText("Eyal");
//        populationNumberLabel.setText("10");
//    }

    @FXML
    void initialize() {
        // Create the root node
        TreeItem<String> root = new TreeItem<>("Properties");

        // Create child nodes
        TreeItem<String> name = new TreeItem<>("Name");
        TreeItem<String> type = new TreeItem<>("String");

        for (int i = 1; i <= 30; i++) {
            TreeItem<String> a = new TreeItem<>("Name " + i);
            root.getChildren().add(a);
        }

        // Add child nodes to the root node
        root.getChildren().addAll(name, type);

        // Create the TreeView and set the root node
        propertiesTreeView.setRoot(root);
        propertiesTreeView.setShowRoot(true);  // Show the root node in the TreeView

        // Update labels
        nameLabel.setText("Ent");
        populationNumberLabel.setText("10");

        titledPane.setText(nameLabel.getText());
    }

}
