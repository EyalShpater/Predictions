package grid;

import definition.entity.impl.EntityDefinitionImpl;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;

public class TestMain {
    public static void main(String[] args) {
        EntityInstance e1 = new EntityInstanceImpl(new EntityDefinitionImpl("e1", 100), 1);
        EntityInstance e2 = new EntityInstanceImpl(new EntityDefinitionImpl("e2", 100), 1);
        SphereSpace space = new SphereSpaceImpl(10, 10);

        space.setRandomLocation(e1);
        space.setRandomLocation(e2);

        space.makeRandomMove(e1);
        space.makeRandomMove(e1);
        space.makeRandomMove(e1);
        space.makeRandomMove(e1);
    }
}
