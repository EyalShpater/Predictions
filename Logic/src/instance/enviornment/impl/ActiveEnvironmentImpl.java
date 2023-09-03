package instance.enviornment.impl;

import api.DTOConvertible;
import impl.PropertyDefinitionDTO;
import instance.enviornment.api.ActiveEnvironment;
import instance.property.api.PropertyInstance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActiveEnvironmentImpl implements ActiveEnvironment , Serializable {
    Map<String, PropertyInstance> propNameToPropInstance = new HashMap<>();

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
}

