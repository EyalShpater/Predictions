package task;

import execution.simulation.api.PredictionsLogic;
import impl.PropertyDefinitionDTO;
import impl.SimulationRunDetailsDTO;
import javafx.concurrent.Task;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RunSimulationTask extends Task<Boolean> {
    PredictionsLogic engine;
    List<PropertyDefinitionDTO> environmentVariables;

    public RunSimulationTask(PredictionsLogic engine, List<PropertyDefinitionDTO> environmentVariables) {
        this.engine = engine;
        this.environmentVariables = environmentVariables;
    }

    @Override
    protected Boolean call() throws Exception {
        SimulationRunDetailsDTO details;

        updateProgress(0, 1);

        /*details =*/
        engine.runNewSimulation(environmentVariables);

        updateProgress(1, 1);
        //updateMessage("Simulation ended during " + (details.isTerminateBySeconds() ? "seconds" : "ticks"));

        return true;
    }
}
