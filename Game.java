/**
 * Creates and starts the game
 * 
 * @author Tom Tilli
 * @version 2013-1003
 * @since 1.6
 * 
 */
public class Game {

    private static Board playerField;
    private static Board enemyField;
    private static AI computer;
    private static Ship[] enemyShipArray;
    private static Ship[] playerShipArray;

    /**
     * Creates all the necessary objects for the game.
     * <p>
     * Creates the starting fields and all the Objects needed to run the game.
     * 
     */
    public static void createGame() {
        // TODO Auto-generated method stub

        playerField = new Board(10);
        playerField.createBoard();
        enemyField = new Board(10);
        enemyField.createBoard();
        Ship enemyShip2 = new Ship(2);
        Ship enemyShip3_1 = new Ship(3);
        Ship enemyShip3_2 = new Ship(3);
        Ship enemyShip4 = new Ship(4);
        Ship enemyShip5 = new Ship(5);

        Ship playerShip2 = new Ship(2);
        Ship playerShip3_1 = new Ship(3);
        Ship playerShip3_2 = new Ship(3);
        Ship playerShip4 = new Ship(4);
        Ship playerShip5 = new Ship(5);

        /*
         * //In case of an emergency! Ship[] enemyShipArray = { enemyShip2,
         * enemyShip3_1, enemyShip3_2, enemyShip4, enemyShip5 }; Ship[]
         * playerShipArray = { playerShip2, playerShip3_1, playerShip3_2,
         * playerShip4, playerShip5 };
         */
        enemyShipArray = new Ship[5];
        playerShipArray = new Ship[5];

        enemyShipArray[0] = enemyShip2;
        enemyShipArray[1] = enemyShip3_1;
        enemyShipArray[2] = enemyShip3_2;
        enemyShipArray[3] = enemyShip4;
        enemyShipArray[4] = enemyShip5;

        playerShipArray[0] = playerShip2;
        playerShipArray[1] = playerShip3_1;
        playerShipArray[2] = playerShip3_2;
        playerShipArray[3] = playerShip4;
        playerShipArray[4] = playerShip5;

        enemyShip5.randomlyPlaceShip(enemyField);
        enemyShip4.randomlyPlaceShip(enemyField);
        enemyShip3_2.randomlyPlaceShip(enemyField);
        enemyShip3_1.randomlyPlaceShip(enemyField);
        enemyShip2.randomlyPlaceShip(enemyField);

        PlayerInput.chooseShips(playerField, playerShipArray);

        computer = new AI();

        System.out.println("      Your field");
        playerField.printBoard(true);
        // System.out.println("\n   Computer's field");
        // enemyField.printBoard(true);
        System.out.println();
        System.out.println("\n   Computer's field");
        enemyField.printBoard(false);
        System.out.println();
        System.out.println();
    }

    /**
     * Runs the game.
     * <p>
     * Runs player and AI decisions for the game and keeps the game running
     * until on side has won.
     * 
     */

    public static void startGame() {

        do {
            PlayerInput.shoot(enemyField, enemyShipArray);

            if (enemyField.playerHasShot()) {
                computer.shoot(playerField, playerShipArray);
            }
            System.out.println("      Your field");
            playerField.printBoard(true);
            // System.out.println("\n       Computer");
            // enemyField.printBoard(true);
            System.out.println();
            System.out.println("\n   Computer's field");
            enemyField.printBoard(false);
            System.out.println();
            System.out.println();

        } while (enemyField.gameEnd(enemyShipArray) == false
                && playerField.gameEnd(playerShipArray) == false);

        System.out.println();

        if (enemyField.gameEnd(enemyShipArray)) {
            System.out.println("<><><><><><><><><><><><><><><><>");
            System.out.println("<> CONGRATULATIONS, YOU WIN!  <>");
            System.out.println("<><><><><><><><><><><><><><><><>");
        } else {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("|  Sorry, You lost...  |");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

}
