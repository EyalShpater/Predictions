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

    private final char HELPER_FUNCTION_TOKEN = '(';
    private final int NOT_FOUND = -1;
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
        areAllRulesActionsEntityNamesValid(world);


        // 5) check that in action no call to a property that doesnt exist
        checkRulesToNotContainActionWithPropertyWithNoMatchEntity(world);


        // 6) check that in (calculation \ increase \ decrease) the args are numbers only including helper functions
        checkNumericCalculationActionToIncludeNumericArgs(world);
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

    private void areAllRulesActionsEntityNamesValid(PRDWorld world){

        List<PRDEntity> entityList = world.getPRDEntities().getPRDEntity();
        List<PRDRule> ruleList = world.getPRDRules().getPRDRule();

        for(PRDRule rule : ruleList){
            try{
                areAllActionsInsideRulesValid( entityList , rule );
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("In rule: " +rule.getName() + e.getMessage());
            }

        }

    }

    private void areAllActionsInsideRulesValid(List<PRDEntity>entityList , PRDRule rule) {

        PRDActions actions = rule.getPRDActions();
        List<PRDAction> actionList = actions.getPRDAction();

        for(PRDAction action : actionList){
            checkIfActionIsOfTypeConditionAndSendToCheckEntityExistence(entityList , action);
        }
    }

    private void checkIfActionIsOfTypeConditionAndSendToCheckEntityExistence(List<PRDEntity>entityList , PRDAction action){
        if(action.getType().equals("condition")){
            checkIfEntityNameExistInEntityList( entityList , action );
            checkIfEntityNameExistInConditionAction( entityList ,  action );
            checkIfEntityNameExistForThenAction( entityList , action );
            checkIfEntityNameExistForElseAction( entityList , action );

        }else{
            checkIfEntityNameExistInEntityList( entityList , action );
        }
    }

    private void checkIfEntityNameExistForThenAction(List<PRDEntity>entityList , PRDAction action){
        PRDThen thenBlock = action.getPRDThen();
        List<PRDAction> actionListForThenBlock = thenBlock.getPRDAction();
        for (PRDAction singleAction :actionListForThenBlock ){
            checkIfActionIsOfTypeConditionAndSendToCheckEntityExistence( entityList ,singleAction );
        }
    }

    private void checkIfEntityNameExistForElseAction(List<PRDEntity>entityList , PRDAction action){
        PRDElse elseBlock = action.getPRDElse();
        if ( elseBlock != null ){
            List<PRDAction> actionListForElseBlock = elseBlock.getPRDAction();
            for (PRDAction singleAction :actionListForElseBlock ){
                checkIfActionIsOfTypeConditionAndSendToCheckEntityExistence( entityList ,singleAction );
            }
        }
    }



    private void checkIfEntityNameExistInConditionAction(List<PRDEntity>entityList , PRDAction action ){
        PRDCondition condition = action.getPRDCondition();
        if (condition.getSingularity().equals("single")){
            checkIfSimpleConditionEntityNameExist( entityList , action ,condition );
        } else if (condition.getSingularity().equals("multiple")) {
            checkIfEntityNameExistForMultipleTypeAction(entityList , action , action.getPRDCondition());
        }
    }
    private void checkIfEntityNameExistForMultipleTypeAction(List<PRDEntity>entityList , PRDAction action , PRDCondition condition ){

        if(condition.getSingularity().equals("single")){
            checkIfSimpleConditionEntityNameExist( entityList , action ,condition );
        }
        else{
            List<PRDCondition> conditionList = condition.getPRDCondition();
            for (PRDCondition smallerCondition : conditionList ){
                checkIfEntityNameExistForMultipleTypeAction(entityList , action , smallerCondition );
            }
        }
    }

    private void checkIfSimpleConditionEntityNameExist(List<PRDEntity>entityList , PRDAction action , PRDCondition condition ){
        boolean isEntityNameInActionExistInEntityList = false;
        for (PRDEntity entity : entityList){
            if(entity.getName().equals(condition.getEntity())){
                isEntityNameInActionExistInEntityList = true;
            }
        }
        if(!isEntityNameInActionExistInEntityList){
            throw new IllegalArgumentException(" the entity name "+ condition.getEntity() +" does not appear in the system ");
        }
    }


    private void checkIfEntityNameExistInEntityList( List<PRDEntity>entityList , PRDAction action ){
        boolean isEntityNameInActionExistInEntityList = false;
        for (PRDEntity entity : entityList){
            if(entity.getName().equals(action.getEntity())){
                isEntityNameInActionExistInEntityList = true;
            }
        }
        if(!isEntityNameInActionExistInEntityList){
            throw new IllegalArgumentException(" the entity name :"+ action.getEntity()
                    + " does not appear in the system ");
        }
    }

    //5555555555555555555

    private void checkRulesToNotContainActionWithPropertyWithNoMatchEntity(PRDWorld world){
        List<PRDEntity> entityList = world.getPRDEntities().getPRDEntity();
        List<PRDRule> ruleList = world.getPRDRules().getPRDRule();
        for(PRDRule rule : ruleList){
            try{
                checkActionsToNotContainActionWithPropertyWithNoMatchEntity(entityList , rule);
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("In rule: "+rule.getName() +e.getMessage());
            }
        }
    }

    private void checkActionsToNotContainActionWithPropertyWithNoMatchEntity(List<PRDEntity> entityList , PRDRule rule ){
        PRDActions actions = rule.getPRDActions();
        List<PRDAction> actionList = actions.getPRDAction();

        for(PRDAction action : actionList){
            checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence(entityList , action);
        }
    }

    private void checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence( List<PRDEntity> entityList , PRDAction action ){
        if(action.getType().equals("condition")){
            checkIfPropertyInActionExistForEntityCondition( entityList ,  action );
            checkIfPropertyInActionExistForThenAction( entityList ,  action );
            checkIfPropertyInActionExistForElseAction( entityList ,  action );
        }else{
            checkIfPropertyInActionExistForEntityNonCondition( entityList , action );
        }
    }



    private void checkIfPropertyInActionExistForThenAction(List<PRDEntity> entityList, PRDAction action) {
        PRDThen thenBlock = action.getPRDThen();
        List<PRDAction> actionListForThenBlock = thenBlock.getPRDAction();
        for (PRDAction singleAction :actionListForThenBlock ){
            checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence( entityList ,singleAction );
        }
    }

    private void checkIfPropertyInActionExistForElseAction(List<PRDEntity> entityList, PRDAction action) {
        PRDElse elseBlock = action.getPRDElse();
        if ( elseBlock != null ){
            List<PRDAction> actionListForElseBlock = elseBlock.getPRDAction();
            for (PRDAction singleAction :actionListForElseBlock ){
                checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence( entityList ,singleAction );
            }
        }
    }

    private void checkIfPropertyInActionExistForEntityCondition(List<PRDEntity> entityList, PRDAction action) {
        PRDCondition condition = action.getPRDCondition();
        if (condition.getSingularity().equals("single")){
            checkIfSimpleConditionPropertyNameExistInEntityPropertyList(entityList , condition);
        } else if (condition.getSingularity().equals("multiple")) {
            checkIfMultipleConditionPropertyNameExistInEntityPropertyList(entityList , condition);
        }
    }

    private void checkIfMultipleConditionPropertyNameExistInEntityPropertyList(List<PRDEntity> entityList, PRDCondition condition) {
        if(condition.getSingularity().equals("single")){
            checkIfSimpleConditionPropertyNameExistInEntityPropertyList( entityList ,condition );
        }
        else{
            List<PRDCondition> conditionList = condition.getPRDCondition();
            for (PRDCondition smallerCondition : conditionList ){
                checkIfMultipleConditionPropertyNameExistInEntityPropertyList(entityList , smallerCondition );
            }
        }
    }

    private void checkIfSimpleConditionPropertyNameExistInEntityPropertyList(List<PRDEntity> entityList, PRDCondition condition) {
        PRDEntity entity = findEntityFromActionInEntityList( entityList , condition.getEntity() );
        if ( entity != null ){
            findPropertyFromActionInPropertyListOfEntity( entity , condition.getProperty());
        }
    }

    private void checkIfPropertyInActionExistForEntityNonCondition(List<PRDEntity> entityList , PRDAction action){
        PRDEntity entity = findEntityFromActionInEntityList( entityList , action.getEntity() );
        if ( entity != null ){
            findPropertyFromActionInPropertyListOfEntity( entity , action.getProperty());
        }
    }

    private void findPropertyFromActionInPropertyListOfEntity(PRDEntity entity, String propertyName) {
        if (propertyName != null) {//might be for kill
            PRDProperties properties = entity.getPRDProperties();;
            List<PRDProperty> propertyList = properties.getPRDProperty();

            PRDProperty theProperty = propertyList.stream()
                    .filter(property -> property.getPRDName().equals(propertyName))
                    .findAny()
                    .orElse(null);

            if( theProperty == null ){
                throw new IllegalArgumentException(" the property: " + propertyName+" does not exist");
            }
        }
    }

    private PRDEntity findEntityFromActionInEntityList(List<PRDEntity> entityList , String entityName) {
        return entityList.stream()
                .filter(entity -> entity.getName().equals(entityName))
                .findAny()
                .orElse(null);
    }


    //6666666666666666666
    private void checkNumericCalculationActionToIncludeNumericArgs(PRDWorld world) {

        PRDEvironment env = world.getPRDEvironment();
        List<PRDEnvProperty> envPropertiesList = env.getPRDEnvProperty();
        List<PRDRule> ruleList = world.getPRDRules().getPRDRule();
        List<PRDEntity> entityList = world.getPRDEntities().getPRDEntity();
        for(PRDRule rule : ruleList) {
            try {
                checkNumericCalculationActionToIncludeNumericArgs(envPropertiesList , rule , entityList );
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("In rule: " + rule.getName() + e.getMessage());
            }

        }
    }



    private void checkNumericCalculationActionToIncludeNumericArgs( List<PRDEnvProperty> envPropertiesList , PRDRule rule , List<PRDEntity> entityList){
        PRDActions actions = rule.getPRDActions();
        List<PRDAction> actionList = actions.getPRDAction();

        for(PRDAction action : actionList){
            checkIfActionIsOfTypeConditionAndSendToCheckIfActionToIncludeNumericArgs(envPropertiesList , action , entityList);
        }
    }

    private void checkIfActionIsOfTypeConditionAndSendToCheckIfActionToIncludeNumericArgs(List<PRDEnvProperty> envPropertiesList, PRDAction action , List<PRDEntity> entityList) {

        if(action.getType().equals("condition")){
            /*checkIfPropertyInActionExistForEntityCondition( entityList ,  action );
            checkIfPropertyInActionExistForThenAction( entityList ,  action );
            checkIfPropertyInActionExistForElseAction( entityList ,  action );*/
        }else{
            //checkIfPropertyInActionExistForEntityNonCondition( entityList , action );
            checkIfArgsInActionAreNumericNonConditionVersion(envPropertiesList , action , entityList);
        }

    }

    private void checkIfArgsInActionAreNumericNonConditionVersion(List<PRDEnvProperty> envPropertiesList, PRDAction action , List<PRDEntity> entityList) {

        if(action.getType().equals("increase") || action.getType().equals("decrease")){
            checkIfArgsInActionAreNumericIncreaseDecreaseStructure( envPropertiesList, action , entityList);
        }else if (action.getType().equals("calculation")){
            checkIfArgsInActionAreNumericCalculationStructure( envPropertiesList, action);
        }
    }

    private void checkIfArgsInActionAreNumericCalculationStructure(List<PRDEnvProperty> envPropertiesList, PRDAction action) {
    }

    private void checkIfArgsInActionAreNumericIncreaseDecreaseStructure(List<PRDEnvProperty> envPropertiesList,  PRDAction action , List<PRDEntity> entityList) {
        String argument = action.getBy();
        String entityOfAction = action.getEntity();
        isHelperFunction(envPropertiesList , argument);
        isProperty(entityOfAction , argument , entityList );
    }

    private void isProperty(String entityOfAction , String argument, List<PRDEntity> entityList) {
        PRDEntity entity = findEntityFromActionInEntityList(entityList , entityOfAction);
        List<PRDProperty> propertyList = entity.getPRDProperties().getPRDProperty();

        PRDProperty theProperty = propertyList.stream()
                .filter(property -> property.getPRDName().equals(argument))
                .findAny()
                .orElse(null);

        if ( theProperty == null ){
            checkIfArgumentIsNumeric(argument);
        }

    }

    private void isHelperFunction(List<PRDEnvProperty> envPropertiesList, String argument) {

        int indexOfToken = argument.indexOf(HELPER_FUNCTION_TOKEN);
        String functionName = null;
        String functionArgument = null;

        if (indexOfToken != NOT_FOUND) {
            functionName = argument.substring(0, indexOfToken);
            int closingParenthesisIndex = argument.indexOf(")");
            if (closingParenthesisIndex != NOT_FOUND && closingParenthesisIndex > indexOfToken) {
                functionArgument = argument.substring(indexOfToken + 1, closingParenthesisIndex).trim(); // Trim to remove any leading/trailing spaces
            }
            isHelperFunctionExist(envPropertiesList , functionName ,functionArgument );
        }
    }

    private void isHelperFunctionExist(List<PRDEnvProperty> envPropertiesList, String functionName , String functionArgument ) {
        if (functionName.equals("environment")  ){
            checkIfEnvironmentArgumentIsValid(envPropertiesList ,functionArgument );
        } else if (functionName.equals("random")) {
            try{
                checkIfArgumentIsNumeric(functionArgument);

            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException(" for random helper function" + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException(" function name provided" +functionName+ " does not exist");
        }
    }

    private void checkIfArgumentIsNumeric(String functionArgument) {
        if (functionArgument == null || functionArgument.isEmpty()) {
            throw new IllegalArgumentException(" the argument provided is empty ");
        }
        for (int i = 0; i < functionArgument.length(); i++) {
            char c = functionArgument.charAt(i);
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException(" the argument is not an integer number " );
            }
        }

        //TODO: isProperty(entityOfAction , argument , entityList );
    }



    private void checkIfEnvironmentArgumentIsValid(List<PRDEnvProperty> envPropertiesList, String functionArgument) {
        PRDEnvProperty theProperty = envPropertiesList.stream()
                .filter(envProperty -> envProperty.getPRDName().equals(functionArgument))
                .findAny()
                .orElse(null);

        if ( theProperty == null ){
            throw new IllegalArgumentException(" the environment variable name you provided: "
                    + functionArgument+ " for environment helper function does not exist");
        }

        if (!(theProperty.getType().equals("decimal")||theProperty.getType().equals("float"))){
            throw new IllegalArgumentException(" the environment variable name you provided: "
                    + functionArgument+ " for environment helper function is not of numeric type");
        }
    }



}