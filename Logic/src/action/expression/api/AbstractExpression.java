package action.expression.api;

import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractExpression implements Expression , Serializable {
    protected String expression;
    protected EntityInstance primaryEntity;
    protected List<EntityInstance> entityInstances;


    public AbstractExpression(String expression, EntityInstance primaryEntity, EntityInstance... entityInstance) {
        this.expression = expression;
        this.primaryEntity = primaryEntity;
        this.entityInstances = Arrays.asList(entityInstance);
    }

    public abstract AbstractExpression convert();
}
