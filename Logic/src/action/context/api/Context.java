package action.context.api;

import action.api.Action;
import action.api.ReplaceMode;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

import java.util.List;

public interface Context {
    EntityInstance getPrimaryEntityInstance();

    EntityInstance getSecondaryEntityInstance();

    PropertyInstance getEnvironmentVariable(String name);

    // TODO: can we delete?
    //void removeEntity(EntityInstance entityInstance);

    Context duplicateContextWithEntityInstance(EntityInstance newEntityInstance);

    Context duplicateAndSwapPrimaryInstanceAndSecondary();

    List<EntityInstance> getSecondEntityFilteredList(SecondaryEntity secondaryEntity);

    boolean isEntityRelatedToAction(String entityName);

    Object getPropertyValueOfEntity(String entityName, String propertyName);

    void setSecondaryEntity(EntityInstance secondaryEntityInstance);

    void produceAnewEntityByMode(EntityDefinition entityToCreate, ReplaceMode mode);

    int getTickNumber();

    int getTickThisPropertyWasntChanged(String propertyName);

    void setForAction(Action action);
}
