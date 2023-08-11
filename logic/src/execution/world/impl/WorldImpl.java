package execution.world.impl;

import definition.entity.api.EntityDefinition;
import definition.environment.api.EnvironmentVariableManager;
import instance.enviornment.api.ActiveEnvironment;
import execution.simulation.api.Termination;
import execution.world.api.World;
import rule.api.Rule;
import temporary.SomeObject;

import java.util.List;

public class WorldImpl implements World {
    private List<EntityDefinition> entitiesDefinition;
    private List<Rule> rules;
    private EnvironmentVariableManager environmentVariables;
    private Termination terminate;

    @Override
    public void setEnvironmentVariablesValues(SomeObject values) {

    }
}
