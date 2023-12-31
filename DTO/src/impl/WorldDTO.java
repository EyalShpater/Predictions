package impl;

import api.DTO;

import java.util.List;

public class WorldDTO implements DTO {
    private final List<EntityDefinitionDTO> entities;
    private final List<RuleDTO> rules;
    private final int gridNumOfRows;
    private final int gridNumOfCols;
    private final String name;


    public WorldDTO(String name, List<EntityDefinitionDTO> entities, List<RuleDTO> rules, int gridNumOfRows, int gridNumOfCols) {
        this.name = name;
        this.entities = entities;
        this.rules = rules;
        this.gridNumOfRows = gridNumOfRows;
        this.gridNumOfCols = gridNumOfCols;
    }

    public List<EntityDefinitionDTO> getEntities() {
        return entities;
    }

    public List<RuleDTO> getRules() {
        return rules;
    }

    public int getGridNumOfRows() {
        return gridNumOfRows;
    }

    public int getGridNumOfCols() {
        return gridNumOfCols;
    }

    public String getName() {
        return name;
    }
}
