package src;

import java.util.Random;
import fri.shapesge.*;

public class Game {
    private Dummy dummy;
    private Player player1;
    private Player player2;
    private ImageHandler img;
    // private int turn;

    public Game() {
        this.player1 = new Player(1);
        this.player2 = new Player(2);
        // this.turn = 1;

        this.start();
    }

    public void start() {
        int number = new Random().nextInt(2);

        switch (number) {
            case 1:
                this.player1.setPlayerTurn(true);
                break;
            case 2:
                this.player2.setPlayerTurn(true);
                break;
            default:
                break;
        }

        TextBlock text = new TextBlock("Player turn:", 0, 865);
        text.changeFont("Aral", FontStyle.BOLD, 50);
        text.makeVisible();

        Rectangle rectangle = new Rectangle(350, 820);
        rectangle.changeColor(player1.getPlayerTurn() ? "vanilla" : "brown");
        rectangle.makeVisible();

        img = new ImageHandler();
        img.drawImage(player1.getPlayerTurn() ? "pawnWhite" : "pawnBlack", 280, 820);
    }

}
