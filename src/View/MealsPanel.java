package View;

import Model.Meal;
import Model.Order;
import Model.Payment;
import Model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.round;

public class MealsPanel extends JPanel {
    JPanel mainMenu;
    SidePanel sidePanel;
//    HashMap<Meal, MealPanel> allMeals = new HashMap<>() ;
    ArrayList<MealPanel> allMeals = new ArrayList<>();
    Meal currentEditMeal = null ;

    // edit meal info
    JDialog editMealDialog = new JDialog() ;
    JTextField nameEdit;
    JTextField priceEdit;
    JTextField ingredientsEdit;
    JTextField imgSrcEdit;
    JButton editMeal = new JButton() ;
    JButton deleteMeal = new JButton() ;

    public MealsPanel (User user) {
        setLayout(new BorderLayout());
        sidePanel = new SidePanel(user) ;
        add(sidePanel, BorderLayout.EAST) ;

        mainMenu = new JPanel() ;
        mainMenu.setBackground(MainFrame.darkGray);
        mainMenu.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollMainMenu = new JScrollPane(mainMenu);
        scrollMainMenu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollMainMenu, BorderLayout.CENTER) ;
    }

    public void fillMainMenu(ArrayList<Meal> meals) {
        mainMenu.removeAll();
        int rowsNum ;
        if (meals.size() % 3 == 0){
            rowsNum = meals.size() / 3 ;
        }else{
            rowsNum = meals.size() / 3 + 1;
        }
        int gap = 10 ;
        mainMenu.setLayout(new GridLayout(0, 3, gap, gap));

        for (Meal m : meals){
            MealPanel mealPanel = createMealPanelTOMenu(m) ;
            mainMenu.add(mealPanel);
            allMeals.add(mealPanel);
        }

        int panelWidth = 900; // 900 - gap * 2 --> cell width
        int panelHeight = 350 * rowsNum + (rowsNum - 1) * gap ; // cell Height * rowsNum + (rowsNum - 1) * gap
        mainMenu.setPreferredSize(new Dimension(panelWidth, panelHeight));
        mainMenu.revalidate();
        mainMenu.repaint();

    }

    MealPanel createMealPanelTOMenu(Meal meal) {
        MealPanel mealPanel = new MealPanel(meal) ;

        // adding mouse listener to make the panel act like a button 🐰
        mealPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mealPanel.setBorder(new LineBorder(new Color(91, 94, 102), 1));
                mealPanel.infoPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(91, 94, 102)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mealPanel.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
                mealPanel.infoPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MainFrame.extraLightGray));
            }
        });
        return mealPanel;
    }

    public void addMealPanelToOrder(Meal meal) {
        if (sidePanel.orderMeals.containsKey(meal)) {
            // edit cnt in meals hashmap
            sidePanel.orderMeals.put(meal, sidePanel.orderMeals.getOrDefault(meal, 0) + 1);
            //edit label in hashmap
            sidePanel.mealsCntLabels.get(meal).setText(":   " + String.valueOf(sidePanel.orderMeals.get(meal)));
            //edit total price
            sidePanel.totalPriceNumber+=meal.getPrice();
            sidePanel.totalPrice.setText(String.valueOf(sidePanel.totalPriceNumber ));
            sidePanel.mealsCntLabels.get(meal).revalidate();
            sidePanel.mealsCntLabels.get(meal).repaint();
            return;
        }
            //add to meals hashmap
        sidePanel.orderMeals.put(meal, sidePanel.orderMeals.getOrDefault(meal, 0) + 1);

        Component smallGap = Box.createRigidArea(new Dimension(0, 10)) ;

        MealPanel mealPanel = createMealPanelTOMenu(meal) ;

        GridBagConstraints gbc = new GridBagConstraints();

        //meal amount
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 10, 10, 0) ;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER ;
        JLabel amount = new JLabel("Amount ");
        amount.setForeground(MainFrame.orange);
        amount.setFont(MainFrame.fontBold.deriveFont(25f));
        mealPanel.infoPanel.add(amount, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST ;
        JLabel mealAmount = new JLabel(":   " + String.valueOf(sidePanel.orderMeals.get(meal)) );
//        System.out.println(sidePanel.orderMeals.get(meal));
        mealAmount.setForeground(MainFrame.orange);
        mealAmount.setFont(MainFrame.fontBold.deriveFont(20f));

        // add label to hashmap
        sidePanel.mealsCntLabels.put(meal, mealAmount);
        mealPanel.infoPanel.add(sidePanel.mealsCntLabels.get(meal), gbc);

        // adding mouse listener to make the panel act like a button 🐰
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        mealPanel.addMouseListener(new MouseAdapter() {
            JDialog deleteDialog;
            @Override
            public void mouseClicked(MouseEvent e) {
                mealPanel.setBorder(new LineBorder(Color.RED, 1));
                if (deleteDialog == null) {
                    deleteDialog = new JDialog(frame, true);

                    deleteDialog.setSize(new Dimension(450, 200));
                    deleteDialog.setLocationRelativeTo(null);
                    // -------------------------------------------------------------------------------Modal ?????!!!!!!
                    deleteDialog.setModal(false);
                    deleteDialog.getContentPane().setBackground(MainFrame.darkGray);
                    deleteDialog.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();

                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.gridwidth = 2;
                    gbc.gridheight = 1;
                    gbc.weightx = 2.0;
                    gbc.weighty = 1.0;
                    gbc.fill = GridBagConstraints.NONE;
                    //---------------------------------------------------------------if the meal cnt > 1 , we can make him choose if he wants to only delete one or the whole thing
                    JLabel confirmation = new JLabel("Do you want to remove this meal from your order ?");
                    confirmation.setFont(MainFrame.fontBold.deriveFont(25f));
                    confirmation.setForeground(MainFrame.orange);
                    deleteDialog.add(confirmation, gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 1;
                    gbc.gridheight = 1;
                    gbc.weightx = 1.0;
                    gbc.weighty = 1.0;
                    JButton cancle = new JButton("cancle");
                    cancle.setPreferredSize(new Dimension(100, 40));
                    cancle.setBackground(MainFrame.lightGray);
                    cancle.setForeground(MainFrame.orange);
                    cancle.setFont(MainFrame.fontBold.deriveFont(25f));
                    cancle.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
                    cancle.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            deleteDialog.dispose();
                            deleteDialog = null;
                        }
                    });
                    deleteDialog.add(cancle, gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    gbc.gridwidth = 1;
                    gbc.gridheight = 1;
                    gbc.weightx = 1.0;
                    gbc.weighty = 1.0;
                    JButton delete = new JButton("Delete");
                    delete.setPreferredSize(new Dimension(100, 40));
                    delete.setBackground(MainFrame.lightGray);
                    delete.setForeground(MainFrame.orange);
                    delete.setFont(MainFrame.fontBold.deriveFont(25f));
                    delete.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
//                            float newTotalPrice = Float.parseFloat(sidePanel.totalPrice.getText()) - meal.getPrice() * sidePanel.orderMeals.get(meal) ;
                            sidePanel.totalPriceNumber -= meal.getPrice() * sidePanel.orderMeals.get(meal);
                            sidePanel.totalPrice.setText(String.valueOf( sidePanel.totalPriceNumber ));

                            // delete from view
                            sidePanel.centerPanel.remove(mealPanel);
                            sidePanel.centerPanel.remove(smallGap);

                            //delete from labels and meals hashmap
                            sidePanel.orderMeals.remove(meal);
                            sidePanel.mealsCntLabels.remove(meal);
                            sidePanel.centerPanel.revalidate();
                            sidePanel.centerPanel.repaint();
                            deleteDialog.dispose();
                            deleteDialog = null;
                        }
                    });
                    deleteDialog.add(delete, gbc);

                    deleteDialog.setVisible(true);
                }
            }
        });

        sidePanel.centerPanel.add(mealPanel) ;
        sidePanel.centerPanel.add(smallGap) ;
        sidePanel.totalPriceNumber += meal.getPrice();
        sidePanel.totalPrice.setText(String.valueOf( sidePanel.totalPriceNumber)) ;
        sidePanel.revalidate();
        sidePanel.repaint();
    }
    public void createEditMealDialog (Meal meal) {
//        currentEditMeal = meal ;

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        editMealDialog = new JDialog(frame, true) ;
        editMealDialog.setSize(new Dimension(400, 550));
        editMealDialog.setLocationRelativeTo(null);
//        editMealDialog.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10 ));
//        editMealDialog.getContentPane().setBackground(MainFrame.darkGray);
        editMealDialog.setLayout(new BorderLayout());

        JPanel editMealPanel = new JPanel() ;
        editMealPanel.setBackground(MainFrame.darkGray);
        editMealPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        editMealPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints() ;

//        //photo ------------------------------------------------later
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 1;
//        gbc.gridheight = 1;
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.0;
//        gbc.insets = new Insets(10, 10, 10, 10) ;
//        gbc.fill = GridBagConstraints.NONE;
//        JPanel mealPhoto = new JPanel() ;
//        mealPhoto.setBackground(MainFrame.darkGray);
//        Image img = Toolkit.getDefaultToolkit().getImage(meal.getImgSrc()).getScaledInstance(editMealDialog.getWidth() - 20, 200, Image.SCALE_SMOOTH) ;
//        JLabel imgLabel = new JLabel(new ImageIcon(img)) ;
//        editMealPanel.add(imgLabel , gbc) ;

        // name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel name = new JLabel("name :");
        name.setForeground(MainFrame.orange);
        name.setFont(MainFrame.fontBold.deriveFont(25f));
        name.setBackground(MainFrame.darkGray);
        editMealPanel.add(name, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 0.1;
        nameEdit = new JTextField(meal.getMealName());
        nameEdit.setFont(MainFrame.fontBold.deriveFont(20f));
        nameEdit.setForeground(MainFrame.orange);
        nameEdit.setBackground(MainFrame.darkGray);
        nameEdit.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
        nameEdit.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameEdit.getText().equals("name")) {
                    nameEdit.setText("");
                    nameEdit.setForeground(MainFrame.orange);
                }
                nameEdit.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (nameEdit.getText().isEmpty()) {
                    nameEdit.setText("name");
                    nameEdit.setForeground(MainFrame.extraLightGray);
                }
                nameEdit.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
            }
        });
        editMealPanel.add(nameEdit, gbc) ;

        //price :
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JLabel price = new JLabel("price : ($)");
        price.setForeground(MainFrame.orange);
        price.setFont(MainFrame.fontBold.deriveFont(25f));
        price.setBackground(MainFrame.darkGray);
        editMealPanel.add(price, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 0.1;
        priceEdit = new JTextField(String.valueOf(meal.getPrice()));
        priceEdit.setFont(MainFrame.fontBold.deriveFont(20f));
        priceEdit.setForeground(MainFrame.orange);
        priceEdit.setBackground(MainFrame.darkGray);
        priceEdit.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
        priceEdit.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (priceEdit.getText().equals("price")) {
                    priceEdit.setText("");
                    priceEdit.setForeground(MainFrame.orange);
                }
                priceEdit.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (priceEdit.getText().isEmpty()) {
                    priceEdit.setText("price");
                    priceEdit.setForeground(MainFrame.extraLightGray);
                }
                priceEdit.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
            }
        });
        priceEdit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                checkInput();
            }
            @Override
            public void keyTyped(KeyEvent e) {
                checkInput();
            }
            public void checkInput() {
                String text = priceEdit.getText();
                if (!text.matches("\\d+(\\.\\d+)?")) {
                    priceEdit.setBorder(new LineBorder(Color.RED, 1));
                    priceEdit.setForeground(Color.RED);
                } else {
                    priceEdit.setBorder(new LineBorder(MainFrame.orange, 1));
                    priceEdit.setForeground(MainFrame.orange);
                }
            }
        });
        editMealPanel.add(priceEdit, gbc) ;

        // ingredients
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JLabel ingredients = new JLabel("ingredients :");
        ingredients.setForeground(MainFrame.orange);
        ingredients.setFont(MainFrame.fontBold.deriveFont(25f));
        ingredients.setBackground(MainFrame.darkGray);
        editMealPanel.add(ingredients, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 0.1;
        ingredientsEdit = new JTextField(meal.getIngredients());
        ingredientsEdit.setFont(MainFrame.fontBold.deriveFont(20f));
        ingredientsEdit.setForeground(MainFrame.orange);
        ingredientsEdit.setBackground(MainFrame.darkGray);
        ingredientsEdit.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
        ingredientsEdit.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ingredientsEdit.getText().equals("ingredients")) {
                    ingredientsEdit.setText("");
                    ingredientsEdit.setForeground(MainFrame.orange);
                }
                ingredientsEdit.setBorder(new LineBorder(MainFrame.orange, 1));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ingredientsEdit.getText().isEmpty()) {
                    ingredientsEdit.setText("ingredients");
                    ingredientsEdit.setForeground(MainFrame.extraLightGray);
                }
                ingredientsEdit.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
            }
        });
        editMealPanel.add(ingredientsEdit, gbc) ;

        // img src
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JLabel imagePath = new JLabel("image path :");
        imagePath.setForeground(MainFrame.orange);
        imagePath.setFont(MainFrame.fontBold.deriveFont(25f));
        imagePath.setBackground(MainFrame.darkGray);
        editMealPanel.add(imagePath, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 0.1;
        imgSrcEdit = new JTextField(meal.getImgSrc());
        imgSrcEdit.setFont(MainFrame.fontBold.deriveFont(20f));
        imgSrcEdit.setForeground(MainFrame.orange);
        imgSrcEdit.setBackground(MainFrame.darkGray);
        imgSrcEdit.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
        imgSrcEdit.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (imgSrcEdit.getText().equals("image path")) {
                    imgSrcEdit.setText("");
                    imgSrcEdit.setForeground(MainFrame.orange);
                }
                imgSrcEdit.setBorder(new LineBorder(MainFrame.orange, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (imgSrcEdit.getText().isEmpty()) {
                    imgSrcEdit.setText("image path");
                    imgSrcEdit.setForeground(MainFrame.extraLightGray);
                }
                imgSrcEdit.setBorder(new LineBorder(MainFrame.extraLightGray, 1));
            }
        });
        editMealPanel.add(imgSrcEdit, gbc) ;

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE ;
        gbc.anchor = GridBagConstraints.WEST ;
        gbc.insets = new Insets(0, 20, 0, 0) ;
        editMeal.setText("edit meal"); ;
        editMeal.setPreferredSize(new Dimension(100, 40));
        editMeal.setBackground(MainFrame.darkGray);
        editMeal.setForeground(MainFrame.orange);
        editMeal.setFont(MainFrame.fontBold.deriveFont(25f));
        editMeal.setBorder(new LineBorder(MainFrame.extraLightGray,2 ));
        editMealPanel.add(editMeal, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST ;
        gbc.insets = new Insets(0, 0, 0, 20) ;
        deleteMeal.setText("Delete meal"); ;
        deleteMeal.setPreferredSize(new Dimension(100, 40));
        deleteMeal.setBackground(MainFrame.darkGray);
        deleteMeal.setForeground(MainFrame.orange);
        deleteMeal.setFont(MainFrame.fontBold.deriveFont(25f));
        deleteMeal.setBorder(new LineBorder(MainFrame.extraLightGray, 2));
        editMealPanel.add(deleteMeal, gbc);

        editMealDialog.add(editMealPanel, BorderLayout.CENTER) ;
        editMealDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                currentEditMeal = null ;
                editMealDialog.removeAll();
                editMealDialog.dispose();
            }
        });
        editMealDialog.setVisible(true);
    }
    public boolean editMealInfoValid(){
        File temp = new File(imgSrcEdit.getText()) ;
        boolean tempIsImg = temp.getPath().endsWith(".jpg") || temp.getPath().endsWith(".jpeg") || temp.getPath().endsWith(".png")
                || temp.getPath().endsWith(".gif") || temp.getPath().endsWith(".bmp") ;
        if (!nameEdit.getText().equals("name") && priceEdit.getText().matches("\\d+(\\.\\d+)?") &&
                !ingredientsEdit.getText().equals("ingredients") && temp.exists() && tempIsImg){
            return true;
        }else{
            if (nameEdit.getText().isEmpty() || nameEdit.getText().equals("name")){
                nameEdit.setBorder(new LineBorder(Color.RED, 1));
            }
            if (priceEdit.getText().isEmpty() || priceEdit.getText().equals("price") || !priceEdit.getText().matches("\\d+(\\.\\d+)?")){
                priceEdit.setBorder(new LineBorder(Color.RED, 1));
            }
            if (ingredientsEdit.getText().isEmpty() || ingredientsEdit.getText().equals("ingredients")){
                ingredientsEdit.setBorder(new LineBorder(Color.RED, 1));
            }
            if( imgSrcEdit.getText().isEmpty() || imgSrcEdit.getText() == null || !temp.exists() || !tempIsImg ) {
                imgSrcEdit.setBorder(new LineBorder(Color.RED, 1));
            }
            return false;
        }

    }

    // for add new meal
    public Meal getNewMealInfo () {
        String path = sidePanel.getImgSrcField().replace("\\", "/");
        Meal meal = new Meal(sidePanel.getNameField(), sidePanel.getIngredientsField(),
                sidePanel.getPriceField(), path);
        System.out.println(path);
        return meal;
    }

    public SidePanel getSidePanel() {
        return sidePanel;
    }
    public ArrayList<MealPanel> getAllMeals () {
        return allMeals ;
    }

    // for edit meal
    // edit info
    public JDialog getEditMealDialog() {
        return editMealDialog;
    }
    public JButton getEditMealButton() {
        return editMeal;
    }
    public Meal getCurrentMeal() {
        return currentEditMeal ;
    }
    public void setCurrentMeal(Meal meal) {
        currentEditMeal = meal;
    }
    public Meal getEditedMealInfo() {
        String path = imgSrcEdit.getText().replace("\\", "/");
        Meal meal = new Meal( nameEdit.getText(), ingredientsEdit.getText(),
                Float.parseFloat(priceEdit.getText()), path) ;
        return meal;
    }
    public MealPanel getMealPanel(Meal meal) {
//        return allMeals.get(meal) ;
        for (MealPanel m: allMeals){
            if(m.getMeal()==meal)
                return m;
        }
        return null;
    }

    public JButton getDeleteMeal() {
        return deleteMeal;
    }
}
