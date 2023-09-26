package definition.environment.api;

import api.DTO;
import definition.property.api.PropertyDefinition;
import impl.PropertyDefinitionDTO;
import instance.enviornment.api.ActiveEnvironment;

import java.util.Collection;

public interface EnvironmentVariableManager {
    void addEnvironmentVariable(PropertyDefinition property);

    Collection<PropertyDefinition> getEnvironmentVariables();

//    ActiveEnvironment createActiveEnvironment();

    void addEnvironmentVariableDTO(PropertyDefinitionDTO environmentVariables);

    boolean isEmpty();
}
