package execution.simulation.api;

import api.DTOConvertible;
import execution.simulation.data.api.SimulationData;
import execution.simulation.termination.api.TerminateCondition;
import impl.SimulationDTO;
import impl.SimulationDataDTO;

public interface Simulation extends DTOConvertible<SimulationDTO> {
    int getSerialNumber();
    long getRunStartTime();
    TerminateCondition run();
    SimulationDataDTO getResultAsDTO(String entityName, String propertyName);
}
