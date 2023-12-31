package action.helper.function.impl;

import action.context.api.Context;
import action.expression.impl.ExpressionFactory;
import action.helper.function.api.HelperFunction;

public class Percent implements HelperFunction {

    Context context;
    String expression1;
    String expression2;

    public Percent(Context context, String expression1, String expression2) {
        this.context = context;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public Object getValue() {

        Object value1 = new ExpressionFactory(expression1, context).getValue(context);
        Object value2 = new ExpressionFactory(expression2, context).getValue(context);

        if (value1 instanceof Number && value2 instanceof Number) {
            double doubleValue1 = ((Number) value1).doubleValue();
            double doubleValue2 = ((Number) value2).doubleValue();

            if (doubleValue2 < 0) {
                throw new IllegalArgumentException("No such thing as negative percent from a whole");
            }
            if (doubleValue1 == 0.0) {
                return 0.0;
            }


            double percentage = (doubleValue1 / 100.0) * doubleValue2;
            return percentage;
        }

        throw new IllegalArgumentException("Percentage calculation is defined for numeric types only");
    }
}
