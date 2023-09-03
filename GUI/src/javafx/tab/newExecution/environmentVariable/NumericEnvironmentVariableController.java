package javafx.tab.newExecution.environmentVariable;

import impl.PropertyDefinitionDTO;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;


public class NumericEnvironmentVariableController extends BasicEnvironmentVariableData {

    @FXML
    private Label envVarNameLabel;

    @FXML
    private Spinner<Double> envVarValueSpinner;


    @FXML
    private void initialize() {
        envVarNameLabel.textProperty().bind(Bindings.concat("<", envVarName, ">"));
        envVarValueSpinner.getEditor().textProperty().bindBidirectional(envValue);
    }


    public NumericEnvironmentVariableController() {
        super("", "");
    }


    public void setEnvVarValueSpinnerValueFactory(PropertyDefinitionDTO envVar) {
        envVar.getType();
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(envVar.getFrom(), envVar.getTo());
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

    //TODO: add a int and double restrictions
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
            }
        } catch (NumberFormatException e) {
            envVarValueSpinner.getEditor().setText(Double.toString(valueFactory.getValue()));
        }
    }

}
