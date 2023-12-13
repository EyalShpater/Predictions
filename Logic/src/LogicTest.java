import definition.entity.impl.EntityDefinitionImpl;
import definition.property.api.PropertyType;
import definition.property.impl.PropertyDefinitionImpl;
import definition.world.impl.WorldImpl;
import execution.simulation.api.Simulation;
import execution.simulation.impl.SimulationImpl;
import execution.simulation.manager.SimulationManager;
import impl.PropertyDefinitionDTO;
import impl.SimulationInitDataFromUserDTO;
import impl.TerminationDTO;
import rule.api.Rule;
import rule.impl.ActivationImpl;
import rule.impl.RuleImpl;

import java.util.*;

public class LogicTest {
    public static void main(String[] args) {
        SimulationManager manager = new SimulationManager();
        WorldImpl world = new WorldImpl();
        SimulationInitDataFromUserDTO initData;
        List<PropertyDefinitionDTO> initDTO = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();

        world.addEntity(new EntityDefinitionImpl("Eyal"));
        world.addRule(new RuleImpl("live", new ActivationImpl(0.5)));
        world.addEnvironmentVariable(new PropertyDefinitionImpl("env-var", PropertyType.STRING, true));
        world.setGridCols(100);
        world.setGridRows(100);
        world.setThreadPoolSize(1);

        initDTO.add(new PropertyDefinitionDTO("live", "STRING", 0.0, 1000.0, false, "blabla"));
        map.put("Eyal", 100);

        initData = new SimulationInitDataFromUserDTO(
                initDTO,
                map,
                new TerminationDTO(100, 5, true, false),
                "Utopia",
                "Tair",
                1
        );

        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("end");
            }
        };

        manager.addObserver(observer);

        manager.runNewSimulation(world, initData);
    }
}
