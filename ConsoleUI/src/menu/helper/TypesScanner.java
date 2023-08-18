package menu.helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TypesScanner {
    private static final Scanner scanner = new Scanner(System.in);

    public int getIntFromUser() {
        int userIntegerInput = -1;
        boolean validInput;

        do {
            try {
                userIntegerInput = scanner.nextInt();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException exception) {
                System.out.println("This is not an integer number!!!");
                scanner.nextLine();
                validInput = false;
            }

        } while (!validInput);

        return userIntegerInput;
    }

    public int getIntFromUserInRange(int from, int to) {
        int choice = from - 1;
        boolean isValid = false;

        while (!isValid) {
            choice = getIntFromUser();
            isValid = choice >= from && choice <= to;
            if (!isValid) {
                System.out.println("The value must be between " + from + " to " + to);
            }
        }

        return choice;
    }

    public double getDoubleFromUser(){
        double userDoubleInput = -1;
        boolean validInput;

        do {
            try {
                userDoubleInput = scanner.nextInt();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException exception) {
                System.out.println("This is not a real number!!!");
                scanner.nextLine();
                validInput = false;
            }
        } while (!validInput);

        return userDoubleInput;
    }

    public double getDoubleFromUserInRange(double from, double to) {
        double choice = from - 1;
        boolean isValid = false;

        while (!isValid) {
            choice = getDoubleFromUser();
            isValid = choice >= from && choice <= to;
            if (!isValid) {
                System.out.println("The value must be between " + from + " to " + to);
            }
        }

        return choice;
    }

    public boolean getBooleanFromUser() {
        System.out.println("Please choose: ");
        System.out.println("1. True");
        System.out.println("2. False");

        return getIntFromUserInRange(1, 2) == 1;
    }

}
