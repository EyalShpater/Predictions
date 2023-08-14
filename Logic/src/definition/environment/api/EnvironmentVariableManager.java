package definition.environment.api;

import api.DTO;
import definition.property.api.PropertyDefinition;
import instance.enviornment.api.ActiveEnvironment;

import java.util.Collection;
import java.util.List;

public interface EnvironmentVariableManager {
    void addEnvironmentVariable(PropertyDefinition property);
    Collection<PropertyDefinition> getEnvironmentVariables();
    ActiveEnvironment createActiveEnvironment();

    void mapEnvironmentVariableDTOtoEnvironmentVariableManager(DTO environmentVariables);
}
