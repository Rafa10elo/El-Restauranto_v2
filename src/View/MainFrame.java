package View;
import Model.Meal;
import Model.Order;
import Model.User;
import com.formdev.flatlaf.FlatDarkLaf ;
import com.sun.source.doctree.ThrowsTree;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainFrame extends JFrame {
    public MealsPanel mealsPanel;
    public ProfilePanel profilePanel ;
    public ReportPanel reportPanel;
    public AllOrdersPanel allOrdersPanel;
    public JPanel panel;


    public static Color darkGray = new Color(30, 31, 34) ;
    public static Color lightGray = new Color(43, 45, 48) ;
    public static Color extraLightGray = new Color(57, 59, 64) ;
    public static Color orange = new Color(206, 129, 76) ;
    public static Font fontBold = null ;
    public static Font fontRegular = null ;
    public static CardLayout cardLayout ;
    public static JPanel cardsPanel;
    public static JButton profileButton;
    public static JButton mainMenuButton;
    public static JButton allOrdersButton;
    public static JButton reportButton;


    static {
        try {
            fontBold = Font.createFont( Font.TRUETYPE_FONT, new File("src/View/Fonts/AmaticSC-Bold.ttf")).deriveFont(35f) ;
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            fontRegular = Font.createFont( Font.TRUETYPE_FONT, new File("src/View/Fonts/AmaticSC-Regular.ttf")).deriveFont(35f) ;
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  MainFrame(User user, ProfilePanel profilePanel, ReportPanel reportPanel, AllOrdersPanel allOrdersPanel) {
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CardLayout cardLayout1 = new CardLayout();
        setSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(cardLayout1);
        setVisible(true);

        // loading panel
        JPanel loadingPanel = new LoadingPage();
        cardLayout1.show(this.getContentPane(), "loading");

        // main panel
        panel = new JPanel(new BorderLayout());
        panel.setSize(new Dimension(this.getWidth(), this.getHeight()));

        // The top panel, which contains the buttons : Meals, Profile, and All Orders
        JPanel navigationBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navigationBarPanel.setBackground(lightGray);
        navigationBarPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        navigationBarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, orange));
        profileButton = createButton("Your Profile");
        mainMenuButton = createButton("Main Menu");
        allOrdersButton = createButton("All Orders");
        reportButton = createButton("Report");

        navigationBarPanel.add(profileButton);
        navigationBarPanel.add(mainMenuButton);
        navigationBarPanel.add(allOrdersButton);
        if (user.getUserType() == 2)
            navigationBarPanel.add(reportButton);

        panel.add(navigationBarPanel, BorderLayout.NORTH);

        // cards panel
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        mealsPanel = new MealsPanel(user);
        cardsPanel.add(mealsPanel, "mealsPanel");

        this.profilePanel = profilePanel;
        cardsPanel.add(profilePanel, "profilePanel");



        this.allOrdersPanel = allOrdersPanel;
        cardsPanel.add(allOrdersPanel, "allOrdersPanel");
        panel.add(cardsPanel, BorderLayout.CENTER);

        //we could've put the buttons actions listeners here

        add(loadingPanel, "loading");
        add(panel, "main");

        Timer showMainPanel = new Timer(6000, e -> {
            cardLayout1.show(this.getContentPane(), "main");
            panel.remove(loadingPanel);
        });
        showMainPanel.start();
    }

//    public void switchCard(JPanel parent,String child){
//        cardLayout.show(parent,child);
//    }


    JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBorderPainted(false);
//        button.setContentAreaFilled(false);
        button.setBackground(lightGray);
        button.setForeground(orange);
        button.setFont(fontBold);
        button.setPreferredSize(new Dimension(170, 40));
        return button;

    }
}
