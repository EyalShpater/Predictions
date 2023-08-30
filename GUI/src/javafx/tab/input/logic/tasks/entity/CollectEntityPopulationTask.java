package javafx.tab.input.logic.tasks.entity;

import execution.simulation.api.PredictionsLogic;
import javafx.tab.input.components.mainComponent.UIAdapter;
import javafx.tab.input.components.singleEntity.BasicEntityData;
import javafx.tab.input.components.singleEntity.EntityData;
import impl.EntityDefinitionDTO;
import impl.WorldDTO;
import javafx.concurrent.Task;

import java.util.List;

public class CollectEntityPopulationTask extends Task<Boolean> {


    private UIAdapter uiAdapter;
    private PredictionsLogic engine;

    public CollectEntityPopulationTask(UIAdapter uiAdapter, PredictionsLogic engine) {
        this.uiAdapter = uiAdapter;
        this.engine = engine;
    }

    @Override
    protected Boolean call() throws Exception {
        WorldDTO loadedSimulationDetails = engine.getLoadedSimulationDetails();
        List<EntityDefinitionDTO> entitiesList = loadedSimulationDetails.getEntities();
        entitiesList.forEach(entity -> {
            EntityData entityData = new BasicEntityData(entity.getName(), entity.getPopulation());
            for (int i = 0; i < 10; i++) {
                uiAdapter.addNewWord(entityData);
            }
            uiAdapter.addNewWord(entityData);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        return Boolean.TRUE;
    }
}
