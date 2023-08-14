package action.helper.function.impl;

import action.helper.function.api.AbstractHelperFunction;
import action.helper.function.api.HelperFunctionType;

import action.context.api.Context;


import java.util.Random;

public class RandomHelperFunction extends AbstractHelperFunction {


    public RandomHelperFunction(HelperFunctionType type) {
        super(type);
    }

    @Override
    public Object getValueFromHelperFunction(Context context) {
        return initRandomlyInteger(context.getExpressionStringValue());
    }

    private Integer initRandomlyInteger(String theValue) {

        Random random = new Random();
        try {
            int max = Integer.parseInt(theValue);
            return random.nextInt(max + 1);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("The value given to random function is not an actual number");
        }
    }
}
