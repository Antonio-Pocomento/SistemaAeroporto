package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HomePage {
    public final JFrame frame = new JFrame("HomePage");
    private JPanel homePagePanel;
    private JPanel buttonsPanel;
    private JButton flightsButton;
    private JButton bagsButton;
    private JButton bookingButton;
    private JButton logoutButton;
    private JLabel logoutIcon;
    private JLabel bookingIcon;
    private JLabel bagsIcon;
    private JLabel flightsIcon;

    public HomePage(JFrame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,homePagePanel);

        flightsIcon.setIcon(ImageLoader.loadIcon("src/main/images/PlaneIcon.png"));
        bagsIcon.setIcon(ImageLoader.loadIcon("src/main/images/bagIcon.png"));
        logoutIcon.setIcon(ImageLoader.loadIcon("src/main/images/exitIcon.png"));
        bookingIcon.setIcon(ImageLoader.loadIcon("src/main/images/bookingIcon.png"));

        buttonsPanel.setBorder(new LineBorder(Color.BLACK,10,false));
        bagsButton.setBorder(new LineBorder(Color.BLACK,3,false));
        flightsButton.setBorder(new LineBorder(Color.BLACK,3,false));
        logoutButton.setBorder(new LineBorder(Color.BLACK,3,false));
        bookingButton.setBorder(new LineBorder(Color.BLACK,3,false));

        UtilFunctionsForGUI.addHoverEffect(flightsButton);
        UtilFunctionsForGUI.addHoverEffect(bagsButton);
        UtilFunctionsForGUI.addHoverEffect(logoutButton);
        UtilFunctionsForGUI.addHoverEffect(bookingButton);

        UtilFunctionsForGUI.setupFrame(frame);

        logoutButton.addActionListener(_ -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });
        bookingButton.addActionListener(_ -> {
            PrenotazioniGUI preGUI = new PrenotazioniGUI(frame, controller);
            preGUI.frame.setVisible(true);
            frame.setVisible(false);
        });
        flightsButton.addActionListener(_ -> {
            VoliGUI volGUI = new VoliGUI(frame, controller);
            volGUI.frame.setVisible(true);
            frame.setVisible(false);
        });
        bagsButton.addActionListener(_ -> {
            BagagliGUI bagGUI = new BagagliGUI(frame, controller);
            bagGUI.frame.setVisible(true);
            frame.setVisible(false);
        });
    }
}
