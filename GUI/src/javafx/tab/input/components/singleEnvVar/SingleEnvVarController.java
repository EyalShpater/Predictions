package javafx.tab.input.components.singleEnvVar;

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
        envVarValueTextField.textProperty().bindBidirectional(envValue);
    }

    @FXML
    void setValueAction(ActionEvent event) {
        setEnvValue(envVarValueTextField.getText());
    }

}
