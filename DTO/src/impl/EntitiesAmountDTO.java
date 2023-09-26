package impl;

import java.util.Map;

public class EntitiesAmountDTO {
    private final Map<String, Long> entityNameToAmount;

    public EntitiesAmountDTO(Map<String, Long> entityNameToAmount) {
        this.entityNameToAmount = entityNameToAmount;
    }

    public Map<String, Long> getEntityToPopulationMap() {
        return entityNameToAmount;
    }
}
