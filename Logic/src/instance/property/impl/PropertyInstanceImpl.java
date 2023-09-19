package instance.property.impl;

import action.context.api.Context;
import definition.property.api.PropertyDefinition;
import impl.PropertyDefinitionDTO;
import instance.property.api.PropertyInstance;
import instance.property.value.generator.RandomValueGenerator;

import java.io.Serializable;

public class PropertyInstanceImpl implements PropertyInstance, Serializable {
    private PropertyDefinition propertyDefinition;
    private Object value;

    private int updateTick;

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition) {
        if (propertyDefinition == null) {
            throw new NullPointerException("Property can not be null!");
        }

        this.propertyDefinition = propertyDefinition;
        this.value = propertyDefinition.isValueInitializeRandomly()
                ? new RandomValueGenerator(propertyDefinition).generateValue()
                : propertyDefinition.getDefaultValue();
        this.updateTick = 0;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getName() {
        return propertyDefinition.getName();
    }

    public PropertyDefinition getPropertyDefinition() {
        return propertyDefinition;
    }

    @Override
    public void setValue(Object val, Context context) {
        this.value = val;
        this.updateTick = context.getTickNumber();
    }

    @Override
    public int getLastUpdateTick() {
        return this.updateTick;
    }

    @Override
    public PropertyDefinitionDTO convertToDTO() {
        return new PropertyDefinitionDTO(
                propertyDefinition.getName(),
                propertyDefinition.getType().name(),
                propertyDefinition.getRange() != null ?
                        propertyDefinition.getRange().getMin() :
                        null,
                propertyDefinition.getRange() != null ?
                        propertyDefinition.getRange().getMax() :
                        null,
                false,
                value);
    }
}
