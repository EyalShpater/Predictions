package impl;

import api.DTO;

import java.util.List;

public class WorldDTO implements DTO {
    private List<EntityDefinitionDTO> entities;
    private List<RuleDTO> rules;
    private TerminationDTO termination;


    public WorldDTO(List<EntityDefinitionDTO> entities, List<RuleDTO> rules, TerminationDTO termination) {
        this.entities = entities;
        this.rules = rules;
        this.termination = termination;
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
}
