package expression.api;

import instance.entity.api.EntityInstance;

public interface expression {

    expressionType getType();

    EntityInstance getPrimaryEntity();
//    void DecipherExpression();

}
