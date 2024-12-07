package src;

import fri.shapesge.Manager;

public class InputHandler {
    private Game game;

    public InputHandler(Game game) {
        Manager manager = new Manager();
        manager.manageObject(this);
        this.game = game;
    }

    public void clickLeft(int x, int y) {
        game.playerMovePiece(x, y);
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
        Piece hoverPiece = game.getBoardPiece(tileX, tileY);
        if (hoverPiece == null) {
            System.out.println("Dummy not found at: " + tileX + ", " + tileY);
            return;
        }
        game.removePieceFromGame(hoverPiece);
    }

    public void exit() {
        System.out.println("Exiting the game...");
        System.exit(0);
    }

    public void printBoard() {
        System.out.println("Current board state:");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = game.getBoardPiece(j, i);

                if (piece != null) {
                    System.out.print("[" + (piece.getPlayerOwner().getPlayerID()) + ":" + piece.getType() + "] ");
                } else {
                    System.out.print("[empty] ");
                }
            }
            System.out.println();
        }
    }
}
