package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * La classe {@code HomePageAdmin}
 *
 */
public class HomePageAdmin {
    public final JFrame frame = new JFrame("HomePage Admin");
    private JPanel homePageAdminPanel;
    private JButton flightsButton;
    private JButton bagsButton;
    private JButton logoutButton;
    private JPanel buttonsPanel;
    private JLabel exitIcon;
    private JLabel bagsIcon;
    private JLabel flightsIcon;

    /**
     * Costruttore della classe {@code HomePageAdmin}
     *
     * @param frameChiamante il frame chiamante
     * @param controller     il controller
     */
    public HomePageAdmin(JFrame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,homePageAdminPanel);

        exitIcon.setIcon(ImageLoader.loadIcon("src/main/images/exitIcon.png"));
        bagsIcon.setIcon(ImageLoader.loadIcon("src/main/images/bagIcon.png"));
        flightsIcon.setIcon(ImageLoader.loadIcon("src/main/images/PlaneIcon.png"));
        buttonsPanel.setBorder(new LineBorder(Color.BLACK,10,false));
        flightsButton.setBorder(new LineBorder(Color.BLACK,3,false));
        bagsButton.setBorder(new LineBorder(Color.BLACK,3,false));
        logoutButton.setBorder(new LineBorder(Color.BLACK,3,false));

        UtilFunctionsForGUI.addHoverEffect(flightsButton);
        UtilFunctionsForGUI.addHoverEffect(bagsButton);
        UtilFunctionsForGUI.addHoverEffect(logoutButton);

        UtilFunctionsForGUI.setupFrame(frame);

        logoutButton.addActionListener(_ -> {
            controller.logoutAdmin();
            frameChiamante.setVisible(true);
            frame.dispose();
        });
        flightsButton.addActionListener(_ -> {
            VoliAdminGUI volAdminGUI = new VoliAdminGUI(frame, controller);
            volAdminGUI.frame.setVisible(true);
            frame.setVisible(false);
        });
        bagsButton.addActionListener(_ -> {
            BagagliAdminGUI bagAdminGUI = new BagagliAdminGUI(frame, controller);
            bagAdminGUI.frame.setVisible(true);
            frame.setVisible(false);
        });
    }
}
