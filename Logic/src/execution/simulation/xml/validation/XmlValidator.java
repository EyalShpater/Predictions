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

    public boolean isValid() throws IllegalArgumentException{
        // 1) check xml exist and type of xml
        checkIfPathExist();
        checkIfXmlType();

        PRDWorld world = loadXmlToWorld();

        // 2) check env-vars to have different names
        checkEnvVarsNames(world.getPRDEvironment());


        // 3) check properties to have different names
        checkPropertiesNames(world.getPRDEntities());

        // 4) check that in action no call to an entity that doesnt exist
        areAllRulesValid(world);
        /*if (!iterateRulesForEntityNameInAction(world)){
            throw new IllegalArgumentException("One of the entities you provided for specific action does not exist");
        }*/

        // 5) check that in action no call to a property that doesnt exist
        if (!checkIfPropertySpecifiedInActionExistInEntity(world)){
            throw new IllegalArgumentException("One of the properties you provided for specific action does not match the entity you provided");
        }


        // 6) check that in (calculation \ increase \ decrease) the args are numbers only including helper functions
        return true;
    }


    //11111111111111111111111

    private void checkIfPathExist(){
        Path xmlpath = Paths.get(this.path);

        if (!Files.exists(xmlpath)) {
            throw new IllegalArgumentException("File path does not exist");
        }
    }

    private void checkIfXmlType(){
        if(!path.endsWith(".xml")) {
            throw new IllegalArgumentException("File path must end with .xml .");
        }
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
    private void checkEnvVarsNames(PRDEvironment environment){

        List<PRDEnvProperty> EnvPropertyList = environment.getPRDEnvProperty();
        for (int i = 0; i < EnvPropertyList.size() - 1; i++) {
            for (int j = i + 1; j < EnvPropertyList.size(); j++) {
                checkVarsNamesToBeDifferent( EnvPropertyList.get(i).getPRDName() , EnvPropertyList.get(j).getPRDName() );
            }
            checkVarsNamesToNotHaveSpaces(EnvPropertyList.get(i).getPRDName());
        }
    }

    private void checkVarsNamesToBeDifferent(String name1 ,String name2 ){

        if (name1.equals(name2)) {
            throw new IllegalArgumentException("The environment variable " +name1+" appears more than one time");
        }
    }

    private void checkVarsNamesToNotHaveSpaces(String envVarName){
        if (envVarName.contains(" ")) {
            throw new IllegalArgumentException("Environment variable "+ envVarName +" should not contain spaces.");
        }
    }

    //33333333333333333333

    private void checkPropertiesNames(PRDEntities entities){
        List<PRDEntity> entityList = entities.getPRDEntity();
        for (PRDEntity entity: entityList ) {
            PRDProperties properties = entity.getPRDProperties();
            List<PRDProperty> propertyList = properties.getPRDProperty();
            for (int i = 0; i < propertyList.size() - 1; i++) {
                for (int j = i + 1; j < propertyList.size(); j++) {
                    checkVarsNamesToBeDifferent( propertyList.get(i).getPRDName() , propertyList.get(j).getPRDName() );
                }
                checkVarsNamesToNotHaveSpaces(propertyList.get(i).getPRDName());
            }
        }
    }


    //4444444444444444444444

    private void areAllRulesValid(PRDWorld world){

        List<PRDEntity> entityList = world.getPRDEntities().getPRDEntity();
        List<PRDRule> ruleList = world.getPRDRules().getPRDRule();

        for(PRDRule rule : ruleList){
            areAllActionsInsideRulesValid( entityList , rule );
        }

    }

    private void areAllActionsInsideRulesValid(List<PRDEntity>entityList , PRDRule rule) {

        PRDActions actions = rule.getPRDActions();
        List<PRDAction> actionList = actions.getPRDAction();

        for(PRDAction action : actionList){
            if(action.getType().equals("condition")){
                checkIfEntityNameExistInConditionAction( entityList ,  action );
                //execute then and else
            }else{
                checkIfEntityNameExistInNonConditionAction( entityList , action );
            }
        }
    }

    private void checkIfEntityNameExistInConditionAction(List<PRDEntity>entityList , PRDAction action ){
        PRDCondition condition = action.getPRDCondition();
        if (condition.getSingularity().equals("single")){

        } else if (condition.getSingularity().equals("multiple")) {

        }


    }

    private void checkIfEntityNameExistInSingleConditionAction(List<PRDEntity>entityList , PRDAction action){
        boolean isEntityNameExistInSingleConditionAction = false;
        for (PRDEntity entity : entityList ){

        }
    }

    private void checkIfEntityNameExistInNonConditionAction(List<PRDEntity>entityList , PRDAction action ){
        checkIfEntityNameExistInEntityList( entityList ,  action );
    }

    private void checkIfEntityNameExistInEntityList( List<PRDEntity>entityList , PRDAction action ){
        boolean isEntityNameInActionExistInEntityList = false;
        for (PRDEntity entity : entityList){
            if(entity.getName().equals(action.getEntity())){
                isEntityNameInActionExistInEntityList = true;
            }
        }
        if(!isEntityNameInActionExistInEntityList){
            throw new IllegalArgumentException("The entity name "+ action.getEntity()
                    + " that appears in the action that attempts to change"+ action.getProperty()
                    +" does not appear in the system ");
        }
    }

    /*private boolean iterateRulesForEntityNameInAction(PRDWorld world){

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
    }*/

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
            if (!action.getType().equals("condition")){
                isPropertyExistInEntity = checkPropertyExistInEntityNonConditionVersion(entity.getPRDProperties(), action);
            } else {
                isPropertyExistInEntity = checkPropertyExistInEntityConditionVersion(action.getPRDCondition() , entity , action);
            }
            return isPropertyExistInEntity;
        }
        return false;
    }

    private boolean checkPropertyExistInEntityConditionVersion(PRDCondition condition ,PRDEntity entity , PRDAction action) {

        boolean isValid = true;

        if (condition.getSingularity().equals("single")){
            return checkPropertyExistInEntitySingleConditionVersion(condition, entity.getPRDProperties());
        } else if (condition.getSingularity().equals("multiple")) {

            List<PRDCondition> conditionList = condition.getPRDCondition();
            for (PRDCondition smallerCondition : conditionList) {
                isValid = isValid && checkPropertyExistInEntityConditionVersion(smallerCondition, entity , action);
            }
        }
        return isValid;
    }

    private boolean checkPropertyExistInEntitySingleConditionVersion(PRDCondition condition ,PRDProperties entityProperties){

        List <PRDProperty> entityPropertyList = entityProperties.getPRDProperty();

        boolean isPropertyExistInEntity = false;

        for(PRDProperty entityProperty : entityPropertyList){

            isPropertyExistInEntity = checkPropertyOfActionNameInEntityProperties(condition.getProperty(), entityProperty.getPRDName() );
            if (isPropertyExistInEntity) {
                break;
            }

        }
        return isPropertyExistInEntity;
    }


    private boolean checkPropertyExistInEntityNonConditionVersion(PRDProperties entityProperties ,PRDAction action ){

        List <PRDProperty> entityPropertyList = entityProperties.getPRDProperty();

        boolean isPropertyExistInEntity = false;

        for(PRDProperty entityProperty : entityPropertyList){

            if(action.getProperty() != null){

                isPropertyExistInEntity = checkPropertyOfActionNameInEntityProperties(action.getProperty(), entityProperty.getPRDName()  );
                if (isPropertyExistInEntity){
                    break;
                }
            }
        }
        return isPropertyExistInEntity;
    }

    private boolean checkPropertyOfActionNameInEntityProperties(String actionPropertyName , String entityProperty){
        return actionPropertyName.equals(entityProperty);
    }

}
