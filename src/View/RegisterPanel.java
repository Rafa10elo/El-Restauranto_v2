package View;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

import static View.LoginAndRegistrationFrame.fieldsFont;

public class RegisterPanel extends JPanel {

    JTextField userField ;
    JTextField emailField;
    JPasswordField passField;
    JPasswordField passCheckField;
    JRadioButton chooseCustomerButton;
    JRadioButton chooseManagerButton;
    JRadioButton chooseEmployeeButton;
    JButton registerButton;
    JTextField optionalField;
    JButton backButton;

    public RegisterPanel(JPanel mainPanel, CardLayout cardLayout) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.out.println("Failed to initialize FlatLaf");
        }

        setLayout(new GridBagLayout());
        setLayout(new GridBagLayout());
        setBackground(MainFrame.darkBackground);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(20, 0, 20, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Username
        JLabel userLabel = createJLabel("Username:", gbc, 0, 0);
        gbc.gridwidth = 1;
        add(userLabel, gbc);

        userField = new JTextField(" Enter your username",20);
        gbc.gridx = 1;
        userField.setFont(LoginAndRegistrationFrame.fieldsFont.deriveFont(20f));
        userField.setBackground(LoginPanel.darkGray);
        userField.setForeground(LoginPanel.extraLightGray);
        userField.setBorder(new LineBorder(LoginPanel.extraLightGray,1));
        userField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userField.getText().equals(" Enter your username")) {
                    userField.setText("");
                }
                userField.setForeground(LoginPanel.white);
                userField.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (userField.getText().isEmpty()) {
                    userField.setText(" Enter your username");
                    userField.setForeground(LoginPanel.extraLightGray);
                }
                userField.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
            }
        });
        userField.addActionListener(e -> emailField.requestFocus());
        add(userField, gbc);

        // Email
        JLabel emailLabel = createJLabel("Email:", gbc, 0, 1);
        add(emailLabel, gbc);

        emailField = new JTextField(" Enter your email", 20);
        emailField.setFont(LoginAndRegistrationFrame.fieldsFont.deriveFont(20f));
        emailField.setBackground(LoginPanel.darkGray);
        emailField.setForeground(LoginPanel.extraLightGray);
        emailField.setBorder(new LineBorder(LoginPanel.extraLightGray,1));
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals(" Enter your email")) {
                    emailField.setText("");
                }
                emailField.setForeground(LoginPanel.white);
                emailField.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText(" Enter your email");
                    emailField.setForeground(LoginPanel.extraLightGray);
                }
                emailField.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
            }
        });

        gbc.gridx = 1;
        emailField.setBorder(new LineBorder(MainFrame.extraLightColor,1));
        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                boolean test = isValidEmail(emailField.getText());
                if(!test)
                    emailField.setBorder(new MatteBorder(0,0,2,0,Color.red));
                else
                    emailField.setBorder(new MatteBorder(0,0,2,0,new Color(70,73,75)));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                boolean test = isValidEmail(emailField.getText());
                if(!test)
                    emailField.setBorder(new MatteBorder(0,0,2,0,Color.red));
                else
                    emailField.setBorder(new MatteBorder(0,0,2,0,new Color(70,73,75)));
            }
        });

        emailField.addActionListener(e -> passField.requestFocus());
        add(emailField, gbc);

        // Password
        JLabel passLabel = createJLabel("Password:", gbc, 0, 2);
        add(passLabel, gbc);

        JPanel passwordEntryPanel = new JPanel(new GridBagLayout());
        passwordEntryPanel.setBorder(new LineBorder(LoginPanel.extraLightGray,1));
        passField = new JPasswordField(" Enter your password", 20);
        passField.setBorder(null);
        passField.setFont(LoginAndRegistrationFrame.fieldsFont.deriveFont(20f));
        passField.setBackground(LoginPanel.darkGray);
        passField.setForeground(LoginPanel.extraLightGray);
        passField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passField.getText().equals(" Enter your password")) {
                    passField.setText("");
                }
                passField.setForeground(LoginPanel.white);
                passwordEntryPanel.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passField.getText().isEmpty()) {
                    passField.setText(" Enter your password");
                    passField.setForeground(LoginPanel.extraLightGray);
                }
                passwordEntryPanel.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
            }
        });

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.insets = new Insets( 0, 0, 0 ,0);
        gbc1.weightx = 8;
        gbc1.weighty= 1;
        gbc1.gridwidth = 10;
        gbc1.gridheight = 1;
        gbc1.fill=GridBagConstraints.BOTH;
        passField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                int test = passwordCheck(passField.getText());
                if(passField.getText().equals(""))
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,new Color(70,73,75)));
                else if(test==0)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.red));
                else if (test == 1)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.ORANGE));
                else if (test == 2)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.YELLOW));
                else if (test == 3)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.GREEN));
                else if (test == 4)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.cyan));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                int test = passwordCheck(passField.getText());
                if(passField.getText().equals(""))
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,new Color(70,73,75)));
                else if(test==0)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.red));
                else if (test == 1)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.ORANGE));
                else if (test == 2)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.YELLOW));
                else if (test == 3)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.GREEN));
                else if (test == 4)
                    passwordEntryPanel.setBorder(new MatteBorder(0,0,1,0,Color.cyan));

            }
        });
        passField.addActionListener(e -> passCheckField.requestFocus());
        passwordEntryPanel.add(passField,gbc1);

        JCheckBox showPasswordCheckbox = new JCheckBox();
        showPasswordCheckbox.setOpaque(false);
        gbc1.gridx = 10;
        gbc1.weightx = 0.2;
        gbc1.gridwidth = 1;
        gbc1.anchor = GridBagConstraints.EAST;
        showPasswordCheckbox.setSize(8,8);

        passwordEntryPanel.add(showPasswordCheckbox,gbc1);
        passwordEntryPanel.setBackground(LoginPanel.darkGray);
        passwordEntryPanel.setBorder(new LineBorder(LoginPanel.extraLightGray,1));
        gbc.gridx = 1;
        add(passwordEntryPanel, gbc);

        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passField.setEchoChar((char) 0);
            } else {
                passField.setEchoChar('•');
            }
        });
        JLabel confirmPassLabel = createJLabel("Confirm Password:", gbc, 0, 3);
        add(confirmPassLabel, gbc);

        // Confirm password
        JPanel passwordEntryPanel1 = new JPanel(new GridBagLayout());
        passwordEntryPanel1.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
        passCheckField = new JPasswordField(" Confirm your password", 20);
        passCheckField.setFont(LoginAndRegistrationFrame.fieldsFont.deriveFont(20f));
        passCheckField.setBorder(null);
        passCheckField.setBackground(LoginPanel.darkGray);
        passCheckField.setForeground(LoginPanel.extraLightGray);
        passCheckField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passCheckField.getText().equals(" Confirm your password")) {
                    passCheckField.setText("");
                }
                passCheckField.setForeground(LoginPanel.white);
                passwordEntryPanel1.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passCheckField.getText().isEmpty()) {
                    passCheckField.setText(" Confirm your password");
                    passCheckField.setForeground(LoginPanel.extraLightGray);
                }
                passwordEntryPanel1.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
            }
        });
        passCheckField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                boolean test = checkingThePass(passField.getText(),passCheckField.getText());
                if(!test)
                    passwordEntryPanel1.setBorder(new MatteBorder(0,0,1,0,Color.red));
                else
                    passwordEntryPanel1.setBorder(new MatteBorder(0,0,1,0,new Color(70,73,75)));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                boolean test = checkingThePass(passField.getText(),passCheckField.getText());
                if(!test)
                    passwordEntryPanel1.setBorder(new MatteBorder(0,0,1,0,Color.red));
                else
                    passwordEntryPanel1.setBorder(new MatteBorder(0,0,1,0,new Color(70,73,75)));
            }
        });
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.insets = new Insets( 0, 0, 0 ,0);
        gbc1.weightx = 8;
        gbc1.weighty= 1;
        gbc1.gridwidth = 10;
        gbc1.gridheight = 1;
        gbc1.fill=GridBagConstraints.BOTH;
        passCheckField.addActionListener(e -> registerButton.doClick());
        passwordEntryPanel1.add(passCheckField,gbc1);

        JCheckBox showPasswordCheckCheckbox = new JCheckBox();
        showPasswordCheckCheckbox.setBackground(LoginPanel.darkGray);
        showPasswordCheckCheckbox.setOpaque(false);
        gbc1.gridx = 10;
        gbc1.weightx = 0.2;
        gbc1.gridwidth = 1;
        gbc1.anchor = GridBagConstraints.EAST;
        showPasswordCheckCheckbox.setSize(8,8);
        passwordEntryPanel1.add(showPasswordCheckCheckbox,gbc1);
        passwordEntryPanel1.setBackground(LoginPanel.darkGray);
        gbc.gridx = 1;
        add(passwordEntryPanel1, gbc);

        showPasswordCheckCheckbox.addActionListener(e -> {
            if (showPasswordCheckCheckbox.isSelected()) {
                passCheckField.setEchoChar((char) 0);
            } else {
                passCheckField.setEchoChar('•');
            }
        });

        // User type
        JLabel chooseUserLabel = createJLabel("User:", gbc, 0, 4);
        add(chooseUserLabel, gbc);

        chooseCustomerButton = new JRadioButton("Customer");
        chooseCustomerButton.setFont(MainFrame.fontBold.deriveFont(25f));
        chooseEmployeeButton = new JRadioButton("Employee");
        chooseEmployeeButton.setFont(MainFrame.fontBold.deriveFont(25f));
        chooseManagerButton = new JRadioButton("Manager");
        chooseManagerButton.setFont(MainFrame.fontBold.deriveFont(25f));

        ButtonGroup userChoice = new ButtonGroup();
        userChoice.add(chooseCustomerButton);
        userChoice.add(chooseEmployeeButton);
        userChoice.add(chooseManagerButton);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 35, 0));
        radioPanel.setBackground(MainFrame.darkBackground);
        radioPanel.add(chooseCustomerButton);
        radioPanel.add(chooseEmployeeButton);
        radioPanel.add(chooseManagerButton);

        radioPanel.setBackground(MainFrame.darkBackground);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(radioPanel, gbc);

        // Code field
        JLabel optionalFieldLabel = createJLabel("Employee/Manager Code:", gbc, 0, 6);
        add(optionalFieldLabel, gbc);

        optionalField = new JTextField(5);
        optionalField.setFont(LoginAndRegistrationFrame.fieldsFont.deriveFont(20f));
        optionalField.setBackground(LoginPanel.darkGray);
        optionalField.setForeground(LoginPanel.extraLightGray);
        optionalField.setBorder(new LineBorder(LoginPanel.extraLightGray,1));
        optionalField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (optionalField.getText().equals(" Enter code")) {
                    optionalField.setText("");
                }
                optionalField.setForeground(LoginPanel.white);
                optionalField.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (optionalField.getText().isEmpty()) {
                    optionalField.setText(" Enter code");
                    optionalField.setForeground(LoginPanel.extraLightGray);
                }
                optionalField.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
            }
        });


        optionalField.setEnabled(false);
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 50, 5, 10);
        optionalField.setBorder(new LineBorder(MainFrame.extraLightColor,1));
        optionalField.addActionListener(e -> registerButton.doClick());
        add(optionalField, gbc);

        gbc.insets = new Insets(20, 0, 0, 10);

        registerButton = new JButton("Register");
        registerButton.setFont(MainFrame.fontBold.deriveFont(30f));
        registerButton.setForeground(MainFrame.orange);
        registerButton.setBackground(LoginPanel.darkGray);
        registerButton.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBorder(new LineBorder(MainFrame.orange, 1));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        backButton = new JButton("Back to Login");
        backButton.setFont(MainFrame.fontBold.deriveFont(30f));
        backButton.setForeground(MainFrame.orange);
        backButton.setBackground(LoginPanel.darkGray);
        backButton.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBorder(new LineBorder(MainFrame.orange, 1));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBorder(new LineBorder(LoginPanel.extraLightGray, 1));
            }
        });

        gbc.gridy = 8;
        add(backButton, gbc);

        ActionListener radioActionListener = e -> {
            if (chooseEmployeeButton.isSelected() || chooseManagerButton.isSelected()) {
                optionalField.setEnabled(true);
                optionalField.setText(" Enter code");
                if (chooseEmployeeButton.isSelected())
                    chooseEmployeeButton.setForeground(MainFrame.orange);
                else
                    chooseManagerButton.setForeground(MainFrame.orange);
            } else {
                optionalField.setEnabled(false);
                optionalField.setText("");
                chooseEmployeeButton.setForeground(new Color(207, 212, 199));
                chooseManagerButton.setForeground(new Color(207, 212, 199));
            }
        };

        chooseCustomerButton.addActionListener(radioActionListener);
        chooseEmployeeButton.addActionListener(radioActionListener);
        chooseManagerButton.addActionListener(radioActionListener);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
    }

    JLabel createJLabel(String message, GridBagConstraints gbc, int gridx, int gridy) {
        JLabel label = new JLabel(message);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        label.setFont(MainFrame.fontBold.deriveFont(30f));
        label.setForeground(MainFrame.orange);
        return label;
    }
    public static int passwordCheck(String password) {
        if (password.length() < 8||password.matches(".*\\s.*")) {
            return 0;
        }
        int score = 1;

        String upperCaseCheck = ".*[A-Z].*";
        String numberCheck = ".*\\d.*";
        String symbolCheck = ".*[!@#$%^&*()\\-_=+{}\\[\\]:;\"'<>,.?/`~|\\\\].*";

        if (password.matches(upperCaseCheck))
            score++;

        if (password.matches(numberCheck))
            score++;

        if (password.matches(symbolCheck))
            score++;


        return score;
    }

    public static boolean isValidEmail(String email) {
        String emailCheck = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailCheck);
    }
    public static boolean checkingThePass(String pass1,String pass2) {

        return pass1.equals(pass2);
    }
    public String getUsername(){
        return userField.getText();
    }
    public String getPassword(){
        return passField.getText();
    }
    public String getEmail(){
        return emailField.getText();
    }
    public int getType (){
        if(chooseCustomerButton.isSelected())
            return 0;
        else if (chooseEmployeeButton.isSelected())
            return 1;
        else
            return 2;
    }
    public String getOptionalText(){
        return optionalField.getText();
    }
    public String getCheckPass(){
        return passCheckField.getText();
    }
    public JButton getRegisterButton() {
        return registerButton;
    }
    public boolean getChooseCustomer(){
        return chooseCustomerButton.isSelected();
    }
    public boolean getChooseEmployee(){
        return chooseEmployeeButton.isSelected();
    }
    public boolean getChooseManager(){
        return chooseManagerButton.isSelected();
    }
}
