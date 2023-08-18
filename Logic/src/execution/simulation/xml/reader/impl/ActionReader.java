package execution.simulation.xml.reader.impl;

import action.api.Action;
import action.impl.*;
import definition.entity.api.EntityDefinition;
import resources.generated.PRDAction;
import resources.generated.PRDCondition;

public class ActionReader {

    public Action read(PRDAction prdAction ,EntityDefinition entityOfAction){

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
            action = readPRDActionConditionTypeAction( prdAction , entityOfAction );
        }else {
            throw new IllegalArgumentException("Type of action is not valid");
        }
        return action;
    }

    private Action readPRDActionConditionTypeAction(PRDAction prdAction, EntityDefinition entityOfAction) {
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

        }
        return null;
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
