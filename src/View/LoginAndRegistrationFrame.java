package View;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoginAndRegistrationFrame extends JFrame {
    public static Color lightGray = new Color(43, 45, 48) ;
    public static Color darkGray = new Color(30, 31, 34) ;
    public static Color orange = new Color(206, 129, 76) ;
    public static Font fontBold = null ;
    public static Font fontRegular = null ;
    public static Font fieldsFont = null;

    public CardLayout cardLayout ;
    public JPanel mainPanel;
    public  LoginPanel loginPanel ;
    public RegisterPanel registerPanel ;
    static {
        try {
            fieldsFont = Font.createFont( Font.TRUETYPE_FONT, new File("src/View/Fonts/ShadowsIntoLight-Regular.ttf")).deriveFont(16f) ;
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
            System.err.println("Failed to initialize FlatLaf");
        }

        setTitle("El Restauranto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,700);
        setLocationRelativeTo(null);
        setVisible(true);

         cardLayout = new CardLayout();
         mainPanel = new JPanel(cardLayout);
         loginPanel = new LoginPanel(mainPanel, cardLayout);
         registerPanel = new RegisterPanel(mainPanel, cardLayout);

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        cardLayout.show(mainPanel, "Login");

        add(mainPanel);

        revalidate();
        repaint();
    }
}
