package instance.enviornment.impl;

import instance.enviornment.api.ActiveEnvironment;
import instance.property.api.PropertyInstance;

import java.util.HashMap;
import java.util.Map;

public class ActiveEnvironmentImpl implements ActiveEnvironment {
    Map<String, PropertyInstance> propNameToPropInstance = new HashMap();

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
}

