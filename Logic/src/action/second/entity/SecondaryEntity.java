package action.second.entity;

import action.context.api.Context;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

public interface SecondaryEntity {

    EntityDefinition getSecondEntity();

    String getInstancesCount();

    Boolean evaluateCondition(Context context, EntityInstance secondaryEntity);

    boolean isConditionExist();
}
