package javafx.input.logic;

import impl.EntityDefinitionDTO;
import impl.PropertyDefinitionDTO;
import impl.WorldDTO;
import javafx.input.logic.tasks.entity.CollectEntityPopulationTask;
import javafx.input.components.mainComponent.UIAdapter;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import javafx.input.components.mainComponent.SecondScreenController;
import javafx.concurrent.Task;

import java.util.List;

public class SecondScreenLogic {
    private SecondScreenController secController;
    private PredictionsLogic engine;
    private Task<Boolean> currentRunningTask;

    public SecondScreenLogic(SecondScreenController controller) {
        this.secController = controller;
        this.engine = new PredictionsLogicImpl();
    }

    public void collectEntitiesData(UIAdapter uiAdapter) {
        currentRunningTask = new CollectEntityPopulationTask(uiAdapter, engine);
        new Thread(currentRunningTask).start();
    }

    public void loadXML(String absolutePath) {
        engine.loadXML(absolutePath);
    }

    public List<EntityDefinitionDTO> getEntityList() {
        WorldDTO loadedSimulationDetails = engine.getLoadedSimulationDetails();
        List<EntityDefinitionDTO> entitiesList = loadedSimulationDetails.getEntities();
        return entitiesList;
    }


    public List<PropertyDefinitionDTO> getEnvironmentVariablesToSet() {
        return engine.getEnvironmentVariablesToSet();
    }
}


