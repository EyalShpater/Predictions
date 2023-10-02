package component.details.environment.variables;

import impl.PropertyDefinitionDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class EnvironmentVariablesController {

    @FXML
    private Label nameLabel;
    @FXML
    private ListView<String> detailsListView;

    private StringProperty entityName = new SimpleStringProperty();

    @FXML
    void initialize() {
        nameLabel.textProperty().bind(entityName);
    }

    public void setDataFromDTO(PropertyDefinitionDTO propertyDTO) {
        entityName.set(propertyDTO.getName());

        detailsListView.getItems().add("Type: " + propertyDTO.getType().toLowerCase());
        if (propertyDTO.getFrom() != null && propertyDTO.getTo() != null) {
            detailsListView.getItems().add("Minimum Value: " + propertyDTO.getFrom());
            detailsListView.getItems().add("Maximum Value: " + propertyDTO.getTo());
        }
    }
}
