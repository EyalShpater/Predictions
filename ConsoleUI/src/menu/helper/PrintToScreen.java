package menu.helper;

import execution.simulation.api.PredictionsLogic;
import impl.*;
import menu.api.MenuOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PrintToScreen {

    public void printCharNumOfTimes(char c, int numOfTimex) {
        for (int i = 1; i <= numOfTimex; i++) {
            System.out.print(c);
        }
    }

    public void printLine(int length, char c) {
        for (int i = 1; i <= length; i++) {
            System.out.print(c);
        }

        System.out.print(System.lineSeparator());
    }

    public void printLoadFileMenu() {
        printTitle("Welcome to our prediction program", '*', '=');
        System.out.println("Please enter XML simulation file path:");
    }

    public void printTitle(String title, char upperChar, char underLineChar) {
        final int NUM_OF_STARS = 10;

        printCharNumOfTimes(upperChar, NUM_OF_STARS);
        System.out.printf(" %s ", title);
        printCharNumOfTimes(upperChar, NUM_OF_STARS);
        System.out.print(System.lineSeparator());
        printLine(NUM_OF_STARS * 2 + 2 + title.length(), underLineChar);
    }

    public void displayMenu() {
        printTitle("Menu", '*', '=');
        System.out.println("Please choose one of the following options");
        System.out.println(MenuOptions.LOAD_FILE.getValue() + ". Load file");
        System.out.println(MenuOptions.SHOW_SIMULATION_DETAILS.getValue() + ". Present simulation details");
        System.out.println(MenuOptions.RUN_SIMULATION.getValue() + ". Play the simulation");
        System.out.println(MenuOptions.SHOW_PREVIOUS_SIMULATIONS.getValue() + ". Present description of previous simulation");
        System.out.println(MenuOptions.EXIT.getValue() + ". Exit program");
    }

    public void printPropertyDefinitionDTOList(List<PropertyDefinitionDTO> properties) {
        for (int i = 1; i <= properties.size(); i++) {
            System.out.print(i + ". ");
            printPropertyDefinitionDTO(properties.get(i - 1));
            System.out.print(System.lineSeparator());
        }
    }

    public void printPropertyDefinitionDTO(PropertyDefinitionDTO dto) {
        System.out.println("Variable name: " + dto.getName());
        System.out.println("Type: " + dto.getType());
        if (dto.getFrom() != null) {
            System.out.println("Minimum value: " + dto.getFrom());
        }
        if (dto.getTo() != null) {
            System.out.println("Maximum value: " + dto.getTo());
        }
    }

    public void printEntityDefinitionDTOList(List<EntityDefinitionDTO> entities) {
        for (int i = 1; i <= entities.size(); i++) {
            System.out.print(i + ". ");
            printEntityDefinitionDTO(entities.get(i - 1));
            System.out.print(System.lineSeparator());
        }
    }

    public void printEntityDefinitionDTO(EntityDefinitionDTO entity) {
        System.out.println("Name: " + entity.getName());
        System.out.println("Population: " + entity.getPopulation());
        printPropertyDefinitionDTOList(entity.getProperties());
    }

    private void printRuleDTOList(List<RuleDTO> rules) {
        for (int i = 1; i <= rules.size(); i++) {
            System.out.print(i + ". ");
            printRuleDTO(rules.get(i - 1));
            System.out.print(System.lineSeparator());
        }
    }

    private void printRuleDTO(RuleDTO rule) {
        System.out.println("Rule name: " + rule.getName());
        System.out.printf("Activate every %d ticks, with a probability of %.2f", rule.getTicks(), rule.getProbability());
        System.out.println(rule.getName() + " has " + rule.getActionsNames().size() + " actions:");
        IntStream.range(0, rule.getActionsNames().size())
                .forEach(i -> System.out.println((i + 1) + ". " + rule.getActionsNames().get(i)));
    }

    private void printTerminationDTO(TerminationDTO termination) {
        System.out.println(termination.getSecondsToTerminate() + " seconds to terminate");
        System.out.println(termination.getTicksToTerminate() + " ticks to terminate");
    }

    public void printWorldDTO(WorldDTO world) {
        printTitle("Entities:", '#', '~');
        printEntityDefinitionDTOList(world.getEntities());
        System.out.println(System.lineSeparator());
        printTitle("Rules:", '#', '~');
        printRuleDTOList(world.getRules());
        System.out.println(System.lineSeparator());
        printTitle("Termination", '#', '~');
        printTerminationDTO(world.getTermination());
    }

    public void showAllSimulationsDTO(List<SimulationDTO> simulations) {
        printTitle("All simulations:", '~', '-');

        if (simulations != null && !simulations.isEmpty()) {
            int count = 1;
            for (SimulationDTO simulation : simulations) {
                System.out.print(count + ". ");
                printSimulationDTO(simulation);
                count++;
            }
        } else {
            System.out.println("No simulations to show");
        }
    }

    private void printSimulationDTO(SimulationDTO dto) {
        System.out.println("Simulation serial number: " + dto.getSerialNumber());
        System.out.println("Run time: ");
        printTime(dto.getStartDate());
        System.out.print(System.lineSeparator());
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

        printTitle("All Entities", '*', '~');
        for (EntityDefinitionDTO entity : dto.getWorld().getEntities()) {
            simulationData = engine.getSimulationData(dto.getSerialNumber(), entity.getName(), null);
            printTitle("Entity #" + count, '#', ' ');
            System.out.println(entity.getName());
            System.out.println("Start population: " + simulationData.getStarterPopulationQuantity());
            System.out.println("Final population: " + simulationData.getFinalPopulationQuantity());
            count++;
        }
    }

    public void showHistogramViewSimulationDTO(SimulationDataDTO data) {
        Map<Object, Integer> valueCountMap = new HashMap<>();

        for (Object obj : data.getPropertyOfEntitySortedByValues()) {
            valueCountMap.put(obj, valueCountMap.getOrDefault(obj, 0) + 1);
        }

        for (Map.Entry<Object, Integer> entry : valueCountMap.entrySet()) {
            System.out.println(entry.getKey() + " appeared " + entry.getValue() + " times");
        }
    }
}
