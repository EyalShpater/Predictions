package tab.details.entities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
        //population.set(String.valueOf(entity.getPopulation()));

        entity.getProperties().forEach(this::addPropertyToTreeView);
    }

    private void addPropertyToTreeView(PropertyDefinitionDTO property) {
        TreeItem<String> nameRoot = new TreeItem<>(property.getName());

        nameRoot.getChildren().add(new TreeItem<>("Type: " + property.getType().toLowerCase()));

        if (property.getFrom() != null && property.getTo() != null) {
            nameRoot.getChildren().addAll(
                    new TreeItem<>("Minimum Value: " + property.getFrom()),
                    new TreeItem<>("Maximum Value: " + property.getTo())
            );
        }

        nameRoot.getChildren().add(new TreeItem<>(
                property.isRandom() ?
                        "The value is set randomly." :
                        "The value does not initialize randomly.")
        );

        propertiesTreeView.getRoot().getChildren().add(nameRoot);
    }
}
