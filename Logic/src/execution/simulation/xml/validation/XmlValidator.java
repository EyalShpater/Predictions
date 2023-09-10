package execution.simulation.xml.validation;

import definition.world.api.World;
import resources.xml.ex2.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.*;
import java.util.List;

public class XmlValidator {
    private final String path;

    private PRDWorld world;

    private final char HELPER_FUNCTION_TOKEN = '(';
    private final int NOT_FOUND = -1;
    public XmlValidator(String path) {
        this.path = path;
    }

    public void isValid() throws IllegalArgumentException, JAXBException {
        // 1) check xml exist and type of xml
        checkIfPathExist();
        checkIfXmlType();

        world = loadXmlToWorld();

        // 2) check env-vars to have different names
        checkEnvVarsNames(world.getPRDEnvironment());


        // 3) check properties to have different names
        checkPropertiesNames(world.getPRDEntities());

        // 4) check that in action no call to an entity that doesnt exist
        areAllRulesActionsEntityNamesValid(world);


        // 5) check that in action no call to a property that doesnt exist
        // TODO: implement condition to get helper functions
        checkRulesToNotContainActionWithPropertyWithNoMatchEntity(world);


        // 6) check that in (calculation \ increase \ decrease) the args are numbers only including helper functions
        checkNumericCalculationActionToIncludeNumericArgs(world);

        //7) Check grid to be in range 10 to 100
        checkGridInRange(world);

    }


    public PRDWorld getWorld(){return this.world;}

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

    private PRDWorld loadXmlToWorld() throws JAXBException {

        File file = new File(this.path);
        JAXBContext jaxbContent = JAXBContext.newInstance(PRDWorld.class);

        Unmarshaller jaxbUnmarshaller = jaxbContent.createUnmarshaller();
        PRDWorld world = (PRDWorld) jaxbUnmarshaller.unmarshal(file);

        return world;
    }


    //222222222222222222222222
    private void checkEnvVarsNames(PRDEnvironment environment) {

        List<PRDEnvProperty> EnvPropertyList = environment.getPRDEnvProperty();
        for (int i = 0; i < EnvPropertyList.size() - 1; i++) {
            for (int j = i + 1; j < EnvPropertyList.size(); j++) {
                try {
                    checkVarsNamesToBeDifferent(EnvPropertyList.get(i).getPRDName(), EnvPropertyList.get(j).getPRDName());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("The environment variable: " + e.getMessage());
                }
            }
            try{
                checkVarsNamesToNotHaveSpaces(EnvPropertyList.get(i).getPRDName());
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("The environment variable: "+e.getMessage());
            }
        }
    }

    private void checkVarsNamesToBeDifferent(String name1 ,String name2 ){

        if (name1.equals(name2)) {
            throw new IllegalArgumentException(name1+" appears more than one time");
        }
    }

    private void checkVarsNamesToNotHaveSpaces(String envVarName){
        if (envVarName.contains(" ")) {
            throw new IllegalArgumentException(envVarName +" should not contain spaces.");
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
                    try{
                        checkVarsNamesToBeDifferent( propertyList.get(i).getPRDName() , propertyList.get(j).getPRDName() );
                    }catch (IllegalArgumentException e){
                        throw new IllegalArgumentException("The property: "+e.getMessage());
                    }
                }
                try{
                    checkVarsNamesToNotHaveSpaces(propertyList.get(i).getPRDName());
                }catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("The property: "+e.getMessage());
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
        try{
            if (action.getType().equals("condition")) {
                checkIfEntityNameExistInEntityList(entityList, action);
                checkIfSecondaryEntityNameExistInEntityList(entityList, action);
                checkIfEntityNameExistInConditionAction(entityList, action);
                checkIfEntityNameExistForThenAction(entityList, action);
                checkIfEntityNameExistForElseAction(entityList, action);

            } else if (action.getType().equals("replace")) {
                checkIfEntityNamesExistForReplaceAction(entityList, action);
            } else if (action.getType().equals("proximity")) {
                checkIfEntityNamesExistForProximityAction(entityList, action);
            } else {
                checkIfEntityNameExistInEntityList(entityList, action);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" action of type: " + action.getType() + e.getMessage());
        }
    }


    private void checkIfEntityNamesExistForReplaceAction(List<PRDEntity> entityList, PRDAction action) {
        String killEntity = action.getKill();
        String createEntity = action.getCreate();

        iterateEntityListForProximityAndReplace(entityList, killEntity);
        iterateEntityListForProximityAndReplace(entityList, createEntity);
    }

    private void checkIfEntityNamesExistForProximityAction(List<PRDEntity> entityList, PRDAction action) {
        String sourceEntity = action.getPRDBetween().getSourceEntity();
        String targetEntity = action.getPRDBetween().getTargetEntity();

        iterateEntityListForProximityAndReplace(entityList, sourceEntity);
        iterateEntityListForProximityAndReplace(entityList, targetEntity);

        List<PRDAction> actionList = action.getPRDActions().getPRDAction();
        for (PRDAction singleAction : actionList) {
            checkIfActionIsOfTypeConditionAndSendToCheckEntityExistence(entityList, singleAction);
        }
    }

    private void iterateEntityListForProximityAndReplace(List<PRDEntity> entityList, String entityName) {
        boolean isEntityNameInActionExistInEntityList = false;
        for (PRDEntity entity : entityList) {
            if (entity.getName().equals(entityName)) {
                isEntityNameInActionExistInEntityList = true;
            }
        }
        if (!isEntityNameInActionExistInEntityList) {
            throw new IllegalArgumentException(" the entity name :" + entityName
                    + " does not appear in the system ");
        }
    }


    private void checkIfEntityNameExistForThenAction(List<PRDEntity> entityList, PRDAction action) {
        try {
            PRDThen thenBlock = action.getPRDThen();
            List<PRDAction> actionListForThenBlock = thenBlock.getPRDAction();
            for (PRDAction singleAction : actionListForThenBlock) {
                checkIfActionIsOfTypeConditionAndSendToCheckEntityExistence(entityList, singleAction);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" then block"+ e.getMessage());
        }

    }

    private void checkIfEntityNameExistForElseAction(List<PRDEntity>entityList , PRDAction action){
        try {
            PRDElse elseBlock = action.getPRDElse();
            if ( elseBlock != null ) {
                List<PRDAction> actionListForElseBlock = elseBlock.getPRDAction();
                for (PRDAction singleAction : actionListForElseBlock) {
                    checkIfActionIsOfTypeConditionAndSendToCheckEntityExistence(entityList, singleAction);
                }
            }
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(" else block"+ e.getMessage());
        }
    }



    private void checkIfEntityNameExistInConditionAction(List<PRDEntity>entityList , PRDAction action ){
        PRDCondition condition = action.getPRDCondition();
        try{
            if (condition.getSingularity().equals("single")) {
                checkIfSimpleConditionEntityNameExist(entityList, action, condition);
            } else if (condition.getSingularity().equals("multiple")) {
                checkIfEntityNameExistForMultipleTypeAction(entityList, action, action.getPRDCondition());
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" " + condition.getSingularity() + e.getMessage());
        }

    }

    private void checkIfSecondaryEntityNameExistInEntityList(List<PRDEntity> entityList, PRDAction action) {
        PRDAction.PRDSecondaryEntity prdSecondaryEntity = action.getPRDSecondaryEntity();
        String secondaryEntityName = prdSecondaryEntity.getEntity();

        iterateEntityListForProximityAndReplace(entityList, secondaryEntityName);

        PRDAction.PRDSecondaryEntity.PRDSelection selection = prdSecondaryEntity.getPRDSelection();
        if (selection.getPRDCondition() != null) {
            checkIfEntityNameExistForMultipleTypeAction(entityList, action, selection.getPRDCondition());
        }
    }

    private void checkIfEntityNameExistForMultipleTypeAction(List<PRDEntity> entityList, PRDAction action, PRDCondition condition) {

        if (condition.getSingularity().equals("single")) {
            checkIfSimpleConditionEntityNameExist(entityList, action, condition);
        } else {
            List<PRDCondition> conditionList = condition.getPRDCondition();
            for (PRDCondition smallerCondition : conditionList) {
                checkIfEntityNameExistForMultipleTypeAction(entityList, action, smallerCondition);
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
            throw new IllegalArgumentException(" single condition with property: " +condition.getProperty() + " and value: " + condition.getValue()+
                    " the entity name "+ condition.getEntity() +" does not appear in the system ");
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
        try{
            if(action.getType().equals("condition")){
                checkIfPropertyInActionExistForEntityCondition( entityList ,  action );
                checkIfPropertyInActionExistForThenAction( entityList ,  action );
                checkIfPropertyInActionExistForElseAction( entityList ,  action );
            }else{
                checkIfPropertyInActionExistForEntityNonCondition( entityList , action );
            }
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(" action of type: "+action.getType() + e.getMessage());
        }

    }



    private void checkIfPropertyInActionExistForThenAction(List<PRDEntity> entityList, PRDAction action) {
        try{
            PRDThen thenBlock = action.getPRDThen();
            List<PRDAction> actionListForThenBlock = thenBlock.getPRDAction();
            for (PRDAction singleAction :actionListForThenBlock ){
                checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence( entityList ,singleAction );
            }
        }catch (IllegalArgumentException e ){
            throw new IllegalArgumentException(" then block"+ e.getMessage());
        }
    }

    private void checkIfPropertyInActionExistForElseAction(List<PRDEntity> entityList, PRDAction action) {
        try{
            PRDElse elseBlock = action.getPRDElse();
            if ( elseBlock != null ){
                List<PRDAction> actionListForElseBlock = elseBlock.getPRDAction();
                for (PRDAction singleAction :actionListForElseBlock ){
                    checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence( entityList ,singleAction );
                }
            }
        }catch (IllegalArgumentException e ){
            throw new IllegalArgumentException(" else block"+ e.getMessage());
        }

    }

    private void checkIfPropertyInActionExistForEntityCondition(List<PRDEntity> entityList, PRDAction action) {
        PRDCondition condition = action.getPRDCondition();
        try{
            if (condition.getSingularity().equals("single")){
                checkIfSimpleConditionPropertyNameExistInEntityPropertyList(entityList , condition);
            } else if (condition.getSingularity().equals("multiple")) {
                checkIfMultipleConditionPropertyNameExistInEntityPropertyList(entityList , condition);
            }
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(" "+condition.getSingularity() + e.getMessage());
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
        try{
            PRDEntity entity = findEntityFromActionInEntityList( entityList , condition.getEntity() );
            if ( entity != null ){
                findPropertyFromActionInPropertyListOfEntityNonCalcVersion( entity , condition.getProperty());
            }
        }catch (IllegalArgumentException e ){
            throw new IllegalArgumentException(" in single condition with entity name: " +condition.getEntity()+ " and value: " + condition.getValue() + e.getMessage());
        }

    }

    private void checkIfPropertyInActionExistForEntityNonCondition(List<PRDEntity> entityList , PRDAction action){
        PRDEntity entity = findEntityFromActionInEntityList( entityList , action.getEntity() );
        if ( entity != null ){
            if (! action.getType().equals("calculation")) {
                findPropertyFromActionInPropertyListOfEntityNonCalcVersion(entity, action.getProperty());
            }
            else{
                findPropertyFromActionInPropertyListOfEntityCalcVersion(entity, action.getResultProp());
            }
        }
    }


    private void findPropertyFromActionInPropertyListOfEntityCalcVersion(PRDEntity entity, String resultProp) {
        try{
            findPropertyFromActionInPropertyListOfEntityNonCalcVersion( entity, resultProp);
        }catch ( IllegalArgumentException e ){
            throw new IllegalArgumentException(" in calculation" + e.getMessage());
        }
    }

    private void findPropertyFromActionInPropertyListOfEntityNonCalcVersion(PRDEntity entity, String propertyName) {
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

        PRDEnvironment env = world.getPRDEnvironment();
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
            checkIfArgsInActionAreNumericConditionVersion(envPropertiesList , action , entityList);
        }else{
            checkIfArgsInActionAreNumericNonConditionVersion(envPropertiesList , action , entityList);
        }

    }

    private void checkIfArgsInActionAreNumericConditionVersion(List<PRDEnvProperty> envPropertiesList, PRDAction action, List<PRDEntity> entityList) {
        PRDThen thenBlock = action.getPRDThen();
        PRDElse elseBloc = action.getPRDElse();
        try {
            iterateActionListOfThenAndElseBlocksToFindIfAllArgsNumeric(envPropertiesList , thenBlock.getPRDAction() , entityList);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(" then block"+ e.getMessage() );
        }
        if ( elseBloc != null ){
            try {
                iterateActionListOfThenAndElseBlocksToFindIfAllArgsNumeric(envPropertiesList , elseBloc.getPRDAction() , entityList);
            }
            catch (IllegalArgumentException e){
                throw new IllegalArgumentException(" else block"+ e.getMessage() );
            }
        }
    }

    private void iterateActionListOfThenAndElseBlocksToFindIfAllArgsNumeric(List<PRDEnvProperty> envPropertiesList, List<PRDAction> actionList, List<PRDEntity> entityList) {
        actionList.forEach( action -> checkIfActionIsOfTypeConditionAndSendToCheckIfActionToIncludeNumericArgs(envPropertiesList , action , entityList) );
    }


    private void checkIfArgsInActionAreNumericNonConditionVersion(List<PRDEnvProperty> envPropertiesList, PRDAction action , List<PRDEntity> entityList) {

        if(action.getType().equals("increase") || action.getType().equals("decrease")){
            checkIfArgsInActionAreNumericIncreaseDecreaseStructure( envPropertiesList, action , entityList);
        }else if (action.getType().equals("calculation")){
            checkIfArgsInActionAreNumericCalculationStructure( envPropertiesList , action , entityList  );
        }
    }



    private void checkIfArgsInActionAreNumericCalculationStructure(List<PRDEnvProperty> envPropertiesList, PRDAction action , List<PRDEntity> entityList) {
        PRDMultiply multiply = action.getPRDMultiply();
        PRDDivide divide = action.getPRDDivide();
        if ( multiply != null ){
            try{
                invokeFunctionThatCheckArgType( envPropertiesList , entityList , action.getEntity() , action.getPRDMultiply().getArg1() );
                invokeFunctionThatCheckArgType( envPropertiesList , entityList , action.getEntity() , action.getPRDMultiply().getArg2() );
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException(" inside multiply " + e.getMessage());
            }

        } else if ( divide != null ) {
            try {
                invokeFunctionThatCheckArgType(envPropertiesList, entityList, action.getEntity(), action.getPRDDivide().getArg1());
                invokeFunctionThatCheckArgType(envPropertiesList, entityList, action.getEntity(), action.getPRDDivide().getArg2());
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException(" inside divide " + e.getMessage());
            }
        }
    }

    private void checkIfArgsInActionAreNumericIncreaseDecreaseStructure(List<PRDEnvProperty> envPropertiesList, PRDAction action , List<PRDEntity> entityList) {
        String argument = action.getBy();
        String entityOfAction = action.getEntity();
        invokeFunctionThatCheckArgType( envPropertiesList , entityList , entityOfAction , argument );
    }

    private void invokeFunctionThatCheckArgType(List<PRDEnvProperty> envPropertiesList, List<PRDEntity> entityList , String entityOfAction, String argument){
        if(!isHelperFunction(envPropertiesList , argument)){
            isProperty(entityOfAction , argument , entityList );
        }
    }


    private void isProperty(String entityOfAction , String argument, List<PRDEntity> entityList) {
        PRDEntity entity = findEntityFromActionInEntityList(entityList , entityOfAction);
        List<PRDProperty> propertyList = entity.getPRDProperties().getPRDProperty();

        PRDProperty theProperty = propertyList.stream()
                .filter(property -> property.getPRDName().equals(argument))
                .findAny()
                .orElse(null);

        if ( theProperty == null ){//also covers numeric value ;
            checkIfArgumentIsNumeric(argument);
        }else if (!(theProperty.getType().equals("decimal")||theProperty.getType().equals("float"))){
            throw new IllegalArgumentException(" the property name you provided: "
                    + argument+ " for action is not of numeric type");
        }

    }

    private boolean isHelperFunction(List<PRDEnvProperty> envPropertiesList, String argument) {

        int indexOfToken = argument.indexOf(HELPER_FUNCTION_TOKEN);
        String functionName = null;
        String functionArgument = null;
        boolean isHelperFunction = false;

        if (indexOfToken != NOT_FOUND) {
            functionName = argument.substring(0, indexOfToken);
            int closingParenthesisIndex = argument.indexOf(")");
            if (closingParenthesisIndex != NOT_FOUND && closingParenthesisIndex > indexOfToken) {
                functionArgument = argument.substring(indexOfToken + 1, closingParenthesisIndex).trim(); // Trim to remove any leading/trailing spaces
            }
            isHelperFunctionExist(envPropertiesList , functionName ,functionArgument );
            isHelperFunction = true;
        }
        return isHelperFunction;
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
            throw new IllegalArgumentException(" function name provided " +functionName+ " does not exist");
        }
    }

    private void checkIfArgumentIsNumeric(String functionArgument) {
        if (functionArgument == null || functionArgument.isEmpty()) {
            throw new IllegalArgumentException(" the argument provided is empty ");
        }
        double value;
        try {
            value = Double.parseDouble(functionArgument);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(" the argument "+ functionArgument +" is not an integer number " );
        }
    }



    private void checkIfEnvironmentArgumentIsValid(List<PRDEnvProperty> envPropertiesList, String functionArgument) {
        PRDEnvProperty theProperty = envPropertiesList.stream()
                .filter(envProperty -> envProperty.getPRDName().equals(functionArgument))
                .findAny()
                .orElse(null);

        if (theProperty == null) {
            throw new IllegalArgumentException(" the environment variable name you provided: "
                    + functionArgument + " for environment helper function does not exist");
        }

        if (!(theProperty.getType().equals("decimal") || theProperty.getType().equals("float"))) {
            throw new IllegalArgumentException(" the environment variable name you provided: "
                    + functionArgument + " for environment helper function is not of numeric type");
        }
    }

    //777777777777777777777
    private void checkGridInRange(PRDWorld world) {
        PRDWorld.PRDGrid grid = world.getPRDGrid();

        int rows = grid.getRows();
        int cols = grid.getColumns();

        if (rows > 100 || rows < 10 || cols > 100 || cols < 10) {
            throw new IllegalArgumentException(" Grid parameters must be between 10 to 100");
        }
    }
}