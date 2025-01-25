package Model;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String userName;
    private String email;
    private String password;
    private int userType;
    Orders orders = Orders.getOrdersSing();
    //private boolean isLoggedIn;

    public int getUserType() {
        return userType;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() { return email; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Orders getOrders() {
        return orders;
    }

    public User(String userName, String email, String password, int userType) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }



//    public void setUserType(int userType) {
//        this.userType = userType;
//    }

    public String toFileFormat() {
        String userString = userName + "***" + email + "***"+ password + "***" + userType + "***";

        if(userType==0){
        for (Order order : orders.getOrdersForUser(this)) {
            userString += order.toFileFormat() + "---";
        }

}
        return userString;
    }


    public static User fromFileFormat(String str) {
        try {
            String[] userParts = str.split("\\*\\*\\*");
            String userName = userParts[0];
            String email = userParts[1];
            String password = userParts[2];
            int userType= Integer.parseInt(userParts[3]);


            User user = new User(userName,email,password, userType);

            if (userParts.length > 4 && userType==0) {
                String[] orderStrings = userParts[4].split("---");
                for (String orderStr : orderStrings) {
                    Order order = Order.fromFileFormat(orderStr);
                    if (order != null) {
                        user.orders.addOrderForUser(user,order);
                    }
                }
            }
            return user;
        }
        catch (Exception e) {
            System.out.println("There is a problem within the format of one of the users while reading it from the file");
            return null;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userType == user.userType &&
                Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

}
