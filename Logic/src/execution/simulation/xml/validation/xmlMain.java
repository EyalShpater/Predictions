package execution.simulation.xml.validation;

public class xmlMain {
    public static void main(String[] args) {
        XmlValidator validator = new XmlValidator("C:/Users/ASUS/IdeaProjects/Predictions2/Logic/src/resources/ex1-cigarets.xml");
        validator.isValid();
    }
}
