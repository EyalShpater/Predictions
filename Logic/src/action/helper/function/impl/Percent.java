package action.helper.function.impl;

import action.context.api.Context;
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
        return null;
    }
}
