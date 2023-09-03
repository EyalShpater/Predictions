package impl;

import api.DTO;

import java.util.Collection;
import java.util.List;

public class SimulationDataDTO implements DTO {
    int numOfDifferentEntities;
    int starterPopulationQuantity;
    int finalPopulationQuantity;
    List<Object> propertyOfEntitySortedByValues;

    public SimulationDataDTO(int numOfDifferentEntities, int starterPopulationQuantity, int finalPopulationQuantity, List<Object> propertyOfEntitySortedByValues) {
        this.numOfDifferentEntities = numOfDifferentEntities;
        this.starterPopulationQuantity = starterPopulationQuantity;
        this.finalPopulationQuantity = finalPopulationQuantity;
        this.propertyOfEntitySortedByValues = propertyOfEntitySortedByValues;
    }

    public int getNumOfDifferentEntities() {
        return numOfDifferentEntities;
    }

    public int getStarterPopulationQuantity() {
        return starterPopulationQuantity;
    }

    public int getFinalPopulationQuantity() {
        return finalPopulationQuantity;
    }

    public List<Object> getPropertyOfEntitySortedByValues() {
        return propertyOfEntitySortedByValues;
    }
}
