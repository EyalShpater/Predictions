package action.second.entity.impl;

import action.context.api.Context;
import action.impl.condition.Condition;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

public class SecondaryEntityImpl implements SecondaryEntity {
    EntityDefinition secondaryEntity;
    Condition condition;
    String count;

    public SecondaryEntityImpl(EntityDefinition secondaryEntity, Condition condition, String count) {
        this.secondaryEntity = secondaryEntity;
        this.condition = condition;
        this.count = count;
    }

    @Override
    public EntityDefinition getSecondaryEntity() {
        return secondaryEntity;
    }

    @Override
    public String getInstancesCount() {
        return count;
    }

    @Override
    public Boolean evaluateCondition(Context context) {
        return condition.evaluate(context);
    }

    @Override
    public boolean isConditionExist() {
        return condition != null;
    }
}
