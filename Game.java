public class Game {
    private Board board;
    private Dummy dummy;
    private int score;
    private int turn;
    private boolean gameEnd;
    private int winner;

    public Game(Board board, Dummy dummy) {
        this.board = board;
        this.dummy = dummy;
    }

    public void start() {
        this.score = 0;
        this.turn = 0;
        this.gameEnd = false;
        this.winner = -1;
    }

    public void end() {
        this.gameEnd = true;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getScore() {
        return this.score;
    }

    public int getTurn() {
        return this.turn;
    }

    public boolean isGameEnd() {
        return this.gameEnd;
    }

    public int getWinner() {
        return this.winner;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
