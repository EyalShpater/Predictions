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

import impl.SimulationInitDataFromUserDTO;


public class WorldImpl implements World  , Serializable {
    private static final int MIN_ROWS_SIZE = 10;
    private static final int MIN_COLS_SIZE = 10;
    private static final int MAX_ROWS_SIZE = 100;
    private static final int MAX_COLS_SIZE = 100;

    private String name;
    private Map<String, EntityDefinition> entitiesDefinition;
    private List<Rule> rules;
    private EnvironmentVariableManager environmentVariables;
    private Termination terminate;
    private int gridRows;
    private int gridCols;
    private int threadPoolSize;
    private int sleepTime;

    public WorldImpl() {
        Random random = new Random();
        entitiesDefinition = new HashMap<>();
        rules = new ArrayList<>();
        environmentVariables = new EnvironmentVariableManagerImpl();
        this.sleepTime = 0;
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
    public List<EntityDefinition> getEntities() {
        return new ArrayList<>(entitiesDefinition.values());
    }

    @Override
    public TerminateCondition isActive(int currentTick, long secondsDuration, boolean userRequestedStop) {
        return terminate.isTerminate(currentTick, secondsDuration, userRequestedStop);
    }

    @Override
    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public void addRule(Rule newRule) {
        if (newRule != null && !isRuleNameExist(newRule.getName())) {
            rules.add(newRule);
        } else {
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty!");
        }

        this.name = name;
    }


    @Override
    public WorldDTO convertToDTO() {
        return new WorldDTO(
                name,
                entitiesDefinition.values()
                        .stream()
                        .map(DTOConvertible::convertToDTO)
                        .collect(Collectors.toList()),
                rules.stream()
                        .map(DTOConvertible::convertToDTO)
                        .collect(Collectors.toList()),
                gridRows,
                gridCols
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
    public TerminateCondition getTerminationCondition() {
        return terminate.isTerminateByTicks() ?
                TerminateCondition.BY_TICKS :
                terminate.isTerminateBySeconds() ?
                        TerminateCondition.BY_SECONDS :
                        TerminateCondition.BY_USER;
    }

    @Override
    public EntityDefinition getEntityByName(String name) {
        return entitiesDefinition.get(name);
    }

    @Override
    public Collection<PropertyDefinition> getEnvironmentVariables() {
        return environmentVariables.getEnvironmentVariables();
    }

    @Override
    public Termination getTermination() {
        return terminate;
    }

    @Override
    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    @Override
    public void setThreadPoolSize(int size) {
        threadPoolSize = size;
    }

    @Override
    public int getGridRows() {
        return gridRows;
    }

    @Override
    public int getGridCols() {
        return gridCols;
    }

    @Override
    public void setGridRows(int rows) {
        if (rows < MIN_ROWS_SIZE || rows > MAX_ROWS_SIZE) {
            throw new IllegalArgumentException("Rows size must be between " + MIN_ROWS_SIZE + " to " + MAX_ROWS_SIZE);
        }

        gridRows = rows;
    }

    @Override
    public void setGridCols(int cols) {
        if (cols < MIN_COLS_SIZE || cols > MAX_COLS_SIZE) {
            throw new IllegalArgumentException("Columns size must be between " + MIN_COLS_SIZE + " to " + MAX_COLS_SIZE);
        }

        gridCols = cols;
    }
}
