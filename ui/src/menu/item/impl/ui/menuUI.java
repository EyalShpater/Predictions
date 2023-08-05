package menu.item.impl.ui;

import java.util.Scanner;

import menu.item.impl.logic.*;

public class menuUI {
    private final Scanner scanner = new Scanner(System.in);
    private int userChoice;

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

    public void getInput() {
        userChoice = scanner.nextInt();
    }

}
