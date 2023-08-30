package javafx.input.logic;

import javafx.input.logic.tasks.entity.CollectEntityPopulationTask;
import javafx.input.components.mainComponent.UIAdapter;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import javafx.input.components.mainComponent.SecondScreenController;
import javafx.concurrent.Task;

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
}


