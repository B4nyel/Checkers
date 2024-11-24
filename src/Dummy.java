package src;

public class Dummy {
    private int type;
    private int positionX;
    private int positionY;
    private String dummyID;
    private Player playerOwner;
    private ImageHandler imageHandler;

    public Dummy(int type, int positionX, int positionY, String dummyID, Player playerOwner) {
        this.type = type;
        this.playerOwner = playerOwner;
        this.positionX = positionX;
        this.positionY = positionY;
        this.dummyID = dummyID;
        this.imageHandler = new ImageHandler();
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getDummyID() {
        return this.dummyID;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
        if (this.type == 2) {
            imageHandler.removeImage(dummyID);
            this.dummyID = "queen";
            imageHandler.drawImage(dummyID, (this.playerOwner.getPlayerID() == 1) ? "queenWhite" : "queenBlack",
                    this.positionX + 18, this.positionY + 18);
        }
    }

    public void drawDummy(String dummyID) {
        switch (this.type) {
            case 1:
                switch (this.playerOwner.getPlayerID()) {
                    case 1:
                        imageHandler.drawImage(dummyID, "pawnWhite", this.positionX + 18, this.positionY + 18);
                        break;
                    case 2:
                        imageHandler.drawImage(dummyID, "pawnBlack", this.positionX + 18, this.positionY + 18);
                        break;
                    default:
                        System.err.println("Invalid player ID received");
                        break;
                }
                break;
            case 2:
                switch (this.playerOwner.getPlayerID()) {
                    case 1:
                        imageHandler.drawImage(dummyID, "queenWhite", this.positionX + 18, this.positionY + 18);
                        break;
                    case 2:
                        imageHandler.drawImage(dummyID, "queenBlack", this.positionX + 18, this.positionY + 18);
                        break;
                    default:
                        System.err.println("Invalid player ID received");
                        break;
                }
                break;

            default:
                System.err.println("Invalid dummy type received");
                break;
        }
    }

    public void removeDummy(String dummyID) {
        imageHandler.removeImage(dummyID);
    }

    public void dummyMove(Dummy dummy, int x, int y) {
        imageHandler.moveImage(dummy.dummyID, x + 18, y + 18);
        dummy.setPositionX(x);
        dummy.setPositionY(y);
    }

    public Player getPlayerOwner() {
        return this.playerOwner;
    }

    public void selectDummy(Dummy dummy) {
        imageHandler.removeImage(dummy.dummyID);
        String img = (dummy.getType() == 1) ? "pawnSelected" : "queenSelected";
        imageHandler.drawImage(dummy.dummyID, img, dummy.positionX + 18, dummy.positionY + 18);
    }

    public void deselectDummy(Dummy dummy) {
        imageHandler.removeImage(dummy.dummyID);
        String img1 = (dummy.getPlayerOwner().getPlayerID() == 1) ? "pawnWhite" : "pawnBlack";
        String img2 = (dummy.getPlayerOwner().getPlayerID() == 1) ? "queenWhite" : "queenBlack";
        imageHandler.drawImage(dummy.dummyID, (dummy.getType() == 1) ? img1 : img2, dummy.positionX + 18,
                dummy.positionY + 18);
        ;
    }
}
