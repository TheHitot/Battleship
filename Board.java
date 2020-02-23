/**
 * Creates a Board object which contains a playing field.
 * 
 * @author Tom Tilli
 * @version 2013-1003
 * @since 1.6
 * 
 */
public class Board {
    private int width;
    private int height;
    private String[][] playingField;
    private int numbers;
    private char letters;
    private int ascii;
    private String[][] visiblePlayingField;
    private boolean goToAITurn;

    /**
     * Creates a Board object with a chosen size.
     * 
     * @size the size of the playing field.
     */
    public Board(int size) {
        width = size;
        height = size;
        playingField = new String[height][width];
        visiblePlayingField = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int z = 0; z < width; z++) {
                visiblePlayingField[i][z] = "[]";
            }
        }
        numbers = 1;
        ascii = 65;
        letters = (char) ascii;
    }

    /**
     * Creates the visual elements to the playing field.
     */
    public void createBoard() {

        for (int i = 0; i < height; i++) {
            for (int z = 0; z < width; z++) {
                playingField[i][z] = "[]";
            }
        }

    }

    /**
     * Prints a two dimensional array.
     * <p>
     * Prints the playing field.
     * 
     * @visible the choice to either have the ships visible when printed or not.
     */
    public void printBoard(boolean visible) {
        if (visible == false) {
            System.out.print(" ");
            for (int i = 0; i < visiblePlayingField.length; i++) {
                System.out.print(" " + letters);
                ascii++;
                letters = (char) ascii;
            }

            for (int i = 0; i < height; i++) {

                System.out.println();

                if (i < height - 1) {
                    System.out.print(numbers + " ");
                    numbers++;
                } else {
                    System.out.print(numbers);
                }
                for (int z = 0; z < width; z++) {
                    System.out.print(visiblePlayingField[i][z]);
                }

            }
            numbers = 1;
            ascii = 65;
            letters = (char) ascii;
        } else {
            System.out.print(" ");
            for (int i = 0; i < playingField.length; i++) {
                System.out.print(" " + letters);
                ascii++;
                letters = (char) ascii;
            }

            for (int i = 0; i < height; i++) {

                System.out.println();

                if (i < height - 1) {
                    System.out.print(numbers + " ");
                    numbers++;
                } else {
                    System.out.print(numbers);
                }
                for (int z = 0; z < width; z++) {
                    System.out.print(playingField[i][z]);
                }

            }
            numbers = 1;
            ascii = 65;
            letters = (char) ascii;
        }
    }

    /**
     * Sets a given String to the visiblePlayingField array.
     * 
     * @param posY
     *            the Y axis on the board.
     * @param posX
     *            the X axis on the board. @ x the String to be added.
     */
    public void setVisiblePlayingField(int posY, int posX, String x) {
        visiblePlayingField[posY][posX] = x;
    }

    /**
     * Returns the visiblePlayingField.
     * 
     * @return the playing field visible for the player.
     */
    public String[][] getVisiblePlayingField() {
        return visiblePlayingField;
    }

    /**
     * Sets a given string to the playingField array.
     * 
     * @param posY
     *            the Y axis on the board.
     * @param posX
     *            the X axis on the board.
     * @param x
     *            the String to be added.
     */
    public void setPlayingField(int posY, int posX, String x) {
        playingField[posY][posX] = x;
    }

    /**
     * Returns the playingField.
     * 
     * @return the playing field invisible for the player.
     */
    public String[][] getPlayingField() {
        return playingField;
    }

    /**
     * Returns the String from the playingField from a specific spot.
     * 
     * @param posY
     *            the Y axis on the board.
     * @param posX
     *            the X axis on the board.
     */
    public String getFieldPosition(int posY, int posX) {
        return playingField[posY][posX];
    }

    /**
     * Returns the boolean goToAITurn;
     * 
     * @param a
     *            true or false stating if the AI can start its turn.
     */
    public boolean playerHasShot() {
        return goToAITurn;
    }

    /**
     * Checks if a shot has hit a ship.
     * <p>
     * Chances the ascii on the board to an X if there was a ship on the given
     * spot and a ? if there wasn't.
     * 
     * @param posY
     *            the Y axis on the board.
     * @param posX
     *            the X axis on the board.
     * @param shipArray
     *            an array of Ship objects to compare.
     */
    public void hitOrMiss(int posY, int posX, Ship[] shipArray) {
        goToAITurn = true;

        if (playingField[posY][posX] == " X") {
            playingField[posY][posX] = " O";
            visiblePlayingField[posY][posX] = " O";
            for (int i = 0; i < shipArray.length; i++) {
                shipHit(posY, posX, shipArray[i]);
            }
        } else if (playingField[posY][posX] == " O"
                || playingField[posY][posX] == " ?") {
            System.out.println("You have already shot this spot");
            goToAITurn = false;
        } else {
            playingField[posY][posX] = " ?";
            visiblePlayingField[posY][posX] = " ?";
        }
    }

    /**
     * Lowers the deathCounter of a hit ship.
     * 
     * @param posY
     *            the Y axis location of the shot.
     * @param posX
     *            the X axis location of the shot.
     * @param x
     *            the Ship which will have its counter decreased.
     */
    public void shipHit(int posY, int posX, Ship x) {
        for (int i = 0; i < x.getSize(); i++) {
            if (posY == x.getPosArray(1)[i]) {
                if (posX == x.getPosArray(2)[i]) {

                    x.lowerDeathCounter();
                }
            }

        }
    }

    /**
     * Checks if a ship sunk after it has been hit.
     * <p>
     * Takes a array of Ship objects and checks if any of then has got their
     * deathCounter lowered to 0.
     * 
     * @param shipArray
     *            the array of Ship objects to compare.
     */
    public void shipDeath(Ship[] shipArray) {
        int sank = shipArray.length;
        Ship[] deadShips = new Ship[shipArray.length];
        int shipCounter = 0;

        for (int i = 0; i < shipArray.length; i++) {
            if (shipArray[i].getDeathCounter() == 0) {

                deadShips[shipCounter] = shipArray[i];

                if (deadShips[shipCounter] == shipArray[i]) {
                    System.out.println("An enemy ship was destroyed!");
                    shipCounter++;
                    sank--;
                }
                System.out.println(sank + "/5 ships left to sink.");
            }
        }
    }

    /**
	 * 
	 */
    public Ship whichShipHit(Ship[] shipArray, int posY, int posX) {

        for (int i = 0; i < shipArray.length; i++) {
            for (int k = 0; k < shipArray[i].getSize(); k++) {
                if (posY == shipArray[i].getPosArray(1)[k]) {
                    if (posX == shipArray[i].getPosArray(2)[k]) {

                        return shipArray[i];
                    }
                }

            }
        }
        System.out.println("Joku meni pieleen");
        return new Ship(0);
    }

    /**
	 * 
	 */
    public boolean gameEnd(Ship[] shipArray) {
        int test = 0;

        for (int i = 0; i < shipArray.length; i++) {
            test = test + shipArray[i].getDeathCounter();
        }

        if (test == 0) {
            return true;
        }
        return false;
    }
}
