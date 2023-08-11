package execution.world.api;

import api.DTO;
import java.util.List;

public interface World {
    void setEnvironmentVariablesValues(List<DTO> values);

    List<DTO> getEnvironmentVariables();
}
