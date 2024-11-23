package src;

import fri.shapesge.Square;

public class Board {
    private int width;
    private int tile;

    public Board() {
        this.width = 800;
        this.tile = 100;

        this.drawBoard();
    }

    private void drawBoard() {
        Square background = new Square(0, 0);
        background.changeSize(this.width);
        background.changeColor("brown");
        background.makeVisible();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    Square square = new Square(i * this.tile, j * this.tile);
                    square.changeSize(this.tile);
                    square.changeColor("vanilla");
                    square.makeVisible();
                }
            }
        }
    }
}
