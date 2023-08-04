package program;

import simulation.propety.entity.model.Entity;
import simulation.propety.entity.model.Property;
import simulation.world.World;

public class Program {
    public static void main(String[] args) {
        System.out.println("hello");

        Entity entity = new Entity();
        entity.setName("Smoker");
        Property p1 = new Property();
        p1.setNeedToBeInitialize(true);
        p1.setTitle("rule1");

        Property p2 = new Property();
        p1.setNeedToBeInitialize(false);
        p2.setTitle("rule2");

        entity.addProperty(p1);
        entity.addProperty(p2);

        System.out.println(entity);
    }
}
