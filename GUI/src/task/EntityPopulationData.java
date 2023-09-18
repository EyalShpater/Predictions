package task;

public class EntityPopulationData {
    private final String entityName;
    private final int population;

    public EntityPopulationData(String entityName, int population) {
        this.entityName = entityName;
        this.population = population;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getPopulation() {
        return population;
    }
}