package action.expression.api;

import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractExpression implements Expression , Serializable {
    protected String expression;
    protected EntityInstance primaryEntity;
    protected EntityInstance secondaryEntity;

    public AbstractExpression(String expression, EntityInstance primaryEntity) {
        this(expression, primaryEntity, null);
    }


    public AbstractExpression(String expression, EntityInstance primaryEntity, EntityInstance secondaryEntity) {
        this.expression = expression;
        this.primaryEntity = primaryEntity;
        this.secondaryEntity = secondaryEntity;
    }

    public abstract AbstractExpression convert();
}
