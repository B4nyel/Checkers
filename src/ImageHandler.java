package src;

import fri.shapesge.Image;

public class ImageHandler {
    private String path;

    public ImageHandler() {
        this.path = "assets/";
    }

    public void drawImage(String name, int x, int y) {
        // TODO: Be able to manage the image externally
        Image image = new Image(this.path + name + ".png", x, y);
        image.makeVisible();
    }
}
