package definition.world.api;


import api.DTO;
import api.DTOConvertible;
import definition.entity.api.EntityDefinition;
import execution.simulation.termination.api.Termination;
import instance.enviornment.api.ActiveEnvironment;
import rule.api.Rule;

import java.util.List;

public interface World extends DTOConvertible {
    void setEnvironmentVariablesValues(List<DTO> values);
    List<DTO> getEnvironmentVariablesDTO();
    ActiveEnvironment createActiveEnvironment();
    List<EntityDefinition> getEntities();
    boolean isActive(int currentTick, long startTime);
    List<Rule> getRules();
    void addRule(Rule newRule);
    void addEntity(EntityDefinition newEntity);
    void setTermination(Termination terminate);
}
