package execution.simulation.manager;

import definition.world.api.World;

import java.util.HashMap;
import java.util.Map;

public class WorldManager {
    private Map<String, World> worlds = new HashMap<>();

    public boolean addWorld(World newWorld) {
        return worlds.putIfAbsent(newWorld.getName(), newWorld) != null;
    }
}
