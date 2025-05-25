package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HomePageAdmin {
    public JFrame frame;
    private JPanel homePageAdminPanel;
    private JPanel contentPanel;
    private JButton voliButton;
    private JButton tornaIndietroButton;
    private JButton bagagliButton;

    public HomePageAdmin(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame();
        frame.setContentPane(homePageAdminPanel);
        homePageAdminPanel.setLayout(new OverlayLayout(homePageAdminPanel));
        contentPanel.setOpaque(false);
        homePageAdminPanel.add(contentPanel);
        homePageAdminPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        voliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VoliAdminGUI volAdminGUI= null;
                try {
                    volAdminGUI = new VoliAdminGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                volAdminGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        bagagliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BagagliAdminGUI bagAdminGUI= null;
                try {
                    bagAdminGUI = new BagagliAdminGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                bagAdminGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }
}
