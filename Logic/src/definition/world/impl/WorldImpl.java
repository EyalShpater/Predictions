package definition.world.impl;

import api.DTO;
import api.DTOConvertible;
import definition.entity.api.EntityDefinition;
import definition.environment.api.EnvironmentVariableManager;
import definition.environment.impl.EnvironmentVariableManagerImpl;
import execution.simulation.termination.api.Termination;
import definition.world.api.World;
import impl.EntityDefinitionDTO;
import impl.RuleDTO;
import impl.TerminationDTO;
import impl.WorldDTO;
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
                .map(DTOConvertible::convertToDTO)
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
        return new WorldDTO(
                entitiesDefinition.stream()
                        .map(entityDefinition -> (EntityDefinitionDTO) entityDefinition.convertToDTO())
                        .collect(Collectors.toList()),
                rules.stream()
                        .map(rule -> (RuleDTO) rule.convertToDTO())
                        .collect(Collectors.toList()),
                (TerminationDTO) terminate.convertToDTO()
        );
    }

    private boolean isRuleNameExist(String name) {
        return rules.contains(name);
    }
}
