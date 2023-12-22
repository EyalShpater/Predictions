package impl;

import api.DTO;

public class RunRequestDTO implements DTO {
    private final String userName;
    private final String worldName;
    private final int numOfRequestedRuns;
    private final TerminationDTO termination;

    public RunRequestDTO(String userName, String worldName, int numOfRequestedRuns, TerminationDTO termination) {
        this.userName = userName;
        this.worldName = worldName;
        this.numOfRequestedRuns = numOfRequestedRuns;
        this.termination = termination;
    }

    public String getUserName() {
        return userName;
    }

    public String getWorldName() {
        return worldName;
    }

    public int getNumOfRequestedRuns() {
        return numOfRequestedRuns;
    }

    public TerminationDTO getTermination() {
        return termination;
    }
}
