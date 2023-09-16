package grid;

import action.api.Action;
import action.context.impl.ContextImpl;
import action.impl.IncreaseAction;
import action.impl.Proximity;
import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;
import instance.entity.manager.api.EntityInstanceManager;
import instance.entity.manager.impl.EntityInstanceManagerImpl;
import instance.enviornment.impl.ActiveEnvironmentImpl;

public class TestMain {
    public static void main(String[] args) {
        EntityDefinition d1 = new EntityDefinitionImpl("e1");
        EntityDefinition d2 = new EntityDefinitionImpl("e2");

        EntityInstance e1 = new EntityInstanceImpl(d1, 1);
        EntityInstance e2 = new EntityInstanceImpl(d2, 1);
        SphereSpace space = new SphereSpaceImpl(10, 10);


        EntityInstanceManager manager = new EntityInstanceManagerImpl();


        manager.createInstancesFromDefinition(d1, space);
        manager.createInstancesFromDefinition(d2, space);

        Proximity pr = new Proximity(d1, d2, "2");
        pr.addAction(new IncreaseAction(d2, "", "3"));

        //pr.invoke(new ContextImpl(manager.getInstances().get(0), manager, new ActiveEnvironmentImpl()));
//        space.setNewRandomLocation(e1);
//        space.setNewRandomLocation(e2);
//
//        space.makeRandomMove(e1);
//        space.makeRandomMove(e1);
//        space.makeRandomMove(e1);
//        space.makeRandomMove(e1);
    }
}
