package gui;

import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code BasicBackgroundPanel} rappresenta un background.
 */
public class BasicBackgroundPanel extends JPanel
{
    private final transient Image backgroundImage;

    /**
     * Costruttore della classe BasicBackgroundPanel.
     *
     * @param backgroundImage l'immagine di background
     */
    public BasicBackgroundPanel(Image backgroundImage)
    {
        this.backgroundImage = backgroundImage;
        setLayout( new BorderLayout() );
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //g.drawImage(background, 0, 0, null); // image full size
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); // image scaled
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this));
    }
}
