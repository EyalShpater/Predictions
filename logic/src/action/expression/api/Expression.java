package action.expression.api;

import execution.context.api.Context;
import instance.entity.api.EntityInstance;

public interface Expression {
    //ExpressionType getType();
    Object getValue(Context context);

    String getExpressionString();

    //EntityInstance getPrimaryEntity();

    //    void DecipherExpression();

}

/*

String expression; //environment(e1)

Expression value = new ExpressionExecution(expression, entity);
value = toDo.getValue();

ExpressionExecution {
    String expression;
    EntityDefinition entity;

    getValue() {
        AbstractExpression expressionClass = convert(expression); // FunctionExpression

        return value.getValue();
    }
}


AbstractExpression exp = new Property
 */