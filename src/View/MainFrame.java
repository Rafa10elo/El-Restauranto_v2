package View;
import Model.User;
import com.formdev.flatlaf.FlatDarkLaf ;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    public MealsPanel mealsPanel;
    public ProfilePanel profilePanel ;
    public ReportPanel reportPanel;
    public AllOrdersPanel allOrdersPanel;
    public JPanel panel;
    public static CardLayout cardLayout ;
    public static JPanel cardsPanel;
    public static JButton profileButton;
    public static JButton mainMenuButton;
    public static JButton allOrdersButton;
    public static JButton reportButton;

    public static Font fontBold = null ;
    public static Font fontRegular = null ;

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

    // colors
    private static Color orange = new Color(206, 129, 76);
    private static Color green = new Color(113, 158, 109);
    private static Color red = new Color(195, 75, 76);
    public static Color darkBackground = new Color(30, 31, 34) ;
    public static Color lightBackground = new Color(43, 45, 48) ;
    public static Color extraLightColor = new Color(57, 59, 64) ;
    public static Color mainColor = orange;
    public JButton backgroundSwitch;
    public JButton orangeB;
    public JButton greenB;
    public JButton redB;

    public  MainFrame(User user, ProfilePanel profilePanel, ReportPanel reportPanel, AllOrdersPanel allOrdersPanel) {
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CardLayout cardLayout1 = new CardLayout();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        navigationBarPanel.setBackground(lightBackground);
        navigationBarPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        navigationBarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, mainColor));
        profileButton = createButton("Your Profile");
        mainMenuButton = createButton("Main Menu");
        allOrdersButton = createButton("All Orders");
        reportButton = createButton("Report");

        navigationBarPanel.add(profileButton);
        navigationBarPanel.add(mainMenuButton);
        navigationBarPanel.add(allOrdersButton);
        if (user.getUserType() == 2){
            navigationBarPanel.add(reportButton);
            navigationBarPanel.add(Box.createRigidArea(new Dimension(680,0)));
        }else
            navigationBarPanel.add(Box.createRigidArea(new Dimension(850,0)));

        // add themes buttons to the top panel
            // color buttons
        JPanel colorsPanel = new JPanel();
        colorsPanel.setLayout(new FlowLayout());
        colorsPanel.setBackground(lightBackground);
        orangeB = createCircleButton(orange);
        greenB = createCircleButton(green);
        redB = createCircleButton(red);
        colorsPanel.add(orangeB);
        colorsPanel.add(greenB);
        colorsPanel.add(redB);
        navigationBarPanel.add(colorsPanel);
            // background button
        backgroundSwitch = new JButton("D");
        backgroundSwitch.setBorderPainted(false);
        backgroundSwitch.setBorderPainted(false);
        backgroundSwitch.setBackground(lightBackground);
        backgroundSwitch.setPreferredSize(new Dimension(50, 40));
        navigationBarPanel.add(backgroundSwitch);

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
        });
        showMainPanel.start();
    }

//    public void switchCard(JPanel parent,String child){
//        cardLayout.show(parent,child);
//    }


    JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBorderPainted(false);
        button.setBackground(lightBackground);
        button.setForeground(mainColor);
        button.setFont(fontBold);
        button.setPreferredSize(new Dimension(170, 40));
        return button;

    }

    JButton createCircleButton(Color color) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2d.setColor(color.darker());
                } else {
                    g2d.setColor(color);
                }
                g2d.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        button.setPreferredSize(new Dimension(20, 20));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    public static void resetMainColor (int main){
        switch (main){
            case 0:
                mainColor = new Color(206, 129, 76);
                break;
            case 1:
                mainColor = new Color(113, 158, 109);
                break;
            case 2:
                mainColor = new Color(195, 75, 76);
                break;
        }
    }
    public static void resetBackground (int bg){
        switch (bg){
            case 1:
                darkBackground = new Color(30, 31, 34) ;
                lightBackground = new Color(43, 45, 48) ;
                extraLightColor = new Color(57, 59, 64) ;
                break;
            case 2:
                mainColor = new Color(113, 158, 109);
                break;
        }
    }
}
