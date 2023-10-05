package tab.newExecution.environmentVariable;

import impl.PropertyDefinitionDTO;
import javafx.beans.property.SimpleStringProperty;

public abstract class BasicEnvironmentVariableData {

    protected SimpleStringProperty envVarName;
    protected SimpleStringProperty envValue;

    protected Boolean isInitRandom = true;


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

    public Boolean getInitRandom() {
        return isInitRandom;
    }

    public abstract void clear();

    public abstract void restoreFromEnvDTO(PropertyDefinitionDTO environmentVariable);
}

