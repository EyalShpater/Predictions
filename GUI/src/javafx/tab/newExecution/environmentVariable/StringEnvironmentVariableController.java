package javafx.tab.newExecution.environmentVariable;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StringEnvironmentVariableController extends BasicEnvironmentVariableData {

    @FXML
    private Label envVarNameLabel;

    @FXML
    private TextField envVarValueTextField;

    public StringEnvironmentVariableController() {
        super("", "");
    }

    @FXML
    private void initialize() {
        envVarNameLabel.textProperty().bind(Bindings.concat("<", envVarName, ">"));
        envVarValueTextField.textProperty().bindBidirectional(envValue);
    }

    @FXML
    void setValueAction(ActionEvent event) {
        setEnvValue(envVarValueTextField.getText());
        isInitRandom = false;
    }

}
