/**
 * @author Tom Tilli
 * @version 2013-1003
 * @since 1.6
 * 
 */
public class Ship {

    private int size;
    private String[] ship;
    private int random;
    private int counter = 0;
    private int[] posYArray;
    private int[] posXArray;
    private int deathCounter;

    /**
     * Creates a Ship object with the chosen size.
     * 
     * @param s
     *            the size of the created object.
     */
    public Ship(int s) {
        size = s;
        ship = new String[s];
        for (int i = 0; i < s; i++) {
            ship[i] = " Z";
        }
        posYArray = new int[size];
        posXArray = new int[size];
        deathCounter = size;
    }

    /**
     * Returns the size of a Ship object.
     * 
     * @return the integer input on creation of given object.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the String array containing the markings of the ship.
     * 
     * @return an array containing size amount of " Z".
     */
    public String[] getArray() {
        return ship;
    }

    /**
     * Returns and array containing the position of the Ship on the playing
     * field.
     * 
     * @param choise
     *            with 1 returns the Y axis and with 2 the X axis.
     * @return an array containing the chosen axis.
     */
    public int[] getPosArray(int choise) {
        if (choise == 1) {
            return posYArray;
        }

        return posXArray;
    }

    /**
     * Lowers the death counter of a Ship with 1.
     */
    public void lowerDeathCounter() {
        deathCounter--;
    }

    /**
     * Returns the deathCounter.
     * 
     * @return the integer value of the deathCounter variable.
     */
    public int getDeathCounter() {
        return deathCounter;
    }

    /**
     * Places 5 ships randomly on a playing field and returns it.
     * <p>
     * Automatically inserts 5 ships of the lengths of 2, 3, 3, 4 and 5 on a
     * chosen playing field. The placement follows the rules of the game
     * battleship. After placing the ships the playing field will be returned.
     * 
     * @param x
     *            the board to with the ships will be added.
     * @return the playing field with 5 ships placed.
     */
    public Board randomlyPlaceShip(Board x) {

        boolean again = false;
        int shipLength = 0;
        int positionX = (int) (Math.random() * 10);
        int positionY = (int) (Math.random() * 10);

        while (Exception.shipPlacement(x, positionY, positionX)
                || x.getPlayingField()[positionY][positionX] == " X") {
            positionX = (int) (Math.random() * 10);
            positionY = (int) (Math.random() * 10);
        }
        x.getPlayingField()[positionY][positionX] = ship[shipLength];
        shipLength++;

        do {
            if (counter > 5) {
                x.getPlayingField()[positionY][positionX] = "[]";

                positionX = (int) (Math.random() * 10);
                positionY = (int) (Math.random() * 10);

                while (Exception.shipPlacement(x, positionY, positionX)
                        || x.getPlayingField()[positionY][positionX] == " X") {
                    positionX = (int) (Math.random() * 10);
                    positionY = (int) (Math.random() * 10);
                }
                shipLength = 0;
                x.getPlayingField()[positionY][positionX] = ship[shipLength];
                counter = 0;
                shipLength++;
            }
            counter++;
            try {
                again = false;
                random = (int) (Math.random() * 4);

                boolean collision = false;

                posYArray[0] = positionY;
                posXArray[0] = positionX;

                for (int i = 0; i < ship.length - 1; i++) {
                    if (collision == false) {
                        switch (random) {
                        case 0: // 'W'
                            if (Exception.shipPlacement(x, positionY
                                    - shipLength, positionX) == false) {
                                x.getPlayingField()[positionY - shipLength][positionX] = ship[shipLength];
                                posYArray[i + 1] = positionY - shipLength;
                                posXArray[i + 1] = positionX;
                                shipLength++;
                            } else {
                                collision = true;
                            }
                            break;
                        case 1: // 'A'
                            if (Exception.shipPlacement(x, positionY, positionX
                                    - shipLength) == false) {
                                x.getPlayingField()[positionY][positionX
                                        - shipLength] = ship[shipLength];
                                posYArray[i + 1] = positionY;
                                posXArray[i + 1] = positionX - shipLength;
                                shipLength++;
                            } else {
                                collision = true;
                            }
                            break;
                        case 2: // 'S'
                            if (Exception.shipPlacement(x, positionY
                                    + shipLength, positionX) == false) {
                                x.getPlayingField()[positionY + shipLength][positionX] = ship[shipLength];
                                posYArray[i + 1] = positionY + shipLength;
                                posXArray[i + 1] = positionX;
                                shipLength++;
                            } else {
                                collision = true;
                            }
                            break;
                        case 3: // 'D'
                            if (Exception.shipPlacement(x, positionY, positionX
                                    + shipLength) == false) {
                                x.getPlayingField()[positionY][positionX
                                        + shipLength] = ship[shipLength];
                                posYArray[i + 1] = positionY;
                                posXArray[i + 1] = positionX + shipLength;
                                shipLength++;
                            } else {
                                collision = true;
                            }
                            break;
                        }
                    }
                    if (collision == true) {
                        Exception.removeFailShip(x, random, shipLength,
                                positionY, positionX);
                        shipLength = 1;
                        again = true;
                    }

                }
            } catch (ArrayIndexOutOfBoundsException e) {
                again = true;
                Exception.removeFailShip(x, random, shipLength, positionY,
                        positionX);
                shipLength = 1;

            }

        } while (again);

        counter = 0;

        for (int i = 0; i < x.getPlayingField().length; i++) {
            for (int k = 0; k < x.getPlayingField().length; k++) {
                if (x.getPlayingField()[i][k] == " Z") {
                    x.getPlayingField()[i][k] = " X";
                }
            }
        }

        return x;
    }

    /**
     * Places a ship to a pre-chosen spot on a playing field.
     * 
     * @param x
     *            the board to which the ship will be placed.
     * @param posY
     *            the Y axis position of the ship.
     * @param posX
     *            the X axis position of the ship.
     * @param direction
     *            the direction to which the ship was chosen to be placed.
     * @param shipLength
     *            the length of the ship to be placed.
     */
    public void chosenShipLocation(Board x, int posY, int posX,
            String direction, int shipLength) {

        x.getPlayingField()[posY][posX] = " X";
        posYArray[0] = posY;
        posXArray[0] = posX;

        for (int i = 1; i < shipLength; i++) {
            switch (direction) {
            case "w":
                x.getPlayingField()[posY - i][posX] = " X";
                posYArray[i] = posY - i;
                posXArray[i] = posX;
                break;
            case "a":
                x.getPlayingField()[posY][posX - i] = " X";
                posYArray[i] = posY;
                posXArray[i] = posX - i;
                break;
            case "s":
                x.getPlayingField()[posY + i][posX] = " X";
                posYArray[i] = posY + i;
                posXArray[i] = posX;
                break;
            case "d":
                x.getPlayingField()[posY][posX + i] = " X";
                posYArray[i] = posY;
                posXArray[i] = posX + i;
                break;
            }

        }
        x.printBoard(true);
        System.out.println();
    }

}
