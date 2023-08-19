package action.context.impl;

import action.context.api.Context;
import action.expression.api.Expression;
import instance.entity.api.EntityInstance;
import instance.entity.manager.api.EntityInstanceManager;
import instance.enviornment.api.ActiveEnvironment;
import instance.property.api.PropertyInstance;

import java.io.Serializable;

public class ContextImpl implements Context , Serializable {
    private EntityInstance entityInstance;
    private EntityInstanceManager entityInstanceManager;
    private ActiveEnvironment activeEnvironment;

    public ContextImpl(EntityInstance entityInstance, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment) {
        this.entityInstance = entityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
    }
    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }

    @Override
    public EntityInstance getEntityInstance() {
        return entityInstance;
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getPropertyByName(name);
    }
}
