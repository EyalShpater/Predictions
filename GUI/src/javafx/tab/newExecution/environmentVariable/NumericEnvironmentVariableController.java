package javafx.tab.newExecution.environmentVariable;

import impl.PropertyDefinitionDTO;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;


public class NumericEnvironmentVariableController extends BasicEnvironmentVariableData {

    @FXML
    private Label envVarNameLabel;

    @FXML
    private Spinner<Double> envVarValueSpinner;

    @FXML
    private CheckBox randomCheckBox;


    @FXML
    private void initialize() {
        envVarNameLabel.textProperty().bind(Bindings.concat("<", envVarName, ">"));
        envVarValueSpinner.getEditor().textProperty().bindBidirectional(envValue);
        envVarValueSpinner.disableProperty().bind(randomCheckBox.selectedProperty());
        randomCheckBox.setSelected(true);
        isInitRandom = true;
    }


    @FXML
    void randomAction(ActionEvent event) {
        /*if (randomCheckBox.isSelected()) {
            isInitRandom = true;
        } else {
            isInitRandom = false;
        }*/
        if (randomCheckBox.isSelected()) {
            isInitRandom = true;
        } else {
            isInitRandom = false;
            setEnvValue(envVarValueSpinner.getEditor().getText()); // Update envValue to match the text field value
        }
    }


    public NumericEnvironmentVariableController() {
        super("", "");
    }


    public void setEnvVarValueSpinnerValueFactory(PropertyDefinitionDTO envVar) {
        //isInitRandom = false;
        setEnvValue(envVar.getFrom().toString());

        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(envVar.getFrom(), envVar.getTo(), envVar.getFrom());
        envVarValueSpinner.setValueFactory(valueFactory);
        setValueOfEnvVarByEnterPressListenerCall(valueFactory);
        setValueOfEnvVarByReleaseListenerCall(valueFactory);
        confirmInputCharactersListenerCall(valueFactory);
    }

    private void setValueOfEnvVarByEnterPressListenerCall(SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory) {
        envVarValueSpinner.getEditor().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setValueByRange(valueFactory);
                event.consume();
            }
        });
    }

    private void setValueOfEnvVarByReleaseListenerCall(SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory) {
        envVarValueSpinner.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                setValueByRange(valueFactory);
            }
        });
    }

    private void confirmInputCharactersListenerCall(SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory) {
        envVarValueSpinner.getEditor().setOnKeyTyped(keyEvent -> {
            String input = keyEvent.getCharacter();
            if (!input.matches("[0-9]") && !(input.equals(".") && !envVarValueSpinner.getEditor().getText().contains("."))) {
                keyEvent.consume();
            }
        });
    }

    private void setValueByRange(SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory) {
        try {
            double parsedValue = Double.parseDouble(envVarValueSpinner.getEditor().getText());
            if (parsedValue >= valueFactory.getMin() && parsedValue <= valueFactory.getMax()) {
                setEnvValue(envVarValueSpinner.getEditor().getText());
                isInitRandom = false;
            } else {
                envVarValueSpinner.getEditor().setText(Double.toString(valueFactory.getValue()));
                randomCheckBox.setSelected(true);
                isInitRandom = true;
            }
        } catch (NumberFormatException e) {
            envVarValueSpinner.getEditor().setText(Double.toString(valueFactory.getValue()));
        }
    }

    public void clear() {
        isInitRandom = true;
        randomCheckBox.setSelected(true);
    }

}
