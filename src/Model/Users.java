package Model;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Users {
  //  public static Object object= new Object();
    ArrayList<User> users = new ArrayList<>();


    public ArrayList<User> getUsers() {
        return users;
    }

    public synchronized void addUser(User user) {
        users.add(user);
    }

    public synchronized boolean deleteUser(String username) {
        return users.removeIf(user -> user.getUserName().equals(username));
    }

    public  boolean modifyUser(String username, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(username)) {
                users.set(i, updatedUser);
                return true;
            }
        }
        return false;
    }

    public User findUser(String username){
        for (User user : users)
            if(username.equals(user.getUserName()))
             return user;
        return null;
    }
   public boolean isCorrectPassword(User user , String password){
            if(user.getPassword().equals(password))
                return true;
            return false;
   }


    public  void writerThread(){
        Thread thread = new Thread(() -> saveToFile());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public  void readerThread(){
        Thread thread = new Thread(() -> loadFromFile());
        thread.start();

    }

    public void  loadFromFile() {
        synchronized (users) {
            try (BufferedReader br = new BufferedReader(new FileReader("Users.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    User user = User.fromFileFormat(line);
                    if (user != null) {
                        users.add(user);
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Something went wrong  while reading from users file ","Error",JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public synchronized void saveToFile() {
        synchronized (users) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Users.txt"))) {
                for (User user : users) {
                    bw.write(user.toFileFormat());
                    bw.newLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Something went wrong  while writing to users file ","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
