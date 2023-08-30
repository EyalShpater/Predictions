package javafx.tab.input.components.singleEntity;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleEntityController extends BasicEntityData {

    @FXML
    private Label entityNameLabel;
    @FXML
    private Label entityPopulationLabel;


    public SingleEntityController() {
        super("", -1);
    }

    @FXML
    private void initialize() {
        entityNameLabel.textProperty().bind(Bindings.concat("<", entityName, ">"));
        entityPopulationLabel.textProperty().bind(population.asString());
    }
}
