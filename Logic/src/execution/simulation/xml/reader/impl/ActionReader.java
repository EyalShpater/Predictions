package execution.simulation.xml.reader.impl;

import action.api.Action;
import action.impl.*;
import action.impl.condition.Condition;
import action.impl.condition.impl.ConditionImpl;
import action.impl.condition.impl.multiple.And;
import action.impl.condition.impl.multiple.MultipleCondition;
import action.impl.condition.impl.multiple.Or;
import action.impl.condition.impl.single.*;
import definition.entity.api.EntityDefinition;
import definition.world.api.World;
import resources.generated.PRDAction;
import resources.generated.PRDCondition;
import resources.generated.PRDElse;
import resources.generated.PRDThen;

import java.util.ArrayList;
import java.util.List;

public class ActionReader {

    public Action read(PRDAction prdAction ,EntityDefinition entityOfAction , World world){
        return readAction( prdAction ,entityOfAction, world);
    }
    private Action readAction(PRDAction prdAction , EntityDefinition entityOfAction , World world){

        Action action = null;
        if ( prdAction.getType().equals("increase") ){
            action = readPRDActionIncreaseTypeAction( prdAction , entityOfAction );
        } else if ( prdAction.getType().equals("decrease") ) {
            action = readPRDActionDecreaseTypeAction( prdAction , entityOfAction );
        } else if ( prdAction.getType().equals("kill") ) {
            action = readPRDActionKillTypeAction( prdAction , entityOfAction );
        }else if ( prdAction.getType().equals("set") ) {
            action = readPRDActionSetTypeAction( prdAction , entityOfAction );
        }else if ( prdAction.getType().equals("calculation") ) {
            action = readPRDActionCalculationTypeAction( prdAction , entityOfAction );
        }else if ( prdAction.getType().equals("condition") ) {
            action = readPRDActionConditionTypeAction( prdAction , entityOfAction , world );
        }else {
            throw new IllegalArgumentException("Type of action is not valid");
        }
        return action;
    }

    private Action readPRDActionConditionTypeAction(PRDAction prdAction, EntityDefinition entityOfAction ,  World world) {
       /*
       * <PRD-action type="condition" entity="ent-1">
                    <PRD-condition singularity="multiple" logical="or">
                        <PRD-condition singularity="single" entity="ent-1" property="p1" operator="bt" value="4"/>
                        <PRD-condition singularity="single" entity="ent-1" property="p2" operator="lt" value="3"/>
                        <PRD-condition singularity="multiple" logical="and">
                            <PRD-condition singularity="single" entity="ent-1" property="p4" operator="!="
                                           value="nothing"/>
                            <PRD-condition singularity="single" entity="ent-1" property="p3" operator="="
                                           value="environment(e2)"/>
                        </PRD-condition>
                    </PRD-condition>
                    <PRD-then>
                        <PRD-action type="increase" entity="ent-1" property="p1" by="3"/>
                        <PRD-action type="set" entity="ent-1" property="p1" value="random(3)"/>
                    </PRD-then>
                    <PRD-else>
                        <PRD-action type="kill" entity="ent-1"/>
                    </PRD-else>
                </PRD-action>
       * */
        PRDCondition condition = prdAction.getPRDCondition();
        if ( condition.getSingularity().equals("single") ){
            return readPRDActionConditionSingleTypeAction(world , prdAction ,entityOfAction);
        } else if (condition.getSingularity().equals("multiple")) {
            return readPRDActionConditionMultipleTypeAction(world , prdAction ,entityOfAction);
        }
        return null;
    }


    private Action readPRDActionConditionMultipleTypeAction(World world ,PRDAction prdAction, EntityDefinition entityOfAction){
        PRDCondition condition = prdAction.getPRDCondition();
        MultipleCondition multipleCondition = null;
        if (condition.getLogical().equals("or")){
            multipleCondition = new Or();
            addSmallerConditionToMultipleConditions(condition ,multipleCondition,entityOfAction);
        } else if (condition.getLogical().equals("and")) {
            multipleCondition = new And();
            addSmallerConditionToMultipleConditions(condition , multipleCondition,entityOfAction);
        }

        PRDThen thenBlock = prdAction.getPRDThen();
        PRDElse elseBloc = prdAction.getPRDElse();

        List<Action> thenList = getThenListFromPRDThen(world ,thenBlock ,entityOfAction);
        List<Action> elseList = getElseListFromPRDElse(world ,elseBloc ,entityOfAction);
        ConditionImpl newActionCondition = new ConditionImpl(multipleCondition , condition.getLogical() , entityOfAction);

        if (thenList != null ){
            newActionCondition.addThanList(thenList);
        }
        if (elseList != null){
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
        if ( condition.getOperator().equals("bt") ){
            return new BiggerThan(condition.getProperty() , condition.getValue());
        }else if ( condition.getOperator().equals("=") ){
            return new Equal(condition.getProperty() , condition.getValue());
        } else if ( condition.getOperator().equals("lt") ) {
            return new LowerThan(condition.getProperty() , condition.getValue());
        } else if (condition.getOperator().equals("!=")) {
            return new NotEqual(condition.getProperty() , condition.getValue());
        }
        return null;
    }



    private Action readPRDActionConditionSingleTypeAction(World world , PRDAction prdAction, EntityDefinition entityOfAction){
        PRDCondition condition = prdAction.getPRDCondition();
        Action action = null;
        if ( condition.getOperator().equals("bt") ){
            action = createActionFromSingleTypeAction(world , prdAction, new BiggerThan(condition.getProperty() , condition.getValue()), entityOfAction , "bt" );
        }else if ( condition.getOperator().equals("=") ){
            action = createActionFromSingleTypeAction(world , prdAction, new Equal(condition.getProperty() , condition.getValue()), entityOfAction , "=" );
        } else if ( condition.getOperator().equals("lt") ) {
            action = createActionFromSingleTypeAction(world , prdAction, new LowerThan(condition.getProperty() , condition.getValue()), entityOfAction , "lt" );
        } else if (condition.getOperator().equals("!=")) {
            action = createActionFromSingleTypeAction(world , prdAction, new NotEqual(condition.getProperty() , condition.getValue()), entityOfAction , "!=" );
        }
        return action;
    }

    private Action createActionFromSingleTypeAction(World world ,PRDAction prdAction , SingleCondition singleCondition, EntityDefinition entityOfAction , String logical){
        PRDCondition condition = prdAction.getPRDCondition();

        PRDThen thenBlock = prdAction.getPRDThen();
        PRDElse elseBloc = prdAction.getPRDElse();

        List<Action> thenList = getThenListFromPRDThen(world ,thenBlock ,entityOfAction);
        List<Action> elseList = getElseListFromPRDElse(world ,elseBloc ,entityOfAction);

        MultipleCondition multipleRepresentingASingle = new And();
        multipleRepresentingASingle.addCondition(singleCondition);

        ConditionImpl newActionCondition = new ConditionImpl(multipleRepresentingASingle , logical , entityOfAction);

        if (thenList != null ){
            newActionCondition.addThanList(thenList);
        }
        if (elseList != null){
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
                elseList.add(readAction(action , world.getEntityByName(action.getEntity()),world ));
            }
        }
        return elseList;
    }

    private List<Action> getThenListFromPRDThen(World world ,PRDThen thenBlock , EntityDefinition entityOfAction ) {
        List<Action> thenList = null;
        if (thenBlock != null ) {
            thenList = new ArrayList<>();
            List<PRDAction> prdActionList = thenBlock.getPRDAction();
            for (PRDAction action: prdActionList) {
                    thenList.add(readAction(action , world.getEntityByName(action.getEntity()),world ));
            }
        }
        return thenList;
    }

    private Action readPRDActionCalculationTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        if ( prdAction.getPRDMultiply() != null ){
            return new MultiplyAction(entityOfAction , prdAction.getResultProp(), prdAction.getPRDMultiply().getArg1() , prdAction.getPRDMultiply().getArg2() );
        } else {
            return new DivideAction(entityOfAction , prdAction.getResultProp(), prdAction.getPRDDivide().getArg1() , prdAction.getPRDDivide().getArg2() );
        }
    }

    private Action readPRDActionSetTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        return new SetAction( entityOfAction , prdAction.getProperty() , prdAction.getValue() );
    }

    private Action readPRDActionKillTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        return new KillAction(entityOfAction);
    }

    private Action readPRDActionDecreaseTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
        return new DecreaseAction(entityOfAction , prdAction.getProperty(), prdAction.getBy() );
    }

    private Action readPRDActionIncreaseTypeAction(PRDAction prdAction , EntityDefinition entityOfAction) {
        return new IncreaseAction( entityOfAction , prdAction.getProperty() , prdAction.getBy() );
    }
}
