package Control;

import Model.User;
import Model.*;
import View.ProfilePanel;
import View.RegisterPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;


public class ProfileController {
    Users users;
    ProfilePanel profilePanel;
    User wantedUser;
    public boolean inEditMode;
    Report report;

    ProfileController(Users users ,ProfilePanel profilePanel , Report report)
    {
        this.report= report;
        this.users = users;
        this.profilePanel =profilePanel;
        wantedUser= profilePanel.getUser();
        inEditMode =false;
        profilePanel.editProfileButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (inEditMode) {
                    updateUser();

                } else {
                    // Switch to edit panel
                    String passwordDialog = JOptionPane.showInputDialog(profilePanel.fillProfile(wantedUser), "Enter your password:");

                    if(passwordDialog!=null &&passwordDialog.equals(wantedUser.getPassword())){
                        profilePanel.getCardLayout().show(profilePanel.getCardPanel(), "edit");
                        inEditMode = true;
                        profilePanel.editProfileButton.setText("Save");
                    }
                    else
                        JOptionPane.showMessageDialog(profilePanel.mainPanel, "Access Denied", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


    }
     void updateUser(){
        User user = users.findUser(profilePanel.getEditedUsername());
        if(users.findUser(profilePanel.getEditedUsername())!=null&& !profilePanel.getEditedUsername().equals(wantedUser.getUserName())){
            JOptionPane.showMessageDialog(profilePanel.getMainPanel(), "Username already taken!", "Error", JOptionPane.INFORMATION_MESSAGE);
        return;
        }
         if (RegisterPanel.passwordCheck(profilePanel.getEditedPassword())==0)
         {
             JOptionPane.showMessageDialog(profilePanel.getMainPanel(), "not correct password formula!", "Error", JOptionPane.INFORMATION_MESSAGE);
             return;
         }
         if(!RegisterPanel.isValidEmail(profilePanel.getEditedEmail()))
         {
             JOptionPane.showMessageDialog(profilePanel.getMainPanel(), "not correct email formula!", "Error", JOptionPane.INFORMATION_MESSAGE);
             return;
         }

         ArrayList<Order> ordersOfTheUsers;

         if(!wantedUser.getUserName().equals(profilePanel.getEditedUsername())){
             int userType = wantedUser.getUserType();
             ordersOfTheUsers = new ArrayList<>(wantedUser.getOrders().getOrdersForUser(wantedUser));
             wantedUser.getOrders().removeKey(wantedUser);
             report.getOrderingUsers().remove(wantedUser)

 ;
             wantedUser.setUserName(profilePanel.getEditedUsername());
             wantedUser.setEmail(profilePanel.getEditedEmail());
             wantedUser.setPassword(profilePanel.getEditedPassword());
             wantedUser.getOrders().addKey(new User(profilePanel.getEditedUsername(),profilePanel.getEditedEmail(),profilePanel.getEditedPassword(),userType));

             for(Order order : ordersOfTheUsers) {
                 order.setUsername(wantedUser.getUserName());
                 wantedUser.getOrders().addOrderForUser(wantedUser, order);
             }
             report.incrementUserCount(wantedUser,ordersOfTheUsers.size());

            report.writerThread();

         }
         else {
             wantedUser.setEmail(profilePanel.getEditedEmail());
             wantedUser.setPassword(profilePanel.getEditedPassword());
         }



            users.writerThread();

         profilePanel.mainPanel = profilePanel.fillProfile(wantedUser);
         profilePanel.getCardPanel().add(profilePanel.mainPanel, "main");
         profilePanel.getCardLayout().show(profilePanel.getCardPanel(), "main");
         profilePanel.editProfileButton.setText("Edit");
         inEditMode = false;


     }



}
