package definition.world.impl;

import api.DTO;
import api.DTOConvertible;
import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import definition.environment.api.EnvironmentVariableManager;
import definition.environment.impl.EnvironmentVariableManagerImpl;
import definition.property.api.PropertyDefinition;
import execution.simulation.termination.api.TerminateCondition;
import execution.simulation.termination.api.Termination;
import definition.world.api.World;
import execution.simulation.termination.impl.TerminationImpl;
import grid.SphereSpaceImpl;
import grid.api.SphereSpace;
import impl.PropertyDefinitionDTO;
import impl.WorldDTO;
import instance.enviornment.api.ActiveEnvironment;
import rule.api.Rule;
import rule.impl.RuleImpl;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class WorldImpl implements World  , Serializable {
    private Map<String, EntityDefinition> entitiesDefinition;
    private List<Rule> rules;
    private EnvironmentVariableManager environmentVariables;
    private Termination terminate;
    private SphereSpace space;
    //todo: add private int threadPoolSize;

    public WorldImpl() {
        entitiesDefinition = new HashMap<>();
        rules = new ArrayList<>();
        environmentVariables = new EnvironmentVariableManagerImpl();

        //todo: only until ex2 xml importer will be implement.
        space = new SphereSpaceImpl(100, 100);
    }

    @Override
    public void setEnvironmentVariablesValues(List<PropertyDefinitionDTO> values) {
        EnvironmentVariableManager variableDefinitions = new EnvironmentVariableManagerImpl();

        if (values != null) {
            values.forEach(variableDefinitions::addEnvironmentVariableDTO);
            this.environmentVariables = variableDefinitions;
        }
    }

    @Override
    public List<PropertyDefinitionDTO> getEnvironmentVariablesDTO() {
        return !environmentVariables.isEmpty() ?
                environmentVariables.getEnvironmentVariables()
                .stream()
                .map(DTOConvertible::convertToDTO)
                .collect(Collectors.toList()) :
                null;
    }

    @Override
    public ActiveEnvironment createActiveEnvironment() {
        if (environmentVariables != null) {
            return environmentVariables.createActiveEnvironment();
        }

        return null;
    }

    @Override
    public List<EntityDefinition> getEntities() {
        return new ArrayList<>(entitiesDefinition.values());
    }

    @Override
    public TerminateCondition isActive(int currentTick, long startTime) {
        return terminate.isTerminate(currentTick, startTime);
    }

    @Override
    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public SphereSpace getSphereSpace() {
        return space;
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
    public void setTermination(Termination terminate) {
        if (terminate == null) {
            throw new NullPointerException("Termination can not be null");
        }

        this.terminate = terminate;
    }

    @Override
    public WorldDTO convertToDTO() {
        return new WorldDTO(
                entitiesDefinition.values()
                        .stream()
                        .map(DTOConvertible::convertToDTO)
                        .collect(Collectors.toList()),
                rules.stream()
                        .map(DTOConvertible::convertToDTO)
                        .collect(Collectors.toList()),
                terminate.convertToDTO()
        );
    }

    private boolean isRuleNameExist(String name) {
        return name != null && rules.contains(name);
    }

    @Override
    public void addEntity(EntityDefinition newEntity) {
        if (newEntity == null) {
            throw new NullPointerException("Can not add null entity!");
        }

        entitiesDefinition.put(newEntity.getName(), newEntity);
    }

    @Override
    public void addEnvironmentVariable(PropertyDefinition newVariable) {
        if (newVariable == null) {
            throw new NullPointerException();
        }

        environmentVariables.addEnvironmentVariable(newVariable);
    }

    @Override
    public void setSphereSpaceSize(int rows, int cols) {
        space = new SphereSpaceImpl(rows, cols);
    }

    @Override
    public EntityDefinition getEntityByName(String name) {
        return entitiesDefinition.get(name);
    }

    @Override
    public Collection<PropertyDefinition> getEnvironmentVariables() {
        return environmentVariables.getEnvironmentVariables();
    }
}
