package src;

public class Piece {
    private int type;
    private int positionX;
    private int positionY;
    private Player playerOwner;
    private ImageHandler imageHandler;

    public Piece(int type, int positionX, int positionY, Player playerOwner) {
        this.type = type;
        this.playerOwner = playerOwner;
        this.positionX = positionX;
        this.positionY = positionY;
        this.imageHandler = new ImageHandler();
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    private void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getType() {
        return this.type;
    }

    public void promote() {
        imageHandler.removeImage(type);
        this.type = 2;
        imageHandler.drawImage(type, (this.playerOwner.getPlayerID() == 1) ? "queenWhite" : "queenBlack",
                this.positionX + 18, this.positionY + 18);
    }

    public void drawPiece(int type) {
        switch (this.type) {
            case 1:
                switch (this.playerOwner.getPlayerID()) {
                    case 1:
                        imageHandler.drawImage(type, "pawnWhite", this.positionX + 18, this.positionY + 18);
                        break;
                    case 2:
                        imageHandler.drawImage(type, "pawnBlack", this.positionX + 18, this.positionY + 18);
                        break;
                    default:
                        System.err.println("Invalid player ID received");
                        break;
                }
                break;
            case 2:
                switch (this.playerOwner.getPlayerID()) {
                    case 1:
                        imageHandler.drawImage(type, "queenWhite", this.positionX + 18, this.positionY + 18);
                        break;
                    case 2:
                        imageHandler.drawImage(type, "queenBlack", this.positionX + 18, this.positionY + 18);
                        break;
                    default:
                        System.err.println("Invalid player ID received");
                        break;
                }
                break;
        }
    }

    public void removePiece(int type) {
        imageHandler.removeImage(type);
    }

    public void movePiece(Piece piece, int x, int y) {
        imageHandler.moveImage(piece.type, x + 18, y + 18);
        piece.setPosition(x, y);
    }

    public Player getPlayerOwner() {
        return this.playerOwner;
    }

    public void selectPiece(Piece piece) {
        imageHandler.removeImage(piece.type);
        String img = (piece.getType() == 1) ? "pawnSelected" : "queenSelected";
        imageHandler.drawImage(piece.type, img, piece.positionX + 18, piece.positionY + 18);
    }

    public void deselectPiece(Piece piece) {
        imageHandler.removeImage(piece.type);
        String img1 = (piece.getPlayerOwner().getPlayerID() == 1) ? "pawnWhite" : "pawnBlack";
        String img2 = (piece.getPlayerOwner().getPlayerID() == 1) ? "queenWhite" : "queenBlack";
        imageHandler.drawImage(piece.type, (piece.getType() == 1) ? img1 : img2, piece.positionX + 18,
                piece.positionY + 18);
    }
}
