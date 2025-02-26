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
    //themes
    public static enum MainColor {ORANGE, GREEN, RED}
    public static enum BgColor {DARK, LIGHT}
    MainColor mc;
    BgColor bg;

    // getters
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
    public int getMc() {
        return mc.ordinal();
    }
    public int getBg() {
        return bg.ordinal();
    }

    //setters
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
    public void setMc(int mc) {
        this.mc = MainColor.values()[mc];
    }
    public void setBg(int bg) {
        this.bg = BgColor.values()[bg];
    }

    public User(String userName, String email, String password, int userType) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.imgSrc = "DEFAULT";
        this.mc = MainColor.ORANGE;
        this.bg = BgColor.DARK;
    }

    public User(String userName, String email, String password, int userType, MainColor mc, BgColor bg, String imgSrc) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.mc = mc;
        this.bg = bg;
        this.imgSrc = imgSrc;
    }

    //    public void setUserType(int userType) {
//        this.userType = userType;
//    }

    public String toFileFormat() {
        String userString = userName + "***" + email + "***"+ password + "***" + userType + "***" + mc.name() + "***" + bg.name() + "***" + imgSrc + "***" ;

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

            MainColor mc = MainColor.valueOf(userParts[4]);
            BgColor bg = BgColor.valueOf(userParts[5]);

            // checking the path for the img
            String imgSrc;
            String path = userParts[6].replace("\\", "/") ;
            if(path.equals("DEFAULT"))
                imgSrc = path;
            else {
                File temp = new File(path) ;
                boolean tempIsImg = temp.getPath().endsWith(".jpg") || temp.getPath().endsWith(".jpeg") || temp.getPath().endsWith(".png")
                        || temp.getPath().endsWith(".gif") || temp.getPath().endsWith(".bmp") ;
                if (temp.exists() && tempIsImg && path.contains("src/profilePics"))
                    imgSrc = path.substring(path.indexOf("src")) ;
                else{
                    System.out.println("There is a problem within the path of one of the users' images while reading it from the file");
                    imgSrc = "DEFAULT" ;
                }
            }

            User user = new User(userName,email,password, userType, mc, bg, imgSrc);

            if (userParts.length > 7 && userType==0) {
                String[] orderStrings = userParts[7].split("---");
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
        if(imgSrc.equals("DEFAULT"))
            return true;
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
                    imgSrc = "DEFAULT" ;
                }
                return saveImg;
            }catch (IOException e){
                System.out.println("IO exception in image saving");
                imgSrc = "DEFAULT" ;
                return false;
            }
        }
        imgSrc = imgSrc.substring(imgSrc.indexOf("src")) ;
        return true;
    }

}
