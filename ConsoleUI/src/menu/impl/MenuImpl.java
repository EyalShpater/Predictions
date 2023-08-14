package menu.impl;

import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import menu.api.Menu;
import menu.api.MenuOptions;

import java.util.Scanner;

public class MenuImpl implements Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final int EXIT = 5;

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

    public void displayMenu() {

        System.out.println("************** Welcome to our prediction program **************");
        System.out.println("===============================================================");
        System.out.println("            Please choose one of the following option:         ");
        System.out.println("            1.Load a simulation:                               ");
        System.out.println("            2.Present simulation details:                      ");
        System.out.println("            3.Play the simulation:                             ");
        System.out.println("            4.Present description of previous simulation:      ");
        System.out.println("            5.Exit program :                                   ");
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
    private void runSimulation() {}

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

}
