package execution.world.impl;

import definition.entity.api.EntityInstance;
import instance.enviornment.api.ActiveEnvironment;
import execution.simulation.api.Termination;
import execution.world.api.World;
import rule.api.Rule;

import java.util.List;

public class WorldImpl implements World {
    private List<EntityInstance> entitiesDefinition;
    private List<Rule> rules;
    private List<ActiveEnvironment> activeEnvironments;
    private Termination terminate;

}
