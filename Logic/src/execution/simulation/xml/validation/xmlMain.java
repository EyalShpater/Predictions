package execution.simulation.xml.validation;

import definition.world.api.World;
import definition.world.impl.WorldImpl;
import execution.simulation.xml.reader.impl.XmlReader;

public class xmlMain {
    public static void main(String[] args) {

        World world = new WorldImpl();
        XmlValidator validator = new XmlValidator("/Users/eyal/Java-Course/Predictions/Logic/src/resources/master-ex1.xml");
        validator.isValid();

        XmlReader reader = new XmlReader(validator.getWorld());
        reader.readXml(world);
        int x = 9;
    }

}
