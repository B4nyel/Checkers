package src;

import java.util.Random;

import fri.shapesge.TextBlock;
import fri.shapesge.FontStyle;

public class Game {
    private Player player1;
    private Player player2;
    private ImageHandler imageHandler;
    private InputHandler inputHandler;
    private SoundHandler soundHandler;
    private Piece[][] board;
    private Piece selectedPiece;
    private TextBlock text;
    private boolean gameEnd;

    public Game() {
        this.player1 = new Player(1);
        this.player2 = new Player(2);
        this.board = new Piece[8][8];
        this.imageHandler = new ImageHandler();
        this.inputHandler = new InputHandler(this);
        this.soundHandler = new SoundHandler();
        this.selectedPiece = null;
        this.gameEnd = false;

        new Board();
        this.start();
        this.Test();

        this.text = new TextBlock("?", 650, 865);
        this.text.changeFont("Aral", FontStyle.BOLD, 50);
        this.text.makeVisible();
    }

    public void start() {
        imageHandler.drawImage(3, "oak", 0, 800);
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

        TextBlock text = new TextBlock("Player turn:", 30, 865);
        text.changeFont("Aral", FontStyle.BOLD, 50);
        text.makeVisible();

        TextBlock winner = new TextBlock("Winner:", 450, 865);
        winner.changeFont("Aral", FontStyle.BOLD, 50);
        winner.makeVisible();

        drawTurn();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i < 3 || i > 4) && (i + j) % 2 != 0) {
                    if (i < 3) {
                        Piece piece = new Piece(1, j * 100, i * 100, player1);
                        board[i][j] = piece;
                        player1.addPiece(piece);
                    } else {
                        Piece piece = new Piece(1, j * 100, i * 100, player2);
                        board[i][j] = piece;
                        player2.addPiece(piece);
                    }
                    board[i][j].drawPiece(board[i][j].getType());
                }
            }
        }

    }

    public void Test() {
        System.out.println("Player 1 has " + player1.getPieces().size() + " dummies");
        System.out.println("Player 2 has " + player2.getPieces().size() + " dummies");
        inputHandler.printBoard();
    }

    public Piece getBoardPiece(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return board[y][x];
        }
        return null;
    }

    public Piece[][] getBoard() {
        return this.board;
    }

    public void removePieceFromGame(Piece piece) {
        int tileX = piece.getPositionX() / 100;
        int tileY = piece.getPositionY() / 100;

        board[tileY][tileX] = null;

        Player player = piece.getPlayerOwner();
        player.removePiece(piece);

        piece.removePiece(piece.getType());
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public void drawTurn() {
        imageHandler.removeImage(4);
        imageHandler.drawImage(4, player1.getPlayerTurn() ? "pawnWhite" : "pawnBlack", 320, 820);
    }

    public void drawWinner(Player player) {
        if (player.getPlayerID() == 1) {
            this.text.makeInvisible();
            soundHandler.playSound("win");
            imageHandler.drawImage(4, "pawnBlack", 650, 820);
        } else if (player.getPlayerID() == 2) {
            this.text.makeInvisible();
            soundHandler.playSound("win");
            imageHandler.drawImage(4, "pawnWhite", 650, 820);
        }

        if (player1.getPieces().size() == 0) {
            this.text.makeInvisible();
            soundHandler.playSound("win");
            imageHandler.drawImage(4, "pawnBlack", 650, 820);
        } else if (player2.getPieces().size() == 0) {
            this.text.makeInvisible();
            soundHandler.playSound("win");
            imageHandler.drawImage(4, "pawnWhite", 650, 820);
        }
    }

    public boolean isValidMove(Piece piece, int x, int y) {
        int startX = piece.getPositionX() / 100;
        int startY = piece.getPositionY() / 100;

        if (piece.getType() == 1) {
            if ((piece.getPlayerOwner().getPlayerID() == 1 && y <= startY) ||
                    (piece.getPlayerOwner().getPlayerID() == 2 && y >= startY)) {
                return false;
            }
        }

        if (piece.getType() == 2) {
            if (Math.abs(x - startX) == Math.abs(y - startY)) {
                int stepX = (x - startX) > 0 ? 1 : -1;
                int stepY = (y - startY) > 0 ? 1 : -1;

                int currentX = startX + stepX;
                int currentY = startY + stepY;
                boolean foundEnemy = false;

                while (currentX != x && currentY != y) {
                    Piece obstacle = getBoardPiece(currentX, currentY);
                    if (obstacle != null) {
                        if (obstacle.getPlayerOwner().getPlayerID() == piece.getPlayerOwner().getPlayerID()) {
                            return false;
                        } else if (obstacle.getPlayerOwner().getPlayerID() != piece.getPlayerOwner().getPlayerID()) {
                            Piece nextTile = getBoardPiece(currentX + stepX, currentY + stepY);
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

                Piece destinationPiece = getBoardPiece(x, y);
                if (destinationPiece == null) {
                    return true;
                }
            }
        } else {
            if (Math.abs(x - startX) == 1 && Math.abs(y - startY) == 1) {
                return getBoardPiece(x, y) == null;
            }

            if (Math.abs(x - startX) == 2 && Math.abs(y - startY) == 2) {
                int midX = (startX + x) / 2;
                int midY = (startY + y) / 2;

                Piece midPiece = getBoardPiece(midX, midY);
                if (midPiece != null && midPiece.getPlayerOwner().getPlayerID() != piece.getPlayerOwner().getPlayerID()
                        && getBoardPiece(x, y) == null) {
                    return true;
                }
            }
        }

        return false;
    }

    public void movePiece(Piece piece, int x, int y) {
        int oldX = piece.getPositionX() / 100;
        int oldY = piece.getPositionY() / 100;

        if (piece.getType() == 2) {
            int stepX = (x - oldX) > 0 ? 1 : -1;
            int stepY = (y - oldY) > 0 ? 1 : -1;

            int currentX = oldX + stepX;
            int currentY = oldY + stepY;

            while (currentX != x && currentY != y) {
                Piece obstacle = getBoardPiece(currentX, currentY);
                if (obstacle != null) {
                    if (obstacle.getPlayerOwner().getPlayerID() != piece.getPlayerOwner().getPlayerID()) {
                        board[currentY][currentX] = null;
                        obstacle.getPlayerOwner().removePiece(obstacle);
                        removePieceFromGame(obstacle);
                        System.out.println("Enemy dummy removed at: " + currentX + ", " + currentY);
                    } else {
                        return;
                    }
                }
                currentX += stepX;
                currentY += stepY;
            }

            Piece destinationPiece = getBoardPiece(x, y);
            if (destinationPiece != null) {
                return;
            }
        } else {
            if (Math.abs(x - oldX) == 2 && Math.abs(y - oldY) == 2) {
                int midX = (oldX + x) / 2;
                int midY = (oldY + y) / 2;

                Piece midPiece = getBoardPiece(midX, midY);
                if (midPiece != null
                        && midPiece.getPlayerOwner().getPlayerID() != piece.getPlayerOwner().getPlayerID()) {
                    board[midY][midX] = null;
                    midPiece.getPlayerOwner().removePiece(midPiece);
                    removePieceFromGame(midPiece);
                    System.out.println("Enemy dummy removed at: " + midX + ", " + midY);
                }
            }
        }

        piece.movePiece(piece, x * 100, y * 100);

        board[oldY][oldX] = null;
        board[y][x] = piece;

        if ((piece.getPlayerOwner().getPlayerID() == 1 && y == 7)
                || (piece.getPlayerOwner().getPlayerID() == 2 && y == 0)) {
            if (piece.getType() == 2) {
                soundHandler.playSound("board");
                return;
            }
            piece.promote();
            soundHandler.playSound("promote");
            return;
        }

        soundHandler.playSound("board");
    }

    public boolean canPlayerMove(Player player) {
        for (Piece piece : player.getPieces()) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (isValidMove(piece, x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void playerMovePiece(int x, int y) {
        if (gameEnd) {
            return;
        }

        if (y >= 800) {
            return;
        }

        int tileX = inputHandler.getTileX(x);
        int tileY = inputHandler.getTileY(y);

        if (selectedPiece == null) {
            selectedPiece = getBoardPiece(tileX, tileY);

            if (selectedPiece == null) {
                System.out.println("Dummy not found at: " + tileX + ", " + tileY);
                return;
            }

            if (selectedPiece.getPlayerOwner().getPlayerID() == 1 && !player1.getPlayerTurn()) {
                System.out.println("Not your turn.");
                selectedPiece = null;
                return;
            } else if (selectedPiece.getPlayerOwner().getPlayerID() == 2 && !player2.getPlayerTurn()) {
                System.out.println("Not your turn.");
                selectedPiece = null;
                return;
            }

            selectedPiece.selectPiece(selectedPiece);

            if (selectedPiece != null) {
                System.out.println("Piece selected at: " + tileX + ", " + tileY);
            }
        } else {
            if (selectedPiece == getBoardPiece(tileX, tileY)) {
                System.out.println("Deselected piece at: " + tileX + ", " + tileY);
                selectedPiece.deselectPiece(selectedPiece);
                selectedPiece = null;
                return;
            }

            if ((tileX + tileY) % 2 == 0) {
                System.out.println("Invalid move.");
                return;
            }

            if (isValidMove(selectedPiece, tileX, tileY)) {
                selectedPiece.deselectPiece(selectedPiece);
                movePiece(selectedPiece, tileX, tileY);
                selectedPiece = null;
                System.out.println("Moved piece to: " + tileX + ", " + tileY);

                player1.setPlayerTurn(!player1.getPlayerTurn());
                player2.setPlayerTurn(!player2.getPlayerTurn());

                drawTurn();

                if (!canPlayerMove(player1) || !canPlayerMove(player2)) {
                    gameEnd = true;
                    drawWinner(player1);
                } else if (!canPlayerMove(player2)) {
                    gameEnd = true;
                    drawWinner(player2);
                }

            } else {
                System.out.println("Invalid move.");
            }
        }
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }
}
