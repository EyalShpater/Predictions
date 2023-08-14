package action.context.impl;

import action.context.api.Context;
import action.expression.api.Expression;
import instance.entity.api.EntityInstance;
import instance.entity.manager.api.EntityInstanceManager;
import instance.enviornment.api.ActiveEnvironment;
import instance.property.api.PropertyInstance;

public class ContextImpl implements Context {
    private EntityInstance primaryEntityInstance;
    private EntityInstanceManager entityInstanceManager;
    private ActiveEnvironment activeEnvironment;
    private Expression expression;
    private String expressionStringValue = "";

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getPropertyByName(name);
    }

    @Override
    public String getExpressionStringValue() {
        return expressionStringValue;
    }

    @Override
    public Expression getExpression() {
        return this.expression;
    }

}
