package execution.simulation.api;

import api.DTOConvertible;
import definition.world.api.World;
import execution.simulation.termination.api.TerminateCondition;
import impl.*;
import javafx.util.Pair;

import java.util.Map;

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

    Map<String, Double> getConsistencyByEntityName(String entityName);

    SimulationInitDataFromUserDTO getUserInputDTO();

    World getWorld();

    double getProgress();

    EntitiesAmountDTO createEntitiesAmountDTO();

    boolean isStarted();

    Map<Integer, Map<String, Long>> getPopulationPerTickData();

    Map<String, Map<Integer, Long>> getPopulationCountSortedByName();
}
