package src;

import fri.shapesge.Image;
import java.util.HashMap;

public class ImageHandler {
    private String path;
    private HashMap<String, Image> images;

    public ImageHandler() {
        this.path = "assets/";
        this.images = new HashMap<>();
    }

    public void drawImage(int type, String name, int x, int y) {
        String id = (type == 1) ? "pawn" : (type == 2) ? "queen" : (type == 3) ? "gui" : "playerTurn";
        String filePath = this.path + name + ".png";
        Image image = new Image(filePath, x, y);
        image.makeVisible();
        images.put(id, image);
    }

    public void moveImage(int type, int deltaX, int deltaY) {
        String id = (type == 1) ? "pawn" : (type == 2) ? "queen" : (type == 3) ? "gui" : "playerTurn";
        Image image = images.get(id);
        if (image != null) {
            image.changePosition(deltaX, deltaY);
        } else {
            System.out.println("No image found with ID: " + id);
        }
    }

    public void removeImage(int type) {
        String id = (type == 1) ? "pawn" : (type == 2) ? "queen" : (type == 3) ? "gui" : "playerTurn";
        Image image = images.get(id);
        if (image != null) {
            image.makeInvisible();
            images.remove(id);
            System.out.println("Image removed with ID: " + id);
        } else {
            System.out.println("No image found with ID: " + id);
        }
    }

    public Image getImage(String id) {
        return images.get(id);
    }
}
