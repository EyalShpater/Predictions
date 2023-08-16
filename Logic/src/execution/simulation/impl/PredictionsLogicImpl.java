package execution.simulation.impl;

import api.DTO;
import api.DTO.*;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.data.api.SimulationData;
import execution.simulation.manager.SimulationManager;
import definition.world.api.World;
import definition.world.impl.WorldImpl;
import impl.WorldDTO;

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

    @Override
    public List<DTO> getEnvironmentVariablesToSet() {
        return null;
    }

    @Override
    public WorldDTO getSimulationDetails() {
        return (WorldDTO) world.convertToDTO();
    }

    @Override
    public void runNewSimulation(List<DTO> environmentVariables) {
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
}
