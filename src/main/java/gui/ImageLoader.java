package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    ImageLoader(){}

    public static ImageIcon loadIcon(String path) {
        BufferedImage img = loadImage(path);
        if (img != null) {
            return new ImageIcon(img);
        }
        return null;
    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException _) {
            return null;
        }
    }

    public static void addBackgroundPanel(JPanel panel) {
        BufferedImage background = loadImage("src/main/images/simpleBackground.jpg");
        if (background != null) {
            panel.add(new BasicBackgroundPanel(background));
        }
    }
}
