package Model;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Meals {
    ArrayList<Meal> meals = new ArrayList<>();
   // private static Object object = new Object();
    public ArrayList<Meal> getMeals(){
        return meals;
    }
    public synchronized void addMeal(Meal meal) {
        meals.add(meal);
    }

//    public synchronized boolean deleteMeal(String mealName) {
//        return meals.removeIf(meal -> meal.mealName.equals(mealName));
//    }
    public synchronized  boolean deleteMeal(Meal meal) {
        return meals.removeIf(meal :: equals);
    }


//    public synchronized boolean modifyMeal(String mealName, Meal updatedMeal) {
//        for (int i = 0; i < meals.size(); i++) {
//            if (meals.get(i).mealName.equals(mealName)) {
//                meals.set(i, updatedMeal);
//                return true;
//            }
//        }
//        return false;
//    }

    public synchronized boolean modifyMeal(Meal oldMeal, Meal updatedMeal) {
        for (int i = 0; i < meals.size(); i++){
            if (meals.get(i) == oldMeal) {
                meals.set(i, updatedMeal);
                return true;
            }
        }
        return false;
    }

    public  void writerThread(){
        Thread thread = new Thread(() -> saveToFile());
        thread.start();

    }
    public  void readerThread(){
        Thread thread = new Thread(() -> loadFromFile());
        thread.start();


    }

    public  void loadFromFile() {
        synchronized (meals){
        try (BufferedReader br = new BufferedReader(new FileReader("MealsFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Meal meal = Meal.fromFileFormat(line);
                if (meal != null) {
                    meals.add(meal);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Something went wrong  while reading from meals file ","Error",JOptionPane.ERROR_MESSAGE);
        }
        }
    }
    public void saveToFile() {
        synchronized (meals) {

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("MealsFile.txt"))) {
                for (Meal meal : meals) {
                    bw.write(meal.toFileFormat());
                    bw.newLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Something went wrong while writing to meals file ","Error",JOptionPane.ERROR_MESSAGE);

            }
        }
    }
}
