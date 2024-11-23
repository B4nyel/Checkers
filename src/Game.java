package src;

import java.util.Random;
import fri.shapesge.*;

public class Game {
    private Player player1;
    private Player player2;
    private ImageHandler imageHandler;
    private InputHandler inputHandler;
    private Dummy[][] board;

    public Game() {
        this.player1 = new Player(1);
        this.player2 = new Player(2);
        this.board = new Dummy[8][8];
        this.imageHandler = new ImageHandler();
        this.inputHandler = new InputHandler(this);

        new Board();
        this.start();
        this.Test();
    }

    public void start() {
        int number = new Random().nextInt(2);

        switch (number) {
            case 0:
                this.player1.setPlayerTurn(true);
                break;
            case 1:
                this.player2.setPlayerTurn(true);
                break;
            default:
                break;
        }

        TextBlock text = new TextBlock("Player turn:", 0, 865);
        text.changeFont("Aral", FontStyle.BOLD, 50);
        text.makeVisible();

        drawTurn();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i < 3 || i > 4) && (i + j) % 2 != 0) {
                    if (i < 3) {
                        Dummy dummy = new Dummy(1, j * 100, i * 100, "pawn", player1);
                        board[i][j] = dummy;
                        player1.addDummy(dummy);
                    } else {
                        Dummy dummy = new Dummy(1, j * 100, i * 100, "pawn", player2);
                        board[i][j] = dummy;
                        player2.addDummy(dummy);
                    }
                    board[i][j].drawDummy(board[i][j].getDummyID());
                }
            }
        }

    }

    public void Test() {
        System.out.println("Player 1 has " + player1.getDummies().size() + " dummies");
        System.out.println("Player 2 has " + player2.getDummies().size() + " dummies");
        inputHandler.printBoard();
    }

    public Dummy getBoardDummy(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return board[y][x];
        }
        return null;
    }

    public Dummy[][] getBoard() {
        return this.board;
    }

    public void removeDummyFromGame(Dummy dummy) {
        int tileX = dummy.getPositionX() / 100;
        int tileY = dummy.getPositionY() / 100;

        board[tileY][tileX] = null;

        Player player = dummy.getPlayerOwner();
        player.removeDummy(dummy);

        dummy.removeDummy(dummy.getDummyID());
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public void drawTurn() {
        imageHandler.removeImage("playerTurn");
        imageHandler.drawImage("playerTurn", player1.getPlayerTurn() ? "pawnWhite" : "pawnBlack", 280, 820);
    }

}
