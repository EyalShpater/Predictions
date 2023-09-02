package impl;

import api.DTO;

import java.util.Map;

public class ActionDTO implements DTO {
    private final String type;
    private final EntityDefinitionDTO mainEntity;
    private final EntityDefinitionDTO secondaryEntity;
    private final Map<String, String> arguments;

    public ActionDTO(String type, EntityDefinitionDTO mainEntity, EntityDefinitionDTO secondaryEntity, Map<String, String> arguments) {
        this.type = type;
        this.mainEntity = mainEntity;
        this.secondaryEntity = secondaryEntity;
        this.arguments = arguments;
    }

    public String getType() {
        return type;
    }

    public EntityDefinitionDTO getMainEntity() {
        return mainEntity;
    }

    public EntityDefinitionDTO getSecondaryEntity() {
        return secondaryEntity;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }
}
