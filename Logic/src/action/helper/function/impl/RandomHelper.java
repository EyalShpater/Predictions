package action.helper.function.impl;

import action.context.api.Context;
import action.helper.function.api.HelperFunction;

import java.io.Serializable;
import java.util.Random;

public class RandomHelper implements HelperFunction , Serializable {
    String bound;
    Context context;

    public RandomHelper(String bound, Context context) {
        this.bound = bound;
        this.context = context;
    }

    @Override
    public Object getValue() {
        return initRandomlyInteger();
    }

    private Integer initRandomlyInteger() {
        try {
            Random random = new Random();
            int max = Integer.parseInt(bound);

            return random.nextInt(max + 1);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("The value given to random function is not an actual number");
        }
    }
}
