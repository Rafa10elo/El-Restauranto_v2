package View;

import Model.Meal;
import Model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;

public class SidePanel extends JPanel {

    // new meal info
    JTextField nameField;
    JTextField priceField;
    JTextField ingredientsField;
    JTextField imgSrcField;
    JButton addMeal ;


    // order info

    float totalPriceNumber = 0;
    JLabel totalPrice = new JLabel(String.valueOf(totalPriceNumber)) ;
    Float[] tips = new Float[] {0.0f, 5.0f, 10.0f, 15.0f} ;
    JComboBox tipsCombo = new JComboBox(tips) ;
    JPanel centerPanel;
    HashMap<Meal, Integer> orderMeals = new HashMap<>();
    HashMap<Meal, JLabel> mealsCntLabels = new HashMap<>();
    JRadioButton  dineIn ;
    JRadioButton  delivery ;
    JTextField specialRequests ;

    // payment info
    JDialog paymentDialog ;
    JRadioButton  cash ;
    JRadioButton creditCard ;
    JTextField creditCardId ;
    JButton pay = new JButton();
    JButton resetOrder = new JButton();
    public SidePanel(User user) {
        setLayout(new BorderLayout());
        setBackground(MainFrame.darkGray);
        setPreferredSize(new Dimension(350, this.getHeight()));
        setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, MainFrame.orange));

        // top panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(MainFrame.darkGray);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, MainFrame.orange));

        if (user.getUserType() == 0) {
            // Your order :
            JLabel youOrder = new JLabel("Your order :");
            youOrder.setForeground(MainFrame.orange);
            youOrder.setFont(MainFrame.fontBold);
            topPanel.add(youOrder);
        }
        else {
            // Add new meal :
            JLabel newMeal = new JLabel("Add new meal :");
            newMeal.setForeground(MainFrame.orange);
            newMeal.setFont(MainFrame.fontBold);
            topPanel.add(newMeal);
        }
        add(topPanel, BorderLayout.NORTH);


        // center panel
        centerPanel = new JPanel();
        if (user.getUserType() == 0) {
            // order meals
            //centerPanel.setPreferredSize(new Dimension(330, (mealsCntLabels.size() * 500)));
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
            centerPanel.setBackground(MainFrame.darkGray);
            add(new JScrollPane(centerPanel), BorderLayout.CENTER);
        }
        else {
            // name, price,ingredients and img path text fields
            centerPanel.setLayout(new GridBagLayout());
            centerPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
            centerPanel.setBackground(MainFrame.darkGray);
            GridBagConstraints gbc = new GridBagConstraints();

            // name
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            JLabel name = new JLabel("name :");
            name.setForeground(MainFrame.orange);
            name.setFont(MainFrame.fontBold);
            name.setBackground(MainFrame.darkGray);
            centerPanel.add(name, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            nameField = new JTextField("name");
            nameField.setFont(MainFrame.fontBold.deriveFont(20f));
            nameField.setForeground(MainFrame.extraLightGray);
            nameField.setBackground(MainFrame.darkGray);
            nameField.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
            nameField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (nameField.getText().equals("name")) {
                        nameField.setText("");
                        nameField.setForeground(MainFrame.orange);
                    }
                    nameField.setBorder(new LineBorder(MainFrame.orange, 1));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (nameField.getText().isEmpty()) {
                        nameField.setText("name");
                        nameField.setForeground(MainFrame.extraLightGray);
                    }
                    nameField.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
                }
            });
            centerPanel.add(nameField, gbc);

            //price :
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            JLabel price = new JLabel("price : ($)");
            price.setForeground(MainFrame.orange);
            price.setFont(MainFrame.fontBold);
            price.setBackground(MainFrame.darkGray);
            centerPanel.add(price, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            priceField = new JTextField("price");
            priceField.setFont(MainFrame.fontBold.deriveFont(20f));
            priceField.setForeground(MainFrame.extraLightGray);
            priceField.setBackground(MainFrame.darkGray);
            priceField.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
            priceField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    priceField.setBorder(new LineBorder(MainFrame.orange, 1));
                    if (priceField.getText().equals("price")) {
                        priceField.setText("");
                        priceField.setForeground(MainFrame.orange);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    priceField.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
                    if (priceField.getText().isEmpty()) {
                        priceField.setText("price");
                        priceField.setForeground(MainFrame.extraLightGray);
                    }
                }
            });
            priceField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    checkInput();
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    checkInput();
                }

                public void checkInput() {
                    String text = priceField.getText();
                    if (!text.matches("\\d+(\\.\\d+)?")) {
                        priceField.setBorder(new LineBorder(Color.RED, 1));
                        priceField.setForeground(Color.RED);
                    } else {
                        priceField.setBorder(new LineBorder(MainFrame.orange, 1));
                        priceField.setForeground(MainFrame.orange);
                    }
                }
            });
            centerPanel.add(priceField, gbc);

            // ingredients
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            JLabel ingredients = new JLabel("ingredients :");
            ingredients.setForeground(MainFrame.orange);
            ingredients.setFont(MainFrame.fontBold);
            ingredients.setBackground(MainFrame.darkGray);
            centerPanel.add(ingredients, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            ingredientsField = new JTextField("ingredients");
            ingredientsField.setFont(MainFrame.fontBold.deriveFont(20f));
            ingredientsField.setForeground(MainFrame.extraLightGray);
            ingredientsField.setBackground(MainFrame.darkGray);
            ingredientsField.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
            ingredientsField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (ingredientsField.getText().equals("ingredients")) {
                        ingredientsField.setText("");
                        ingredientsField.setForeground(MainFrame.orange);
                    }
                    ingredientsField.setBorder(new LineBorder(MainFrame.orange, 1));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (ingredientsField.getText().isEmpty()) {
                        ingredientsField.setText("ingredients");
                        ingredientsField.setForeground(MainFrame.extraLightGray);
                    }
                    ingredientsField.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
                }
            });
            centerPanel.add(ingredientsField, gbc);

            // img src
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            JLabel imagePath = new JLabel("image path :");
            imagePath.setForeground(MainFrame.orange);
            imagePath.setFont(MainFrame.fontBold);
            imagePath.setBackground(MainFrame.darkGray);
            centerPanel.add(imagePath, gbc);

            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            imgSrcField = new JTextField("image path");
            imgSrcField.setPreferredSize(new Dimension(280, 50));
            imgSrcField.setFont(MainFrame.fontBold.deriveFont(20f));
            imgSrcField.setForeground(MainFrame.extraLightGray);
            imgSrcField.setBackground(MainFrame.darkGray);
            imgSrcField.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
            imgSrcField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (imgSrcField.getText().equals("image path")) {
                        imgSrcField.setText("");
                        imgSrcField.setForeground(MainFrame.orange);
                    }
                    imgSrcField.setBorder(new LineBorder(MainFrame.orange, 1));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (imgSrcField.getText().isEmpty()) {
                        imgSrcField.setText("image path");
                        imgSrcField.setForeground(MainFrame.extraLightGray);
                    }
                    imgSrcField.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
                }
            });
            centerPanel.add(imgSrcField, gbc);

            add(centerPanel, BorderLayout.CENTER);
        }


        // bottom panel
        if (user.getUserType() == 0) {
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            bottomPanel.setBackground(MainFrame.darkGray);
            bottomPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, MainFrame.orange));

            //total price panel : (TOTAL PRICE : ??? $)
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.gridheight = 2;
            gbc.weightx = 2.0;
            gbc.weighty = 2.0;
            gbc.fill = GridBagConstraints.BOTH;

            JPanel totalPricePanel = new JPanel();
            totalPricePanel.setBackground(MainFrame.darkGray);

//            JLabel totalPriceLabel = new JLabel(" Total Price : ");
//            totalPriceLabel.setForeground(MainFrame.orange);
//            totalPriceLabel.setFont(MainFrame.fontBold);
//            totalPriceLabel.setBackground(MainFrame.darkGray);
//            totalPricePanel.add(totalPriceLabel);

            totalPrice.setFont(MainFrame.fontBold.deriveFont(25f));
            totalPrice.setBackground(MainFrame.darkGray);
            totalPricePanel.add(totalPrice);

            JLabel dollar = new JLabel(" $");
            dollar.setForeground(MainFrame.orange);
            dollar.setFont(MainFrame.fontBold);
            dollar.setBackground(MainFrame.darkGray);
            totalPricePanel.add(dollar);

//            JScrollPane scrollPrice = new JScrollPane(totalPricePanel) ;
//            scrollPrice.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//            scrollPrice.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            bottomPanel.add(totalPricePanel, gbc);

            //tip
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;

            JPanel tipPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            tipPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, MainFrame.extraLightGray));
            tipPanel.setBackground(MainFrame.darkGray);

            JLabel tipLabel = new JLabel("tip :");
            tipLabel.setForeground(MainFrame.orange);
            tipLabel.setFont(MainFrame.fontBold.deriveFont(25f));

            tipsCombo.setFont(MainFrame.fontBold.deriveFont(25f));
            tipsCombo.setBackground(MainFrame.darkGray);
            tipsCombo.setBorder(new LineBorder(MainFrame.extraLightGray, 1));

            tipPanel.add(tipLabel);
            tipPanel.add(tipsCombo);

            bottomPanel.add(tipPanel, gbc);

            // submit order
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            gbc.gridheight = 3;
            gbc.weightx = 3.0;
            gbc.weighty = 3.0;
            gbc.fill = GridBagConstraints.BOTH;
            JButton submitOrder = new JButton("Submit Order");
            submitOrder.setForeground(MainFrame.orange);
            submitOrder.setFont(MainFrame.fontBold);
            submitOrder.setBackground(MainFrame.darkGray);
            submitOrder.setBorder(new LineBorder(MainFrame.extraLightGray, 2));

            submitOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!orderMeals.isEmpty())
                        createPaymentDialog();
                }
            });
            bottomPanel.add(submitOrder, gbc);

            add(bottomPanel, BorderLayout.SOUTH);
        } else {
            addMeal = new JButton("add meal");
            addMeal.setPreferredSize(new Dimension(this.getWidth(), 100));
            addMeal.setForeground(MainFrame.orange);
            addMeal.setFont(MainFrame.fontBold);
            addMeal.setBackground(MainFrame.darkGray);
            addMeal.setBorder(new LineBorder(MainFrame.orange, 2));

            // this action listener is just for fun ... it should be in the controller  x 2
//            addMeal.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                        System.out.println(Float.parseFloat(priceField.getText()));
//                    if (mealInfoValid()){
//                        System.out.println("tmmmmmm");
//                        newMenuTextFieldsReset();
//
//                    }else
//                        System.out.println("wrong");
//                }
//            });
            add(addMeal, BorderLayout.SOUTH);
        }
    }
    public boolean mealInfoValid() {

        File temp = new File(imgSrcField.getText()) ;
        boolean tempIsImg = temp.getPath().endsWith(".jpg") || temp.getPath().endsWith(".jpeg") || temp.getPath().endsWith(".png")
                || temp.getPath().endsWith(".gif") || temp.getPath().endsWith(".bmp") ;
        if (!nameField.getText().equals("name") && priceField.getText().matches("\\d+(\\.\\d+)?") &&
                !ingredientsField.getText().equals("ingredients") && temp.exists() && tempIsImg){
            return true;
        }else{
            if (nameField.getText().isEmpty() || nameField.getText().equals("name")){
                nameField.setBorder(new LineBorder(Color.RED, 1));
            }
            if (priceField.getText().isEmpty() || priceField.getText().equals("price") || !priceField.getText().matches("\\d+(\\.\\d+)?")){
                priceField.setBorder(new LineBorder(Color.RED, 1));
            }
            if (ingredientsField.getText().isEmpty() || ingredientsField.getText().equals("ingredients")){
                ingredientsField.setBorder(new LineBorder(Color.RED, 1));
            }
            if( imgSrcField.getText().isEmpty() || imgSrcField.getText() == null || !temp.exists() || !tempIsImg ) {
                imgSrcField.setBorder(new LineBorder(Color.RED, 1));
            }
            return false;
        }

    }
    public boolean paymentInfoValid(){
        if ( ( cash.isSelected() )  ||  ( creditCard.isSelected() && (!creditCardId.getText().isEmpty() && creditCardId.getText()!=null) ))
            return true ;
        else{
            if ( !cash.isSelected() && !creditCard.isSelected()){
                cash.setForeground(Color.RED);
                creditCard.setForeground(Color.RED);
            }else{
                cash.setForeground(MainFrame.orange);
                creditCard.setForeground(MainFrame.orange);
            }
            if ( creditCard.isSelected() && creditCardId.getText().isEmpty() ){
                creditCardId.setBorder(new LineBorder(Color.RED, 1));
            }
            return false;
        }
    }
    public void newMealReset() {
        nameField.setText("name");
        nameField.setForeground(MainFrame.extraLightGray);
        priceField.setText("price");
        priceField.setForeground(MainFrame.extraLightGray);
        ingredientsField.setText("ingredients");
        ingredientsField.setForeground(MainFrame.extraLightGray);
        imgSrcField.setText("image path");
        imgSrcField.setForeground(MainFrame.extraLightGray);

    }
    public void orderReset(){

        totalPriceNumber=0;
        totalPrice.setText(String.valueOf(totalPriceNumber));
        centerPanel.removeAll();
        tipsCombo.setSelectedIndex(0);
        orderMeals.clear();
        mealsCntLabels.clear();

        creditCard.setSelected(false);
        cash.setSelected(false);
        creditCardId.setText("");
        paymentDialog.dispose();
        paymentDialog.removeAll();

        this.revalidate();
        this.repaint();
    }
    public void createPaymentDialog () {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        paymentDialog = new JDialog(frame,"payment", true);
        paymentDialog.setSize(new Dimension(550, 400));
        paymentDialog.setLocationRelativeTo(null);

//        paymentDialog.setModal(true);
//        paymentDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        paymentDialog.getContentPane().setBackground(MainFrame.darkGray);
        paymentDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //payment amount
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel paidAmount = new JLabel(" Payment :");
        paidAmount.setFont(MainFrame.fontBold.deriveFont(25f));
        paidAmount.setForeground(MainFrame.orange);
        paymentDialog.add(paidAmount, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        JLabel totalPrice = new JLabel(String.valueOf(totalPriceNumber + (float) tipsCombo.getSelectedItem() ) ) ;
        totalPrice.setFont(MainFrame.fontBold.deriveFont(25f));
        totalPrice.setForeground(MainFrame.orange);
        paymentDialog.add(totalPrice, gbc);

        // payment method
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JLabel method = new JLabel(" How do you want to pay ?");
        method.setFont(MainFrame.fontBold.deriveFont(25f));
        method.setForeground(MainFrame.orange);
        paymentDialog.add(method, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        cash = new JRadioButton("Cash");
        cash.setForeground(MainFrame.orange);
        cash.setFont(MainFrame.fontBold.deriveFont(25f));
        cash.setFocusPainted(false);
        cash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCardId.setBackground(MainFrame.darkGray);
                creditCardId.setBorder(new LineBorder(MainFrame.lightGray, 1));
                creditCardId.setEnabled(false);
                creditCardId.repaint();
            }
        });
        paymentDialog.add(cash, gbc) ;

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        creditCard = new JRadioButton("Credit Card");
        creditCard.setForeground(MainFrame.orange);
        creditCard.setFont(MainFrame.fontBold.deriveFont(25f));
        creditCard.setFocusPainted(false);
        creditCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCardId.setBackground(MainFrame.lightGray);
                creditCardId.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
                creditCardId.setEnabled(true);
                creditCardId.repaint();
            }
        });
        paymentDialog.add(creditCard, gbc) ;

        ButtonGroup group = new ButtonGroup();
        group.add(cash) ;
        group.add(creditCard);

        // credit card id
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JLabel ccID = new JLabel(" Enter your card number :");
        ccID.setForeground(MainFrame.orange);
        ccID.setFont(MainFrame.fontBold.deriveFont(25f));
        paymentDialog.add(ccID, gbc) ;

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        gbc.insets = new Insets(0, 0, 0, 10);
        creditCardId = new JTextField("") ;
        creditCardId.setPreferredSize(new Dimension(0, 30));
        creditCardId.setBackground(MainFrame.darkGray);
        creditCardId.setBorder(new LineBorder(MainFrame.lightGray, 1));
        creditCardId.setEnabled(false);
        paymentDialog.add(creditCardId, gbc) ;

        // order type
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JLabel orderType = new JLabel(" Order type :");
        orderType.setForeground(MainFrame.orange);
        orderType.setFont(MainFrame.fontBold.deriveFont(25f));
        paymentDialog.add(orderType, gbc) ;

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        dineIn = new JRadioButton("Dine-in");
        dineIn.setForeground(MainFrame.orange);
        dineIn.setFont(MainFrame.fontBold.deriveFont(25f));
        dineIn.setFocusPainted(false);
        dineIn.setSelected(true);
        paymentDialog.add(dineIn, gbc) ;

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        delivery = new JRadioButton("Delivery");
        delivery.setForeground(MainFrame.orange);
        delivery.setFont(MainFrame.fontBold.deriveFont(25f));
        delivery.setFocusPainted(false);
        paymentDialog.add(delivery, gbc) ;

        ButtonGroup orderTypeGroup = new ButtonGroup();
        orderTypeGroup.add(dineIn) ;
        orderTypeGroup.add(delivery);

        // Special Requests
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JLabel specialRequestsLabel = new JLabel(" Special Requests :");
        specialRequestsLabel.setForeground(MainFrame.orange);
        specialRequestsLabel.setFont(MainFrame.fontBold.deriveFont(25f));
        paymentDialog.add(specialRequestsLabel, gbc) ;

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        gbc.insets = new Insets(0, 0, 0, 10);
        specialRequests = new JTextField("") ;
        specialRequests.setPreferredSize(new Dimension(0, 30));
        specialRequests.setBackground(MainFrame.lightGray);
        specialRequests.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
        paymentDialog.add(specialRequests, gbc) ;

        // cancel button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE ;
        gbc.anchor = GridBagConstraints.WEST ;
        gbc.insets = new Insets(0, 30, 0, 0);
        resetOrder.setText("reset order"); ;
        resetOrder.setPreferredSize(new Dimension(100, 40));
        resetOrder.setBackground(MainFrame.lightGray);
        resetOrder.setForeground(MainFrame.orange);
        resetOrder.setFont(MainFrame.fontBold.deriveFont(25f));
        resetOrder.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
        paymentDialog.add(resetOrder, gbc);

        // pay button
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST ;
        gbc.insets = new Insets(0, 0, 0, 30);
        pay.setText("pay"); ;
        pay.setPreferredSize(new Dimension(100, 40));
        pay.setBackground(MainFrame.lightGray);
        pay.setForeground(MainFrame.orange);
        pay.setFont(MainFrame.fontBold.deriveFont(25f));
        pay.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
        paymentDialog.add(pay, gbc);

        paymentDialog.setVisible(true);
    }


    // getters

    // for add order
        // payment is in the controller directly
        // order is in the controller directly

    // new meal info
    public String getNameField() {
        return nameField.getText();
    }

    public Float getPriceField() {
        return Float.parseFloat(priceField.getText());
    }

    public String  getIngredientsField() {
        return ingredientsField.getText();
    }

    public String getImgSrcField() {
        return imgSrcField.getText();
    }

    public JButton getAddMealButton() {
        return addMeal;
    }

        // new order info
    // payment
    public String getMethod() {
        if ( cash.isSelected() )
            return "cash" ;
        return "credit card" ;
    }
    public JTextField getCreditCardId() {
        return creditCardId;
    }
    public Float getPaymentAmount() {
        return (float) (totalPriceNumber + tips[tipsCombo.getSelectedIndex()] ) ;
    }
    public JButton getPayButton() {
        return pay;
    }
    public JButton getResetOrderButton() {
        return resetOrder;
    }
    public Float getTotalPrice() {
        return totalPriceNumber;
    }
    public Float getTips() {
        return (float) tipsCombo.getSelectedItem();
    }
    public HashMap<Meal, Integer> getOrderMeals() {
        return orderMeals;
    }
    public Boolean getInRestaurant() {
        if ( delivery.isSelected() )
            return false ;
        return true ;
    }

}
