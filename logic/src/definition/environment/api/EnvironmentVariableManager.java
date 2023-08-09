package definition.environment.api;

import definition.property.api.PropertyDefinition;
import instance.enviornment.api.ActiveEnvironment;

import java.util.Collection;

public interface EnvironmentVariableManager {
    void addEnvironmentVariable(PropertyDefinition property);

    Collection<PropertyDefinition> getEnvironmentVariables();

    ActiveEnvironment createActiveEnvironment();
}
