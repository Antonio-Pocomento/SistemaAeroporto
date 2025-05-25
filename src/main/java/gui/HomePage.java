package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HomePage {
    public JFrame frame;
    private JPanel homePagePanel;
    private JPanel contentPanel;
    private JButton voliButton1;
    private JButton tornaIndietroButton;
    private JButton bagagliButton;
    private JButton prenotazioniButton;

    public HomePage(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("HomePage");
        frame.setContentPane(homePagePanel);
        homePagePanel.setLayout(new OverlayLayout(homePagePanel));
        contentPanel.setOpaque(false);
        homePagePanel.add(contentPanel);
        homePagePanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        voliButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VoliGUI VoliGUI= null;
                try {
                    VoliGUI = new VoliGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                VoliGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        bagagliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BagagliGUI bagGui = null;
                try {
                    bagGui = new BagagliGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                bagGui.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        prenotazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrenotazioniGUI prenotGui = null;
                try {
                    prenotGui = new PrenotazioniGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                prenotGui.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }
}
