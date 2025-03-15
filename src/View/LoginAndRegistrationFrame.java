package View;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoginAndRegistrationFrame extends JFrame {
    public static Font fieldsFont = null;

    public CardLayout cardLayout ;
    public JPanel mainPanel;
    public  LoginPanel loginPanel ;
    public RegisterPanel registerPanel ;
    static {
        try {
            fieldsFont = Font.createFont( Font.TRUETYPE_FONT, new File("src/View/FontsAndIcons/ShadowsIntoLight-Regular.ttf")).deriveFont(16f) ;
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginAndRegistrationFrame(){
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.out.println("Failed to initialize FlatLaf");
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // 1550 x 878
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());

        // cardLayout for login and register panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setPreferredSize(new Dimension(450, 1080));
        loginPanel = new LoginPanel(mainPanel, cardLayout);
        registerPanel = new RegisterPanel(mainPanel, cardLayout);

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        cardLayout.show(mainPanel, "Login");
        add(mainPanel, BorderLayout.CENTER);

        // the left pic
        JButton leftPic = new JButton(new ImageIcon("C:/Users/Lenovo/Desktop/Untitled-3.png"));
        leftPic.setUI(new BasicButtonUI());
        leftPic.setBorderPainted(false);
        leftPic.setBackground(MainFrame.darkBackground);
        add(leftPic, BorderLayout.EAST);

        // the right panel : right pic + label
        JPanel rightPane = new JPanel();
        rightPane.setPreferredSize(new Dimension(780, this.getHeight()));
        rightPane.setBackground(LoginPanel.darkGray);
        rightPane.setLayout(null);

        JLabel l = new JLabel("EL-RESTAURANTO");
        l.setFont(MainFrame.fontBold.deriveFont(70f));
        l.setBackground(LoginPanel.darkGray);
        l.setForeground(MainFrame.orange);
        l.setBounds(250, 100, 350, 60);
        rightPane.add(l);

        JButton rightPic = new JButton(new ImageIcon("C:/Users/Lenovo/Desktop/Untitled-2.png")); // 780
        rightPic.setBounds(0, 0, 780, this.getHeight());
        rightPic.setUI(new BasicButtonUI());
        rightPic.setBorderPainted(false);
        rightPic.setFocusable(false);
        rightPic.setFocusPainted(false);
        rightPic.setOpaque(false);
        rightPane.add(rightPic);

        add(rightPane, BorderLayout.WEST);

        revalidate();
        repaint();
    }
}
