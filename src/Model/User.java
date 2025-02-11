package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class User {
    private String userName;
    private String email;
    private String password;
    private int userType;
    private String imgSrc;
    Orders orders = Orders.getOrdersSing();
    //private boolean isLoggedIn;

    public int getUserType() {
        return userType;
    }
    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Orders getOrders() {
        return orders;
    }
    public String getImgSrc() {
        return imgSrc;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public User(String userName, String email, String password, int userType) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.imgSrc = "src/profilePics/profilePicture.png";
    }

    public User(String userName, String email, String password, int userType, String imgSrc) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.imgSrc = imgSrc;
    }

    //    public void setUserType(int userType) {
//        this.userType = userType;
//    }

    public String toFileFormat() {
        String userString = userName + "***" + email + "***"+ password + "***" + userType + "***" + imgSrc + "***";

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

            // checking the path for the img
            String imgSrc;
            String path = userParts[4].replace("\\", "/") ;
            File temp = new File(path) ;
            boolean tempIsImg = temp.getPath().endsWith(".jpg") || temp.getPath().endsWith(".jpeg") || temp.getPath().endsWith(".png")
                    || temp.getPath().endsWith(".gif") || temp.getPath().endsWith(".bmp") ;
            if (temp.exists() && tempIsImg && path.contains("src/profilePics")){
                imgSrc = path.substring(path.indexOf("src")) ;
            }else{
                System.out.println("There is a problem within the path of one of the users' images while reading it from the file");
                imgSrc = "src/profilePics/profilePicture.png" ;
            }

            User user = new User(userName,email,password, userType, imgSrc);

            if (userParts.length > 5 && userType==0) {
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

    public boolean saveImgToProject() {
        if(!imgSrc.contains("src/profilePics") ){
            try{
                BufferedImage localImg = ImageIO.read(new File(imgSrc));
                File file = new File("src/profilePics", new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date()) + ".jpg");
                boolean saveImg = ImageIO.write(localImg, "jpg", file) ;
                if(saveImg){
                    System.out.println("img saved successfully :]");
                    imgSrc = file.getPath();
                }else{
                    System.out.println("img not saved for some reason");
                    imgSrc = "src/profilePics/profilePicture.png" ;
                }
                return saveImg;
            }catch (IOException e){
                System.out.println("IO exception in image saving");
                imgSrc = "src/profilePics/profilePicture.png" ;
                return false;
            }
        }
        imgSrc = imgSrc.substring(imgSrc.indexOf("src")) ;
        return true;
    }

}
