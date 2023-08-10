package execution.simulation.impl;

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
    public void run() {
        //create entity instances
        //create environment variables instances
        //run simulation with ticks and collect the data to Data (member)...
    }
}


/*

Smoker.population = 100
Cigarette. population = 3000

[{ smoker1, smoker2, ..., smoker100 }, { cigarette1, ...., cigarette3000 }]

 */
