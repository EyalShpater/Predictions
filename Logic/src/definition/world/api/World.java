package definition.world.api;

import api.DTO;
import definition.entity.api.EntityDefinition;

import java.util.List;

public interface World {
    void setEnvironmentVariablesValues(List<DTO> values);
    List<DTO> getEnvironmentVariables();

    List<EntityDefinition> getEntities();
}
