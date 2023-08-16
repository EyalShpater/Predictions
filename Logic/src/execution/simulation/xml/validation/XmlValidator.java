package execution.simulation.xml.validation;

import resources.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.*;
import java.util.List;

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
        if (!iterateRulesForEntityNameInAction(world)){
            throw new IllegalArgumentException("One of the entities you provided for specific action does not exist");
        }

        // 5) check that in action no call to a property that doesnt exist
        if (!checkIfPropertySpecifiedInActionExistInEntity(world)){
            throw new IllegalArgumentException("One of the properties you provided for specific action does not match the entity you provided");
        }


        // 6) check that in (calculation \ increase \ decrease) the args are numbers only including helper functions
        return true;
    }


    //11111111111111111111111
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


    //222222222222222222222222
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

    //33333333333333333333
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


    //4444444444444444444444
    //TODO: Make the rule iteration generic
    private boolean iterateRulesForEntityNameInAction(PRDWorld world){

        List<PRDEntity> entityList = world.getPRDEntities().getPRDEntity();
        List<PRDRule> ruleList = world.getPRDRules().getPRDRule();

        boolean areActionsForThisRuleValid = false;

        for(PRDRule rule : ruleList){

            areActionsForThisRuleValid = checkIfAllTheRulesAreValid(entityList ,rule );

            if(!areActionsForThisRuleValid){
                return false;
            }
        }
        return true;
    }

    private boolean checkIfAllTheRulesAreValid(List<PRDEntity>entityList ,PRDRule rule){

        PRDActions actions = rule.getPRDActions();
        List<PRDAction> actionList = actions.getPRDAction();

        boolean isActionForThisRuleValid = false;

        for(PRDAction action : actionList){

            for (PRDEntity entity : entityList){
                isActionForThisRuleValid = checkIfEntityNameExist(entity.getName(), action.getEntity() );
            }
            if (!isActionForThisRuleValid){
                return false;
            }
        }
        return true;
    }

    private boolean checkIfEntityNameExist(String entityName, String entityInAction){
        return entityName.equals(entityInAction);
    }

    //5555555555555555555\

    private boolean checkIfPropertySpecifiedInActionExistInEntity(PRDWorld world){
        List<PRDEntity> entityList = world.getPRDEntities().getPRDEntity();
        List<PRDRule> ruleList = world.getPRDRules().getPRDRule();

        boolean areActionsForThisRuleValid = false;

        for(PRDRule rule : ruleList){

            areActionsForThisRuleValid = checkPropertyExistInEntityForRules( entityList ,rule );

            if(!areActionsForThisRuleValid){
                return false;
            }
        }
        return true;
    }

    private boolean checkPropertyExistInEntityForRules(List<PRDEntity>entityList ,PRDRule rule){
        PRDActions actions = rule.getPRDActions();
        List<PRDAction> actionList = actions.getPRDAction();

        boolean isActionForThisRuleValid = false;

        for(PRDAction action : actionList){

            for (PRDEntity entity : entityList){
                isActionForThisRuleValid = checkPropertyExistInEntityForActions( entity, action );
            }
            if (!isActionForThisRuleValid){
                return false;
            }
        }
        return true;
    }

    private boolean checkPropertyExistInEntityForActions(PRDEntity entity , PRDAction action){

        if(entity.getName().equals(action.getEntity())){

            boolean isPropertyExistInEntity = false;

            PRDProperties entityProperties = entity.getPRDProperties();
            List <PRDProperty> entityPropertyList = entityProperties.getPRDProperty();

            for(PRDProperty entityProperty : entityPropertyList){

                if(action.getProperty() != null){

                    isPropertyExistInEntity =checkPropertyOfActionNameInEntityProperties(action.getProperty(), entityProperty.getPRDName()  );
                    if (isPropertyExistInEntity){
                        break;
                    }
                }

            }
            return isPropertyExistInEntity;
        }
        return false;
    }

    private boolean checkPropertyOfActionNameInEntityProperties(String actionPropertyName , String entityProperty){
        return actionPropertyName.equals(entityProperty);
    }

}
