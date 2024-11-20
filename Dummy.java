public class Dummy {
    private int type;
    private Player playerOwner;
    private int positionX;
    private int positionY;

    public Dummy(int type, Player playerOwner, int positionX, int positionY) {
        this.type = 1;
        this.playerOwner = playerOwner;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }
}
