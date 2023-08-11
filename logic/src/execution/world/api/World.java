package execution.world.api;

import api.DTO;
import temporary.SomeObject;

import java.util.List;

public interface World {
    void setEnvironmentVariablesValues(List<DTO> values);

    List<DTO> getEnvironmentVariables();
}
