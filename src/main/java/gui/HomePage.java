package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class HomePage {
    public JFrame frame;
    private JPanel homePagePanel;
    private JPanel contentPanel;
    private JPanel buttonsPanel;
    private JButton flightsButton;
    private JButton bagsButton;
    private JButton bookingButton;
    private JButton logoutButton;
    private JLabel logoutIcon;
    private JLabel bookingIcon;
    private JLabel bagsIcon;
    private JLabel flightsIcon;
    private JPanel flightsPanel;
    private JPanel bagsPanel;
    private JPanel bookingPanel;
    private JPanel logoutPanel;

    public HomePage(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("HomePage");
        frame.setContentPane(homePagePanel);
        homePagePanel.setLayout(new OverlayLayout(homePagePanel));
        homePagePanel.add(contentPanel);
        homePagePanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        flightsIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/PlaneIcon.png"))));
        bagsIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/bagIcon.png"))));
        logoutIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/exitIcon.png"))));
        bookingIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/bookingIcon.png"))));
        buttonsPanel.setBorder(new LineBorder(Color.BLACK,10,false));

        bagsButton.setBorder(new LineBorder(Color.BLACK,3,false));
        flightsButton.setBorder(new LineBorder(Color.BLACK,3,false));
        logoutButton.setBorder(new LineBorder(Color.BLACK,3,false));
        bookingButton.setBorder(new LineBorder(Color.BLACK,3,false));
        flightsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                flightsButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                flightsButton.setBackground(null);
            }
        });
        bagsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                bagsButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                bagsButton.setBackground(null);
            }
        });
        bookingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                bookingButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                bookingButton.setBackground(null);
            }
        });
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                logoutButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                logoutButton.setBackground(null);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
        bookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrenotazioniGUI preGUI = null;
                try {
                    preGUI = new PrenotazioniGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                preGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        flightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VoliGUI volGUI = null;
                try {
                    volGUI = new VoliGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                volGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        bagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BagagliGUI bagGUI = null;
                try {
                    bagGUI = new BagagliGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                bagGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }
}
