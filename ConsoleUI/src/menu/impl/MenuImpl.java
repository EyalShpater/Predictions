package menu.impl;

import impl.PropertyDefinitionDTO;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import impl.SimulationDTO;
import impl.WorldDTO;
import menu.api.Menu;
import menu.api.MenuOptions;
import menu.helper.PrintToScreen;
import menu.helper.TypesScanner;

import java.util.*;
import java.util.stream.Collectors;

public class MenuImpl implements Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TypesScanner typeScanner = new TypesScanner();
    private static final PrintToScreen printer = new PrintToScreen();

    private static final int EXIT = 5;
    private static final int YES = 1;
    private static final int NO = 2;

    private PredictionsLogic engine = new PredictionsLogicImpl();

    @Override
    public void show() {
        int choice = 0;

        //loadFileFromUser();
        engine.hardCodeWorldInit();
        while (choice != EXIT) {
            printer.displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

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
                    // save to file (bonus)
                    break;
            }
        }
    }

    private void loadFileFromUser() {
        String filePath;
        boolean isLegalPath = false;

        printer.printLoadFileMenu();
        while (!isLegalPath) {
            filePath = scanner.nextLine();
            isLegalPath = engine.loadXML(filePath);

            if (!isLegalPath) {
                System.out.println("Cannot create a simulation using this file path.");
                System.out.println("Please try again.");
            }
        }
    }

    private void showSimulationDetails() {
        WorldDTO details = engine.getSimulationDetails();

        printer.printWorldDTO(details);
    }

    private void runSimulation() {
        List<PropertyDefinitionDTO> updatedEnvironmentVariables = getEnvironmentVariablesFromUser();

        engine.runNewSimulation(updatedEnvironmentVariables);
    }

    private void showPreviousSimulation() {
        List<SimulationDTO> allSimulations = engine.getPreviousSimulations();

        printer.printTitle("Get Previous Simulations Data");
        System.out.println("Please choose a simulation");
        printer.showAllSimulationsDTO(allSimulations);
        typeScanner.getIntFromUserInRange(1, allSimulations.size());
        printer.printSimulationDetailViewOptions();

    }

    //todo: separate it to sub functions.
    private List<PropertyDefinitionDTO> getEnvironmentVariablesFromUser() {
        List<PropertyDefinitionDTO> environmentVariables = engine.getEnvironmentVariablesToSet();
        int choice = -1;

        if (environmentVariables != null) {
            printer.printTitle("Set Environment Variables");
            printer.printPropertyDefinitionDTOList(
                    environmentVariables
                            .stream()
                            .map(variable -> (PropertyDefinitionDTO) variable)
                            .collect(Collectors.toList())
            );
            System.out.println(System.lineSeparator());
            System.out.println("Please enter the number of variable you want to set");
            choice = typeScanner.getIntFromUserInRange(1, environmentVariables.size());

            while (choice != 0) {
                PropertyDefinitionDTO toUpdate = (PropertyDefinitionDTO) environmentVariables.get(choice - 1);
                environmentVariables.set(choice - 1, initEnvironmentVariableDTOFromUserInput(toUpdate));
                System.out.println("Please enter the number of variable you want to set");
                choice = typeScanner.getIntFromUserInRange(1, environmentVariables.size());
            }
        }

        return environmentVariables;
    }

    private PropertyDefinitionDTO initEnvironmentVariableDTOFromUserInput(PropertyDefinitionDTO variableDTO) {
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

    private void askUserIfHeWantToUpdateTheVariable(String name) {
        System.out.println("Do you want to update " + name + "'s value?");
        System.out.println(YES + ". Yes");
        System.out.println(NO + ". No");
    }
}
