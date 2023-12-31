package tab.newExecution.environmentVariable;

import impl.PropertyDefinitionDTO;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class BooleanEnvironmentVariableController extends BasicEnvironmentVariableData {

    @FXML
    private Label envVarNameLabel;

    @FXML
    private RadioButton falseRadioButton;

    @FXML
    private RadioButton trueRadioButton;
    @FXML
    private RadioButton randomRadioButton;

    @FXML
    private ToggleGroup radioButtonGroup;


    @FXML
    private void initialize() {
        envVarNameLabel.textProperty().bind(Bindings.concat("<", envVarName, ">"));
        connectRadioButtons();
        //envVarValueTextField.textProperty().bindBidirectional(envValue);
    }

    private void connectRadioButtons() {
        randomRadioButton.setSelected(true);
        radioButtonGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue == randomRadioButton) {
                trueRadioButton.setSelected(false);
                falseRadioButton.setSelected(false);
                isInitRandom = true;
            } else if (newValue == falseRadioButton) {
                trueRadioButton.setSelected(false);
                randomRadioButton.setSelected(false);
                isInitRandom = false;
                setEnvValue("false");
            } else if (newValue == trueRadioButton) {
                falseRadioButton.setSelected(false);
                randomRadioButton.setSelected(false);
                isInitRandom = false;
                setEnvValue("true");
            }
        });
    }

    public BooleanEnvironmentVariableController() {
        super("", "");
    }

    public void clear() {
        isInitRandom = true;
        randomRadioButton.setSelected(true);
        trueRadioButton.setSelected(false);
        falseRadioButton.setSelected(false);
    }

    @Override
    public void restoreFromEnvDTO(PropertyDefinitionDTO environmentVariable) {
        setEnvVarName(environmentVariable.getName());
        if (!environmentVariable.isRandom()) {
            Boolean value = (Boolean) environmentVariable.getDefaultValue();
            randomRadioButton.setSelected(false);
            if (value) {
                falseRadioButton.setSelected(false);
                trueRadioButton.setSelected(true);

            } else {
                trueRadioButton.setSelected(false);
                falseRadioButton.setSelected(true);
            }
            isInitRandom = false;
            setEnvValue(String.valueOf(value));
        } else {
            trueRadioButton.setSelected(false);
            falseRadioButton.setSelected(false);
            randomRadioButton.setSelected(true);
            isInitRandom = true;
        }
    }
}
