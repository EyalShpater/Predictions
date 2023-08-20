package instance.enviornment.api;


import api.DTOConvertible;
import impl.PropertyDefinitionDTO;
import instance.property.api.PropertyInstance;

import java.util.List;

public interface ActiveEnvironment extends DTOConvertible<List<PropertyDefinitionDTO>> {
    void addPropertyInstance(PropertyInstance property);
    PropertyInstance getPropertyByName(String PropertyName);
}
