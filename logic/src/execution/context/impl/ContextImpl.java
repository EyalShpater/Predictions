package execution.context.impl;

import action.expression.api.Expression;
import execution.context.api.Context;
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
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
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
    public void setExpressionStringValue(String value) {
        this.expressionStringValue = value;
    }

    @Override
    public Expression getExpression() {
        return this.expression;
    }

    @Override
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

}
