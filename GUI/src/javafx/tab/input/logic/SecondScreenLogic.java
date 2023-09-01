package javafx.tab.input.logic;

import impl.EntityDefinitionDTO;
import impl.PropertyDefinitionDTO;
import impl.SimulationRunDetailsDTO;
import impl.WorldDTO;
import javafx.tab.input.logic.tasks.entity.CollectEntityPopulationTask;
import javafx.tab.input.components.mainComponent.UIAdapter;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import javafx.tab.input.components.mainComponent.SecondScreenController;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBException;
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
        try {
            engine.loadXML(absolutePath);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EntityDefinitionDTO> getEntityList() {
        WorldDTO loadedSimulationDetails = engine.getLoadedSimulationDetails();
        List<EntityDefinitionDTO> entitiesList = loadedSimulationDetails.getEntities();
        return entitiesList;
    }


    public List<PropertyDefinitionDTO> getEnvironmentVariablesToSet() {
        return engine.getEnvironmentVariablesToSet();
    }

    public SimulationRunDetailsDTO runNewSimulation(List<PropertyDefinitionDTO> updatedEnvironmentVariables) {
        return engine.runNewSimulation(updatedEnvironmentVariables);
    }
}


