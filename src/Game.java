package src;

import java.util.Random;
import fri.shapesge.*;

public class Game {
    private Player player1;
    private Player player2;
    private ImageHandler imageHandler;
    private InputHandler inputHandler;
    private SoundHandler soundHandler;
    private Dummy[][] board;
    private Dummy selectedDummy;

    public Game() {
        this.player1 = new Player(1);
        this.player2 = new Player(2);
        this.board = new Dummy[8][8];
        this.imageHandler = new ImageHandler();
        this.inputHandler = new InputHandler(this);
        this.soundHandler = new SoundHandler();

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

    public boolean isValidMove(Dummy dummy, int x, int y) {
        int startX = dummy.getPositionX() / 100;
        int startY = dummy.getPositionY() / 100;

        if (dummy.getType() == 1) {
            if ((dummy.getPlayerOwner().getPlayerID() == 1 && y <= startY) ||
                    (dummy.getPlayerOwner().getPlayerID() == 2 && y >= startY)) {
                return false;
            }
        }

        if (dummy.getType() == 2) {
            if (Math.abs(x - startX) == Math.abs(y - startY)) {
                int stepX = (x - startX) > 0 ? 1 : -1;
                int stepY = (y - startY) > 0 ? 1 : -1;

                int currentX = startX + stepX;
                int currentY = startY + stepY;
                boolean foundEnemy = false;

                while (currentX != x && currentY != y) {
                    Dummy obstacle = getBoardDummy(currentX, currentY);
                    if (obstacle != null) {
                        if (obstacle.getPlayerOwner().getPlayerID() == dummy.getPlayerOwner().getPlayerID()) {
                            return false;
                        } else if (obstacle.getPlayerOwner().getPlayerID() != dummy.getPlayerOwner().getPlayerID()) {
                            Dummy nextTile = getBoardDummy(currentX + stepX, currentY + stepY);
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

                Dummy destinationDummy = getBoardDummy(x, y);
                if (destinationDummy == null) {
                    return true;
                }
            }
        } else {
            if (Math.abs(x - startX) == 1 && Math.abs(y - startY) == 1) {
                return getBoardDummy(x, y) == null;
            }

            if (Math.abs(x - startX) == 2 && Math.abs(y - startY) == 2) {
                int midX = (startX + x) / 2;
                int midY = (startY + y) / 2;

                Dummy midDummy = getBoardDummy(midX, midY);
                if (midDummy != null && midDummy.getPlayerOwner().getPlayerID() != dummy.getPlayerOwner().getPlayerID()
                        && getBoardDummy(x, y) == null) {
                    return true;
                }
            }
        }

        return false;
    }

    public void moveDummy(Dummy dummy, int x, int y) {
        int oldX = dummy.getPositionX() / 100;
        int oldY = dummy.getPositionY() / 100;

        if (dummy.getType() == 2) {
            int stepX = (x - oldX) > 0 ? 1 : -1;
            int stepY = (y - oldY) > 0 ? 1 : -1;

            int currentX = oldX + stepX;
            int currentY = oldY + stepY;

            while (currentX != x && currentY != y) {
                Dummy obstacle = getBoardDummy(currentX, currentY);
                if (obstacle != null) {
                    if (obstacle.getPlayerOwner().getPlayerID() != dummy.getPlayerOwner().getPlayerID()) {
                        board[currentY][currentX] = null;
                        obstacle.getPlayerOwner().removeDummy(obstacle);
                        removeDummyFromGame(obstacle);
                        System.out.println("Enemy dummy removed at: " + currentX + ", " + currentY);
                    } else {
                        return;
                    }
                }
                currentX += stepX;
                currentY += stepY;
            }

            Dummy destinationDummy = getBoardDummy(x, y);
            if (destinationDummy != null) {
                return;
            }
        } else {
            if (Math.abs(x - oldX) == 2 && Math.abs(y - oldY) == 2) {
                int midX = (oldX + x) / 2;
                int midY = (oldY + y) / 2;

                Dummy midDummy = getBoardDummy(midX, midY);
                if (midDummy != null
                        && midDummy.getPlayerOwner().getPlayerID() != dummy.getPlayerOwner().getPlayerID()) {
                    board[midY][midX] = null;
                    midDummy.getPlayerOwner().removeDummy(midDummy);
                    removeDummyFromGame(midDummy);
                    System.out.println("Enemy dummy removed at: " + midX + ", " + midY);
                }
            }
        }

        dummy.dummyMove(dummy, x * 100, y * 100);

        board[oldY][oldX] = null;
        board[y][x] = dummy;

        if ((dummy.getPlayerOwner().getPlayerID() == 1 && y == 7)
                || (dummy.getPlayerOwner().getPlayerID() == 2 && y == 0)) {
            if (dummy.getType() == 2) {
                return;
            }
            dummy.setType(2);
            soundHandler.playSound("promote.wav");
            return;
        }

        soundHandler.playSound("board.wav");
    }

    public void playerMoveDummy(int x, int y) {
        int tileX = inputHandler.getTileX(x);
        int tileY = inputHandler.getTileY(y);

        if (selectedDummy == null) {
            selectedDummy = getBoardDummy(tileX, tileY);

            if (selectedDummy == null) {
                System.out.println("Dummy not found at: " + tileX + ", " + tileY);
                return;
            }

            if (selectedDummy.getPlayerOwner().getPlayerID() == 1 && !player1.getPlayerTurn()) {
                System.out.println("Not your turn.");
                selectedDummy = null;
                return;
            } else if (selectedDummy.getPlayerOwner().getPlayerID() == 2 && !player2.getPlayerTurn()) {
                System.out.println("Not your turn.");
                selectedDummy = null;
                return;
            }

            selectedDummy.selectDummy(selectedDummy);

            if (selectedDummy != null) {
                System.out.println("Piece selected at: " + tileX + ", " + tileY);
            }
        } else {
            if (selectedDummy == getBoardDummy(tileX, tileY)) {
                System.out.println("Deselected piece at: " + tileX + ", " + tileY);
                selectedDummy.deselectDummy(selectedDummy);
                selectedDummy = null;
                return;
            }

            if ((tileX + tileY) % 2 == 0) {
                System.out.println("Invalid move.");
                return;
            }

            if (isValidMove(selectedDummy, tileX, tileY)) {
                selectedDummy.deselectDummy(selectedDummy);
                moveDummy(selectedDummy, tileX, tileY);
                selectedDummy = null;
                System.out.println("Moved piece to: " + tileX + ", " + tileY);

                player1.setPlayerTurn(!player1.getPlayerTurn());
                player2.setPlayerTurn(!player2.getPlayerTurn());

                drawTurn();
            } else {
                System.out.println("Invalid move.");
            }
        }
    }

    public Dummy getSelectedDummy() {
        return selectedDummy;
    }

    public void setSelectedDummy(Dummy selectedDummy) {
        this.selectedDummy = selectedDummy;
    }
}
