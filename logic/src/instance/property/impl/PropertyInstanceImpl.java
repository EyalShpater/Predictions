package instance.property.impl;

import definition.property.api.PropertyDefinition;
import definition.property.impl.PropertyDefinitionImpl;
import instance.property.api.PropertyInstance;
import definition.property.api.Range;
import instance.property.value.generator.RandomValueGenerator;

import java.util.Random;

public class PropertyInstanceImpl implements PropertyInstance {
    private PropertyDefinition propertyDefinition;
    private Object value;

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition) {
        if (propertyDefinition == null) {
            throw new NullPointerException("Property can not be null!");
        }

        this.propertyDefinition = propertyDefinition;
        this.value = propertyDefinition.isValueInitializeRandomly()
                ? new RandomValueGenerator(propertyDefinition).generateValue()
                : propertyDefinition.getDefaultValue();
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
}
