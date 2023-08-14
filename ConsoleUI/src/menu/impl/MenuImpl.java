package menu.impl;

import action.helper.function.impl.Environment;
import api.DTO;
import environment.variable.EnvironmentVariableDTO;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import menu.api.Menu;
import menu.api.MenuOptions;
import java.util.*;

public class MenuImpl implements Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int EXIT = 5;
    private static final int YES = 1;
    private static final int NO = 2;

    private PredictionsLogic engine = new PredictionsLogicImpl();

    @Override
    public void show() {
        int choice = 0;

        loadFileFromUser();
        while (choice != EXIT) {
            displayMenu();
            choice = scanner.nextInt();

            switch (MenuOptions.fromInt(choice)) {
                case LOAD_FILE:
                    loadFileFromUser();
                    break;
                case SHOW_SIMULATION_DETAILS:
                    showSimulationDetails();
                    break;
                case RUN_SIMULATION:
                    runSimulation();
                    break;
                case SHOW_PREVIOUS_SIMULATIONS:
                    showPreviousSimulation();
                    break;
                case EXIT:
                    choice = EXIT;
                    break;

            }

        }
    }

    private void displayMenu() {
        printTitle("Menu");
        System.out.println("Please choose one of the following options");
        System.out.println(MenuOptions.LOAD_FILE.getValue() + ". Load file");
        System.out.println(MenuOptions.SHOW_SIMULATION_DETAILS.getValue() + ". Present simulation details");
        System.out.println(MenuOptions.RUN_SIMULATION.getValue() + ". Play the simulation");
        System.out.println(MenuOptions.SHOW_PREVIOUS_SIMULATIONS.getValue() + ". Present description of previous simulation");
        System.out.println(MenuOptions.EXIT.getValue() + ". Exit program");
    }

    private void loadFileFromUser() {
        String filePath = "";
        boolean isLegalPath = false;

        printLoadFileMenu();
        while (!isLegalPath) {
            filePath = scanner.nextLine();
            isLegalPath = engine.loadXML(filePath);

            if (!isLegalPath) {
                System.out.println("Cannot create a simulation using this file path.");
                System.out.println("Please try again.");
            }
        }
    }

    //todo: impel
    private void showSimulationDetails() {

    }

    //todo :impel
    private void runSimulation() {
        setEnvironmentVariables();
        //engine.runNewSimulation();
    }

    //todo: impel
    private void showPreviousSimulation() {

    }

    private void printLoadFileMenu() {
        printTitle("Welcome to our prediction program");
        System.out.println("Please enter XML simulation file path:");
    }

    private void printTitle(String title) {
       final int NUM_OF_STARS = 10;

        printStars(NUM_OF_STARS);
        System.out.printf(" %s ", title);
        printStars(NUM_OF_STARS);
        System.out.print(System.lineSeparator());
        printLine(NUM_OF_STARS * 2 + 2 + title.length(), '=');
    }

    private void printStars(int numOfStars) {
        for (int i = 1; i <= numOfStars; i++) {
            System.out.print("*");
        }
    }

    private void printLine(int length, char c) {
        for (int i = 1; i <= length; i++) {
            System.out.print(c);
        }

        System.out.print(System.lineSeparator());
    }

    private void setEnvironmentVariables() {
        List<DTO> environmentVariables = engine.getEnvironmentVariablesToSet();
        List<DTO> updatedVariables = new ArrayList<>();

        environmentVariables.forEach(environmentVariable -> {
            DTO updated = initEnvironmentVariableDTOFromUserInput((EnvironmentVariableDTO)environmentVariable);
            updatedVariables.add(updated);
        });

        engine.setEnvironmentVariablesValues(updatedVariables);
    }

    private DTO SetEnvironmentVariableFromUser(DTO environmentVariable) {
        EnvironmentVariableDTO dto = (EnvironmentVariableDTO) environmentVariable;
        int choice;

        printEnvironmentVariableDTO(dto);
        askUserIfHeWantToUpdateTheVariable(dto.getName());
        choice = getIntFromUserInRange(YES, NO);
        switch (choice) {
            case YES:
                return initEnvironmentVariableDTOFromUserInput(dto);
            case NO:
                return dto;
            default:
                throw new IllegalArgumentException();
        }
    }

    private DTO initEnvironmentVariableDTOFromUserInput(EnvironmentVariableDTO variableDTO) {
        String input;
        Object value;
        boolean isValid = false;

        while (!isValid) {
            System.out.println("Please enter a value");
            input = scanner.nextLine();

            switch (variableDTO.getType()) {
                case "Integer Number":
                   // todo: complete!

            }
        }

        return null;
    }



    private void printEnvironmentVariableDTO(EnvironmentVariableDTO dto) {
        System.out.println("Variable name: " + dto.getName());
        System.out.println("Type: " + dto.getType());
        if (dto.getFrom() != null) {
            System.out.println("Minimum value: " + dto.getFrom());
        }
        if (dto.getTo() != null) {
            System.out.println("Maximum value: " + dto.getTo());
        }
    }

    private void askUserIfHeWantToUpdateTheVariable(String name) {
        System.out.println("Do you want to update " + name + "'s value?");
        System.out.println(YES + ". Yes");
        System.out.println(NO + ". No");
    }

    private int getIntFromUserInRange(int from, int to) {
        int choice = from - 1;
        boolean isValid = false;

        while (!isValid) {
            try {
                choice = scanner.nextInt();
                isValid = (choice >= from && choice <= to);

                if (!isValid) {
                    System.out.println("Input must be between " + from + " and " + to);
                }
            } catch (Exception e){
                System.out.println("You must insert an integer!");
            }
        }

        return choice;
    }

    private double getDoubleFromUserInRange(double from, double to) {
        double choice = from - 1;
        boolean isValid = false;

        while (!isValid) {
            try {
                choice = scanner.nextDouble();
                isValid = (choice >= from && choice <= to);

                if (!isValid) {
                    System.out.println("Input must be between " + from + " and " + to);
                }
            } catch (Exception e) {
                System.out.println("You must insert an double!");
            }
        }

        return choice;
    }

}
