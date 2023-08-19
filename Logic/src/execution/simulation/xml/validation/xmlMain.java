package execution.simulation.xml.validation;

import definition.world.api.World;
import definition.world.impl.WorldImpl;
import execution.simulation.xml.reader.impl.XmlReader;

public class xmlMain {
    public static void main(String[] args) {

        World world = new WorldImpl();
        //XmlValidator validator = new XmlValidator("C:\\Users\\ASUS\\IdeaProjects\\Predictions2\\Logic\\src\\resources\\ex1-cigarets.xml");
        //XmlValidator validator = new XmlValidator("C:\\Users\\ASUS\\IdeaProjects\\Predictions2\\Logic\\src\\resources\\ex1-error-2.xml");
        //XmlValidator validator = new XmlValidator("C:\\Users\\ASUS\\IdeaProjects\\Predictions2\\Logic\\src\\resources\\ex1-error-4.xml");
        //XmlValidator validator = new XmlValidator("C:\\Users\\ASUS\\IdeaProjects\\Predictions2\\Logic\\src\\resources\\ex1-error-6.xml");
        XmlValidator validator = new XmlValidator("C:\\Users\\ASUS\\IdeaProjects\\Predictions\\Logic\\src\\resources\\master-ex1.xml");
        validator.isValid();

        XmlReader reader = new XmlReader(validator.getWorld());
        reader.readXml(world);
        int x = 9;
    }

}
