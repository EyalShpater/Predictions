package instance.property.api;

import action.context.api.Context;
import api.DTOConvertible;
import definition.property.api.PropertyDefinition;
import impl.PropertyDefinitionDTO;

public interface PropertyInstance extends DTOConvertible<PropertyDefinitionDTO> {
    Object getValue();

    String getName();

    PropertyDefinition getPropertyDefinition();

    void setValue(Object val, Context context);

    int getLastUpdateTick();

    double getAverageConsistency(int finalTick, int deathTick);
}
