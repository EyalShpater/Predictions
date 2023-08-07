package execution.simulation.impl;

import execution.simulation.api.Data;
import execution.simulation.api.Simulation;
import execution.world.api.World;

public class SimulationImpl implements Simulation {
    private static int serialNumberGenerator = 1;
    private int serialNumber;
    World world;
    Data data;

    public SimulationImpl(World world) {
        this.world = world;
        serialNumber = serialNumberGenerator++;
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
