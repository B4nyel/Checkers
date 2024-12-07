package src;

import java.util.ArrayList;

public class Player {
    private ArrayList<Piece> pieces;
    private int playerID;
    private boolean playerTurn;

    public Player(int playerID) {
        this.playerID = playerID;
        this.pieces = new ArrayList<Piece>();
        this.playerTurn = false;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean getPlayerTurn() {
        return this.playerTurn;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public void addPiece(Piece piece) {
        this.pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        this.pieces.remove(piece);
    }

    public ArrayList<Piece> getPieces() {
        return this.pieces;
    }
}
