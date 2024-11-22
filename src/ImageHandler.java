package src;

import fri.shapesge.Image;
import java.util.HashMap;

public class ImageHandler {
    private String path;
    private HashMap<String, Image> images; // Store images with unique IDs

    public ImageHandler() {
        this.path = "assets/";
        this.images = new HashMap<>(); // Use a map to store images by unique ID
    }

    // Draw and store an image with a unique ID
    public void drawImage(String id, String name, int x, int y) {
        String filePath = this.path + name + ".png";
        Image image = new Image(filePath, x, y);
        image.makeVisible();
        images.put(id, image); // Store the image with its unique ID
        // System.out.println("Image added with ID: " + id);
    }

    // Move a specific image by ID
    public void moveImage(String id, int deltaX, int deltaY) {
        Image image = images.get(id);
        if (image != null) {
            image.changePosition(deltaX, deltaY);
        } else {
            System.out.println("No image found with ID: " + id);
        }
    }

    // Remove a specific image by ID
    public void removeImage(String id) {
        Image image = images.get(id);
        if (image != null) {
            image.makeInvisible(); // Hide the image
            images.remove(id); // Remove it from the map
            System.out.println("Image removed with ID: " + id);
        } else {
            System.out.println("No image found with ID: " + id);
        }
    }

    // Get an image by ID for direct manipulation if needed
    public Image getImage(String id) {
        return images.get(id);
    }
}
