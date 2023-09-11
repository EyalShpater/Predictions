package impl;

import api.DTO;

import java.util.List;

public class WorldDTO implements DTO {
    private final List<EntityDefinitionDTO> entities;
    private final List<RuleDTO> rules;
    private final TerminationDTO termination;
    private final int gridNumOfRows;
    private final int gridNumOfCols;


    public WorldDTO(List<EntityDefinitionDTO> entities, List<RuleDTO> rules, TerminationDTO termination, int gridNumOfRows, int gridNumOfCols) {
        this.entities = entities;
        this.rules = rules;
        this.termination = termination;
        this.gridNumOfRows = gridNumOfRows;
        this.gridNumOfCols = gridNumOfCols;
    }

    public List<EntityDefinitionDTO> getEntities() {
        return entities;
    }

    public List<RuleDTO> getRules() {
        return rules;
    }

    public TerminationDTO getTermination() {
        return termination;
    }

    public int getGridNumOfRows() {
        return gridNumOfRows;
    }

    public int getGridNumOfCols() {
        return gridNumOfCols;
    }
}
