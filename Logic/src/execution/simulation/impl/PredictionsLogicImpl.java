package execution.simulation.impl;

import action.impl.DecreaseAction;
import action.impl.IncreaseAction;
import action.impl.KillAction;
import action.impl.SetAction;
import api.DTO;
import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.PropertyDefinitionImpl;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.data.api.SimulationData;
import execution.simulation.manager.SimulationManager;
import definition.world.api.World;
import definition.world.impl.WorldImpl;
import execution.simulation.termination.impl.TerminationImpl;
import impl.PropertyDefinitionDTO;
import impl.WorldDTO;
import rule.api.Rule;
import rule.impl.ActivationImpl;
import rule.impl.RuleImpl;

import java.util.List;

public class PredictionsLogicImpl implements PredictionsLogic {
    private SimulationManager allSimulations;
    private World world;

    public PredictionsLogicImpl() {
        this.allSimulations = new SimulationManager();
    }

    // TODO: impl
    @Override
    public boolean loadXML(String path) {
        world = new WorldImpl();
        //TODO: read data from file and put it in world.
        return true;
    }

    //TODO: impl
    @Override
    public List<PropertyDefinitionDTO> getEnvironmentVariablesToSet() {
        return null;
    }

    @Override
    public WorldDTO getSimulationDetails() {
        return (WorldDTO) world.convertToDTO();
    }

    @Override
    public void runNewSimulation(List<PropertyDefinitionDTO> environmentVariables) {
        allSimulations.runNewSimulation(world, environmentVariables);
    }

    @Override
    public List<DTO> getAllPreviousSimulationData() {
        return null;
    }

    //TODO: impl
    private DTO convertSimulationDateToSimulationDataDTO(SimulationData originalData) {
        return null;
    }


    //TODO: DELETE! ONLY FOR DEBUGGING.
    @Override
    public void hardCodeWorldInit() {
        world = new WorldImpl();

        PropertyDefinition agePropertyDefinition = new PropertyDefinitionImpl("age", PropertyType.INT, true, new Range(15, 90));
        PropertyDefinition smokingInDayPropertyDefinition = new PropertyDefinitionImpl("smokingInDay", PropertyType.DOUBLE, false,7.5);
        PropertyDefinition cancerPrecentage = new PropertyDefinitionImpl("cancerPrecentage", PropertyType.DOUBLE, true, new Range(0, 100));
        PropertyDefinition cancerAdvanement = new PropertyDefinitionImpl("cancerAdvancement", PropertyType.DOUBLE, true, new Range(0, 150));
        PropertyDefinition isCancerPositive = new PropertyDefinitionImpl("cancerPositive", PropertyType.BOOLEAN,false,false);
        PropertyDefinition CancerSerialString = new PropertyDefinitionImpl("CancerSerialString", PropertyType.STRING,true);


        EntityDefinition smokerEntityDefinition = new EntityDefinitionImpl("smoker", 10);
        smokerEntityDefinition.addProperty(agePropertyDefinition);
        smokerEntityDefinition.addProperty(smokingInDayPropertyDefinition);
        smokerEntityDefinition.addProperty(cancerPrecentage);
        smokerEntityDefinition.addProperty(cancerAdvanement);
        smokerEntityDefinition.addProperty(isCancerPositive);
        smokerEntityDefinition.addProperty(CancerSerialString);

        world.addEntity(smokerEntityDefinition);

        Rule rule1 = new RuleImpl("First_User_Rule", new ActivationImpl(0.95), "smoker");
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "age", "random(10)"));
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "smokingInDay", "3.5"));
        rule1.addAction(new SetAction(smokerEntityDefinition, "cancerPositive", "true"));
        rule1.addAction(new DecreaseAction(smokerEntityDefinition, "cancerPrecentage", "random(15)"));
        rule1.addAction(new KillAction(smokerEntityDefinition));

        world.addRule(rule1);
        world.setTermination(new TerminationImpl(100, 15));
//        EnvironmentVariableManager envVariablesManager = new EnvironmentVariableManagerImpl();
//        PropertyDefinition taxAmountEnvironmentVariablePropertyDefinition = new PropertyDefinitionImpl("tax-amount", PropertyType.INT, true, new Range(10, 100));
//        envVariablesManager.addEnvironmentVariable(taxAmountEnvironmentVariablePropertyDefinition);
    }
}
