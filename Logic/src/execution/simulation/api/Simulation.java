package execution.simulation.api;

import api.DTOConvertible;
import definition.world.api.World;
import execution.simulation.data.api.SimulationData;
import execution.simulation.termination.api.TerminateCondition;
import impl.*;
import instance.enviornment.api.ActiveEnvironment;

public interface Simulation extends DTOConvertible<SimulationDTO> {
    int getSerialNumber();

    long getRunStartTime();

    void run();

    void pause();

    void stop();

    void resume();

    boolean isPaused();

    boolean isStop();

    boolean isEnded();

    TerminateCondition getEndReason();

    SimulationDataDTO getResultAsDTO(String entityName, String propertyName);

    SimulationRunDetailsDTO createRunDetailDTO();

    SimulationInitDataFromUserDTO getUserInputDTO();

    World getWorld();

    double getProgress();

    EntitiesAmountDTO createEntitiesAmountDTO();

    boolean isStarted();
}
