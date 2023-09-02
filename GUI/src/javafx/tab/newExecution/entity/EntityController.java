package javafx.tab.newExecution.entity;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EntityController extends BasicEntityData {

    @FXML
    private Label entityNameLabel;
    @FXML
    private Label entityPopulationLabel;


    public EntityController() {
        super("", -1);
    }

    @FXML
    private void initialize() {
        entityNameLabel.textProperty().bind(Bindings.concat("<", entityName, ">"));
        entityPopulationLabel.textProperty().bind(population.asString());
    }
}
