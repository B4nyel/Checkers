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
            if (selectedDummy != null) {
                System.out.println("Piece selected at: " + tileX + ", " + tileY);
            }
        } else {
            if (isValidMove(selectedDummy, tileX, tileY)) {
                moveDummy(selectedDummy, tileX, tileY);
                selectedDummy = null;
                System.out.println("Moved piece to: " + tileX + ", " + tileY);
            } else {
                System.out.println("Invalid move.");
            }
        }
    }

    private boolean isValidMove(Dummy dummy, int x, int y) {
        return game.getBoardDummy(x, y) == null;
    }

    private void moveDummy(Dummy dummy, int x, int y) {
        int oldX = dummy.getPositionX() / 100;
        int oldY = dummy.getPositionY() / 100;

        dummy.dummyMove(dummy, x * 100, y * 100);

        game.getBoard()[oldY][oldX] = null;
        game.getBoard()[y][x] = dummy;

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
