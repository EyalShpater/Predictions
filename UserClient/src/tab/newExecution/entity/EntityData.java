package tab.newExecution.entity;

public interface EntityData {
    String getEntity();

    void setEntity(String word);

    String getPopulation();

    void setPopulation(String count);

    void clear();

    void restoreTileFromPrevData(String entityName, int population);
}
