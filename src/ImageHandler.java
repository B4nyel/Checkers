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

    public void drawImage(String id, String name, int x, int y) {
        String filePath = this.path + name + ".png";
        Image image = new Image(filePath, x, y);
        image.makeVisible();
        images.put(id, image);
    }

    public void moveImage(String id, int deltaX, int deltaY) {
        Image image = images.get(id);
        if (image != null) {
            image.changePosition(deltaX, deltaY);
        } else {
            System.out.println("No image found with ID: " + id);
        }
    }

    public void removeImage(String id) {
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
