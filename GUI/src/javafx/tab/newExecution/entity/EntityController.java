package javafx.tab.newExecution.entity;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class EntityController extends BasicEntityData {

    @FXML
    private Label entityNameLabel;
    @FXML
    private TextField entityPopulationTextField;

    public EntityController() {
        super("", "0");
    }

    @FXML
    private void initialize() {
        entityNameLabel.textProperty().bind(Bindings.concat("<", entityName, ">"));

        // Add an event filter to allow only numeric input
        entityPopulationTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!isNumeric(event.getCharacter())) {
                event.consume();
            }
        });

        // Add a listener to update the population property when text changes
        entityPopulationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Update the population property when a valid numeric value is entered
                int newPopulation = Integer.parseInt(newValue);
                population.set(newValue);
            } catch (NumberFormatException e) {

            }
        });
    }

    @FXML
    void setPopulationActionListener(ActionEvent event) {
        // You can handle population updates here if needed
    }

    // Helper method to check if a string is numeric
    private boolean isNumeric(String str) {
        return str.matches("-?\\d*");
    }

    public void clear() {
        entityPopulationTextField.clear();
    }

    @Override
    public void restoreTileFromPrevData(String entityName, int population) {
        setEntity(entityName);
        entityPopulationTextField.setText(String.valueOf(population));
    }
}