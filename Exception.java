/**
 * Checks if it is ok to add a ship to a location.
 * 
 * @author 	Tom Tilli
 * @version	2013-1003
 * @since 	1.6
 * 
 */

import java.lang.ArrayIndexOutOfBoundsException;

public class Exception {
    /**
     * Checks it there is an X around the specified spot in an array.
     * 
     * @param x
     *            the board to where a ship will be added.
     * @param posY
     *            the Y axis position on the board.
     * @param posX
     *            the X axis position on the board.
     * @return true if there is an X around the spot.
     */
    public static boolean shipPlacement(Board x, int posY, int posX) {

        try {
            if (x.getPlayingField()[posY - 1][posX - 1] == " X"
                    || x.getPlayingField()[posY - 1][posX] == " X"
                    || x.getPlayingField()[posY - 1][posX + 1] == " X"
                    || x.getPlayingField()[posY][posX - 1] == " X"
                    || x.getPlayingField()[posY][posX + 1] == " X"
                    || x.getPlayingField()[posY + 1][posX - 1] == " X"
                    || x.getPlayingField()[posY + 1][posX] == " X"
                    || x.getPlayingField()[posY + 1][posX + 1] == " X") {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {

            try {
                if (posY == 0) {
                    if (posX == 0) {
                        if (x.getPlayingField()[posY][posX + 1] == " X"
                                || x.getPlayingField()[posY + 1][posX] == " X"
                                || x.getPlayingField()[posY + 1][posX + 1] == " X") {
                            return true;
                        }
                    } else if (posX == x.getPlayingField().length - 1) {
                        if (x.getPlayingField()[posY][posX - 1] == " X"
                                || x.getPlayingField()[posY + 1][posX] == " X"
                                || x.getPlayingField()[posY + 1][posX - 1] == " X") {
                            return true;
                        }
                    } else {
                        if (x.getPlayingField()[posY][posX + 1] == " X"
                                || x.getPlayingField()[posY + 1][posX] == " X"
                                || x.getPlayingField()[posY][posX - 1] == " X"
                                || x.getPlayingField()[posY + 1][posX + 1] == " X"
                                || x.getPlayingField()[posY + 1][posX - 1] == " X") {
                            return true;
                        }

                    }
                } else if (posY == x.getPlayingField().length - 1) {
                    if (posX == 0) {
                        if (x.getPlayingField()[posY][posX + 1] == " X"
                                || x.getPlayingField()[posY - 1][posX] == " X"
                                || x.getPlayingField()[posY - 1][posX + 1] == " X") {
                            return true;
                        }
                    } else if (posX == x.getPlayingField().length - 1) {
                        if (x.getPlayingField()[posY][posX - 1] == " X"
                                || x.getPlayingField()[posY - 1][posX] == " X"
                                || x.getPlayingField()[posY - 1][posX - 1] == " X") {
                            return true;
                        }
                    } else {
                        if (x.getPlayingField()[posY][posX + 1] == " X"
                                || x.getPlayingField()[posY - 1][posX] == " X"
                                || x.getPlayingField()[posY][posX - 1] == " X"
                                || x.getPlayingField()[posY - 1][posX + 1] == " X"
                                || x.getPlayingField()[posY - 1][posX - 1] == " X") {
                            return true;
                        }
                    }
                } else if (posX == 0) {
                    if (x.getPlayingField()[posY][posX + 1] == " X"
                            || x.getPlayingField()[posY - 1][posX] == " X"
                            || x.getPlayingField()[posY + 1][posX] == " X"
                            || x.getPlayingField()[posY + 1][posX + 1] == " X"
                            || x.getPlayingField()[posY - 1][posX + 1] == " X") {
                        return true;
                    }
                } else if (posX == x.getPlayingField().length - 1) {
                    if (x.getPlayingField()[posY][posX - 1] == " X"
                            || x.getPlayingField()[posY - 1][posX] == " X"
                            || x.getPlayingField()[posY + 1][posX] == " X"
                            || x.getPlayingField()[posY + 1][posX - 1] == " X"
                            || x.getPlayingField()[posY - 1][posX - 1] == " X") {
                        return true;
                    }
                } else {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException h) {
            }
            ;
        }
        ;

        return false;
    }

    /**
     * Removes a ship which has been placed to an invalid location.
     * <p>
     * Chances the unwanted X:s marking the ship back to empty spots on the
     * playing field.
     * 
     * @param x
     *            the board from which the ship will be removed.
     * @param direction
     *            the direction to which the unwanted ship was placed.
     * @param length
     *            the length of the unwanted ship.
     */
    public static void removeFailShip(Board x, int direction, int length,
            int posY, int posX) {
        int removeLength = length - 1;

        for (int i = 0; i < length - 1; i++) {
            switch (direction) {
            case 0: // 'W'

                x.getPlayingField()[posY - removeLength][posX] = "[]";

                break;
            case 1: // 'A'

                x.getPlayingField()[posY][posX - removeLength] = "[]";

                break;
            case 2: // 'S'

                x.getPlayingField()[posY + removeLength][posX] = "[]";

                break;
            case 3: // 'D'

                x.getPlayingField()[posY][posX + removeLength] = "[]";

                break;
            }
            removeLength--;
        }
    }

    /**
     * Checks if a ship fits in a spot chosen by the player.
     * 
     * @param x
     *            the board to which the ship will be placed.
     * @param shipLength
     *            the lenght of the ship.
     * @param posY
     *            the Y axis position on the board.
     * @param posX
     *            the X axis position on the board.
     * @param direction
     *            the direction of the ship.
     */
    public static boolean doesShipFit(Board x, int shipLength, int posY,
            int posX, String direction) {

        try {
            if (x.getPlayingField()[posY][posX] == "[]") {

                if (shipPlacement(x, posY, posX) == false) {

                    for (int i = 1; i < shipLength; i++) {
                        switch (direction) {
                        case "w":
                            if (x.getPlayingField()[posY - i][posX] == "[]") {
                                if (shipPlacement(x, posY - i, posX)) {
                                    System.out
                                            .println("The spot where you wanted to place your ship was not big enough.");
                                    return false;
                                }
                            }
                            break;
                        case "a":
                            if (x.getPlayingField()[posY][posX - i] == "[]") {
                                if (shipPlacement(x, posY, posX - i)) {
                                    System.out
                                            .println("The spot where you wanted to place your ship was not big enough.");
                                    return false;
                                }
                            }
                            break;
                        case "s":
                            if (x.getPlayingField()[posY + 1][posX] == "[]") {
                                if (shipPlacement(x, posY + i, posX)) {
                                    System.out
                                            .println("The spot where you wanted to place your ship was not big enough.");
                                    return false;
                                }
                            }
                            break;
                        case "d":
                            if (x.getPlayingField()[posY][posX + 1] == "[]") {
                                if (shipPlacement(x, posY, posX + i)) {
                                    System.out
                                            .println("The spot where you wanted to place your ship was not big enough.");
                                    return false;
                                }
                            }
                            break;
                        }

                    }
                    return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out
                    .println("The spot where you wanted to place your ship was not big enough.");
            return false;
        }

        System.out
                .println("The spot where you wanted to place your ship was not big enough.");
        return false;
    }

}
