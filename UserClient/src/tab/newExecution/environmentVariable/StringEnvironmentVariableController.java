package tab.newExecution.environmentVariable;

import impl.PropertyDefinitionDTO;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StringEnvironmentVariableController extends BasicEnvironmentVariableData {

    @FXML
    private Label envVarNameLabel;

    @FXML
    private TextField envVarValueTextField;

    @FXML
    private CheckBox randomCheckBox;

    public StringEnvironmentVariableController() {
        super("", "");
    }

    @FXML
    private void initialize() {
        envVarNameLabel.textProperty().bind(Bindings.concat("<", envVarName, ">"));
        envVarValueTextField.textProperty().bindBidirectional(envValue);
        envVarValueTextField.disableProperty().bind(randomCheckBox.selectedProperty());
        randomCheckBox.setSelected(true);
        randomUnCheckedListenerSet();
        textFieldFocusLostListener();
    }

    private void textFieldFocusLostListener() {
        envVarValueTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateTextField();
            }
        });
    }

    private void randomUnCheckedListenerSet() {
        randomCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateTextField();
            }
        });
    }

    @FXML
    void randomAction(ActionEvent event) {
        if (randomCheckBox.isSelected()) {
            isInitRandom = true;
        } else {
            isInitRandom = false;
            validateTextField();
        }
    }

    @FXML
    void setValueAction(ActionEvent event) {
        setEnvValue(envVarValueTextField.getText());
        isInitRandom = false;
    }

    private void validateTextField() {
        if (!randomCheckBox.isSelected() && envVarValueTextField.getText().isEmpty()) {
            isInitRandom = true;
        } else if (!randomCheckBox.isSelected() && !envVarValueTextField.getText().isEmpty()) {
            isInitRandom = false;
            setEnvValue(envVarValueTextField.getText());
        }
    }

    public void clear() {
        isInitRandom = true;
        randomCheckBox.setSelected(true);
        envVarValueTextField.clear();
    }

    @Override
    public void restoreFromEnvDTO(PropertyDefinitionDTO environmentVariable) {
        setEnvVarName(environmentVariable.getName());
        if (!environmentVariable.isRandom()) {
            randomCheckBox.setSelected(false);
            setEnvValue((String) environmentVariable.getDefaultValue());
        }
    }
}
