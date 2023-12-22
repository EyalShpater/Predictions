package impl;

import api.DTO;

import java.util.Map;

public class EntitiesAmountDTO implements DTO {
    private final Map<String, Long> entityNameToAmount;

    public EntitiesAmountDTO(Map<String, Long> entityNameToAmount) {
        this.entityNameToAmount = entityNameToAmount;
    }

    public Map<String, Long> getEntityToPopulationMap() {
        return entityNameToAmount;
    }
}
