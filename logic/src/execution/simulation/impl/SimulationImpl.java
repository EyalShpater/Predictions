package execution.simulation.impl;

import api.DTO;
import execution.simulation.api.Data;
import execution.simulation.api.Simulation;
import execution.world.api.World;

public class SimulationImpl implements Simulation {
    private final int serialNumber;
    private World world;
    private Data data;

    public SimulationImpl(World world, int serialNumber) {
        this.world = world;
        this.serialNumber = serialNumber;
        data = null;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public Data getData() {
        return data;
    }

    @Override
    public DTO run() {
        //create entity instances
        //create environment variables instances
        //run simulation with ticks and collect the data to Data (member)...
        return null;
    }
}