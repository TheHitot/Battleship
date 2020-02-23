/**
 * Contains all the actions of a player.
 * 
 * @author 	Tom Tilli
 * @version	2013-1003
 * @since 	1.6
 * 
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class PlayerInput {
    private static Scanner input = new Scanner(System.in);

    /**
     * Asks the player a place to shoot on the playing field.
     * <p>
     * Asks the player for a letter and a number to know which spot on the
     * playing field will be shot.
     * 
     * @param x
     *            the board to be shot.
     * @param shipArray
     *            and array of Ships located on the board.
     */
    public static void shoot(Board x, Ship[] shipArray) {
        String[] letterArray = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j" };
        boolean again = true;
        String letter = "";
        int number = 0;

        System.out
                .println("Choose a spot to bomb by entering the corresponding number and letter");

        do {
            System.out.println("Choose a letter");
            letter = input.nextLine();

            for (int i = 0; i < letterArray.length; i++) {
                if (letter.equals(letterArray[i])) {
                    again = false;
                }
            }
            if (again) {
                System.out.println("This letter is not valid");
            }
        } while (again);
        int letterInt = letterToNumber(letter);
        again = true;

        do {
            try {
                System.out.println("Choose a number");
                number = input.nextInt();
                input.nextLine();
                again = false;
                if (number > 10 || number < 1) {
                    System.out.println("This number is not valid");
                    again = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("This number is not valid");
                input.nextLine();
            }
        } while (again);

        x.hitOrMiss(number - 1, letterInt, shipArray);
        x.shipDeath(shipArray);
    }

    /**
     * Changes a letter to a number.
     * <p>
     * Takes a valid input of a letter from A to J and changes it to a number
     * representing it on the playing field array.
     * 
     * @param x
     *            the letter to be changed.
     * @return an integer from 0 to 9.
     */
    public static int letterToNumber(String x) {
        switch (x) {
        case "a":
            return 0;
        case "b":
            return 1;
        case "c":
            return 2;
        case "d":
            return 3;
        case "e":
            return 4;
        case "f":
            return 5;
        case "g":
            return 6;
        case "h":
            return 7;
        case "i":
            return 8;
        case "j":
            return 9;
        }
        return 0;
    }

    /**
     * Asks the player for a spot to place a ship.
     * <p>
     * Asks for a letter and a number representing the spot on the playing field
     * from where the ship will be started from and follows up with a question
     * for the direction to which the ship will be built.
     * 
     * @param x
     *            the board to which the ship will be placed.
     * @param shipArray
     *            the array of Ship objects to be placed.
     */
    public static void chooseShips(Board x, Ship[] shipArray) {

        String[] array = { "", "first ", "second ", "", "" };
        String[] letterArray = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j" };
        boolean again = true;
        String letter = "";
        int number = 0;
        String direction = "";
        int letterInt = 0;

        boolean again2 = false;
        boolean again3 = false;

        do {
            again2 = false;

            System.out
                    .println("Do you want to choose where your ships are yourself? Y/N");
            String notRandom = input.nextLine();

            if (notRandom.equalsIgnoreCase("Y")) {

                x.printBoard(true);
                System.out.println();

                for (int k = 0; k < shipArray.length; k++) {
                    do {

                        System.out.println("Choose where you want your "
                                + array[k] + "size " + shipArray[k].getSize()
                                + " ship to start from.");

                        do {
                            again = true;
                            
                            System.out.println("Choose a letter");
                            letter = input.nextLine();

                            for (int i = 0; i < letterArray.length; i++) {
                                if (letter.equals(letterArray[i])) {
                                    again = false;
                                }
                            }
                            if (again) {
                                System.out.println("This letter is not valid");
                            }
                        } while (again);
                        letterInt = letterToNumber(letter);
                        again = true;

                        do {
                            try {
                                System.out.println("Choose a number");
                                number = input.nextInt() - 1;
                                input.nextLine();
                                again = false;
                                if (number > 9 || number < 0) {
                                    System.out
                                            .println("This number is not valid");
                                    again = true;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("This number is not valid");
                                input.nextLine();
                            }
                        } while (again);

                        do {
                            System.out
                                    .println("Choose which direction you want the ship to extend.");
                            direction = input.nextLine();

                            if (direction.equalsIgnoreCase("w")
                                    || direction.equalsIgnoreCase("a")
                                    || direction.equalsIgnoreCase("s")
                                    || direction.equalsIgnoreCase("d")) {
                                again3 = false;
                            } else {
                                System.out.println("This input is not valid.");
                                again3 = true;
                            }
                        } while (again3);
                    } while (Exception.doesShipFit(x, shipArray[k].getSize(),
                            number, letterInt, direction) == false);

                    shipArray[k].chosenShipLocation(x, number, letterInt,
                            direction, shipArray[k].getSize());
                }

            } else if (notRandom.equalsIgnoreCase("N")) {

                shipArray[0].randomlyPlaceShip(x);
                shipArray[1].randomlyPlaceShip(x);
                shipArray[2].randomlyPlaceShip(x);
                shipArray[3].randomlyPlaceShip(x);
                shipArray[4].randomlyPlaceShip(x);

            } else {
                System.out.println("This on not a valid input.");
                again2 = true;
            }
        } while (again2);

        System.out.println("      GAME START!\n");
    }
}
