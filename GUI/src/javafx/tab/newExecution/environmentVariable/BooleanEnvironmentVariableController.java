package javafx.tab.newExecution.environmentVariable;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class BooleanEnvironmentVariableController extends BasicEnvironmentVariableData {

    @FXML
    private Label envVarNameLabel;

    @FXML
    private RadioButton falseRadioButton;

    @FXML
    private RadioButton trueRadioButton;


    @FXML
    private void initialize() {
        envVarNameLabel.textProperty().bind(Bindings.concat("<", envVarName, ">"));
        //envVarValueTextField.textProperty().bindBidirectional(envValue);
    }

    @FXML
    void setValueFalseAction(ActionEvent event) {
        setEnvValue("false");
        isInitRandom = false;
    }

    @FXML
    void setValueTrueAction(ActionEvent event) {
        setEnvValue("true");
        isInitRandom = false;

    }

    public BooleanEnvironmentVariableController() {
        super("", "");
    }

}
