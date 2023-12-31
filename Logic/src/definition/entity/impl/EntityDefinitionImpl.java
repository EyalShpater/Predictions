package definition.entity.impl;

import api.DTO;
import api.DTOConvertible;
import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import definition.property.impl.PropertyDefinitionImpl;
import impl.EntityDefinitionDTO;
import impl.PropertyDefinitionDTO;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class EntityDefinitionImpl implements EntityDefinition , Serializable {
    private String name;
    private List<PropertyDefinition> properties = new ArrayList<>();

    public EntityDefinitionImpl(String name) {
        setName(name);
    }

    public EntityDefinitionImpl(EntityDefinitionDTO dto) {
        this(dto.getName());
        dto.getProperties().forEach(property -> addProperty(new PropertyDefinitionImpl(property)));
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new NullPointerException("Name can not be empty");
        }

        this.name = name;
    }

    public void addProperty(PropertyDefinition newProperty) {
        if (newProperty == null) {
            throw new NullPointerException("Can not add empty property!");
        }

        properties.add(newProperty);
    }

    @Override
    public PropertyDefinition propertyAt(int index) {
        return properties.get(index);
    }

    @Override
    public PropertyDefinition getPropertyByName(String name) {
        Optional<PropertyDefinition> theProperty =
                properties.stream()
                .filter(property -> property.getName().equals(name))
                .findFirst();

        return theProperty.orElse(null);
    }

    @Override
    public int getNumOfProperties() {
        return properties.size();
    }

    @Override
    public EntityDefinitionDTO convertToDTO() {
        return new EntityDefinitionDTO(
                name,
                properties.stream()
                        .map(DTOConvertible::convertToDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Name: ").append(name).append(System.lineSeparator());
        result.append("Properties:").append(System.lineSeparator());
        for (PropertyDefinition prop : properties) {
            result.append(prop).append(System.lineSeparator());
        }

        return result.toString();
    }
}
