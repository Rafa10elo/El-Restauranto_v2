package View;

import Model.User;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static View.LoginAndRegistrationFrame.fieldsFont;
import static View.ReportPanel.*;



public class ProfilePanel extends JPanel {



    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField editPasswordField;
    private JTextField editEmailField;
    private JTextField editUsernameTextField;
    private User user;
    public JButton editProfileButton;
    public JButton logoutButton;
    public JPanel mainPanel;

    public ProfilePanel(User user) {
        this.user=user;
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setLayout(new BorderLayout());

        //sidebar
        JPanel profileSidebarPanel = new JPanel();
        profileSidebarPanel.setLayout(new BorderLayout());
        profileSidebarPanel.setBackground(MainFrame.lightGray);
        profileSidebarPanel.setPreferredSize(new Dimension(200, 400));
        profileSidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, MainFrame.orange));
        add(profileSidebarPanel, BorderLayout.WEST);

        //pfp
        ImageIcon profileIcon = new ImageIcon("src/View/Images/profilePicture.png");
        Image scaledImage = profileIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel profilePicLabel = new JLabel(new ImageIcon(scaledImage));
        profilePicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileSidebarPanel.add(profilePicLabel, BorderLayout.NORTH);

        //sidebar buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(MainFrame.lightGray);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        editProfileButton = new JButton("Edit");
        editProfileButton.setFont(MainFrame.fontBold.deriveFont(40F));
        editProfileButton.setFocusPainted(false);
        editProfileButton.setBackground(MainFrame.orange);
        editProfileButton.setForeground(MainFrame.darkGray);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(MainFrame.fontBold.deriveFont(40F));
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(MainFrame.orange);
        logoutButton.setForeground(MainFrame.darkGray);

        buttonPanel.add(editProfileButton);
        buttonPanel.add(logoutButton);
        profileSidebarPanel.add(buttonPanel, BorderLayout.SOUTH);

        //switching card layout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel, BorderLayout.CENTER);

        //main panel
         mainPanel = fillProfile(user);
        //edit panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());
        editPanel.setBackground(MainFrame.darkGray);

        JLabel editUsernameLabel = new JLabel("Username:");
        editUsernameLabel.setFont(MainFrame.fontBold.deriveFont(40F));
        editUsernameLabel.setForeground(MainFrame.orange);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        editPanel.add(editUsernameLabel, gbc);

        editUsernameTextField = new JTextField(user.getUserName(), 15);
        editUsernameTextField.setFont(fieldsFont.deriveFont(30f));
        gbc.gridx = 1;
        gbc.gridy = 0;
        editPanel.add(editUsernameTextField, gbc);


        JLabel editEmailLabel = new JLabel("Email:");
        editEmailLabel.setFont(MainFrame.fontBold.deriveFont(40F));
        editEmailLabel.setForeground(MainFrame.orange);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        editPanel.add(editEmailLabel, gbc);

        editEmailField = new JTextField(user.getEmail(), 15);
        editEmailField.setFont(fieldsFont.deriveFont(30f));
        gbc.gridx = 1;
        gbc.gridy = 1;
        editPanel.add(editEmailField, gbc);


        JLabel editPasswordLabel = new JLabel("Password:");
        editPasswordLabel.setFont(MainFrame.fontBold.deriveFont(40F));
        editPasswordLabel.setForeground(MainFrame.orange);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        editPanel.add(editPasswordLabel, gbc);

        editPasswordField = new JTextField(user.getPassword(), 15);
        editPasswordField.setFont(fieldsFont.deriveFont(30f));
        gbc.gridx = 1;
        gbc.gridy = 2;
        editPasswordField.addKeyListener(new KeyAdapter() {
            @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);
            int test = RegisterPanel.passwordCheck(editPasswordField.getText());
            if(editPasswordField.getText().equals(""))
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,new Color(70,73,75)));
            else if(test==0)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.red));
            else if (test == 1)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.ORANGE));
            else if (test == 2)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.YELLOW));
            else if (test == 3)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.GREEN));
            else if (test == 4)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.cyan));


        }

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if(editPasswordField.getText().equals(""))
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,new Color(70,73,75)));
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            int test = RegisterPanel.passwordCheck(editPasswordField.getText());
            if(editPasswordField.getText().equals(""))
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,new Color(70,73,75)));
            else if(test==0)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.red));
            else if (test == 1)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.ORANGE));
            else if (test == 2)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.YELLOW));
            else if (test == 3)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.GREEN));
            else if (test == 4)
                editPasswordField.setBorder(new MatteBorder(0,0,2,0,Color.cyan));

        }
    });
        editPanel.add(editPasswordField, gbc);

        cardPanel.add(mainPanel, "main");
        cardPanel.add(editPanel, "edit");


        //to switch between edit and main panel
//        editProfileButton.addActionListener(new ActionListener() {
//            private boolean inEditMode = false;
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (inEditMode) {
//                    // Switch back to main panel
//                    cardLayout.show(cardPanel, "main");
//                    editProfileButton.setText("Edit");
//                    inEditMode = false;
//                } else {
//                    // Switch to edit panel
//                    String passwordDialog = JOptionPane.showInputDialog(mainPanel, "Enter your password:");
//                    if(Objects.equals(passwordDialog, user.getPassword())){
//                        cardLayout.show(cardPanel, "edit");
//                        inEditMode = true;
//                        editProfileButton.setText("Save");
//                    }
//                    else
//                    JOptionPane.showMessageDialog(mainPanel, "Access Denied", "Error", JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });


//        logoutButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                LoginAndRegistrationFrame loginAndRegistrationFrame=new LoginAndRegistrationFrame();
//
//            }
//        });
    }
    public String getEditedUsername(){return editUsernameTextField.getText();}
    public String getEditedEmail(){return editEmailField.getText();}
    public String getEditedPassword(){return editPasswordField.getText();}
    public User getUser(){return user;}
    public JPanel getMainPanel(){return mainPanel;}

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public JPanel fillProfile(User user){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(MainFrame.darkGray);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("Welcome, "+user.getUserName()+" !");
        welcomeLabel.setFont(MainFrame.fontBold.deriveFont(60F));
        welcomeLabel.setForeground(MainFrame.orange);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(welcomeLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(MainFrame.fontBold.deriveFont(40F));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameLabel, gbc);

        JLabel usernameValue = new JLabel(user.getUserName());
        usernameValue.setFont(MainFrame.fontRegular.deriveFont(40F));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameValue, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(MainFrame.fontBold.deriveFont(40F));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(emailLabel, gbc);

        JLabel emailValue = new JLabel(user.getEmail());
        emailValue.setFont(MainFrame.fontRegular.deriveFont(40F));
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(emailValue, gbc);

        JLabel userTypeLabel = new JLabel("User:");
        userTypeLabel.setFont(MainFrame.fontBold.deriveFont(40F));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(userTypeLabel, gbc);

        String userType= "";
        switch (user.getUserType()){
            case 0:
                userType="Customer";
                break;
            case 1:
                userType="Employee";
                break;
            case 2:
                userType="Manager";
                break;
        }

        JLabel userTypeValue = new JLabel(userType);
        userTypeValue.setFont(MainFrame.fontRegular.deriveFont(40F));
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(userTypeValue, gbc);

        return mainPanel;
    }
}
