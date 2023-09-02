package javafx.tab.newExecution.environmentVariable;

import javafx.beans.property.SimpleStringProperty;

public class BasicEnvironmentVariableData {

    protected SimpleStringProperty envVarName;
    protected SimpleStringProperty envValue;

    public BasicEnvironmentVariableData(String envVarName, String envVarValue) {
        this.envVarName = new SimpleStringProperty(envVarName);
        this.envValue = new SimpleStringProperty(envVarValue);
    }

    public String getEnvVarName() {
        return envVarName.get();
    }

    public void setEnvVarName(String envVarName) {
        this.envVarName.set(envVarName);
    }

    public String getEnvValue() {
        return envValue.get();
    }

    public void setEnvValue(String envValue) {
        this.envValue.set(envValue);
    }
}

