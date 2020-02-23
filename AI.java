/**
 * Controls the AI of the opponent.
 * 
 * @author 	Tom Tilli
 * @version	2013-1003
 * @since 	1.6
 * 
 */

import java.lang.ArrayIndexOutOfBoundsException;

public class AI {

    private int nextPosY;
    private int nextPosX;
    private boolean destroyingShip;
    private boolean horizontal;
    private int firstPosY;
    private int firstPosX;
    private boolean continueShot;
    private boolean stopW;
    private boolean stopA;
    private boolean stopS;
    private boolean stopD;
    private Ship shipHit;

    /**
     * Creates an AI object with a starting value of "continueShot" at false.
     */
    public AI() {
        continueShot = false;
    }

    /**
     * Makes the AI shoot a spot on the players field.
     * 
     * @param x
     *            the board where the AI will shoot to.
     * @param shipArray
     *            an array of Ship objects placed on the board.
     */
    public void shoot(Board x, Ship[] shipArray) {
        boolean again = true;
        // boolean continueShot = false;

        if (continueShot) {

            // shipHit = x.whichShipHit(shipArray, firstPosY, firstPosX);

            afterHit(x, shipArray, firstPosY, firstPosX);

        } else {
            do {
                again = true;

                firstPosY = (int) (Math.random() * 10);
                firstPosX = (int) (Math.random() * 10);

                if (x.getPlayingField()[firstPosY][firstPosX] == "[]"
                        || x.getPlayingField()[firstPosY][firstPosX] == " X") {
                    x.hitOrMiss(firstPosY, firstPosX, shipArray);
                    again = false;
                }
            } while (again);

            if (x.getPlayingField()[firstPosY][firstPosX] == " O") {
                continueShot = true;
            }
        }

    }

    /**
     * Chooses how the AI will act after hitting a ship.
     * 
     * @param x
     *            the board where the AI shot.
     * @param shipArray
     *            an array of Ship object placed on the board.
     * @param posY
     *            the Y axis position on the board.
     * @param posX
     *            the X axis position on the board.
     */
    public void afterHit(Board x, Ship[] shipArray, int posY, int posX) {

        if (destroyingShip == false) {
            firstTry(x, shipArray, posY, posX);
        } else {
            continueTry(x, shipArray, nextPosY, nextPosX);
        }
    }

    /**
     * Makes the AI try and hit a ship again.
     * <p>
     * Chooses from 4 possible directions to where the ship might continue and
     * shoots there if the spot hasn't already been shot.
     * 
     * @param x
     *            the board to where the AI will shoot.
     * @param shipArray
     *            an array of Ship objects located at the board.
     * @param posY
     *            the Y axis position on the board.
     * @param posX
     *            the X axis position on the board.
     */
    public void firstTry(Board x, Ship[] shipArray, int posY, int posX) {

        boolean again = false;
        String exceptionTest;

        int random = 0;

        do {
            random = (int) (Math.random() * 4);

            again = false;
            try {
                switch (random) {
                case 0: // W
                    exceptionTest = x.getPlayingField()[posY - 1][posX];

                    nextPosY = posY - 1;
                    nextPosX = posX;
                    horizontal = true;

                    if (x.getPlayingField()[posY - 1][posX] == " O"
                            || x.getPlayingField()[posY - 1][posX] == " ?") {
                        again = true;

                    } else {
                        x.hitOrMiss(posY - 1, posX, shipArray);
                    }

                    break;
                case 1: // A
                    exceptionTest = x.getPlayingField()[posY][posX - 1];

                    nextPosY = posY;
                    nextPosX = posX - 1;
                    horizontal = false;

                    if (x.getPlayingField()[posY][posX - 1] == " O"
                            || x.getPlayingField()[posY][posX - 1] == " ?") {
                        again = true;

                    } else {
                        x.hitOrMiss(posY, posX - 1, shipArray);
                    }

                    break;
                case 2: // S
                    exceptionTest = x.getPlayingField()[posY + 1][posX];

                    nextPosY = posY + 1;
                    nextPosX = posX;
                    horizontal = true;

                    if (x.getPlayingField()[posY + 1][posX] == " O"
                            || x.getPlayingField()[posY + 1][posX] == " ?") {
                        again = true;

                    } else {
                        x.hitOrMiss(posY + 1, posX, shipArray);
                    }

                    break;
                case 3: // D
                    exceptionTest = x.getPlayingField()[posY][posX + 1];

                    nextPosY = posY;
                    nextPosX = posX + 1;
                    horizontal = false;

                    if (x.getPlayingField()[posY][posX + 1] == " O"
                            || x.getPlayingField()[posY][posX + 1] == " ?") {
                        again = true;

                    } else {
                        x.hitOrMiss(posY, posX + 1, shipArray);
                    }

                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {

                again = true;
            }
        } while (again);

        if (x.getPlayingField()[nextPosY][nextPosX] == " O") {
            shipHit = x.whichShipHit(shipArray, nextPosY, nextPosX);
            destroyingShip = true;
            if (shipHit.getDeathCounter() == 0) {
                continueShot = false;
                destroyingShip = false;
            }
        } else {
            destroyingShip = false;
        }

    }

    /**
     * Chooses how the AI will behave after it has hit a ship twice.
     * <p>
     * Makes the AI shoot in the same axis to where two shots hit the same ship
     * twice. If the AI hits an empty shot, it will turn around and start
     * shooting in the opposite direction. The AI will continue to shoot until
     * it has sank the ship.
     * 
     * @param x
     *            the board to where the AI will shoot.
     * @param shipArray
     *            an array of Ship objects located on the board.
     * @param posY
     *            the Y axis position on the board.
     * @param posX
     *            the X axis position on the board.
     */
    public void continueTry(Board x, Ship[] shipArray, int posY, int posX) {

        int nextShot = (int) (Math.random() * 2);
        int add = 1;
        boolean again = false;
        boolean shootAgain = false;

        do {
            shootAgain = false;
            try {
                if (horizontal) {

                    if (nextShot == 0 && stopW == false) {

                        do {
                            again = false;
                            if (x.getPlayingField()[posY - add][posX] == "[]"
                                    || x.getPlayingField()[posY - add][posX] == " X") {
                                x.hitOrMiss(posY - add, posX, shipArray);
                                if (x.getPlayingField()[posY - add][posX] == " ?") {
                                    stopW = true;
                                }
                            } else if (x.getPlayingField()[posY - add][posX] == " ?") {
                                stopW = true;
                                shootAgain = true;
                                nextShot = 1;
                                add = 1;
                            } else if (x.getPlayingField()[posY - add][posX] == " O") {
                                add++;
                                again = true;
                            }
                        } while (again);

                    } else if (nextShot == 1 && stopS == false) {
                        do {
                            again = false;
                            if (x.getPlayingField()[posY + add][posX] == "[]"
                                    || x.getPlayingField()[posY + add][posX] == " X") {
                                x.hitOrMiss(posY + add, posX, shipArray);
                                if (!(x.getPlayingField()[posY + add][posX] == " O")) {
                                    stopS = true;
                                }
                            } else if (x.getPlayingField()[posY + add][posX] == " ?") {
                                stopS = true;
                                shootAgain = true;
                                nextShot = 0;
                                add = 1;
                            } else if (x.getPlayingField()[posY + add][posX] == " O") {
                                add++;
                                again = true;
                            }
                        } while (again);

                    }

                } else {

                    if (nextShot == 0 && stopA == false) {

                        do {
                            again = false;
                            if (x.getPlayingField()[posY][posX - add] == "[]"
                                    || x.getPlayingField()[posY][posX - add] == " X") {
                                x.hitOrMiss(posY, posX - add, shipArray);
                                if (!(x.getPlayingField()[posY][posX - add] == " O")) {
                                    stopA = true;
                                }
                            } else if (x.getPlayingField()[posY][posX - add] == " ?") {
                                stopA = true;
                                shootAgain = true;
                                nextShot = 1;
                                add = 1;
                            } else if (x.getPlayingField()[posY][posX - add] == " O") {
                                add++;
                                again = true;
                            }
                        } while (again);

                    } else if (nextShot == 1 && stopD == false) {
                        do {
                            again = false;
                            if (x.getPlayingField()[posY][posX + add] == "[]"
                                    || x.getPlayingField()[posY][posX + add] == " X") {
                                x.hitOrMiss(posY, posX + add, shipArray);
                                if (!(x.getPlayingField()[posY][posX + add] == " O")) {
                                    stopS = true;
                                }
                            } else if (x.getPlayingField()[posY][posX + add] == " ?") {
                                stopS = true;
                                shootAgain = true;
                                nextShot = 0;
                                add = 1;
                            } else if (x.getPlayingField()[posY][posX + add] == " O") {
                                add++;
                                again = true;
                            }
                        } while (again);

                    }

                }
            } catch (ArrayIndexOutOfBoundsException e) {
                shootAgain = true;
                nextShot = (int) (Math.random() * 2);
                add = 1;
            }
        } while (shootAgain);

        if (shipHit.getDeathCounter() == 0) {
            continueShot = false;
            stopW = false;
            stopA = false;
            stopS = false;
            stopD = false;
            destroyingShip = false;
        }

    }
}
