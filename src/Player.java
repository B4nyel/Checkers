package src;

import java.util.ArrayList;

public class Player {
    private ArrayList<Dummy> dummies;
    private int playerID;
    private boolean playerTurn;

    public Player(int playerID) {
        this.playerID = playerID;
        this.dummies = new ArrayList<Dummy>();
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
}
