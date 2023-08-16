package definition.world.api;


import api.DTO;
import definition.entity.api.EntityDefinition;
import instance.enviornment.api.ActiveEnvironment;
import rule.api.Rule;

import java.util.List;

public interface World {
    void setEnvironmentVariablesValues(List<DTO> values);
    List<DTO> getEnvironmentVariablesDTO();
    ActiveEnvironment createActiveEnvironment();
    List<EntityDefinition> getEntities();
    boolean isActive(int currentTick, long startTime);
    List<Rule> getRules();
    void addRule(Rule newRule);
    DTO convertToDTO();
}
