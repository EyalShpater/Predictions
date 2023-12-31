# Predictions

Predictions is a simulations system that creates a virtual world where entities interact based on a predefined set of
rules and properties. It predicts the outcomes of these interactions, allowing you to explore and understand the
consequences of different connections between the entities. This simulation provides valuable insights into how various
factors and relationships play out, helping you analyze and make informed decisions in a controlled environment.

## Technologies used

* Programming language: Java 8
* GUI: JavaFX
* Client: OkHttp
* Server: Tomcat

## System overview

The Predictions framework consists of the following components:

* **World:** The world is a container for entities and laws. It defines the initial state of the simulation, including
  the number of entities and their properties.
* **Entity:** An entity is a single object in the world. It has a name, a type, and a set of properties.
* **Rule:** A rule is a set of instructions that govern the behavior of entities. It can change the properties of
  entities, create new entities, or delete entities.
* **Environment Properties:** The environment property is a set of global variables that can be accessed by entities and
  laws.
* **Termination conditions:** Termination conditions define when the simulation will end.

## Simulation process

The simulation process consists of the following steps:

1. **Initialization:** The world is initialized with the specified number of entities and their properties. The
   environment is also initialized with the specified values.
2. **Simulation steps:** The simulation runs for a specified number of steps. In each step, the following actions are
   performed:
    * All laws that are eligible to be executed are executed.
    * The properties of all entities are updated.
3. **Termination:** The simulation terminates if one of the termination conditions is met.

## Simulation output

At the end of the simulation, users can access the following output:

* **Entity counts and types:** A summary of the number of entities and their types.
* **Entity property averages:** The average value of a property for a given entity type over time.
* **Entity property consistency:** The consistency of a property value for a given entity type over time.

## Actions

* **Increase:** This action allows increasing the numerical property value of a specific entity by a certain amount.
* **Decrease:** This action allows reducing the numerical property value of a specific entity by a certain amount.
* **Calculation:** This action allows performing a mathematical calculation on the numerical property value of the main
  entity.
    * **Multiply:** Perform multiplication of two numbers. The result of the multiplication will be assigned to the
      property "result-prop".
    * **Divide:** Perform division of two numbers. The result of the division will be assigned to the property "
      result-prop".
* **Condition:** This action allows defining a conditional operation. The condition can be defined in two ways based on
  its singularity value:
    1. **Simple Condition:** This structure describes a simple, unique condition in the system that can be evaluated.
    2. **Multiple Condition:** This structure describes the union/composition (aggregation) of other conditions, whether
       they are simple or complex in themselves.<br> The union and composition can be performed using logical
       operators: "and" and "or." This allows the construction of more complex conditions with deeper nesting levels.
       This structure does not contain specific conditions by itself but serves solely for the union and composition of
       simpler conditions.<br>
* **Set:** This action allows updating the value of a property for a specific entity.
* **Kill:** This action allows killing the main entity within which it is defined.
* **Replace:** This action allows replacing one entity with another.
* **Proximity:** This action allows performing additional operations based on the proximity check between two entities
  in the simulation "space." Within the action, two entities will be specified for proximity evaluation, and when both
  are considered "close," a set of actions can be defined to be executed on these two entities. If two entities are
  found that meet the proximity criteria, a collection of actions can be defined to be performed on both entities.

## Additional features

The Predictions framework also supports the following features:

* **Spatial entities:** Entities can be assigned a location in space.
* **Randomized entities and properties:** The properties of entities can be randomly generated at initialization.
* **Parallel Simulation Runs:** The framework can run multiple simulations simultaneously, using the number of threads
  specified in the configuration file.
* **Simulation Control:**  Users can stop, pause, and resume running simulations. They can also advance simulations by
  one tick while paused.

## Comments

* **Configuration Files:** Sample simulation configuration files are provided for your convenience. If you want to
  write your own configuration file, you can use the provided files or the provided XML schema as a reference.