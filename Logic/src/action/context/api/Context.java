package action.context.api;

import action.second.entity.SecondaryEntity;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

import java.util.List;

public interface Context {
    EntityInstance getEntityInstance();

    PropertyInstance getEnvironmentVariable(String name);

    void removeEntity(EntityInstance entityInstance);

    Context duplicateContextWithEntityInstance(EntityInstance newEntityInstance);

    List<EntityInstance> getSecondEntityFilteredList(SecondaryEntity secondaryEntity);

    boolean isEntityRelatedToAction(String entityName);

    Object getPropertyValueOfEntity(String entityName, String propertyName);

    void setSecondaryEntity(EntityInstance secondaryEntityInstance);

}
