package execution.simulation.xml.validation;

public class xmlMain {
    public static void main(String[] args) {
        XmlValidator validator = new XmlValidator("/Users/eyal/Java-Course/Predictions/Logic/src/resources/ex1-cigarets.xml");
        validator.isValid();
    }

}
