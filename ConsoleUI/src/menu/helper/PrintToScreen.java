package menu.helper;

import execution.simulation.api.PredictionsLogic;
import impl.*;
import menu.api.MenuOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrintToScreen {
    final static int INDENTATION = 4;
    final static int NO_INDENTATION = 0;


    public void printCharNumOfTimes(char c, int numOfTimex) {
        for (int i = 1; i <= numOfTimex; i++) {
            System.out.print(c);
        }
    }

    public void printLine(int length, char c) {
        for (int i = 1; i <= length; i++) {
            System.out.print(c);
        }
    }

    public void printLoadFileMenu() {
        printTitle("Load New Simulation File", ' ', '=', NO_INDENTATION);
        System.out.print(System.lineSeparator());
        System.out.println("Please enter XML simulation file path:");
    }

    public void printTitle(String title, char lineChar, char borderLineChar, int indentation) {
        final int NUM_OF_CHARS = 10;

        printLine(indentation, ' ');
        printLine(NUM_OF_CHARS * 2 + 2 + title.length(), borderLineChar);
        System.out.println();

        printLine(indentation, ' ');
        printCharNumOfTimes(lineChar, NUM_OF_CHARS);
        System.out.printf(" %s ", title);
        printCharNumOfTimes(lineChar, NUM_OF_CHARS);
        System.out.println();

        printLine(indentation, ' ');
        printLine(NUM_OF_CHARS * 2 + 2 + title.length(), borderLineChar);
    }

    public void displayMenu() {
        printTitle("Menu", ' ', '=', NO_INDENTATION);
        System.out.println();
        System.out.println("Please choose one of the following options");
        System.out.println(MenuOptions.LOAD_FILE.getValue() + ". Load file");
        System.out.println(MenuOptions.SHOW_SIMULATION_DETAILS.getValue() + ". Present simulation details");
        System.out.println(MenuOptions.RUN_SIMULATION.getValue() + ". Run the simulation");
        System.out.println(MenuOptions.SHOW_PREVIOUS_SIMULATIONS.getValue() + ". Present description of previous simulation");
        System.out.println(MenuOptions.EXIT.getValue() + ". Exit program");
    }

    public void printPropertyDefinitionDTOList(List<PropertyDefinitionDTO> properties, int indentation, boolean isEnvironmentVariable) {
        for (int i = 1; i <= properties.size(); i++) {
            printLine(indentation, ' ');
            System.out.print(i + ". ");
            printPropertyDefinitionDTO(properties.get(i - 1),
                    Integer
                            .valueOf(i)
                            .toString()
                            .length() + indentation + 2,
                    isEnvironmentVariable
            );
            System.out.print(System.lineSeparator());
        }
    }

    public void printPropertyDefinitionDTO(PropertyDefinitionDTO dto, int indentation, boolean isEnvironmentVariable) {
        System.out.println("Variable name: " + dto.getName());

        printLine(indentation, ' ');
        System.out.println("Type: " + dto.getType());

        if (dto.getFrom() != null) {
            printLine(indentation, ' ');
            System.out.println("Minimum value: " +
                    (dto.getType().equals("INT") ?
                    String.valueOf(dto.getFrom().intValue()) :
                    dto.getFrom())
            );
        }

        if (dto.getTo() != null) {
            printLine(indentation, ' ');
            System.out.println("Maximum value: " +
                    (dto.getType().equals("INT") ?
                            String.valueOf(dto.getTo().intValue()) :
                            dto.getTo())
            );

            if (!isEnvironmentVariable) {
                printLine(indentation, ' ');
                System.out.println(
                        (!dto.isRandom()) ?
                        "Not Randomly initialized" :
                        "Randomly initialized"
                );
            }

        }
    }

    public void printEntityDefinitionDTOList(List<EntityDefinitionDTO> entities, int indentation) {
        for (int i = 1; i <= entities.size(); i++) {
            printTitle("Entity #" + i, '-', ' ', indentation);
            System.out.println();
            printEntityDefinitionDTO(entities.get(i - 1), indentation);
        }
    }

    public void printEntityDefinitionDTO(EntityDefinitionDTO entity, int indentation) {
        printLine(indentation, ' ');
        System.out.println("Name: " + entity.getName());

        printLine(indentation, ' ');
        System.out.println("Population: " + entity.getPopulation());

        printLine(indentation, ' ');
        System.out.println("Properties:");
        printPropertyDefinitionDTOList(entity.getProperties(), indentation + "properties:".length(), false);
    }

    private void printRuleDTOList(List<RuleDTO> rules, int indentation) {
        for (int i = 1; i <= rules.size(); i++) {
            printLine(indentation, ' ');
            System.out.print( i + ". ");
            printRuleDTO(rules.get(i - 1),
                    Integer
                    .valueOf(i)
                    .toString()
                    .length() + indentation + 2
            );
        }
    }

    private void printRuleDTO(RuleDTO rule, int indentation) {
        System.out.println("Rule name: " + rule.getName());

        printLine(indentation, ' ');
        System.out.printf("Activate every %d ticks, with a probability of %.2f", rule.getTicks(), rule.getProbability());
        System.out.println();

        printLine(indentation, ' ');
        System.out.println(rule.getName() + " has " + rule.getActionsNames().size() + " actions:");
        IntStream.range(0, rule.getActionsNames().size())
                .forEach(i -> {
                    printLine(indentation * 2, ' ');
                    System.out.println((i + 1) + ". " + rule.getActionsNames().get(i));
                    System.out.println();
                });
    }

    private void printTerminationDTO(TerminationDTO termination, int indentation) {
        if (termination.isTerminateBySeconds()) {
            printLine(indentation, ' ');
            System.out.println(termination.getSecondsToTerminate() + " seconds to terminate");
        }

        if (termination.isTerminateByTicks()) {
            printLine(indentation, ' ');
            System.out.println(termination.getTicksToTerminate() + " ticks to terminate");
        }
    }

    public void printWorldDTO(WorldDTO world) {
        printTitle("Entities", '#', ' ', NO_INDENTATION);
        printEntityDefinitionDTOList(world.getEntities(), INDENTATION);

        printTitle("Rules", '#', ' ', NO_INDENTATION);
        System.out.println();
        printRuleDTOList(world.getRules(), 0);

        printTitle("Termination", '#', ' ', NO_INDENTATION);
        System.out.println();
        printTerminationDTO(world.getTermination(), 0);
        System.out.println();
    }

    public void showAllSimulationsDTO(List<SimulationDTO> simulations) {
        printTitle("All simulations:", '~', ' ', NO_INDENTATION);
        System.out.println();

        if (simulations != null && !simulations.isEmpty()) {
            int count = 1;

            for (SimulationDTO simulation : simulations) {
                System.out.print(count + ". ");
                printSimulationDTO(simulation,
                        Integer
                                .valueOf(count)
                                .toString()
                                .length() + 2);

                count++;
            }
        } else {
            System.out.println("No simulations to show");
        }
    }

    private void printSimulationDTO(SimulationDTO dto, int indentation) {
        System.out.println("Simulation serial number: " + dto.getSerialNumber());

        printLine(indentation, ' ');
        System.out.print("Run time: ");
        printTime(dto.getStartDate());

        System.out.println(System.lineSeparator());
    }

    private void printTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");

        System.out.print(simpleDateFormat.format(date));
    }

    public void printSimulationDetailViewOptions() {
        System.out.println("1. Show entities amount");
        System.out.println("2. Show property histogram");
    }

    public void showAmountViewSimulationDTO(int serialNumber, PredictionsLogic engine) {
        SimulationDTO dto = engine.getSimulationDTOBySerialNumber(serialNumber);
        SimulationDataDTO simulationData;
        int count = 1;

        printTitle("All Entities", '*', '~', NO_INDENTATION);
        for (EntityDefinitionDTO entity : dto.getWorld().getEntities()) {
            simulationData = engine.getSimulationData(dto.getSerialNumber(), entity.getName(), null);

            printTitle(entity.getName(), '~', ' ', NO_INDENTATION);
            System.out.println();

            System.out.println("Start population: " + simulationData.getStarterPopulationQuantity());
            System.out.println("Final population: " + simulationData.getFinalPopulationQuantity());
            System.out.println();
            count++;
        }
    }

    public void showHistogramViewSimulationDTO(SimulationDataDTO data) {
        Map<Object, Integer> valueCountMap = new HashMap<>();

        for (Object obj : data.getPropertyOfEntitySortedByValues()) {
            valueCountMap.put(obj, valueCountMap.getOrDefault(obj, 0) + 1);
        }

        if (valueCountMap.entrySet().isEmpty()) {
            System.out.println("All instances of the requested entity were terminated during the simulation.");
            System.out.println("There is no available data to display.");

        }

        for (Map.Entry<Object, Integer> entry : valueCountMap.entrySet()) {
            System.out.println(entry.getKey() + " appeared " + entry.getValue() + " times");
        }

        System.out.println();
    }

    public void showEnvironmentVariablesChooseMenu(List<PropertyDefinitionDTO> environmentVariables) {
        if (environmentVariables != null) {
            printPropertyDefinitionDTOList(environmentVariables, 0, true);
            System.out.print(System.lineSeparator());
            System.out.println("Please enter the number of variable you want to set, or 0 to finish the setup");
        }
    }

    public void printRunDetailsDTO(SimulationRunDetailsDTO runDetails) {
        String terminationMessage = runDetails.isTerminateBySeconds() ?
                String.format("Simulation #%d ended due to seconds condition",  runDetails.getSerialNumber()) :
                String.format("Simulation #%d ended due to tick condition", runDetails.getSerialNumber());

        System.out.println(terminationMessage);
    }

    public void viewEnvironmentVariablesValues(List<PropertyDefinitionDTO> variables) {
        int count = 1;

        printTitle("Environment Variables", '~', ' ', NO_INDENTATION);
        for (PropertyDefinitionDTO dto : variables) {
            System.out.println(count + ". " + dto.getName());
            printLine(Integer.valueOf(count).toString().length() + 2, ' ');
            System.out.println("Value: " + dto.getDefaultValue());
        }
    }
}
