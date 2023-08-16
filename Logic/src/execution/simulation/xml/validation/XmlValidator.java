package execution.simulation.xml.validation;

import resources.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;

public class XmlValidator {

    private final String path;
    public XmlValidator(String path) {
        this.path = path;
    }

    public boolean isValid(){
        // 1) check xml exist and type of xml
        if(!checkIfPathExist()) {
            throw new IllegalArgumentException("File path does not exist");
        } else if (!checkIfXmlType()) {
            throw new IllegalArgumentException("File path must end with .xml .");
        }

        PRDWorld world = loadXmlToWorld();

        // 2) check env-vars to have different names
        if (!checkEnvVarsNames(world.getPRDEvironment())){
            throw new IllegalArgumentException("Environment variable names must be different .");
        }

        // 3) check properties to have different names
        if (!checkPropertiesNames(world.getPRDEntities())){
            throw new IllegalArgumentException("The names of properties inside entity definition must be different  .");
        }
        // 4) check that in action no call to an entity that doesnt exist
        if (!checkIfEntityExistInActions(world)){
            throw new IllegalArgumentException("One of the entities you provided for specific action does not exist");
        }
        // 6) check that in (calculation \ increase \ decrease) the args are numbers only including helper functions
        return true;
    }

    private boolean checkIfPathExist(){
        Path xmlpath = Paths.get(this.path);
         if (!Files.exists(xmlpath)) {
             return false;
        }
        return true;
    }

    private boolean checkIfXmlType(){
        if(!path.endsWith(".xml")){
            return false;
        }
        return true;
    }

    private PRDWorld loadXmlToWorld(){
        try
        {
            File file = new File(this.path);
            JAXBContext jaxbContent = JAXBContext.newInstance(PRDWorld.class);

            Unmarshaller jaxbUnmarshaller = jaxbContent.createUnmarshaller();
            PRDWorld world  = (PRDWorld) jaxbUnmarshaller.unmarshal(file);

            return world;

        }catch (JAXBException e){
            e.printStackTrace();
        }
        return null;
    }

    //TODO: make the bubble sort style generic in checkEnvVarsNames ,checkPropertiesNames
    private boolean checkEnvVarsNames(PRDEvironment environment){

        boolean hasEqualStrings = false;

        List<PRDEnvProperty> EnvPropertyList = environment.getPRDEnvProperty();
        for (int i = 0; i < EnvPropertyList.size() - 1; i++) {
            for (int j = i + 1; j < EnvPropertyList.size(); j++) {
                if (EnvPropertyList.get(i).getPRDName().equals(EnvPropertyList.get(j).getPRDName())) {
                    hasEqualStrings = true;
                    break;
                }
            }
            if (hasEqualStrings) {
                break;
            }
        }

        return !hasEqualStrings;

    }

    //logic is not correct
    private boolean checkPropertiesNames(PRDEntities entities){

        boolean hasEqualStrings = false;

        List<PRDEntity> entityList = entities.getPRDEntity();
        for (PRDEntity entity: entityList )
        {
            PRDProperties properties = entity.getPRDProperties();
            List<PRDProperty> PropertyList = properties.getPRDProperty();
            for (int i = 0; i < PropertyList.size() - 1; i++) {
                for (int j = i + 1; j < PropertyList.size(); j++) {
                    if (PropertyList.get(i).getPRDName().equals(PropertyList.get(j).getPRDName())) {
                        hasEqualStrings = true;
                        break;
                    }
                }
                if (hasEqualStrings) {
                    break;
                }
            }
        }


        return !hasEqualStrings;
    }

    //logic is not correct
    private boolean checkIfEntityExistInActions(PRDWorld world){

        List<PRDEntity> entityList = world.getPRDEntities().getPRDEntity();

        List<PRDRule> ruleList = world.getPRDRules().getPRDRule();

        boolean foundEntityInAction = false;
        boolean isValidAction = true;


        for(PRDRule rule : ruleList){//for every rule
            PRDActions actions = rule.getPRDActions();
            List<PRDAction> actionList = actions.getPRDAction();
            for(PRDAction action : actionList){ // for every action inside a rule
                for (PRDEntity entity : entityList){ // for every entity in the world
                    if (entity.getName().equals(action.getEntity())){ // check that the entity specified in action exist in the world
                        foundEntityInAction = true;

                    }
                }
                isValidAction = isValidAction && foundEntityInAction;
                foundEntityInAction = false;
            }
        }

        return isValidAction;

    }
}
