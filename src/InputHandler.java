package src;

import fri.shapesge.*;

public class InputHandler {
    private Game game;
    private Dummy selectedDummy;
    private SoundHandler soundHandler;

    public InputHandler(Game game) {
        Manager manager = new Manager();
        manager.manageObject(this);
        this.game = game;
        this.selectedDummy = null;
        this.soundHandler = new SoundHandler();
    }

    public void clickLeft(int x, int y) {
        int tileX = getTileX(x);
        int tileY = getTileY(y);

        if (selectedDummy == null) {
            selectedDummy = game.getBoardDummy(tileX, tileY);

            if (selectedDummy == null) {
                System.out.println("Dummy not found at: " + tileX + ", " + tileY);
                return;
            }

            if (selectedDummy.getPlayerOwner().getPlayerID() == 1 && !game.getPlayer1().getPlayerTurn()) {
                System.out.println("Not your turn.");
                selectedDummy = null;
                return;
            } else if (selectedDummy.getPlayerOwner().getPlayerID() == 2 && !game.getPlayer2().getPlayerTurn()) {
                System.out.println("Not your turn.");
                selectedDummy = null;
                return;
            }

            if (selectedDummy != null) {
                System.out.println("Piece selected at: " + tileX + ", " + tileY);
            }
        } else {
            if ((tileX + tileY) % 2 == 0) {
                System.out.println("Invalid move.");
                return;
            }

            if (isValidMove(selectedDummy, tileX, tileY)) {
                moveDummy(selectedDummy, tileX, tileY);
                selectedDummy = null;
                System.out.println("Moved piece to: " + tileX + ", " + tileY);

                game.getPlayer1().setPlayerTurn(!game.getPlayer1().getPlayerTurn());
                game.getPlayer2().setPlayerTurn(!game.getPlayer2().getPlayerTurn());

                game.drawTurn();
            } else {
                System.out.println("Invalid move.");
            }
        }
    }

    private boolean isValidMove(Dummy dummy, int x, int y) {
        int startX = dummy.getPositionX() / 100;
        int startY = dummy.getPositionY() / 100;

        if (dummy.getType() == 2) {
            if (Math.abs(x - startX) == Math.abs(y - startY)) {
                int stepX = (x - startX) > 0 ? 1 : -1;
                int stepY = (y - startY) > 0 ? 1 : -1;

                int currentX = startX + stepX;
                int currentY = startY + stepY;
                boolean foundEnemy = false;

                while (currentX != x && currentY != y) {
                    Dummy obstacle = game.getBoardDummy(currentX, currentY);
                    if (obstacle != null) {
                        if (obstacle.getPlayerOwner().getPlayerID() == dummy.getPlayerOwner().getPlayerID()) {
                            return false;
                        } else if (obstacle.getPlayerOwner().getPlayerID() != dummy.getPlayerOwner().getPlayerID()) {
                            Dummy nextTile = game.getBoardDummy(currentX + stepX, currentY + stepY);
                            if (nextTile == null) {
                                foundEnemy = true;
                            } else {
                                return false;
                            }
                        }
                    }
                    currentX += stepX;
                    currentY += stepY;
                }

                if (foundEnemy) {
                    return true;
                }

                Dummy destinationDummy = game.getBoardDummy(x, y);
                if (destinationDummy == null) {
                    return true;
                }
            }
        } else {
            if (Math.abs(x - startX) == 1 && Math.abs(y - startY) == 1) {
                return game.getBoardDummy(x, y) == null;
            }

            if (Math.abs(x - startX) == 2 && Math.abs(y - startY) == 2) {
                int midX = (startX + x) / 2;
                int midY = (startY + y) / 2;

                Dummy midDummy = game.getBoardDummy(midX, midY);
                if (midDummy != null && midDummy.getPlayerOwner().getPlayerID() != dummy.getPlayerOwner().getPlayerID()
                        && game.getBoardDummy(x, y) == null) {
                    return true;
                }
            }
        }

        return false;
    }

    private void moveDummy(Dummy dummy, int x, int y) {
        int oldX = dummy.getPositionX() / 100;
        int oldY = dummy.getPositionY() / 100;

        if (dummy.getType() == 2) {
            int stepX = (x - oldX) > 0 ? 1 : -1;
            int stepY = (y - oldY) > 0 ? 1 : -1;

            int currentX = oldX + stepX;
            int currentY = oldY + stepY;

            while (currentX != x && currentY != y) {
                Dummy obstacle = game.getBoardDummy(currentX, currentY);
                if (obstacle != null) {
                    if (obstacle.getPlayerOwner().getPlayerID() != dummy.getPlayerOwner().getPlayerID()) {
                        game.getBoard()[currentY][currentX] = null;
                        obstacle.getPlayerOwner().removeDummy(obstacle);
                        game.removeDummyFromGame(obstacle);
                        System.out.println("Enemy dummy removed at: " + currentX + ", " + currentY);
                    } else {
                        return;
                    }
                }
                currentX += stepX;
                currentY += stepY;
            }

            Dummy destinationDummy = game.getBoardDummy(x, y);
            if (destinationDummy != null) {
                return;
            }
        }

        dummy.dummyMove(dummy, x * 100, y * 100);

        game.getBoard()[oldY][oldX] = null;
        game.getBoard()[y][x] = dummy;

        if ((dummy.getPlayerOwner().getPlayerID() == 1 && y == 7)
                || (dummy.getPlayerOwner().getPlayerID() == 2 && y == 0)) {
            dummy.setType(2);
            soundHandler.playSound("promote.wav");
            return;
        }

        soundHandler.playSound("board.wav");
    }

    public int getTileX(int x) {
        return x / 100;
    }

    public int getTileY(int y) {
        return y / 100;
    }

    public void clickRight(int x, int y) {
        printBoard();
    }

    public void clickMiddle(int x, int y) {
        int tileX = getTileX(x);
        int tileY = getTileY(y);
        Dummy hoverDummy = game.getBoardDummy(tileX, tileY);
        if (hoverDummy == null) {
            System.out.println("Dummy not found at: " + tileX + ", " + tileY);
            return;
        }
        game.removeDummyFromGame(hoverDummy);
    }

    public void exit() {
        System.out.println("Exiting the game...");
        System.exit(0);
    }

    public void printBoard() {
        System.out.println("Current board state:");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Dummy dummy = game.getBoardDummy(j, i);

                if (dummy != null) {
                    System.out.print("[" + (dummy.getPlayerOwner().getPlayerID()) + ":" + dummy.getDummyID() + "] ");
                } else {
                    System.out.print("[empty] ");
                }
            }
            System.out.println();
        }
    }
}
