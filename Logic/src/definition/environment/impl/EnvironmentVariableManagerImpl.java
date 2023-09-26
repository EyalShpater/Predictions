package definition.environment.impl;

import definition.environment.api.EnvironmentVariableManager;
import definition.property.api.PropertyDefinition;
import definition.property.impl.PropertyDefinitionImpl;
import impl.PropertyDefinitionDTO;
import instance.enviornment.api.ActiveEnvironment;
import instance.enviornment.impl.ActiveEnvironmentImpl;
import instance.property.impl.PropertyInstanceImpl;

import java.io.Serializable;
import java.util.*;

public class EnvironmentVariableManagerImpl implements EnvironmentVariableManager , Serializable {
    private Map<String, PropertyDefinition> propNameToPropDefinition = new HashMap<>();

    @Override
    public void addEnvironmentVariable(PropertyDefinition property) {
        if (property == null) {
            throw new NullPointerException("Property can not be null!");
        }

        propNameToPropDefinition.put(property.getName(), property);
    }

    @Override
    public Collection<PropertyDefinition> getEnvironmentVariables() {
        return propNameToPropDefinition.values();
    }

//    @Override
//    public ActiveEnvironment createActiveEnvironment() {
//        ActiveEnvironment environment = new ActiveEnvironmentImpl();
//
//        for (PropertyDefinition property : propNameToPropDefinition.values()) {
//            environment.addPropertyInstance(new PropertyInstanceImpl(property));
//        }
//
//        return environment;
//    }

    @Override
    public void addEnvironmentVariableDTO(PropertyDefinitionDTO environmentVariable) {
        if (environmentVariable == null) {
            throw new NullPointerException();
        }

        propNameToPropDefinition.put(environmentVariable.getName(), new PropertyDefinitionImpl(environmentVariable));
    }

    @Override
    public boolean isEmpty() {
        return propNameToPropDefinition.isEmpty();
    }
}
