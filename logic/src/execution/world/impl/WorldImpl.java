package execution.world.impl;

import api.DTO;
import definition.entity.api.EntityDefinition;
import definition.environment.api.EnvironmentVariableManager;
import environment.variable.EnvironmentVariableDTO;
import execution.simulation.api.Termination;
import execution.world.api.World;
import rule.api.Rule;
import java.util.List;
import java.util.stream.Collectors;

public class WorldImpl implements World {
    private List<EntityDefinition> entitiesDefinition;
    private List<Rule> rules;
    private EnvironmentVariableManager environmentVariables;
    private Termination terminate;

    @Override
    public void setEnvironmentVariablesValues(List<DTO> values) {

    }

    @Override
    public List<DTO> getEnvironmentVariables() {
        return environmentVariables.getEnvironmentVariables()
                .stream()
                .map(propertyDefinition -> new EnvironmentVariableDTO(
                        propertyDefinition.getName(),
                        propertyDefinition.getType().toString(),
                        propertyDefinition.getRange() != null
                                ? propertyDefinition.getRange().getMin()
                                : null,
                        propertyDefinition.getRange() != null
                                ? propertyDefinition.getRange().getMax()
                                : null,
                        propertyDefinition.isValueInitializeRandomly(),
                        propertyDefinition.getDefaultValue()
                ))
                .collect(Collectors.toList());
    }
}
