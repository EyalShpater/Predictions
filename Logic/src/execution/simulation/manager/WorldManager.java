package execution.simulation.manager;

import api.DTOConvertible;
import definition.world.api.World;
import impl.WorldDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorldManager {
    private Map<String, World> worlds = new HashMap<>();

    public boolean addWorld(World newWorld) {
        return worlds.putIfAbsent(newWorld.getName(), newWorld) != null;
    }

    public World getWorld(String name) {
        return worlds.get(name);
    }

    public List<String> getAllWorldsNames() {
        return new ArrayList<>(worlds.keySet());
    }

    public List<WorldDTO> getAllWorldsAsDTO() {
        return worlds
                .values()
                .stream()
                .map(DTOConvertible::convertToDTO)
                .collect(Collectors.toList());
    }
}
