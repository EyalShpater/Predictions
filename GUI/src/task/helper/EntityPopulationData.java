package task.helper;

public class EntityPopulationData {
    private final String entityName;
    private final long population;

    public EntityPopulationData(String entityName, long population) {
        this.entityName = entityName;
        this.population = population;
    }

    public String getEntityName() {
        return entityName;
    }

    public long getPopulation() {
        return population;
    }
}