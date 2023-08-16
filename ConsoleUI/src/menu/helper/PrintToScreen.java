package menu.helper;

import impl.*;
import menu.api.MenuOptions;

import java.util.List;
import java.util.stream.IntStream;

public class PrintToScreen {

    public void printStars(int numOfStars) {
        for (int i = 1; i <= numOfStars; i++) {
            System.out.print("*");
        }
    }

    public void printLine(int length, char c) {
        for (int i = 1; i <= length; i++) {
            System.out.print(c);
        }

        System.out.print(System.lineSeparator());
    }

    public void printLoadFileMenu() {
        printTitle("Welcome to our prediction program");
        System.out.println("Please enter XML simulation file path:");
    }

    public void printTitle(String title) {
        final int NUM_OF_STARS = 10;

        printStars(NUM_OF_STARS);
        System.out.printf(" %s ", title);
        printStars(NUM_OF_STARS);
        System.out.print(System.lineSeparator());
        printLine(NUM_OF_STARS * 2 + 2 + title.length(), '=');
    }

    public void displayMenu() {
        printTitle("Menu");
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

    private void printEntityDefinitionDTOList(List<EntityDefinitionDTO> entities) {
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
        printTitle("Entities:");
        printEntityDefinitionDTOList(world.getEntities());
        System.out.println(System.lineSeparator());
        printTitle("Rules:");
        printRuleDTOList(world.getRules());
        System.out.println(System.lineSeparator());
        printTitle("Termination");
        printTerminationDTO(world.getTermination());
    }

}
