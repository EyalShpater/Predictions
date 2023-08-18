package execution.simulation.xml.reader.impl;

import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.PropertyDefinitionImpl;
import resources.generated.PRDProperty;
import resources.generated.PRDRange;

public class PropertyReader {

    public PropertyDefinition read( PRDProperty prdProperty ){
        return readDataFromPRDPropertyToPropertyDefinition(prdProperty);
    }

    private PropertyDefinition readDataFromPRDPropertyToPropertyDefinition( PRDProperty prdProperty ) {
        Range range = checkRangeExist(prdProperty.getPRDRange());
        PropertyDefinition newProperty = null;
        if ( range == null ){
            newProperty = createPropertyWithOutRange(prdProperty);
        }else{
            newProperty = createPropertyWithRange(prdProperty , range);
        }
        return newProperty;
    }

    private PropertyDefinition createPropertyWithRange(PRDProperty prdProperty , Range range) {
        boolean isRandomInitialize = prdProperty.getPRDValue().isRandomInitialize();
        PropertyType type = createPropertyType(prdProperty.getType());
        if (isRandomInitialize){
            return new PropertyDefinitionImpl( prdProperty.getPRDName() , type , isRandomInitialize , range ) ;
        }
        else{
            Object value = prdProperty.getPRDValue().getInit();
            return new PropertyDefinitionImpl( prdProperty.getPRDName() , type , range  , isRandomInitialize , value ) ;
        }
    }

    private PropertyDefinition createPropertyWithOutRange(PRDProperty prdProperty) {
        boolean isRandomInitialize = prdProperty.getPRDValue().isRandomInitialize();
        PropertyType type = createPropertyType(prdProperty.getType());
        if (isRandomInitialize){
            return new PropertyDefinitionImpl( prdProperty.getPRDName() , type , isRandomInitialize ) ;
        }
        else{
            Object value = prdProperty.getPRDValue().getInit();
            return new PropertyDefinitionImpl( prdProperty.getPRDName() , type , isRandomInitialize , value ) ;
        }
    }

    private PropertyType createPropertyType(String type) {
        if ( type.equals("decimal") ) {
            return PropertyType.INT;
        } else if (type.equals("float")) {
            return PropertyType.DOUBLE;
        }else if (type.equals("boolean")) {
            return PropertyType.BOOLEAN;
        }else {
            return PropertyType.STRING;
        }
    }

    private Range checkRangeExist(PRDRange prdRange) {
        Range range = null;
        if ( prdRange != null){
            range = new Range(prdRange.getFrom() , prdRange.getTo());
        }
        return range;
    }
}
