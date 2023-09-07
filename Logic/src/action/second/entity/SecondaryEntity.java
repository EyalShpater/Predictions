package action.second.entity;

import action.context.api.Context;
import definition.entity.api.EntityDefinition;

public interface SecondaryEntity {

    EntityDefinition getSecondEntity();

    String getInstancesCount();

    Boolean evaluateCondition(Context context);

    boolean isConditionExist();
}
