package execution.simulation.xml.reader.impl;

import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.PropertyDefinitionImpl;
import definition.world.api.World;
import resources.generated.PRDEntity;
import resources.generated.PRDProperty;
import resources.generated.PRDRange;
import resources.generated.PRDWorld;

import java.util.List;

public class EntityReader {

    public void read (PRDWorld prdWorld , World world){
        List<PRDEntity> entityList = prdWorld.getPRDEntities().getPRDEntity();
        entityList.forEach(prdEntity -> readDataFromPRDEntityToEntityDefinition(prdEntity , world));
    }

    private void readDataFromPRDEntityToEntityDefinition(PRDEntity prdEntity, World world) {

        EntityDefinition newEntity = new EntityDefinitionImpl(prdEntity.getName() , prdEntity.getPRDPopulation());

        //Object that reads into Property
        List<PRDProperty> propertyListOfPRDEntity = prdEntity.getPRDProperties().getPRDProperty();
        for ( PRDProperty prdProperty : propertyListOfPRDEntity ){
            PropertyReader propertyReader = new PropertyReader();
            newEntity.addProperty( propertyReader.read( prdProperty ) );
        }

        world.addEntity(newEntity);
    }
}
