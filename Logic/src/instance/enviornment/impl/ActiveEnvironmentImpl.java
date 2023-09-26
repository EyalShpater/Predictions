package instance.enviornment.impl;

import api.DTOConvertible;
import definition.environment.api.EnvironmentVariableManager;
import definition.environment.impl.EnvironmentVariableManagerImpl;
import definition.property.api.PropertyDefinition;
import impl.PropertyDefinitionDTO;
import instance.enviornment.api.ActiveEnvironment;
import instance.property.api.PropertyInstance;
import instance.property.impl.PropertyInstanceImpl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActiveEnvironmentImpl implements ActiveEnvironment , Serializable {
    Map<String, PropertyInstance> propNameToPropInstance;

    public ActiveEnvironmentImpl(List<PropertyDefinitionDTO> environmentVariables) {
        this.propNameToPropInstance = new HashMap<>();
        EnvironmentVariableManager manager = setEnvironmentVariablesValues(environmentVariables);

        for (PropertyDefinition property : manager.getEnvironmentVariables()) {
            addPropertyInstance(new PropertyInstanceImpl(property));
        }
    }

    @Override
    public void addPropertyInstance(PropertyInstance property) {
        if (property == null) {
            throw new NullPointerException("Property can not be null!");
        }

        propNameToPropInstance.put(property.getName(), property);
    }

    @Override
    public PropertyInstance getPropertyByName(String PropertyName) {
        return propNameToPropInstance.get(PropertyName);
    }

    @Override
    public List<PropertyDefinitionDTO> convertToDTO() {
        return propNameToPropInstance.values()
                .stream()
                .map(DTOConvertible::convertToDTO)
                .collect(Collectors.toList());
    }

    private EnvironmentVariableManager setEnvironmentVariablesValues(List<PropertyDefinitionDTO> values) {
        EnvironmentVariableManager variableDefinitions = new EnvironmentVariableManagerImpl();

        values.forEach(variableDefinitions::addEnvironmentVariableDTO);

        return variableDefinitions;
    }
}

