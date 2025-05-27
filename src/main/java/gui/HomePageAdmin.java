package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class HomePageAdmin {
    public JFrame frame;
    private JPanel homePageAdminPanel;
    private JPanel contentPanel;
    private JButton flightsButton;
    private JButton bagsButton;
    private JButton logoutButton;
    private JPanel buttonsPanel;
    private JLabel exitIcon;
    private JLabel bagsIcon;
    private JLabel flightsIcon;

    public HomePageAdmin(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Home Page Admin");
        frame.setContentPane(homePageAdminPanel);
        homePageAdminPanel.setLayout(new OverlayLayout(homePageAdminPanel));
        homePageAdminPanel.add(contentPanel);
        homePageAdminPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        exitIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/exitIcon.png"))));
        bagsIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/bagIcon.png"))));
        flightsIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/PlaneIcon.png"))));
        buttonsPanel.setBorder(new LineBorder(Color.BLACK,10,false));
        flightsButton.setBorder(new LineBorder(Color.BLACK,3,false));
        bagsButton.setBorder(new LineBorder(Color.BLACK,3,false));
        logoutButton.setBorder(new LineBorder(Color.BLACK,3,false));

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
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
    }
}
