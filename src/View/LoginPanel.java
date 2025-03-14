package View;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {
   public JTextField userField;
   public JPasswordField passField;
   public JButton loginButton;

    static Color darkGray = new Color(30, 31, 34) ;
    static Color lightGray = new Color(43, 45, 48) ;
    static Color extraLightGray = new Color(57, 59, 64) ;
    static Color white = new Color(207, 212, 199);

    public LoginPanel(JPanel mainPanel, CardLayout cardLayout){

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.out.println("Failed to initialize FlatLaf");
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        setFont(MainFrame.fontRegular);
        setLayout(new GridBagLayout());
        setBackground(MainFrame.darkBackground);

        JLabel userLabel = createJLabel("Username:",gbc,0,0);
        add(userLabel, gbc);

        userField = new JTextField(" Enter your username",20);
        gbc.gridx = 1;
        userField.setFont(LoginAndRegistrationFrame.fieldsFont.deriveFont(20f));
        userField.setBackground(darkGray);
        userField.setForeground(extraLightGray);
        userField.setBorder(new LineBorder(extraLightGray,1));
        userField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userField.getText().equals(" Enter your username")) {
                    userField.setText("");
                }
                userField.setForeground(white);
                userField.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (userField.getText().isEmpty()) {
                    userField.setText(" Enter your username");
                    userField.setForeground(extraLightGray);
                }
                userField.setBorder(new LineBorder(extraLightGray, 1));
            }
        });
        userField.addActionListener(e -> passField.requestFocus());
        add(userField, gbc);

        JLabel passLabel = createJLabel("Password:",gbc,0,1);
        add(passLabel, gbc);

        JPanel passwordEntryPanel = new JPanel(new GridBagLayout());
        passwordEntryPanel.setBorder(new LineBorder(extraLightGray,1));
        passwordEntryPanel.setBackground(darkGray);

        passField = new JPasswordField(" Enter your password", 20);
        passField.setFont(LoginAndRegistrationFrame.fieldsFont.deriveFont(20f));
        passField.setBorder(null);
        passField.setBackground(darkGray);
        passField.setForeground(extraLightGray);
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.insets = new Insets( 0, 0, 0 ,0);
        gbc1.weightx = 8;
        gbc1.weighty= 1;
        gbc1.gridwidth = 10;
        gbc1.gridheight = 1;
        gbc1.fill=GridBagConstraints.BOTH;
        passField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passField.getText().equals(" Enter your password")) {
                    passField.setText("");
                }
                passField.setForeground(white);
                passwordEntryPanel.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passField.getText().isEmpty()) {
                    passField.setText(" Enter your password");
                    passField.setForeground(extraLightGray);
                }
                passwordEntryPanel.setBorder(new LineBorder(extraLightGray, 1));
            }
        });
        passField.addActionListener(e -> loginButton.doClick());
        passwordEntryPanel.add(passField,gbc1);

        JCheckBox showPasswordCheckbox = new JCheckBox();
        showPasswordCheckbox.setBackground(darkGray);
        showPasswordCheckbox.setFont(LoginAndRegistrationFrame.fieldsFont);
        showPasswordCheckbox.setOpaque(false);
        gbc1.gridx = 10;
        gbc1.weightx = 0.2;
        gbc1.gridwidth = 1;
        gbc1.anchor = GridBagConstraints.EAST;

        showPasswordCheckbox.setSize(8,8);
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passField.setEchoChar((char) 0);
            } else {
                passField.setEchoChar('•');
            }
        });
        passwordEntryPanel.add(showPasswordCheckbox,gbc1);
        gbc.gridx = 1;
        add(passwordEntryPanel, gbc);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(MainFrame.fontBold.deriveFont(30f));
        loginButton.setForeground(MainFrame.orange);
        loginButton.setBackground(darkGray);
        loginButton.setBorder(new LineBorder(extraLightGray, 1));
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBorder(new LineBorder(MainFrame.orange, 1));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBorder(new LineBorder(extraLightGray, 1));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        JLabel registerLabel = new JLabel("Don't have an account?");
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerLabel.setFont(MainFrame.fontRegular.deriveFont(30f));
        registerLabel.setForeground(MainFrame.orange);
        add(registerLabel, gbc);

        JButton cmdRegister = new JButton("Register Here!");
        cmdRegister.setFont(MainFrame.fontBold.deriveFont(30f));
        cmdRegister.setBackground(darkGray);
        cmdRegister.setBorderPainted(false);
        cmdRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Register");
            }
        });

        gbc.gridx = 1;
        gbc.insets=new Insets(10,120,10,5);
        add(cmdRegister, gbc);

    }

    JLabel createJLabel(String message,GridBagConstraints gbc,int gridx,int gridy){
        JLabel label = new JLabel(message);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        label.setFont(MainFrame.fontBold.deriveFont(30f));
        label.setForeground(MainFrame.orange);
        return label;
    }
    public String getUsername(){
        return userField.getText();
    }
    public String getPassword(){
        return passField.getText();
    }




}
