package impl;

import java.util.Map;

public class EntitiesAmountDTO {
    private final Map<String, Integer> entityNameToAmount;

    public EntitiesAmountDTO(Map<String, Integer> entityNameToAmount) {
        this.entityNameToAmount = entityNameToAmount;
    }

    public Map<String, Integer> getEntityToPopulationMap() {
        return entityNameToAmount;
    }
}
