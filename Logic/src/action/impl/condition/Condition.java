package action.impl.condition;

import action.context.api.Context;
import instance.entity.api.EntityInstance;

import java.util.Map;

public interface Condition {
    Boolean evaluate(Context context, EntityInstance secondEntityInstance);

    String getOperationSign();

    Map<String, String> getArguments();

    Map<String, String> getAdditionalInformation();

    boolean isSingleCondition();
}
