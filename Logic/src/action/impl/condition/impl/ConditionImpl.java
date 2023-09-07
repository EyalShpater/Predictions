package action.impl.condition.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.context.api.Context;
import action.impl.condition.Condition;
import action.impl.condition.impl.multiple.MultipleCondition;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConditionImpl extends AbstractAction implements Condition, Serializable {
    private final MultipleCondition condition; // todo: think to change it to Condition instead of MultipleCondition
    private final String logical;
    List<Action> then;
    List<Action> notTrue;

    public ConditionImpl(MultipleCondition condition, String logical, EntityDefinition entity) {
        super(entity, ActionType.CONDITION);
        this.condition = condition;
        this.logical = logical;
        this.then = new ArrayList<>();
        this.notTrue = new ArrayList<>();
    }

    public ConditionImpl(MultipleCondition condition, String logical, EntityDefinition mainEntity, SecondaryEntity secondaryEntity) {
        super(mainEntity, secondaryEntity, ActionType.CONDITION);
        this.condition = condition;
        this.logical = logical;
        this.then = new ArrayList<>();
        this.notTrue = new ArrayList<>();
    }

    public void addThanList(List<Action> thanList) {
        if (thanList == null) {
            throw new IllegalArgumentException();
        }

        this.then = thanList;
    }

    public void addNotTrueList(List<Action> newNotTrueList) {
        if (newNotTrueList == null) {
            throw new IllegalArgumentException();
        }

        this.notTrue = newNotTrueList;
    }

    @Override
    public void applyAction(Context context) {
        //forEach secondary
        if (evaluate(context)) {
            then.forEach(action -> action.invoke(context));
        } else if (notTrue != null){
            notTrue.forEach(action -> action.invoke(context));
        }
    }

    @Override
    public boolean evaluate(Context context) {
        return condition.evaluate(context);
    }

    @Override
    public String getOperationSign() {
        return null;
    }

    @Override
    public Map<String, String> getArguments() {
        Map<String, String> attributes = new LinkedHashMap<>();
        Condition singleCondition = condition.isSingleCondition();

        if (singleCondition != null) {
            attributes = singleCondition.getArguments();
        } else {
            attributes = condition.getArguments();
        }

        return attributes;
    }
}
/*<PRD-action type="condition" entity="ent-1">
					<PRD-secondary-entity entity="ent-2">
						<PRD-selection count="4">
							<PRD-condition singularity="single" entity="ent-2" property="p1" operator="bt" value="4"/>
						</PRD-selection>
					</PRD-secondary-entity>
					<PRD-condition singularity="multiple" logical="or">
						<PRD-condition singularity="single" entity="ent-1" property="ticks(ent-1.p1)" operator="bt" value="4"/>
						<PRD-condition singularity="single" entity="ent-2" property="p2" operator="lt" value="3"/>
						<PRD-condition singularity="multiple" logical="and">
							<PRD-condition singularity="single" entity="ent-1" property="p4" operator="!=" value="nothing"/>
							<PRD-condition singularity="single" entity="ent-1" property="p3" operator="=" value="environment(e2)"/>
						</PRD-condition>
					</PRD-condition>
					<PRD-then>
						<PRD-action type="increase" entity="ent-1" property="p1" by="3"/>
						<PRD-action type="set" entity="ent-1" property="p1" value="random(3)"/>
					</PRD-then>
					<PRD-else>
						<PRD-action type="kill" entity="ent-1"/>
					</PRD-else>
				</PRD-action>*/