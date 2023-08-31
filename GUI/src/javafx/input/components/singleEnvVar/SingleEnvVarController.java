package javafx.input.components.singleEnvVar;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SingleEnvVarController extends BasicEnvVarData {

    @FXML
    private Label envVarNameLabel;

    @FXML
    private TextField envVarValueTextField;

    public SingleEnvVarController() {
        super("", "");
    }


    @FXML
    private void initialize() {
        envVarNameLabel.textProperty().bind(Bindings.concat("<", envVarName, ">"));
//        envVarValueTextField.textProperty().bind(envValue);
    }

    @FXML
    void setValueAction(ActionEvent event) {
        envVarValueTextField.textProperty().bind(Bindings.concat(envVarValueTextField.textProperty().toString()));
        setEnvValue(envVarValueTextField.getText());
    }

}
