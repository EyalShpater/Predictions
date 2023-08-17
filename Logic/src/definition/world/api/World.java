package definition.world.api;


import api.DTO;
import api.DTOConvertible;
import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import execution.simulation.termination.api.Termination;
import impl.PropertyDefinitionDTO;
import impl.WorldDTO;
import instance.enviornment.api.ActiveEnvironment;
import rule.api.Rule;

import java.util.List;

public interface World extends DTOConvertible<World, WorldDTO> {
    void setEnvironmentVariablesValues(List<PropertyDefinitionDTO> values);
    List<PropertyDefinitionDTO> getEnvironmentVariablesDTO();
    ActiveEnvironment createActiveEnvironment();
    List<EntityDefinition> getEntities();
    boolean isActive(int currentTick, long startTime);
    List<Rule> getRules();
    void addRule(Rule newRule);
    void addEntity(EntityDefinition newEntity);
    void setTermination(Termination terminate);
    void addEnvironmentVariable(PropertyDefinition newVariable);
    EntityDefinition getEntityByName(String name);
}
