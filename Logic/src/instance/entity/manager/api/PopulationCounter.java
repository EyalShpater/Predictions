package instance.entity.manager.api;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class PopulationCounter {
    private Map<Integer, Map<String, Long>> populationCounterByTick;
    private Map<String, Map<Integer, Long>> populationCounterByName;

    public PopulationCounter(EntityInstanceManager entities) {
        populationCounterByTick = new HashMap<>();
        populationCounterByName = new HashMap<>();
    }

    public void update(int tick, Map<String, Long> entitiesToPopulation) {
        populationCounterByTick.put(tick, entitiesToPopulation);

        entitiesToPopulation.forEach((name, population) -> {
            populationCounterByName
                    .computeIfAbsent(name, k -> new HashMap<>())
                    .put(tick, population);
        });

    }

    public Map<String, Long> getPopulationCounterByTick(int tick) {
        return populationCounterByTick.getOrDefault(tick, populationCounterByTick.get(populationCounterByTick.size()));
    }

    public Map<Integer, Map<String, Long>> getPopulationCounterByTick() {
        return new HashMap<>(populationCounterByTick);
    }

    public Map<String, Map<Integer, Long>> getPopulationCounterByName() {
        return new HashMap<>(populationCounterByName);
    }

}
