package View;

import Model.Meal;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MealPanel extends JPanel {
//    Meal meal ;
    JLabel imgLabel ;
    JLabel mealName ;
    JLabel mealPrice ;
    JTextArea mealIngredients ;
    JPanel infoPanel;
    Meal meal ;
    public MealPanel (Meal meal, int theme) {
        this.meal = meal ;
        setLayout(new BorderLayout());
        setBackground(MainFrame.darkBackground);
        if (theme == 0)
            setBorder(new LineBorder(MainFrame.extraLightColor, 1));
        else
            setBorder(new LineBorder(MainFrame.extraLightColor.brighter(), 1));

        // meal image
        JPanel mealPhoto = new JPanel() ;
        mealPhoto.setBackground(MainFrame.darkBackground);
        Image img = Toolkit.getDefaultToolkit().getImage(meal.getImgSrc()).getScaledInstance(350, 200, Image.SCALE_SMOOTH) ;
        imgLabel = new JLabel(new ImageIcon(img)) ;
        mealPhoto.add(imgLabel) ;

        // meal info panel
        infoPanel = new JPanel(new GridBagLayout()) ;
        infoPanel.setBackground(MainFrame.darkBackground);
        if (theme == 0)
            infoPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MainFrame.extraLightColor));
        else
            infoPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MainFrame.extraLightColor.brighter()));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 0) ;

        //the info :
        // meal name :
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel name = new JLabel("Name");
        name.setForeground(MainFrame.mainColor);
        name.setFont(MainFrame.fontBold.deriveFont(25f));
        infoPanel.add(name, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mealName = new JLabel(":   " + meal.getMealName());
        mealName.setForeground(MainFrame.differentColor);
        mealName.setFont(MainFrame.fontBold.deriveFont(20f));
        infoPanel.add(mealName, gbc);


        // meal price :
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel price = new JLabel("Price");
        price.setForeground(MainFrame.mainColor);
        price.setFont(MainFrame.fontBold.deriveFont(25f));
        infoPanel.add(price, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mealPrice = new JLabel(":   " +String.valueOf(meal.getPrice()) + " $");
        mealPrice.setForeground(MainFrame.differentColor);
        mealPrice.setFont(MainFrame.fontBold.deriveFont(20f));
        infoPanel.add(mealPrice, gbc);


        //meal ingredients :
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH ;
        JLabel ingredients = new JLabel("Ingredients ");
        ingredients.setForeground(MainFrame.mainColor);
        ingredients.setFont(MainFrame.fontBold.deriveFont(25f));
        infoPanel.add(ingredients, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 2.0;
        gbc.weighty = 2.0;
        gbc.fill = GridBagConstraints.BOTH;
        mealIngredients = new JTextArea(":   " +meal.getIngredients());
        mealIngredients.setBackground(MainFrame.darkBackground);
        mealIngredients.setLineWrap(true);
        mealIngredients.setWrapStyleWord(true);
        mealIngredients.setEditable(false);
        mealIngredients.setForeground(MainFrame.differentColor);
        mealIngredients.setFont(MainFrame.fontBold.deriveFont(20f));
        mealIngredients.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane scrollPane = new JScrollPane(mealIngredients);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        infoPanel.add(scrollPane, gbc);

        // adding the photo and info
        add(mealPhoto, BorderLayout.NORTH) ;
        add(infoPanel, BorderLayout.CENTER);
    }

    public Meal getMeal() {
        return meal;
    }

    public JLabel getImgLabel() {
        return imgLabel;
    }

    public JLabel getMealName() {
        return mealName;
    }

    public JLabel getMealPrice() {
        return mealPrice;
    }

    public JTextArea getMealIngredients() {
        return mealIngredients;
    }
    public void setMealInfo(Meal mealEdited) {
        this.meal = mealEdited ;
        Image img = Toolkit.getDefaultToolkit().getImage(mealEdited.getImgSrc()).getScaledInstance(350, 200, Image.SCALE_SMOOTH) ;
        imgLabel.setIcon(new ImageIcon(img)); ;

        this.mealName.setText(mealEdited.getMealName());

        this.mealPrice.setText(":   " +String.valueOf(mealEdited.getPrice()) + " $");

        this.mealIngredients.setText(mealEdited.getIngredients());

        revalidate();
        repaint();

    }
}
