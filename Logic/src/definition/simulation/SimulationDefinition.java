package definition.simulation;

import execution.simulation.termination.api.Termination;

public class SimulationDefinition {
    private String worldName;
    private Termination termination;

    public SimulationDefinition(String worldName, Termination termination) {
        this.worldName = worldName;
        this.termination = termination;
    }

    public String getWorldName() {
        return worldName;
    }

    public Termination getTermination() {
        return termination;
    }
}
