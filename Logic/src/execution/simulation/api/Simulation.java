package execution.simulation.api;

import api.DTOConvertible;
import execution.simulation.data.api.SimulationData;
import impl.SimulationDTO;
import impl.SimulationDataDTO;

public interface Simulation extends DTOConvertible<Simulation, SimulationDTO> {
    int getSerialNumber();
    long getRunStartTime();
    void run();
    SimulationDataDTO getResultAsDTO(String entityName, String propertyName);
}
