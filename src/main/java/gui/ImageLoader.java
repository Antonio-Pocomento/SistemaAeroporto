package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * La classe {@code ImageLoader}
 */
public class ImageLoader {

    /**
     * Costruttore ImageLoader.
     */
    ImageLoader(){}

    /**
     * Carica le icone
     *
     * @param path path da cui prendere le icone
     * @return icona
     */
    public static ImageIcon loadIcon(String path) {
        BufferedImage img = loadImage(path);
        if (img != null) {
            return new ImageIcon(img);
        }
        return null;
    }

    /**
     * Carica Immagine
     *
     * @param path path da cui prendere l'immagine
     * @return immagine
     */
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException _) {
            return null;
        }
    }

    /**
     * Aggiungi background panel.
     *
     * @param panel panel
     */
    public static void addBackgroundPanel(JPanel panel) {
        BufferedImage background = loadImage("src/main/images/simpleBackground.jpg");
        if (background != null) {
            panel.add(new BasicBackgroundPanel(background));
        }
    }
}
