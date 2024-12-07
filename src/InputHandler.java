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

    public void clickMiddle(int x, int y) {
        int tileX = x / 100;
        int tileY = y / 100;
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
}
