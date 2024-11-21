package src;

import fri.shapesge.*;

public class Janech {
    private ImageHandler imageHandler;

    public Janech() {
        Manager yolo = new Manager();
        yolo.manageObject(this);
        imageHandler = new ImageHandler();
    }

    public void clickLeft(int x, int y) {
        // System.out.println("Left click at: " + x + ", " + y);
        // Circle circle = new Circle(x, y);
        // circle.makeVisible();

        imageHandler.drawImage("pawnWhite", x, y);
    }

    public void clickRight(int x, int y) {
        // System.out.println("Right click at: " + x + ", " + y);
        // Square square = new Square(x, y);
        // square.makeVisible();

        imageHandler.drawImage("pawnBlack", x, y);
    }

    public void clickMiddle(int x, int y) {
        System.out.println("Middle click at: " + x + ", " + y);
        Triangle triangle = new Triangle(x, y);
        triangle.makeVisible();
    }

    public void relis(int x, int y) {
        System.out.println("Released left click at: " + x + ", " + y);
        Rectangle rectangle = new Rectangle(x, y);
        rectangle.makeVisible();
    }

    public void exit() {
        System.out.println("Exiting the game...");
        System.exit(0);
    }
}
