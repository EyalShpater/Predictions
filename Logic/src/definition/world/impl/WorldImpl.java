package definition.world.impl;

import api.DTO;
import definition.entity.api.EntityDefinition;
import definition.environment.api.EnvironmentVariableManager;
import definition.environment.impl.EnvironmentVariableManagerImpl;
import impl.PropertyDefinitionDTO;
import execution.simulation.termination.api.Termination;
import definition.world.api.World;
import instance.enviornment.api.ActiveEnvironment;
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
        EnvironmentVariableManager variableDefinitions = new EnvironmentVariableManagerImpl();

        values.forEach(variableDefinitions::mapEnvironmentVariableDTOtoEnvironmentVariableAndCreateIt);
        this.environmentVariables = variableDefinitions;
    }

    @Override
    public List<DTO> getEnvironmentVariablesDTO() {
        return environmentVariables.getEnvironmentVariables()
                .stream()
                .map(propertyDefinition -> new PropertyDefinitionDTO(
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

    @Override
    public ActiveEnvironment createActiveEnvironment() {
        return environmentVariables.createActiveEnvironment();
    }

    @Override
    public List<EntityDefinition> getEntities() {
        return entitiesDefinition;
    }

    @Override
    public boolean isActive(int currentTick, long startTime) {
        return !terminate.isTerminate(currentTick, startTime);
    }

    @Override
    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public void addRule(Rule newRule) {
        if (newRule != null && !isRuleNameExist(newRule.getName())) {
            rules.add(newRule);
        }
        else {
            throw new NullPointerException("Rule can not be null!");
        }
    }

    @Override
    public DTO convertToDTO() {
        return null;
    }

    private boolean isRuleNameExist(String name) {
        return rules.contains(name);
    }
}
