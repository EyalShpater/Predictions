package execution.simulation.api;

import api.DTOConvertible;
import definition.world.api.World;
import execution.simulation.data.api.SimulationData;
import execution.simulation.termination.api.TerminateCondition;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import impl.SimulationRunDetailsDTO;
import instance.enviornment.api.ActiveEnvironment;

public interface Simulation extends DTOConvertible<SimulationDTO> {
    int getSerialNumber();

    long getRunStartTime();

    void run();

    void pause();

    void stop();

    void resume();

    TerminateCondition getEndReason();

    SimulationDataDTO getResultAsDTO(String entityName, String propertyName);

    SimulationRunDetailsDTO createRunDetailDTO();

    World getWorld();
}
