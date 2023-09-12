package action.second.entity;

import action.context.api.Context;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

public interface SecondaryEntity {

    EntityDefinition getSecondaryEntity();

    String getInstancesCount();

    Boolean evaluateCondition(Context context, EntityInstance secondaryEntity);

    boolean isConditionExist();
}
