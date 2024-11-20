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
}
