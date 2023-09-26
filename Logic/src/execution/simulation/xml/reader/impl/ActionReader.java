package execution.simulation.xml.reader.impl;

import action.api.Action;
import action.impl.*;
import action.impl.condition.Condition;
import action.impl.condition.impl.ConditionImpl;
import action.impl.condition.impl.multiple.And;
import action.impl.condition.impl.multiple.MultipleCondition;
import action.impl.condition.impl.multiple.Or;
import action.impl.condition.impl.single.*;
import action.second.entity.SecondaryEntity;
import action.second.entity.impl.SecondaryEntityImpl;
import definition.entity.api.EntityDefinition;
import definition.world.api.World;
import resources.xml.ex2.generated.PRDAction;
import resources.xml.ex2.generated.PRDCondition;
import resources.xml.ex2.generated.PRDElse;
import resources.xml.ex2.generated.PRDThen;

import java.util.ArrayList;
import java.util.List;


public class ActionReader {

    World world;

    public Action read(PRDAction prdAction, EntityDefinition entityOfAction, World world) {
        this.world = world;
        return readAction(prdAction, entityOfAction);
    }

    private Action readAction(PRDAction prdAction, EntityDefinition entityOfAction) {

        Action action = null;
        switch (prdAction.getType()) {
            case "increase":
                action = readPRDActionIncreaseTypeAction(prdAction, entityOfAction);
                break;
            case "decrease":
                action = readPRDActionDecreaseTypeAction(prdAction, entityOfAction);
                break;
            case "kill":
                action = readPRDActionKillTypeAction(prdAction, entityOfAction);
                break;
            case "set":
                action = readPRDActionSetTypeAction(prdAction, entityOfAction);
                break;
            case "calculation":
                action = readPRDActionCalculationTypeAction(prdAction, entityOfAction);
                break;
            case "condition":
                action = readPRDActionConditionTypeAction(prdAction, entityOfAction);
                break;
            case "proximity":
                action = readPRDActionProximityTypeAction(prdAction, entityOfAction);
                break;
            case "replace":
                action = readPRDActionReplaceTypeAction(prdAction);
                break;
            default:
                throw new IllegalArgumentException("Type of action is not valid");
        }
        return action;
    }

    private Action readPRDActionProximityTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {

        EntityDefinition sourceEntity = world.getEntityByName(prdAction.getPRDBetween().getSourceEntity());
        EntityDefinition targetEntity = world.getEntityByName(prdAction.getPRDBetween().getTargetEntity());

        Proximity proximity = new Proximity(sourceEntity, targetEntity, prdAction.getPRDEnvDepth().getOf());
        List<PRDAction> actionList = prdAction.getPRDActions().getPRDAction();

        actionList.forEach(action -> proximity.addAction(readAction(action, world.getEntityByName(action.getEntity()))));

        return proximity;
    }

    private Action readPRDActionConditionTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        PRDCondition condition = prdAction.getPRDCondition();
        if (condition.getSingularity().equals("single")) {
            return readPRDActionConditionSingleTypeAction(prdAction, entityOfAction);
        } else if (condition.getSingularity().equals("multiple")) {
            return readPRDActionConditionMultipleTypeAction(prdAction, entityOfAction);
        }
        return null;
    }


    private Action readPRDActionConditionMultipleTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        PRDCondition condition = prdAction.getPRDCondition();
        ConditionImpl newActionCondition;

        MultipleCondition multipleCondition = null;
        if (condition.getLogical().equals("or")) {
            multipleCondition = new Or();
            addSmallerConditionToMultipleConditions(condition, multipleCondition, entityOfAction);
        } else if (condition.getLogical().equals("and")) {
            multipleCondition = new And();
            addSmallerConditionToMultipleConditions(condition, multipleCondition, entityOfAction);
        }

        PRDThen thenBlock = prdAction.getPRDThen();
        PRDElse elseBloc = prdAction.getPRDElse();

        List<Action> thenList = getThenListFromPRDThen(world, thenBlock, entityOfAction);
        List<Action> elseList = getElseListFromPRDElse(world, elseBloc, entityOfAction);

        if (isSecondaryEntityExist(prdAction)) {
            newActionCondition = new ConditionImpl(multipleCondition, entityOfAction, secondaryEntityReader(prdAction));
        } else {
            newActionCondition = new ConditionImpl(multipleCondition, entityOfAction);
        }

        if (thenList != null) {
            newActionCondition.addThanList(thenList);
        }
        if (elseList != null) {
            newActionCondition.addNotTrueList(elseList);
        }
        return newActionCondition;
    }


    private void addSmallerConditionToMultipleConditions(PRDCondition condition , MultipleCondition multipleCondition , EntityDefinition entityOfAction){
        List<PRDCondition> conditionList = condition.getPRDCondition();
        for ( PRDCondition smallerCondition : conditionList){
            multipleCondition.addCondition(createMultipleCondition(smallerCondition ,entityOfAction ));
        }
    }

    private Condition createMultipleCondition(PRDCondition condition , EntityDefinition entityOfAction){
        if (condition.getSingularity().equals("single")){
            return createSingleCondition(condition);
        }else{
            MultipleCondition multipleCondition = null;
            if(condition.getLogical().equals("or")){
                 multipleCondition = new Or();
            }else if (condition.getLogical().equals("and")) {
                multipleCondition = new And();
            }
            List<PRDCondition> conditionList = condition.getPRDCondition();
            for ( PRDCondition smallerCondition : conditionList ){
                multipleCondition.addCondition(createMultipleCondition(smallerCondition , entityOfAction));
            }
            return multipleCondition;
        }
    }

    private SingleCondition createSingleCondition(PRDCondition condition){
        switch (condition.getOperator()) {
            case "bt":
                return new BiggerThan(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity()));
            case "=":
                return new Equal(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity()));
            case "lt":
                return new LowerThan(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity()));
            case "!=":
                return new NotEqual(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity()));
        }
        return null;
    }


    private Action readPRDActionConditionSingleTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        PRDCondition condition = prdAction.getPRDCondition();
        Action action = null;
        switch (condition.getOperator()) {
            case "bt":
                action = createActionFromSingleTypeAction(prdAction, new BiggerThan(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity())), entityOfAction, "bt");
                break;
            case "=":
                action = createActionFromSingleTypeAction(prdAction, new Equal(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity())), entityOfAction, "=");
                break;
            case "lt":
                action = createActionFromSingleTypeAction(prdAction, new LowerThan(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity())), entityOfAction, "lt");
                break;
            case "!=":
                action = createActionFromSingleTypeAction(prdAction, new NotEqual(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity())), entityOfAction, "!=");
                break;
        }
        return action;
    }

    private Action createActionFromSingleTypeAction(PRDAction prdAction, SingleCondition singleCondition, EntityDefinition entityOfAction, String logical) {
        PRDCondition condition = prdAction.getPRDCondition();
        ConditionImpl newActionCondition;

        PRDThen thenBlock = prdAction.getPRDThen();
        PRDElse elseBloc = prdAction.getPRDElse();

        List<Action> thenList = getThenListFromPRDThen(world, thenBlock, entityOfAction);
        List<Action> elseList = getElseListFromPRDElse(world, elseBloc, entityOfAction);

//        MultipleCondition multipleRepresentingASingle = new And();
//        multipleRepresentingASingle.addCondition(singleCondition);

        if (isSecondaryEntityExist(prdAction)) {

            newActionCondition = new ConditionImpl(singleCondition, entityOfAction, secondaryEntityReader(prdAction));
        } else {
            newActionCondition = new ConditionImpl(singleCondition, entityOfAction);
        }

        if (thenList != null) {
            newActionCondition.addThanList(thenList);
        }
        if (elseList != null) {
            newActionCondition.addNotTrueList(elseList);
        }
        return newActionCondition;
    }

    private List<Action> getElseListFromPRDElse(World world , PRDElse elseBloc , EntityDefinition entityOfAction) {
        List<Action> elseList = null;
        if (elseBloc != null ) {
            elseList = new ArrayList<>();
            List<PRDAction> prdActionList = elseBloc.getPRDAction();
            for (PRDAction action: prdActionList) {
                elseList.add(readAction(action, world.getEntityByName(action.getEntity())));
            }
        }
        return elseList;
    }

    private List<Action> getThenListFromPRDThen(World world ,PRDThen thenBlock , EntityDefinition entityOfAction ) {
        List<Action> thenList = null;
        if (thenBlock != null ) {
            thenList = new ArrayList<>();
            List<PRDAction> prdActionList = thenBlock.getPRDAction();
            for (PRDAction action : prdActionList) {
                thenList.add(readAction(action, world.getEntityByName(action.getEntity())));
            }
        }
        return thenList;
    }

    private Action readPRDActionCalculationTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        if (prdAction.getPRDMultiply() != null) {
            return readPRDActionMultiplyTypeAction(prdAction, entityOfAction);
        } else {
            return readPRDActionDivideTypeAction(prdAction, entityOfAction);
        }
    }

    private Action readPRDActionMultiplyTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        if (isSecondaryEntityExist(prdAction)) {
            return new MultiplyAction(entityOfAction, secondaryEntityReader(prdAction), prdAction.getResultProp(), prdAction.getPRDMultiply().getArg1(), prdAction.getPRDMultiply().getArg2());
        } else {
            return new MultiplyAction(entityOfAction, prdAction.getResultProp(), prdAction.getPRDMultiply().getArg1(), prdAction.getPRDMultiply().getArg2());
        }
    }

    private Action readPRDActionDivideTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        if (isSecondaryEntityExist(prdAction)) {
            return new DivideAction(entityOfAction, secondaryEntityReader(prdAction), prdAction.getResultProp(), prdAction.getPRDDivide().getArg1(), prdAction.getPRDDivide().getArg2());
        } else {
            return new DivideAction(entityOfAction, prdAction.getResultProp(), prdAction.getPRDDivide().getArg1(), prdAction.getPRDDivide().getArg2());
        }
    }

    private Action readPRDActionSetTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        if (isSecondaryEntityExist(prdAction)) {
            return new SetAction(entityOfAction, secondaryEntityReader(prdAction), prdAction.getProperty(), prdAction.getValue());
        } else {
            return new SetAction(entityOfAction, prdAction.getProperty(), prdAction.getValue());
        }
    }

    private Action readPRDActionKillTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        if (isSecondaryEntityExist(prdAction)) {
            return new KillAction(entityOfAction, secondaryEntityReader(prdAction));
        } else {
            return new KillAction(entityOfAction);
        }
    }

    private Action readPRDActionDecreaseTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        if (isSecondaryEntityExist(prdAction)) {
            return new DecreaseAction(entityOfAction, secondaryEntityReader(prdAction), prdAction.getProperty(), prdAction.getBy());
        } else {
            return new DecreaseAction(entityOfAction, prdAction.getProperty(), prdAction.getBy());
        }
    }

    private Action readPRDActionIncreaseTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        if (isSecondaryEntityExist(prdAction)) {
            return new IncreaseAction(entityOfAction, secondaryEntityReader(prdAction), prdAction.getProperty(), prdAction.getBy());
        } else {
            return new IncreaseAction(entityOfAction, prdAction.getProperty(), prdAction.getBy());
        }
    }

    private Action readPRDActionReplaceTypeAction(PRDAction prdAction) {
        EntityDefinition entityToKill = world.getEntityByName(prdAction.getKill());
        EntityDefinition entityToCreate = world.getEntityByName(prdAction.getCreate());
        String mode = prdAction.getMode();
        return new ReplaceAction(entityToKill, entityToCreate, mode);
    }

    private SecondaryEntity secondaryEntityReader(PRDAction action) {

        SecondaryEntity secondaryEntity = null;
        PRDAction.PRDSecondaryEntity prdSecondaryEntity = action.getPRDSecondaryEntity();
        if (prdSecondaryEntity != null) {
            EntityDefinition secondaryEntityDefinition = world.getEntityByName(prdSecondaryEntity.getEntity());
            if (isConditionExistForSecondaryEntity(prdSecondaryEntity)) {
                //readPRDActionConditionTypeAction(prdAction, entityOfAction);
                secondaryEntity = new SecondaryEntityImpl(secondaryEntityDefinition, readPRDActionConditionTypeActionForSecondaryEntity(action, secondaryEntityDefinition), prdSecondaryEntity.getPRDSelection().getCount());
            } else {
                secondaryEntity = new SecondaryEntityImpl(secondaryEntityDefinition, null, prdSecondaryEntity.getPRDSelection().getCount());
            }
        }
        return secondaryEntity;
    }

    private Condition readPRDActionConditionTypeActionForSecondaryEntity(PRDAction prdAction, EntityDefinition entityOfAction) {
        PRDCondition condition = prdAction.getPRDSecondaryEntity().getPRDSelection().getPRDCondition();
        if (condition.getSingularity().equals("single")) {
            return readConditionSingleTypeActionAndReturnCondition(condition, entityOfAction);
        } else if (condition.getSingularity().equals("multiple")) {
            return readConditionMultipleTypeActionAndReturnCondition(condition, entityOfAction);
        }
        return null;
    }

    private Condition readConditionSingleTypeActionAndReturnCondition(PRDCondition condition, EntityDefinition entityOfAction) {

        Condition conditionForSecondEntity = null;
        switch (condition.getOperator()) {
            case "bt":
                conditionForSecondEntity = createConditionFromSingle(condition, new BiggerThan(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity())), entityOfAction, "bt");
                break;
            case "=":
                conditionForSecondEntity = createConditionFromSingle(condition, new Equal(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity())), entityOfAction, "=");
                break;
            case "lt":
                conditionForSecondEntity = createConditionFromSingle(condition, new LowerThan(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity())), entityOfAction, "lt");
                break;
            case "!=":
                conditionForSecondEntity = createConditionFromSingle(condition, new NotEqual(condition.getProperty(), condition.getValue(), world.getEntityByName(condition.getEntity())), entityOfAction, "!=");
                break;
        }
        return conditionForSecondEntity;
    }

    private Condition createConditionFromSingle(PRDCondition condition, SingleCondition singleCondition, EntityDefinition entityOfAction, String logical) {
        ConditionImpl newActionCondition;

//        MultipleCondition multipleRepresentingASingle = new And();
//        multipleRepresentingASingle.addCondition(singleCondition);

        newActionCondition = new ConditionImpl(singleCondition, entityOfAction);

        return newActionCondition;
    }

    private Condition readConditionMultipleTypeActionAndReturnCondition(PRDCondition condition, EntityDefinition entityOfAction) {
        ConditionImpl newActionCondition;

        MultipleCondition multipleCondition = null;
        if (condition.getLogical().equals("or")) {
            multipleCondition = new Or();
            addSmallerConditionToMultipleConditions(condition, multipleCondition, entityOfAction);
        } else if (condition.getLogical().equals("and")) {
            multipleCondition = new And();
            addSmallerConditionToMultipleConditions(condition, multipleCondition, entityOfAction);
        }

        newActionCondition = new ConditionImpl(multipleCondition, entityOfAction);

        return newActionCondition;
    }

    private boolean isSecondaryEntityExist(PRDAction action) {
        return action.getPRDSecondaryEntity() != null;
    }

    private boolean isConditionExistForSecondaryEntity(PRDAction.PRDSecondaryEntity prdSecondaryEntity) {
        return prdSecondaryEntity.getPRDSelection().getPRDCondition() != null;
    }
}
