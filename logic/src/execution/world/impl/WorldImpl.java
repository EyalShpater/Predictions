package execution.world.impl;

import definition.entity.api.EntityInstance;
import execution.enviornment.api.EnvironmentVariable;
import execution.simulation.api.Termination;
import execution.world.api.World;
import rule.api.Rule;

import java.util.List;

public class WorldImpl implements World {
    private List<EntityInstance> entitiesDefinition;
    private List<Rule> rules;
    private List<EnvironmentVariable> environmentVariables;
    private Termination terminate;

}
