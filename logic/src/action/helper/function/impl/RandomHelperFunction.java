package action.helper.function.impl;

import action.helper.function.api.AbstractHelperFunction;
import action.helper.function.api.HelperFunctionType;
import action.helper.function.api.HelperFunctionValueGenerator;
import action.helper.function.context.api.HelperFunctionContext;


import java.util.Random;

public class RandomHelperFunction extends AbstractHelperFunction {


    @Override
    public Object getValueFromHelperFunction(HelperFunctionContext context) {
        return initRandomlyInteger(context.getTheStringValue());
    }

    private Integer initRandomlyInteger(String theValue) {

        Random random = new Random();
        try {
            int min = 0, max = Integer.parseInt(theValue);
            return random.nextInt(max + 1);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("The value given to random function is not an actual number");
        }
    }
}
