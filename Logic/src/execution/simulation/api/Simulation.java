package execution.simulation.api;

import api.DTOConvertible;
import execution.simulation.data.api.SimulationData;
import impl.SimulationDTO;

public interface Simulation extends DTOConvertible<Simulation, SimulationDTO> {
    int getSerialNumber();
    void run();
}
