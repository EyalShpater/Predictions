package execution.simulation.xml.validation;

import resources.xml.ex3.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;

public class XmlValidator {
    private final static char HELPER_FUNCTION_TOKEN = '(';
    private final static int NOT_FOUND = -1;

    private final String path;
    private final InputStream inputStream;
    private PRDWorld world;
    private List<PRDEntity> entityList;
    private List<PRDEnvProperty> envPropertiesList;

    public XmlValidator(String path) {
        this.path = path;
        this.inputStream = null;
    }

    public XmlValidator(InputStream inputStream) {
        this.path = "";
        this.inputStream = inputStream;
    }

    public void isValid() throws IllegalArgumentException, JAXBException {
        // 1) check xml exist and type of xml
        checkIfPathExist();
        checkIfXmlType();

        world = loadXmlToWorld();
        entityList = world.getPRDEntities().getPRDEntity();
        envPropertiesList = world.getPRDEnvironment().getPRDEnvProperty();

        checkGridInRange(world);

        // 2) check env-vars to have different names
        checkEnvVarsNames(world.getPRDEnvironment());


        // 3) check properties to have different names
        checkPropertiesNames(world.getPRDEntities());

        // 4) check that in action no call to an entity that doesnt exist
        areAllRulesActionsEntityNamesValid(world);


        // 5) check that in action no call to a property that doesnt exist
        checkRulesToNotContainActionWithPropertyWithNoMatchEntity(world);


        // 6) check that in (calculation \ increase \ decrease) the args are numbers only including helper functions
        checkNumericCalculationActionToIncludeNumericArgs(world);


    }


    public PRDWorld getWorld(){return this.world;}

    //11111111111111111111111

    private void checkIfPathExist(){
        if (!path.isEmpty()) { //todo:
            Path xmlpath = Paths.get(this.path);

            if (!Files.exists(xmlpath)) {
                throw new IllegalArgumentException("File path does not exist");
            }
        }
    }

    private void checkIfXmlType(){
        if (!path.isEmpty()) { //todo
            if (!path.endsWith(".xml")) {
                throw new IllegalArgumentException("File path must end with .xml .");
            }
        }
    }

    private PRDWorld loadXmlToWorld() throws JAXBException {

        File file = path.isEmpty() ? null : new File(this.path);
        JAXBContext jaxbContent = JAXBContext.newInstance(PRDWorld.class);

        Unmarshaller jaxbUnmarshaller = jaxbContent.createUnmarshaller();
        PRDWorld world = path.isEmpty() ?
                (PRDWorld) jaxbUnmarshaller.unmarshal(inputStream) :
                (PRDWorld) jaxbUnmarshaller.unmarshal(file);

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
                checkIfSecondaryEntityNameExistInEntityList(entityList, action);
            } else if (action.getType().equals("proximity")) {
                checkIfEntityNamesExistForProximityAction(entityList, action);
                checkIfSecondaryEntityNameExistInEntityList(entityList, action);
            } else {
                checkIfEntityNameExistInEntityList(entityList, action);
                checkIfSecondaryEntityNameExistInEntityList(entityList, action);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" action of type: " + action.getType() + e.getMessage());
        }
    }


    private void checkIfEntityNamesExistForReplaceAction(List<PRDEntity> entityList, PRDAction action) {
        String killEntity = action.getKill();
        String createEntity = action.getCreate();

        iterateEntityListForProximityAndReplaceAndSecondaryEntity(entityList, killEntity);
        iterateEntityListForProximityAndReplaceAndSecondaryEntity(entityList, createEntity);
    }

    private void checkIfEntityNamesExistForProximityAction(List<PRDEntity> entityList, PRDAction action) {
        String sourceEntity = action.getPRDBetween().getSourceEntity();
        String targetEntity = action.getPRDBetween().getTargetEntity();

        iterateEntityListForProximityAndReplaceAndSecondaryEntity(entityList, sourceEntity);
        iterateEntityListForProximityAndReplaceAndSecondaryEntity(entityList, targetEntity);

        List<PRDAction> actionList = action.getPRDActions().getPRDAction();
        for (PRDAction singleAction : actionList) {
            checkIfActionIsOfTypeConditionAndSendToCheckEntityExistence(entityList, singleAction);
        }
    }

    private void iterateEntityListForProximityAndReplaceAndSecondaryEntity(List<PRDEntity> entityList, String entityName) {
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
        if (prdSecondaryEntity != null) {
            String secondaryEntityName = prdSecondaryEntity.getEntity();

            try {
                iterateEntityListForProximityAndReplaceAndSecondaryEntity(entityList, secondaryEntityName);

                PRDAction.PRDSecondaryEntity.PRDSelection selection = prdSecondaryEntity.getPRDSelection();
                if (selection.getPRDCondition() != null) {
                    checkIfEntityNameExistForMultipleTypeAction(entityList, action, selection.getPRDCondition());
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(" in secondary entity" + e.getMessage());
            }
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

        if (action.getEntity() == null) {
            throw new IllegalArgumentException(" entity name was not provided where it was needed to");
        }

        for (PRDEntity entity : entityList) {
            if (entity.getName().equals(action.getEntity())) {
                isEntityNameInActionExistInEntityList = true;
            }
        }
        if (!isEntityNameInActionExistInEntityList) {
            throw new IllegalArgumentException(" the entity name :" + action.getEntity()
                    + " does not appear in the system ");
        }
    }

    //5555555555555555555

    private void checkRulesToNotContainActionWithPropertyWithNoMatchEntity(PRDWorld world){
        List<PRDRule> ruleList = world.getPRDRules().getPRDRule();
        for(PRDRule rule : ruleList){
            try{
                checkActionsToNotContainActionWithPropertyWithNoMatchEntity(rule);
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("In rule: "+rule.getName() +e.getMessage());
            }
        }
    }

    private void checkActionsToNotContainActionWithPropertyWithNoMatchEntity(PRDRule rule) {
        PRDActions actions = rule.getPRDActions();
        List<PRDAction> actionList = actions.getPRDAction();

        for (PRDAction action : actionList) {
            checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence(action);
        }
    }

    private void checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence(PRDAction action) {
        try {
            if (action.getType().equals("condition")) {
                checkIfPropertyInActionExistForEntityCondition(action);
                checkSecondaryEntityConditionProperty(action);
                checkIfPropertyInActionExistForThenAction(action);
                checkIfPropertyInActionExistForElseAction(action);
            } else {
                checkIfPropertyInActionExistForEntityNonCondition(action);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" action of type: " + action.getType() + e.getMessage());
        }

    }

    private void checkSecondaryEntityConditionProperty(PRDAction action) {
        try {
            if (action.getPRDSecondaryEntity() != null) {
                if (action.getPRDSecondaryEntity().getPRDSelection().getPRDCondition() != null) {
                    checkConditionPropertyArgs(action, action.getPRDSecondaryEntity().getPRDSelection().getPRDCondition());
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" in secondary entity " + e.getMessage());
        }

    }


    private void checkIfPropertyInActionExistForThenAction(PRDAction action) {
        try {
            PRDThen thenBlock = action.getPRDThen();
            List<PRDAction> actionListForThenBlock = thenBlock.getPRDAction();
            for (PRDAction singleAction : actionListForThenBlock) {
                checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence(singleAction);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" then block" + e.getMessage());
        }
    }

    private void checkIfPropertyInActionExistForElseAction(PRDAction action) {
        try {
            PRDElse elseBlock = action.getPRDElse();
            if (elseBlock != null) {
                List<PRDAction> actionListForElseBlock = elseBlock.getPRDAction();
                for (PRDAction singleAction : actionListForElseBlock) {
                    checkIfActionIsOfTypeConditionAndSendToCheckPropertyExistence(singleAction);
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" else block" + e.getMessage());
        }

    }

    private void checkIfPropertyInActionExistForEntityCondition(PRDAction action) {
        PRDCondition condition = action.getPRDCondition();
        try {
            if (condition.getSingularity().equals("single")) {
                checkConditionPropertyArgs(action, condition);
            } else if (condition.getSingularity().equals("multiple")) {
                checkIfMultipleConditionPropertyNameExistInEntityPropertyList(action, condition);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" " + condition.getSingularity() + e.getMessage());
        }
    }

    private void checkConditionPropertyArgs(PRDAction action, PRDCondition condition) {
        boolean isConditionPropertyExistForEntity = true;
        boolean isConditionPropertyExpressionCorrect = true;

        String isConditionPropertyExistForEntityMessage = "";
        String isConditionPropertyExpressionCorrectMessage = "";

        try {
            checkIfSimpleConditionPropertyNameExistInEntityPropertyList(condition);
        } catch (IllegalArgumentException e) {
            isConditionPropertyExistForEntity = false;
            isConditionPropertyExistForEntityMessage = e.getMessage();
        }
        try {
            invokeFunctionThatCheckArgType(action, condition.getProperty());
        } catch (IllegalArgumentException e) {
            isConditionPropertyExpressionCorrect = false;
            isConditionPropertyExpressionCorrectMessage = e.getMessage();
        }

        if (!isConditionPropertyExistForEntity && !isConditionPropertyExpressionCorrect) {
            if (isPropertyFormat(condition.getProperty())) {
                throw new IllegalArgumentException(" condition property argument is wrong " + isConditionPropertyExistForEntityMessage);
            } else {
                throw new IllegalArgumentException(" condition property argument is wrong " + isConditionPropertyExpressionCorrectMessage);
            }
        }
    }

    private boolean isPropertyFormat(String property) {

        boolean isProperty = false;
        int indexOfToken = property.indexOf('.');

        if (indexOfToken == NOT_FOUND) {
            isProperty = true;
        }
        return isProperty;
    }

    private void checkIfMultipleConditionPropertyNameExistInEntityPropertyList(PRDAction action, PRDCondition condition) {

        if (condition.getSingularity().equals("single")) {
            checkConditionPropertyArgs(action, condition);
        } else {
            List<PRDCondition> conditionList = condition.getPRDCondition();
            for (PRDCondition smallerCondition : conditionList) {
                checkIfMultipleConditionPropertyNameExistInEntityPropertyList(action, smallerCondition);
            }
        }
    }

    private void checkIfSimpleConditionPropertyNameExistInEntityPropertyList(PRDCondition condition) {
        try {
            PRDEntity entity = findEntityFromActionInEntityList(condition.getEntity());
            if (entity != null) {
                findPropertyFromActionInPropertyListOfEntityNonCalcVersion(entity, condition.getProperty());
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" in single condition with entity name: " + condition.getEntity() + " and value: " + condition.getValue() + e.getMessage());
        }

    }

    private void checkIfPropertyInActionExistForEntityNonCondition(PRDAction action) {
        PRDEntity entity = findEntityFromActionInEntityList(action.getEntity());
        if (entity != null) {
            if (!action.getType().equals("calculation")) {
                findPropertyFromActionInPropertyListOfEntityNonCalcVersion(entity, action.getProperty());
            } else {
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

    private PRDEntity findEntityFromActionInEntityList(String entityName) {
        return entityList.stream()
                .filter(entity -> entity.getName().equals(entityName))
                .findAny()
                .orElse(null);
    }


    //6666666666666666666
    private void checkNumericCalculationActionToIncludeNumericArgs(PRDWorld world) {

        PRDEnvironment env = world.getPRDEnvironment();
        List<PRDRule> ruleList = world.getPRDRules().getPRDRule();
        for(PRDRule rule : ruleList) {
            try {
                checkNumericCalculationActionToIncludeNumericArgs(rule);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("In rule: " + rule.getName() + e.getMessage());
            }

        }
    }


    private void checkNumericCalculationActionToIncludeNumericArgs(PRDRule rule) {
        PRDActions actions = rule.getPRDActions();
        List<PRDAction> actionList = actions.getPRDAction();

        for (PRDAction action : actionList) {
            checkIfActionIsOfTypeConditionAndSendToCheckIfActionToIncludeNumericArgs(action);
        }
    }

    private void checkIfActionIsOfTypeConditionAndSendToCheckIfActionToIncludeNumericArgs(PRDAction action, String... relevantEntities) {

        if (action.getType().equals("condition")) {
            checkIfArgsInActionAreNumericConditionVersion(action, relevantEntities);
        } else {
            checkIfArgsInActionAreNumericNonConditionVersion(action, relevantEntities);
        }

    }

    private void checkIfArgsInActionAreNumericConditionVersion(PRDAction action, String... relevantEntities) {
        PRDThen thenBlock = action.getPRDThen();
        PRDElse elseBloc = action.getPRDElse();
        String[] relevant = new String[2];
        relevant[0] = action.getEntity();
        if (action.getPRDSecondaryEntity() != null) {
            relevant[1] = action.getPRDSecondaryEntity().getEntity();
        }
        try {
            iterateActionListOfThenAndElseBlocksToFindIfAllArgsNumeric(thenBlock.getPRDAction(), relevant);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" then block" + e.getMessage());
        }
        if (elseBloc != null) {
            try {
                iterateActionListOfThenAndElseBlocksToFindIfAllArgsNumeric(elseBloc.getPRDAction(), relevant);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(" else block" + e.getMessage());
            }
        }
    }

    private void iterateActionListOfThenAndElseBlocksToFindIfAllArgsNumeric(List<PRDAction> actionList, String... relevantEntities) {
        actionList.forEach(action -> checkIfActionIsOfTypeConditionAndSendToCheckIfActionToIncludeNumericArgs(action, relevantEntities));
    }


    private void checkIfArgsInActionAreNumericNonConditionVersion(PRDAction action, String... relevantEntities) {

        if (action.getType().equals("increase") || action.getType().equals("decrease")) {
            checkIfArgsInActionAreNumericIncreaseDecreaseStructure(action, relevantEntities);
        } else if (action.getType().equals("calculation")) {
            checkIfArgsInActionAreNumericCalculationStructure(action, relevantEntities);
        } else if (action.getType().equals("proximity")) {
            checkIfArgsInActionAreNumericProximityStructure(action);
        }
    }

    private void checkIfArgsInActionAreNumericProximityStructure(PRDAction action, String... relevantEntities) {
        List<PRDAction> proximityActionList = action.getPRDActions().getPRDAction();
        proximityActionList.forEach(proximityAction -> checkIfArgsInActionAreNumericNonConditionVersion(proximityAction, action.getPRDBetween().getSourceEntity(), action.getPRDBetween().getTargetEntity()));
    }


    private void checkIfArgsInActionAreNumericCalculationStructure(PRDAction action, String... relevantEntities) {
        PRDMultiply multiply = action.getPRDMultiply();
        PRDDivide divide = action.getPRDDivide();
        if (multiply != null) {
            try {
                invokeFunctionThatCheckArgType(action, action.getPRDMultiply().getArg1(), relevantEntities);
                invokeFunctionThatCheckArgType(action, action.getPRDMultiply().getArg2(), relevantEntities);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(" inside multiply " + e.getMessage());
            }

        } else if ( divide != null ) {
            try {
                invokeFunctionThatCheckArgType(action, action.getPRDDivide().getArg1(), relevantEntities);
                invokeFunctionThatCheckArgType(action, action.getPRDDivide().getArg2(), relevantEntities);
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException(" inside divide " + e.getMessage());
            }
        }
    }

    private void checkIfArgsInActionAreNumericIncreaseDecreaseStructure(PRDAction action, String... relevantEntities) {
        String argument = action.getBy();
        invokeFunctionThatCheckArgType(action, argument, relevantEntities);
    }

    private void invokeFunctionThatCheckArgType(PRDAction action, String argument, String... relevantEntities) {
        if (!isHelperFunction(action, argument, relevantEntities)) {
            isProperty(action.getEntity(), argument, relevantEntities);
        }
    }


    private void isProperty(String entityOfAction, String argument, String... relevantEntities) {
        PRDEntity entity = findEntityFromActionInEntityList(entityOfAction);
        List<PRDProperty> propertyList = entity.getPRDProperties().getPRDProperty();

        PRDProperty theProperty = propertyList.stream()
                .filter(property -> property.getPRDName().equals(argument))
                .findAny()
                .orElse(null);

        if (theProperty == null) {//also covers numeric value ;
            checkIfArgumentIsNumeric(argument);
        }else if (!(theProperty.getType().equals("decimal")||theProperty.getType().equals("float"))){
            throw new IllegalArgumentException(" the property name you provided: "
                    + argument+ " for action is not of numeric type");
        }

    }

    private boolean isHelperFunction(PRDAction action, String argument, String... relevantEntities) {

        int indexOfToken = argument.indexOf(HELPER_FUNCTION_TOKEN);
        String functionName = null;
        String functionArgument = null;
        boolean isHelperFunction = false;

        if (indexOfToken != NOT_FOUND) {
            functionName = argument.substring(0, indexOfToken);
            int closingParenthesisIndex = argument.lastIndexOf(")");
            if (closingParenthesisIndex != NOT_FOUND && closingParenthesisIndex > indexOfToken) {
                functionArgument = argument.substring(indexOfToken + 1, closingParenthesisIndex).trim(); // Trim to remove any leading/trailing spaces
            }
            isHelperFunctionExist(action, functionName, functionArgument, relevantEntities);
            isHelperFunction = true;
        }
        return isHelperFunction;
    }

    private void isHelperFunctionExist(PRDAction action, String functionName, String functionArgument, String... relevantEntities) {
        if (functionName.equals("environment")) {
            checkIfEnvironmentArgumentIsValid(envPropertiesList, functionArgument, relevantEntities);
        } else if (functionName.equals("random")) {
            checkIfArgumentIsNumericWrapFunc(functionArgument, relevantEntities);
        } else if (functionName.equals("evaluate")) {
            checkIfArgumentIsValidFormatTicksEvaluatePercentWrapFunc(action, functionArgument, "evaluate", relevantEntities);
        } else if (functionName.equals("ticks")) {
            checkIfArgumentIsValidFormatTicksEvaluatePercentWrapFunc(action, functionArgument, "ticks", relevantEntities);
        } else if (functionName.equals("percent")) {
            checkIfArgumentIsValidFormatTicksEvaluatePercentWrapFunc(action, functionArgument, "percent", relevantEntities);
        } else {
            throw new IllegalArgumentException(" function name provided " + functionName + " does not exist");
        }
    }

    private void checkIfArgumentIsValidFormatTicksEvaluatePercentWrapFunc(PRDAction action, String functionArgument, String funcName, String... relevantEntities) {
        try {
            if (funcName.equals("percent")) {
                checkIfArgumentIsValidFormatPercent(action, functionArgument, relevantEntities);
            } else {
                checkIfArgumentIsValidFormatTickAndEvaluate(action, functionArgument, funcName, relevantEntities);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" in " + funcName + " function " + e.getMessage());
        }
    }

    private void checkIfArgumentIsValidFormatPercent(PRDAction action, String functionArgument, String... relevantEntities) {
        int indexOfToken = functionArgument.indexOf(',');
        String expression1;
        String expression2;

        if (indexOfToken != NOT_FOUND) {
            expression1 = functionArgument.substring(0, indexOfToken);
            expression2 = functionArgument.substring(indexOfToken + 1, functionArgument.length());
            invokeFunctionThatCheckArgType(action, expression1, relevantEntities);
            invokeFunctionThatCheckArgType(action, expression2, relevantEntities);
        } else {
            throw new IllegalArgumentException(" invalid arg format " + functionArgument);
        }
    }

    private void checkIfArgumentIsValidFormatTickAndEvaluate(PRDAction action, String functionArgument, String funcName, String... relevantEntities) {
        int indexOfToken = functionArgument.indexOf('.');
        String entityFromArgName = null;
        String propertyName = null;

        if (indexOfToken != NOT_FOUND) {
            entityFromArgName = functionArgument.substring(0, indexOfToken);
            propertyName = functionArgument.substring(indexOfToken + 1, functionArgument.length());
            checkIfPropertyExistInEntityTickAndEvaluate(action, entityFromArgName, propertyName, funcName, relevantEntities);
        } else {
            throw new IllegalArgumentException(" invalid arg format " + functionArgument);
        }
    }

    private void checkIfPropertyExistInEntityTickAndEvaluate(PRDAction action, String entityFromArgName, String propertyName, String funcName, String... relevantEntities) {
        if (relevantEntities.length == 0) {
            checkIfPropertyExistInEntityTickAndEvaluateForNonProximity(action, entityFromArgName, funcName, propertyName);
        } else {
            checkIfPropertyExistInEntityTickAndEvaluateForProximity(action, entityFromArgName, propertyName, funcName, relevantEntities);
        }
    }

    private void checkIfPropertyExistInEntityTickAndEvaluateForProximity(PRDAction action, String entityFromArgName, String propertyName, String funcName, String... relevantEntities) {
        boolean isPropertyInEntity = false;
        PRDEntity relevantEntity1 = findEntityFromActionInEntityList(relevantEntities[0]);
        PRDEntity relevantEntity2 = findEntityFromActionInEntityList(relevantEntities[1]);

        if (!entityFromArgName.equals(relevantEntities[0])) {
            if (!entityFromArgName.equals(relevantEntities[1])) {
                throw new IllegalArgumentException(" entity name provided: " + entityFromArgName + " is not in context for action");
            } else {
                if (!funcName.equals("ticks")) {
                    checkIfNumericProperty(relevantEntities[1], propertyName);
                }
                isPropertyInEntity = isPropertyInEntity || checkIfPropertyExistForEntity(relevantEntity2, propertyName);
            }
        } else {
            isPropertyInEntity = isPropertyInEntity || checkIfPropertyExistForEntity(relevantEntity1, propertyName);
        }
        if (!isPropertyInEntity) {
            throw new IllegalArgumentException(" the property: " + propertyName + " does not exist for entity " + entityFromArgName);
        }
        if (!funcName.equals("ticks")) {
            checkIfNumericProperty(relevantEntities[0], propertyName);
        }
    }

    private void checkIfPropertyExistInEntityTickAndEvaluateForNonProximity(PRDAction action, String entityFromArgName, String funcName, String propertyName) {

        boolean isPropertyInEntity = false;
        PRDEntity prdEntity = findEntityFromActionInEntityList(action.getEntity());

        if (!entityFromArgName.equals(action.getEntity())) {
            PRDAction.PRDSecondaryEntity secondaryEntity = action.getPRDSecondaryEntity();
            if (secondaryEntity != null) {
                if (!entityFromArgName.equals(secondaryEntity.getEntity())) {
                    throw new IllegalArgumentException(" entity name provided: " + entityFromArgName + " does not exist");
                } else {
                    if (!funcName.equals("ticks")) {
                        checkIfNumericProperty(secondaryEntity.getEntity(), propertyName);
                    }
                    isPropertyInEntity = isPropertyInEntity || checkIfPropertyExistInEntityTickAndEvaluateForSecondaryEntity(action, entityFromArgName, propertyName);
                }
            } else {
                throw new IllegalArgumentException(" entity name provided: " + entityFromArgName + " is not in context for action");
            }
        } else {
            isPropertyInEntity = isPropertyInEntity || checkIfPropertyExistForEntity(prdEntity, propertyName);
        }
        if (!isPropertyInEntity) {
            throw new IllegalArgumentException(" the property: " + propertyName + " does not exist for entity " + entityFromArgName);
        }
        if (!funcName.equals("ticks")) {
            checkIfNumericProperty(action.getEntity(), propertyName);
        }
    }

    private void checkIfNumericProperty(String entity, String propertyName) {
        PRDEntity prdEntity = findEntityFromActionInEntityList(entity);
        List<PRDProperty> propertyList = prdEntity.getPRDProperties().getPRDProperty();

        PRDProperty theProperty = propertyList.stream()
                .filter(property -> property.getPRDName().equals(propertyName))
                .findAny()
                .orElse(null);

        if (theProperty == null) {
            throw new IllegalArgumentException(" the property: " + propertyName + " does not exist for entity ");
        } else {
            if (!theProperty.getType().equals("float")) {
                throw new IllegalArgumentException(" the property: " + propertyName + " is not numeric ");
            }
        }

    }

    private boolean checkIfPropertyExistInEntityTickAndEvaluateForSecondaryEntity(PRDAction action, String entityFromArgName, String propertyName) {
        boolean isPropertyInEntity = false;
        PRDAction.PRDSecondaryEntity secondaryEntity = action.getPRDSecondaryEntity();
        String secondaryEntityName = secondaryEntity.getEntity();
        if (secondaryEntityName.equals(entityFromArgName)) {
            PRDEntity prdSecondaryEntity = findEntityFromActionInEntityList(secondaryEntityName);
            isPropertyInEntity = isPropertyInEntity || checkIfPropertyExistForEntity(prdSecondaryEntity, propertyName);
        }
        return isPropertyInEntity;
    }

    private boolean checkIfPropertyExistForEntity(PRDEntity prdEntity, String propertyName) {
        boolean isPropertyInEntity = false;

        if (propertyName != null) {//might be for kill
            PRDProperties properties = prdEntity.getPRDProperties();
            ;
            List<PRDProperty> propertyList = properties.getPRDProperty();

            PRDProperty theProperty = propertyList.stream()
                    .filter(property -> property.getPRDName().equals(propertyName))
                    .findAny()
                    .orElse(null);

            if (theProperty != null) {
                isPropertyInEntity = true;
            }

        }
        return isPropertyInEntity;
    }

    private void checkIfArgumentIsNumericWrapFunc(String functionArgument, String... relevantEntities) {
        try {
            checkIfArgumentIsNumeric(functionArgument);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" for random helper function" + e.getMessage());
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


    private void checkIfEnvironmentArgumentIsValid(List<PRDEnvProperty> envPropertiesList, String functionArgument, String... relevantEntities) {
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