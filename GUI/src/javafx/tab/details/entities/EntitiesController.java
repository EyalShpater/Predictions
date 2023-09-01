package javafx.tab.details.entities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import execution.simulation.api.PredictionsLogic;
import impl.EntityDefinitionDTO;
import impl.PropertyDefinitionDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ContextMenuEvent;

public class EntitiesController {

    @FXML
    private TreeView<String> propertiesTreeView;
    @FXML
    private Label populationNumberLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private TitledPane mainScreen;

    private StringProperty population = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();

    @FXML
    void initialize() {
        populationNumberLabel.textProperty().bind(population);
        nameLabel.textProperty().bind(name);
        mainScreen.textProperty().bind(name);
        propertiesTreeView.setRoot(new TreeItem<String>("Properties"));
        propertiesTreeView.setShowRoot(false);
    }

    public void setDataFromDTO(EntityDefinitionDTO entity) {
        name.set(entity.getName());
        population.set(String.valueOf(entity.getPopulation()));

        entity.getProperties().forEach(this::addPropertyToTreeView);
    }

    private void addPropertyToTreeView(PropertyDefinitionDTO property) {
        TreeItem<String> nameRoot = new TreeItem<>(property.getName());

        TreeItem<String> type = new TreeItem<>("Type: " + property.getType().toLowerCase());
        nameRoot.getChildren().add(type);

        if (property.getFrom() != null && property.getTo() != null) {
            TreeItem<String> minValue = new TreeItem<>("Minimum Value: " + property.getFrom());
            TreeItem<String> maxValue = new TreeItem<>("Maximum Value: " + property.getTo());

            nameRoot.getChildren().addAll(minValue, maxValue);
        }

        TreeItem<String> isRandom = new TreeItem<>(
                property.isRandom() ?
                        "The value is set randomly." :
                        "The value does not initialize randomly.");

        nameRoot.getChildren().add(isRandom);

        propertiesTreeView.getRoot().getChildren().add(nameRoot);
    }
}
