package action.second.entity;

import action.context.api.Context;
import definition.entity.api.EntityDefinition;

public interface SecondEntity {

    EntityDefinition getSecondEntity();

    String getInstancesCount();

    Boolean evaluateCondition(Context context);
}
