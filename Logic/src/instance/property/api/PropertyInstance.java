package instance.property.api;

import definition.property.api.PropertyDefinition;

public interface PropertyInstance {
    Object getValue();

    String getName();

    PropertyDefinition getPropertyDefinition();

    void setValue(Object val);
}
