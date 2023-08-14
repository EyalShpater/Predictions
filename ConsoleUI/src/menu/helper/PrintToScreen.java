package menu.helper;

import api.DTO;
import environment.variable.EnvironmentVariableDTO;
import menu.api.MenuOptions;

import java.util.List;

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

    public void printEnvironmentVariableDTOList(List<DTO> environmentVariables) {
        for (int i = 1; i <= environmentVariables.size(); i++) {
            System.out.print(i + ". ");
            printEnvironmentVariableDTO(environmentVariables.get(i - 1));
            System.out.print(System.lineSeparator());
        }
    }

    public void printEnvironmentVariableDTO(DTO environmentVariable) {
        EnvironmentVariableDTO dto =
                environmentVariable instanceof EnvironmentVariableDTO ?
                        (EnvironmentVariableDTO) environmentVariable :
                        null;

        if (dto == null) {
            throw new IllegalArgumentException();
        }

        System.out.println("Variable name: " + dto.getName());
        System.out.println("Type: " + dto.getType());
        if (dto.getFrom() != null) {
            System.out.println("Minimum value: " + dto.getFrom());
        }
        if (dto.getTo() != null) {
            System.out.println("Maximum value: " + dto.getTo());
        }
    }
}
