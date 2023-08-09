package instance.enviornment.api;


import instance.property.api.PropertyInstance;

public interface ActiveEnvironment {
    void addPropertyInstance(PropertyInstance property);

    PropertyInstance getPropertyByName(String PropertyName);
}
