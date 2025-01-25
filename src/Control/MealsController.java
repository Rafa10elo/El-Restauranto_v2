package Control;

import Model.Meal;
import Model.Meals;
import Model.User;
import View.MealPanel;
import View.MealsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MealsController {
    Meals meals;
    MealsPanel mealsPanel;
    User user ;
    public MealsController(Meals meals, MealsPanel mealsPanel, User user){
        this.meals =meals;
        this.mealsPanel = mealsPanel;
        this.user = user;

        if( user.getUserType() == 0){
            mealsPanel.fillMainMenu(meals.getMeals());
            // add click action listener to meal panels in main menu : click = add to order
            for (MealPanel m : mealsPanel.getAllMeals()) {
                m.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mealsPanel.addMealPanelToOrder(m.getMeal());
                    }
                });
            }
        }
        else{
            // ADD MEAL BUTTON
            repaintMainMenu();
            ActionListener addMealListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (mealsPanel.getSidePanel().mealInfoValid()){
                        Meal newMeal = mealsPanel.getNewMealInfo() ;

                        //saving the image to the pics file
                        boolean saved = newMeal.saveImgToProject();
                        if(!saved){
                            JOptionPane.showMessageDialog(mealsPanel, "Something happened while saving the image to the project :(\nthe image will be set to the default one, try editing it later", "", JOptionPane.ERROR_MESSAGE);
                        }

                        // add to model + write
                        meals.addMeal(newMeal);
                        meals.writerThread();
                        // edit view + add it to hashmap
                        mealsPanel.getSidePanel().newMealReset();
                        repaintMainMenu();
                        JOptionPane.showMessageDialog(mealsPanel, "Meal added successfully! :)", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                }};
            mealsPanel.getSidePanel().getAddMealButton().addActionListener(addMealListener);

            // EDIT MEAL
            ActionListener editMealListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (mealsPanel.editMealInfoValid()){
                        Meal editedMeal = mealsPanel.getEditedMealInfo() ;
                        //saving the image to the pics file
                        boolean saved = editedMeal.saveImgToProject();
                        if(!saved){
                            JOptionPane.showMessageDialog(mealsPanel, "Something happened while saving the new image to the project :(", "", JOptionPane.ERROR_MESSAGE);
                            editedMeal.setImgSrc(mealsPanel.getCurrentMeal().getImgSrc());
                        }

                        // edit view
                        mealsPanel.getMealPanel(mealsPanel.getCurrentMeal()).setMealInfo(editedMeal);
//                        mealsPanel.editMeals(mealsPanel.getCurrentMeal(), editedMeal) ;
                        // edit model
                        meals.modifyMeal(mealsPanel.getCurrentMeal(), editedMeal);
                        meals.writerThread();

                        mealsPanel.setCurrentMeal(null);

                        mealsPanel.getEditMealDialog().removeAll();
                        mealsPanel.getEditMealDialog().dispose();
                        repaintMainMenu();

                        JOptionPane.showMessageDialog(mealsPanel, "Meal edited successfully!", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            };
            mealsPanel.getEditMealButton().addActionListener(editMealListener);

            // DELETE MEAL
            ActionListener deleteMealListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // edit model
                    meals.deleteMeal(mealsPanel.getCurrentMeal()) ;
                    meals.writerThread();
                    // edit view
                    mealsPanel.getAllMeals().remove(mealsPanel.getCurrentMeal()) ;
                    mealsPanel.setCurrentMeal(null);

                    mealsPanel.getEditMealDialog().removeAll();
                    mealsPanel.getEditMealDialog().dispose();

                    repaintMainMenu();
                    JOptionPane.showMessageDialog(mealsPanel, "Meal deleted successfully!", "", JOptionPane.INFORMATION_MESSAGE);
                }
            };
            mealsPanel.getDeleteMeal().addActionListener(deleteMealListener);
        }

    }
    void repaintMainMenu (){
        mealsPanel.fillMainMenu(meals.getMeals());
        for (MealPanel m: mealsPanel.getAllMeals()) {
            m.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mealsPanel.setCurrentMeal(m.getMeal());
                    mealsPanel.createEditMealDialog(m.getMeal());
                }
            });
        }
    }
}
