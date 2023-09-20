package instance.entity.manager.api;

import instance.entity.api.EntityInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PopulationCounter {
    private Map<Integer, Map<String, Long>> populationCounter;

    public PopulationCounter(EntityInstanceManager entities) {
        populationCounter = new HashMap<>();
    }

    public void update(int tick, Map<String, Long> entitiesToPopulation) {
        populationCounter.put(tick, entitiesToPopulation);
    }

    public Map<String, Long> getPopulationCountByTick(int tick) {
        return populationCounter.getOrDefault(tick, populationCounter.get(populationCounter.size()));
    }

    public Map<Integer, Map<String, Long>> getPopulationCounter() {
        return new HashMap<>(populationCounter);
    }

}
